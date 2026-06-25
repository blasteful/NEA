package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;

    private Renderer renderer;
    private Map map;
    private  ShapeRenderer sr;
    private Mouseclick ms;
    private Monster mon;
    List<Monster> Monsters = new ArrayList<>();

    private float frametimer = 0f;
    private float intervals = 0.3f;

    int sizex = 64;
    int sizey = 48;
    int mode;


    @Override
    public void create() {
        renderer = new Renderer();
        map = new Map(sizex,sizey);
        map.pathfind();
        sr = new ShapeRenderer();
        ms = new Mouseclick(sizex, sizey, map.getMap());

        int basehp = 100;

        for (int i = 0; i < 4; i++) {
            Monsters.add(new Monster(MonsterData.Genre.Ground, MonsterData.Tier.IV, map));
            Monsters.add(new Monster(MonsterData.Genre.Ground, MonsterData.Tier.I, map));
        }




        mode = 1;

    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        frametimer += Gdx.graphics.getDeltaTime();
        List<Monster> toRemove = new ArrayList<>();


        for (Monster m : Monsters) {
            boolean end = m.Move(map);
            if(end) {
                toRemove.add(m);
            }
        }


        if(frametimer >= intervals) {
            frametimer = 0f;
            Monsters.add(new Monster(MonsterData.Genre.Ground, MonsterData.Tier.I, map));

            for (Monster m : Monsters) {
                if (m.frame == 1) {
                    m.setFrame(2);
                } else {
                    m.setFrame(1);
                }
            }
        }

        Monsters.removeAll(toRemove);




        if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            if(mode==1) {
                mode = 2;
            }else {
                mode = 1;

            }
        }

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
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


        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {


            Monsters.add(new Monster(MonsterData.Genre.Ground, MonsterData.Tier.I, map));
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
            renderer.renderBMap(sr, map.getMap());

        }
        sr.end();





    }

    @Override
    public void dispose() {
        sr.dispose();
    }
}
