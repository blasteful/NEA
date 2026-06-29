package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Menu {

    public void Menu_Open(ShapeRenderer sr) {

        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rect((float) Gdx.graphics.getWidth() / 2 - 200, (float) Gdx.graphics.getHeight() / 2, 200, 100);
        sr.rect((float) Gdx.graphics.getWidth() / 2 + 100, (float) Gdx.graphics.getHeight() / 2, 200, 100);
        sr.rect((float) Gdx.graphics.getWidth() / 2 + 200, (float) Gdx.graphics.getHeight() / 2, 200, 100);
        sr.end();

    }

}
