package io.github.some_example_name;

import com.badlogic.gdx.Gdx;

import java.util.List;

public class Mouseclick {

    int sizex;
    int sizey;
    Tile[][] map;


    public Mouseclick(int sizex, int sizey, Tile[][] map) {
        this.sizex = sizex;
        this.sizey = sizey;
        this.map = map;
    }

    public int getscreen_x() {
        return(Gdx.input.getX());
    }
    public int getscreen_y() {
        return(Gdx.graphics.getHeight() - Gdx.input.getY());
    }

    public Tile getTile() {
        int mousex = Gdx.input.getX();
        int mousey = Gdx.graphics.getHeight() - Gdx.input.getY();

        float tilewidth = (float) Gdx.graphics.getWidth() / sizex;
        float tileheight = (float) Gdx.graphics.getHeight() / sizey;

        int tilex = (int) (mousex / tilewidth);
        int tiley = (int) (mousey / tileheight);


        if(tilex < 0 || tilex >= sizex || tiley < 0 || tiley >= sizey) {
            return map[0][0];
        }

        return map[tilex][tiley];
    }

    public Monster getMonster(List<Monster> monsters) {

        Tile mouseTile = getTile();

        if (mouseTile == null) {
            return null;
        }

        for (Monster m : monsters) {

            if (m.hp <= 0) {
                continue;
            }

            if (m.current == mouseTile) {
                return m;
            }
        }

        return null;

    }

}
