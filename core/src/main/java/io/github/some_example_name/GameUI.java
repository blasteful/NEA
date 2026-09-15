package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


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

    private Texture extrainfo = new Texture("GameUI/extrainfo.png");
    private Texture extrainfo2 = new Texture("GameUI/extrainfoblock.png");

    private Texture turret = new Texture("Towers/turret1.png");
    private Texture spire = new Texture("Towers/spire1.png");
    private Texture detonator = new Texture("Towers/detonator1.png");
    private Texture barricade = new Texture("Tiles/barricade.png");

    private Texture turretbutton = new Texture("GameUI/turretbutton.png");
    private Texture turretbuttonoff = new Texture("GameUI/turretbuttonoff.png");

    private Texture barricadebutton = new Texture("GameUI/barricadebutton.png");
    private Texture barricadebuttonoff = new Texture("GameUI/barricadebuttonoff.png");


    float interval = 0.3f;
    float interval2 = 0.4f;
    float timer = 0f;
    float timer2 = 0f;
    boolean cycle = false;
    boolean cycle2 = false;


    int infoWidth = 150;
    boolean openedmons = false;

    public void draw(SpriteBatch batch, ShapeRenderer sr, int cash, int wavenum, int hp, Weather weather, Main.Phase phase, int mode, TowerData.Tower selected, Tile t, Monster m, Boolean hide, int trp, int srp, int drp) {

        int screenWidth = Gdx.graphics.getWidth();

        int tileX;
        int monsterX;

        Tower tower = t.tower;

        if (m != null && m.hp > 0) {
            monsterX = screenWidth - infoWidth;
            tileX = screenWidth - infoWidth * 2;
        } else {
            tileX = screenWidth  - infoWidth;
            monsterX = screenWidth;
        }

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

        int ycord = 80; //80
        int ycord2 = 90; //90
        int ycord3 = 100; // 100

        if(mode == 2) {
            font.draw(batch, "Z" , 670, ycord);
            font.draw(batch, "X" , 730, ycord);
            font.draw(batch, "C" , 790, ycord);
            font.draw(batch, "V" , 850, ycord);

            font.setColor(Color.PURPLE);

            batch.draw(turretbutton, 650, ycord2, 50, 50);
            batch.draw(turretbutton, 710, ycord2, 50, 50);
            batch.draw(turretbutton, 770, ycord2, 50, 50);
            batch.draw(barricadebutton, 830, ycord2, 50, 50);

            if(selected == TowerData.Tower.Turret) {
                batch.draw(turretbuttonoff, 650, ycord2, 50, 50);
            }
            if(selected == TowerData.Tower.Spire) {
                batch.draw(turretbuttonoff, 710, ycord2, 50, 50);
            }
            if(selected == TowerData.Tower.Detonator) {
                batch.draw(turretbuttonoff, 770, ycord2, 50, 50);
            }
            if(selected == TowerData.Tower.Barricade) {
                batch.draw(barricadebuttonoff, 830, ycord2, 50, 50);
            }
            font.setColor(Color.PURPLE);

            font.draw(batch, "" + trp, 690, ycord3);
            font.draw(batch, "" + srp, 750, ycord3);
            font.draw(batch, "" + drp, 810, ycord3);

            font.setColor(Color.WHITE);
        }


        if(hide == false) {


            batch.draw(extrainfo2, tileX, 150);
            Texture ttexture = getTileTexture(t);


            if (t.type == Tile.Type.PLACED_TOWER) {
                Texture towertexture = getTowerTexture(t.tower);
                font.draw(batch, "" + t.tower.tower_type, tileX + 40, 340);
                batch.draw(towertexture, tileX + 42, 250, 64, 64);
                font.draw(batch, "Level: " + (tower.level + 1), tileX + 10, 230);
                font.draw(batch, "Range: " + TowerData.TowerDataStorage.stats.get(tower.tower_type).range, tileX + 10, 212);
                font.draw(batch, "CD: " + tower.cooldown, tileX + 10, 197);
                font.draw(batch, "Damage: " + TowerData.TowerDataStorage.stats.get(tower.tower_type).damage, tileX + 10, 182);



                float tilewidth = (float) Gdx.graphics.getWidth() / 64;
                float tileheight = (float) Gdx.graphics.getHeight() / 48;

                float towerScreenX = t.x * tilewidth + tilewidth / 2f;
                float towerScreenY = t.y * tileheight + tileheight / 2f;

                if(mode == 2) {
                    batch.end();
                    sr.begin(ShapeRenderer.ShapeType.Filled);
                    Gdx.gl.glEnable(GL20.GL_BLEND);
                    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                    sr.setColor(1f, 1f, 0f, 0.25f);
                    sr.circle(towerScreenX,towerScreenY, TowerData.TowerDataStorage.stats.get(t.tower.tower_type).range * tilewidth);
                    sr.end();
                    Gdx.gl.glDisable(GL20.GL_BLEND);

                    batch.begin();
                }



            } else {
                batch.draw(ttexture, tileX + 42, 250, 64, 64);
                font.draw(batch, "" + t.type + " Tile", tileX + 30, 340);
                font.draw(batch, "Walkable: " + t.getwalkable(), tileX + 10, 230);
                font.draw(batch, "Difficulty: " + t.getPathingcost() + "/100", tileX + 10, 212);
            }
            if (t.type == Tile.Type.PATH) {
                font.draw(batch, "Was a " + t.previous + " Tile", tileX + 10, 182);
            }

            if (m != null && m.hp > 0) {

                batch.draw(extrainfo, monsterX, 150);

                Texture monsterTexture = getMonsterTexture(m);
                Texture monsterTexture2 = getMonsterTexture2(m);

                if (monsterTexture != null && monsterTexture2 != null) {
                    if (cycle) {
                        batch.draw(monsterTexture, monsterX + 30, 240, 75, 75);
                    } else {
                        batch.draw(monsterTexture2, monsterX + 30, 240, 75, 75);
                    }
                }

                font.draw(batch, "" + m.creature, monsterX + 50, 340);
                if(m.holymantle == 0) {
                    font.draw(batch, "HP: " + m.hp + "/" + MonsterData.MonsterDataStorage.getStats(m.creature).health, monsterX + 10, 230);
                } else {
                    font.draw(batch, "Shield: " + m.holymantle, monsterX + 10, 230);
                }

                font.draw(batch, "Speed: " + m.speed * 100, monsterX + 10, 212);
                font.draw(batch, "Tier: " + m.tier, monsterX + 10, 197);
                font.draw(batch, "Genre: " + m.genre, monsterX + 10, 182);
            }
        }


        batch.end();
    }

    private Texture getMonsterTexture(Monster m) {
        if (m == null) {
            return null;
        }

        return new Texture( m.creature + "1.png");
    }


    private Texture getMonsterTexture2(Monster m) {
        if (m == null) {
            return null;
        }

        return new Texture( m.creature + "2.png");
    }

    private Texture getTileTexture(Tile t) {
        if (t == null) {
            return new Texture( "Tiles/nil.png");
        }

        String path;

        if(t.type == Tile.Type.DIRT) {
            path = "Tiles/GRASS.png";
        } else {
            path = "Tiles/" + t.type + ".png";
        }

        if (Gdx.files.internal(path).exists()) {
            return new Texture(path);
        }
        return new Texture("Tiles/nil.png");
    }

    private Texture getTowerTexture(Tower t) {

        if (t == null) {
            return new Texture("Towers/nil.png");
        }

        String path = "Towers/" + t.tower_type + "1.png";
        return new Texture(path);
    }

}
