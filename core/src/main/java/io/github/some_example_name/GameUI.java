package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class GameUI {

    private BitmapFont font;
    private Texture bg = new Texture("GameUI/background.png");
    private Texture coin = new Texture("GameUI/coin.png");
    private Texture wave = new Texture("GameUI/wave.png");
    private Texture heart = new Texture("GameUI/heart.png");
    private Texture rainy = new Texture("GameUI/rainy.png");
    private Texture sunny = new Texture("GameUI/sunny.png");
    private Texture snow = new Texture("GameUI/snowman.png");
    private Texture avalanche = new Texture("GameUI/avalanche.png");
    private Texture bankholiday = new Texture("GameUI/bankholiday.png");
    private Texture startwave1 = new Texture("GameUI/startwave1.png");
    private Texture startwave2 = new Texture("GameUI/startwave2.png");
    private Texture battling1 = new Texture("GameUI/battling1.png");
    private Texture battling2 = new Texture("GameUI/battling2.png");
    private Texture tab1 = new Texture("GameUI/tab1.png");
    private Texture tab2 = new Texture("GameUI/tab2.png");
    private Texture btab1 = new Texture("GameUI/btab1.png");

    private Texture turret = new Texture("Towers/turret1.png");
    private Texture spire = new Texture("Towers/spire1.png");
    private Texture detonator = new Texture("Towers/detonator1.png");
    private Texture barricade = new Texture("Tiles/barricade.png");


    float interval = 0.3f;
    float interval2 = 0.4f;
    float timer = 0f;
    float timer2 = 0f;
    boolean cycle = false;
    boolean cycle2 = false;

    public void draw(SpriteBatch batch, int cash, int wavenum, int hp, Weather weather, Main.Phase phase, int mode, TowerData.Tower selected, Tile t, Monster m) {

        timer += Gdx.graphics.getDeltaTime();
        timer2 += Gdx.graphics.getDeltaTime();

        if (timer >= interval) {
            cycle = !cycle;
            timer = 0f;
        }
        if (timer2 >= interval2) {
            cycle2 = !cycle2;
            timer2 = 0f;
        }

        font = new BitmapFont();
        batch.begin();
        batch.draw(bg, 280, 0);
        batch.draw(coin, 300, 90);
        batch.draw(wave, 295, 20);
        batch.draw(heart, 430, 90);
        if(weather.current_event == Weather.Weather_events.Sunny) {
            batch.draw(sunny, 430, 20);
        }
        if(weather.current_event == Weather.Weather_events.Snow) {
            batch.draw(snow, 430, 20);
        }
        if(weather.current_event == Weather.Weather_events.Avalanche) {
            batch.draw(avalanche, 430, 20);
        }
        if(weather.current_event == Weather.Weather_events.BankHoliday) {
            batch.draw(bankholiday, 430, 20);
        }
        if(weather.current_event != null) {
            font.draw(batch, "" + weather.current_event, 490, 50);
        }

        if(selected == TowerData.Tower.Turret && mode == 2) {
            batch.draw(turret, 700, 20);
        }
        if(selected == TowerData.Tower.Spire && mode == 2) {
            batch.draw(spire, 710, 30);
        }
        if(selected == TowerData.Tower.Detonator && mode == 2) {
            batch.draw(detonator, 700, 20);
        }
        if(selected == TowerData.Tower.Barricade && mode == 2) {
            batch.draw(barricade, 700, 20);
        }

        if(phase == Main.Phase.BUILD) {
            if(cycle2) {
                batch.draw(tab1, 1045, 85);
            } else {
                batch.draw(tab2, 1045, 85);
            }
        }
        if(phase == Main.Phase.BUILD && mode == 2) {
            if(cycle2) {
                batch.draw(btab1, 1045, 85);
            } else {
                batch.draw(btab1, 1045, 85);
            }
        }

        if(cycle) {
            if(phase == Main.Phase.FIGHT) {
                batch.draw(battling1, 1000, 20);
            } else {
                batch.draw(startwave1, 1000, 20);
            }
        } else {
            if(phase == Main.Phase.FIGHT) {
                batch.draw(battling2, 1000, 20);
            } else {
                batch.draw(startwave2, 1000, 20);
            }
        }


        font.draw(batch, "" + hp, 490, 120);
        font.draw(batch, "" + wavenum, 355, 50);
        font.draw(batch, "" + cash, 360, 120);
        font.draw(batch, "" + wavenum, 355, 50);


        if(t.type == Tile.Type.PLACED_TOWER) {
            font.draw(batch, "" + t.tower.tower_type + " Tile", 550, 140);
        } else {
            font.draw(batch, "" + t.type + " Tile", 550, 140);
            font.draw(batch, "Walkable: "  + t.getwalkable(), 550, 105);
            font.draw(batch, "Difficulty: "  + t.getPathingcost() + "/100", 550, 85);
        }
        if(t.type == Tile.Type.PATH) {
            font.draw(batch, "Previously a "  + t.previous + " Tile", 550, 45);
        }

        if(m != null) {

            Texture monsterTexture = getMonsterTexture(m);

            if(monsterTexture != null) {
                batch.draw(monsterTexture, 900, 50, 100, 100);
            }


            font.draw(batch, "" + m.creature, 800, 140);
            font.draw(batch, "HP: " + m.hp + "/" + MonsterData.MonsterDataStorage.getStats(m.creature).health, 800, 105);
            font.draw(batch, "Speed: " + m.speed * 100, 800, 85);
            font.draw(batch, "Tier: " + m.tier, 800, 65);
            font.draw(batch, "Genre: " + m.genre, 800, 45);
            font.draw(batch, "Gimmicks: " + MonsterData.MonsterDataStorage.getStats(m.creature).gimmick, 800, 25);
        } else {
            font.draw(batch, "Click A Monster" , 800, 140);
        }



        batch.end();
    }

    private Texture getMonsterTexture(Monster m) {
        if (m == null) {
            return null;
        }

        return new Texture( m.creature + "1.png");
    }

}
