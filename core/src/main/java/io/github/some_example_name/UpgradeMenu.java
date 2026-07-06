package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UpgradeMenu {

    private Texture bg = new Texture("buttons/UpgradeMenu/UpgradeMenuBg.png");
    private Texture back = new Texture("buttons/back.png");
    private Texture researchbuy = new Texture("buttons/UpgradeMenu/ResearchAccept.png");


    private int backX;
    private int backY;
    private int buyX;
    private int buyY;
    private int buttonW = 100;
    private int buttonH = 100;

    String text;
    int cost;
    private BitmapFont writing;

    public UpgradeMenu() {
        // Calculate positions based on center
        backX = (int) ((Gdx.graphics.getWidth() / 2) + 225);
        backY = (int) ((Gdx.graphics.getHeight() / 2) - 180);
        buyX = (int) ((Gdx.graphics.getWidth() / 2) - 300);
        buyY = (int) ((Gdx.graphics.getHeight() / 2) - 180);
    }

    public void render(SpriteBatch batch, int cost, String text) {
        writing = new BitmapFont();
        writing.getData().setScale(2);
        batch.draw(bg, (float) (Gdx.graphics.getWidth() / 2) - 335, (float) (Gdx.graphics.getHeight() / 2) - 200, 700, 450);
        batch.draw(back, backX, backY, buttonW, buttonH);
        batch.draw(researchbuy, buyX, buyY, 300, 100);
        writing.draw(batch, text, (float) (Gdx.graphics.getWidth() / 2) - 276, (float) (Gdx.graphics.getHeight() / 2) + 210);
        writing.draw(batch, "Research Cost: "+ cost, (float) (Gdx.graphics.getWidth() / 2) - 276, (float) (Gdx.graphics.getHeight() / 2) - 50);
    }

    public boolean backClicked(int mousex, int mousey) {
        return mousex >= backX && mousex <= backX + buttonW && mousey >= backY && mousey <= backY + buttonH;
    }

    public boolean buyClicked(int mousex, int mousey) {
        return mousex >= buyX && mousex <= buyX + 300 && mousey >= buyY && mousey <= buyY + 100;
    }
}
