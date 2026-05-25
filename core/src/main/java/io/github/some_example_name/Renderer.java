package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Renderer {



    public void renderMap(ShapeRenderer sr, Tile[][] map) {

        float tilewidth = (float) Gdx.graphics.getWidth() / map.length;
        float tileheight = (float) Gdx.graphics.getHeight() / map[0].length;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {

                Tile t = map[i][j];

                if(t.type == Tile.Type.GRASS) {
                    sr.setColor(new Color(Color.GRAY));
                }
                if(t.type == Tile.Type.WATER) {
                    sr.setColor(42/255f, 116/255f, 168/255f, 1f);
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


}
