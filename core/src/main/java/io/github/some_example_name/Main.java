package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private BitmapFont font;

    private Renderer renderer;
    private Map map;
    private  ShapeRenderer sr;
    private Mouseclick ms;
    private Monster mon;
    private WaveDirector wd;

    List<Monster> Monsters = new ArrayList<>();
    List<Monster> toSpawn = new ArrayList<>();


    private float frametimer = 0f;
    private float intervals = 0.3f;

    int sizex = 64;
    int sizey = 48;
    int mode;

    public enum Phase {
        BUILD,
        FIGHT,
        Nil,
    }

    Phase phase = Phase.BUILD;

    int wave;



    @Override
    public void create() {

        batch = new SpriteBatch();
        font = new BitmapFont();
        wd = new WaveDirector();
        font.setColor(Color.WHITE);
        font.getData().setScale(2f);



        renderer = new Renderer();
        map = new Map(sizex,sizey);
        map.pathfind();
        sr = new ShapeRenderer();
        ms = new Mouseclick(sizex, sizey, map.getMap());



        int basehp = 100;
        wave = 1;
        mode = 1;

    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        frametimer += Gdx.graphics.getDeltaTime();
        List<Monster> toRemove = new ArrayList<>();
        List<Monster> toRemoveFromSpawn = new ArrayList<>();

        for (Monster m : Monsters) {
            boolean end = m.Move(map);
            if(end) {
                toRemove.add(m);
            }
        }

        for (Monster m : toSpawn) {
            Monsters.add(m);
            toRemoveFromSpawn.add(m);
        }

        if(frametimer >= intervals) {
            frametimer = 0f;
            for (Monster m : Monsters) {
                if (m.frame == 1) {
                    m.setFrame(2);
                } else {
                    m.setFrame(1);
                }
            }
        }

        Monsters.removeAll(toRemove);


        if(Gdx.input.isKeyJustPressed(Input.Keys.Z) && phase == Phase.BUILD) {
            if(mode == 2) {
                mode = 1;
            } else {
                mode = 2;
            }
        }

        System.out.println(Monsters);

        if(Monsters.isEmpty() && phase == Phase.FIGHT) {
            phase = Phase.BUILD;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && phase == Phase.BUILD) {
            phase = Phase.FIGHT;
            toSpawn = wd.createWave(wave, map);
        }



        toSpawn.removeAll(toRemoveFromSpawn);



        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && mode == 1) {
            Tile t = ms.getTile();
            if(t == null) return;
            if(t.type == Tile.Type.ENTRANCE || t.type == Tile.Type.EXIT) {
                return;
            }
            Tile.Type originalType = t.originalType;
            t.setType(Tile.Type.ROCK);
            t.originalType = Tile.Type.ROCK;

            if(!map.pathfind()) {
                t.type = originalType;
                t.originalType  = originalType;
                map.pathfind();
                System.out.println("would block path");
            }
        }

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && mode == 2 && ms.getTile().type != Tile.Type.ROCK) {
            Tile t = ms.getTile();
            if(t == null) return;
            if(t.type == Tile.Type.ENTRANCE || t.type == Tile.Type.EXIT) {
                return;
            }
            Tile.Type originalType = t.originalType;
            t.setType(Tile.Type.DEEPWATER);
            t.originalType = Tile.Type.DEEPWATER;

            if(!map.pathfind()) {
                t.type = originalType;
                t.originalType  = originalType;
                map.pathfind();
                System.out.println("would block path");
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.M)) {
            Tile t = ms.getTile();
            if(t == null) return;
            if(t.type == Tile.Type.ENTRANCE || t.type == Tile.Type.EXIT) {
                return;
            }
            Tile.Type originalType = t.originalType;
            t.setType(Tile.Type.DIRT);
            t.originalType = Tile.Type.DIRT;

            if(!map.pathfind()) {
                t.type = originalType;
                t.originalType  = originalType;
                map.pathfind();
                System.out.println("would block path");
            }
        }





        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            Tile t = ms.getTile();

            System.out.println(t.type + " Tile" );
            System.out.println("Magnetism: -" + t.getPathingcost());
            System.out.println("Walk damage: " + t.getWalkingDamage());
            System.out.println("Walkable: " + t.type.walkable );
            System.out.println("Previously a " + t.previous+ " Tile" );
            System.out.println("Parent: " + t.parent);
            System.out.println("Child: " + t.child);
            System.out.println();

        }


        sr.begin(ShapeRenderer.ShapeType.Filled);
        if(mode==1) {
            renderer.renderMap(sr, map.getMap());
            renderer.renderMonsters(Monsters, sr, map);

        } if (mode==2) {
            renderer.renderBMap(sr, map.getMap(), ms.getTile());

        }
        sr.end();

        batch.begin();
        font.draw(batch, "PHASE: " + phase, ((float) Gdx.graphics.getWidth() / 2 - 100), 950); // (x, y) position
        batch.end();




    }

    @Override
    public void dispose() {
        sr.dispose();
    }
}
