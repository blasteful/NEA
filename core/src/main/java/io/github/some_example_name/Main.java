package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;

    private Renderer renderer;
    private Map map;
    private  ShapeRenderer sr;
    private Mouseclick ms;

    int sizex = 32;
    int sizey = 24;

    @Override
    public void create() {
        renderer = new Renderer();
        map = new Map(sizex,sizey);
        map.pathfind();
        sr = new ShapeRenderer();
        ms = new Mouseclick(sizex, sizey, map.getMap());


    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);



        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Tile t = ms.getTile();
            if(t == null) return;
            if(t.type == Tile.Type.ENTRANCE || t.type == Tile.Type.EXIT) {
                return;
            }
            Tile.Type originalType = t.originalType;
            t.setType(Tile.Type.DISTRACTION);
            t.originalType = Tile.Type.DISTRACTION;

            if(!map.pathfind()) {
                t.type = originalType;
                t.originalType  = originalType;
                map.pathfind();
                System.out.println("would block path");
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
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


        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            Tile t = ms.getTile();

            System.out.println(t.type + " Tile" );
            System.out.println("Magnetism: -" + t.getPathingcost());
            System.out.println("Walk damage: " + t.getWalkingDamage());
            System.out.println("Walkable: " + t.type.walkable );
            System.out.println("Previously a " + t.previous+ " Tile" );
            System.out.println();

        }


        sr.begin(ShapeRenderer.ShapeType.Filled);
        renderer.renderMap(sr, map.getMap());
        sr.end();




    }

    @Override
    public void dispose() {
        sr.dispose();
    }
}
