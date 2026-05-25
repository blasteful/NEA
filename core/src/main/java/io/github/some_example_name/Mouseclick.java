package io.github.some_example_name;

import com.badlogic.gdx.Gdx;

public class Mouseclick {

    int sizex;
    int sizey;
    Tile[][] map;

    public Mouseclick(int sizex, int sizey, Tile[][] map) {
        this.sizex = sizex;
        this.sizey = sizey;
        this.map = map;
    }

    public Tile getTile() {
        int mousex = Gdx.input.getX();
        int mousey = Gdx.graphics.getHeight() - Gdx.input.getY();

        float tilewidth = (float) Gdx.graphics.getWidth() / sizex;
        float tileheight = (float) Gdx.graphics.getHeight() / sizey;

        int tilex = (int) (mousex / tilewidth);
        int tiley = (int) (mousey / tileheight);


        if(tilex < 0 || tilex >= sizex || tiley < 0 || tiley >= sizey) {
            return null;
        }

        return map[tilex][tiley];
    }

}
