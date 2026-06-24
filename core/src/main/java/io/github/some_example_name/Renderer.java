package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.List;

public class Renderer {

    public void renderBMap(ShapeRenderer sr, Tile[][] map) {

        float tilewidth = (float) Gdx.graphics.getWidth() / map.length;
        float tileheight = (float) Gdx.graphics.getHeight() / map[0].length;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {

                Tile t = map[i][j];


                if(t.type == Tile.Type.THORNS) {
                    sr.setColor(106/255f, 238/255f, 255/255f, 1f);
                }

                if(t.type == Tile.Type.GRASS) {
                    sr.setColor(106/255f, 238/255f, 255/255f, 1f);
                }
                if(t.type == Tile.Type.WATER) {
                    sr.setColor(106/255f, 238/255f, 255/255f, 1f);
                }
                if(t.type == Tile.Type.DEEPWATER) {
                    sr.setColor(242/255f, 27/255f, 27/255f, 1f);
                }
                if(t.type == Tile.Type.SAND) {
                    sr.setColor(106/255f, 238/255f, 255/255f, 1f);
                }
                if(t.type == Tile.Type.ROCK) {
                    sr.setColor(242/255f, 27/255f, 27/255f, 1f);
                }
                if(t.type == Tile.Type.BASALT) {
                    sr.setColor(106/255f, 238/255f, 255/255f, 1f);
                }
                if(t.type == Tile.Type.ENTRANCE) {
                    sr.setColor(new Color(Color.GREEN));
                }
                if(t.type == Tile.Type.EXIT) {
                    sr.setColor(106/255f, 238/255f, 255/255f, 1f);
                }
                if(t.type == Tile.Type.DISTRACTION) {
                    sr.setColor(106/255f, 238/255f, 255/255f, 1f);
                }
                if(t.type == Tile.Type.MUD) {
                    sr.setColor(106/255f, 238/255f, 255/255f, 1f);
                }
                if(t.type == Tile.Type.DIRT) {
                    sr.setColor(106/255f, 238/255f, 255/255f, 1f);
                }
                if(t.type == Tile.Type.PATH) {
                    sr.setColor(27/255f, 108/255f, 242/255f, 1f);
                }




                sr.rect(i * tilewidth,j * tileheight, tilewidth, tileheight);

            }

        }

        sr.end();

        sr.begin(ShapeRenderer.ShapeType.Line);

        sr.setColor(Color.BLACK);

        for (int i = 0; i <= map.length; i++) {
            sr.line(
                i * tilewidth, 0,
                i * tilewidth, Gdx.graphics.getHeight()
            );
        }

        for (int j = 0; j <= map[0].length; j++) {
            sr.line(
                0, j * tileheight,
                Gdx.graphics.getWidth(), j * tileheight
            );
        }
    }

    public void renderMap(ShapeRenderer sr, Tile[][] map) {

        float tilewidth = (float) Gdx.graphics.getWidth() / map.length;
        float tileheight = (float) Gdx.graphics.getHeight() / map[0].length;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {

                Tile t = map[i][j];

                if(t.type == Tile.Type.THORNS) {
                    sr.setColor(76/255f, 99/255f, 64/255f, 1f);
                }

                if(t.type == Tile.Type.GRASS) {
                    sr.setColor(new Color(Color.GRAY));
                }
                if(t.type == Tile.Type.WATER) {
                    sr.setColor(42/255f, 116/255f, 168/255f, 1f);
                }
                if(t.type == Tile.Type.DEEPWATER) {
                    sr.setColor(55/255f, 81/255f, 125/255f, 1f);
                }
                if(t.type == Tile.Type.SAND) {
                    sr.setColor(181/255f, 159/255f, 98/255f, 1f);
                }
                if(t.type == Tile.Type.ROCK) {
                    sr.setColor(new Color(Color.DARK_GRAY));
                }
                if(t.type == Tile.Type.BASALT) {
                    sr.setColor(101/255f, 112/255f, 109/255f, 1f);
                }
                if(t.type == Tile.Type.ENTRANCE) {
                    sr.setColor(new Color(Color.GREEN));
                }
                if(t.type == Tile.Type.EXIT) {
                    sr.setColor(new Color(Color.RED));
                }
                if(t.type == Tile.Type.DISTRACTION) {
                    sr.setColor(new Color(Color.RED));
                }
                if(t.type == Tile.Type.MUD) {
                    sr.setColor(new Color(Color.BROWN));
                }
                if(t.type == Tile.Type.DIRT) {
                    sr.setColor(41/255f, 79/255f, 47/255f, 1f);
                }
                if(t.type == Tile.Type.PATH) {
                    sr.setColor(107/255f, 92/255f, 70/255f, 1f);
                }




                sr.rect(i * tilewidth,j * tileheight, tilewidth, tileheight);

            }

        }

        sr.end();

        sr.begin(ShapeRenderer.ShapeType.Line);

        sr.setColor(Color.BLACK);

        for (int i = 0; i <= map.length; i++) {
            sr.line(
                i * tilewidth, 0,
                i * tilewidth, Gdx.graphics.getHeight()
            );
        }

        for (int j = 0; j <= map[0].length; j++) {
            sr.line(
                0, j * tileheight,
                Gdx.graphics.getWidth(), j * tileheight
            );
        }
    }

    public void renderMonsters(List<Monster> mon, ShapeRenderer sr, Map map) {





        sr.setColor(new Color(Color.RED));
        sr.end();
        sr.begin(ShapeRenderer.ShapeType.Filled);

        for(Monster m : mon) {

            sr.circle(m.x * ((float) Gdx.graphics.getWidth() / map.sizex) + 9,m.y * ((float) Gdx.graphics.getHeight() / map.sizey) + 9,5);
        }

        sr.end();





    }


}
