package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import jdk.internal.org.jline.utils.OSUtils;



public class Menu {

    private Mouseclick ms;
    private BitmapFont text;
    private BitmapFont selectedtext;

    private UpgradeMenu upgradeMenu;

    private enum toShow {
        Null,

        TURRET_1,
        TURRET_2,
        TURRET_3,
        TURRET_4,

        SPIRE_1,
        SPIRE_2,
        SPIRE_3,
        SPIRE_4

    };


    private toShow currentUpgrade = toShow.Null;

    private boolean hovered = false;
    private boolean sopened = false;

    String[] options = {"MENU", "SETTINGS", "EXIT", "PLAY"};

    int startx = (Gdx.graphics.getWidth() / 2) - 225;
    int starty = 300;
    int startw = 250;
    int starth = 100;

    int settingsx = (Gdx.graphics.getWidth() / 2) - 225;
    int settingsy = 175;
    int settingsw = 250;
    int settingsh = 100;

    int resx = (Gdx.graphics.getWidth() / 2)  + 50;
    int resy = 300;
    int resw = 100;
    int resh = 100;

    int exx = (Gdx.graphics.getWidth() / 2) + 50;
    int exy = 175;
    int exw = 100;
    int exh = 100;

    int logox = (Gdx.graphics.getWidth() / 2) - 450;
    int logoy = 550;
    int logow = 800;
    int logoh = 400;

    int volox = 50;
    int voloy = 800;
    int volow = 100;
    int voloh = 100;

    int backx = 1125;
    int backy = 800;
    int backw = 100;
    int backh = 100;

    int rendervx = 50;
    int rendervy = 670;
    int rendervw = 100;
    int rendervh = 100;

    int spire1x = 190;
    int spire1y = 125;
    int spire1w = 100;
    int spire1h = 100;

    int spire2x = 190;
    int spire2y = 250;
    int spire2w = 100;
    int spire2h = 100;

    int spire3x = 190;
    int spire3y = 375;
    int spire3w = 100;
    int spire3h = 100;

    int spire4x = 190;
    int spire4y = 500;
    int spire4w = 100;
    int spire4h = 100;

    int turretup1x = 50;
    int turretup1y = 125;
    int turretup1w = 100;
    int turretup1h = 100;

    int turretup2x = 50;
    int turretup2y = 250;
    int turretup2w = 100;
    int turretup2h = 100;

    int turretup3x = 50;
    int turretup3y = 375;
    int turretup3w = 100;
    int turretup3h = 100;

    int turretup4x = 50;
    int turretup4y = 500;
    int turretup4w = 100;
    int turretup4h = 100;

    int research_points = 1000;

    int turret_upgrade = 0;
    int spire_upgrade = 0;

    boolean volume = true;
    boolean rendermode = true;

    float cooldown = 0f;
    final float intervals = 0.6f;

    boolean sw = false;
    private Audio audio;

    private Texture start = new Texture("buttons/start.png");
    private Texture settings = new Texture("buttons/settings.png");
    private Texture research = new Texture("buttons/research.png");
    private Texture exit = new Texture("buttons/exit.png");
    private Texture bg = new Texture("buttons/background3.png");

    private Texture logo = new Texture("buttons/logo.png");
    private Texture logo2 = new Texture("buttons/logo2.png");

    private Texture hsettings = new Texture("buttons/selectedsettings.png");
    private Texture hstart = new Texture("buttons/selectedstart.png");
    private Texture hresearch = new Texture("buttons/selectedresearch.png");
    private Texture hexit = new Texture("buttons/selectedexit.png");

    private Texture settingsbg = new Texture("buttons/settingsbg.png");
    private Texture vol_on = new Texture("buttons/volumeon.png");
    private Texture vol_off = new Texture("buttons/volumeoff.png");
    private Texture rendermodenew = new Texture("buttons/newrenderer.png");
    private Texture rendermodeold = new Texture("buttons/oldrenderer.png");
    private Texture back = new Texture("buttons/back.png");

    private Texture researchbg = new Texture("buttons/researchbg.png");

    private Texture turret1 = new Texture("buttons/turretupgrade1.png");
    private Texture turret2 = new Texture("buttons/turretupgrade2.png");
    private Texture turret3 = new Texture("buttons/turretupgrade3.png");
    private Texture turret4 = new Texture("buttons/turretupgrade4.png");

    private Texture spire1 = new Texture("buttons/spireupgrade1.png");
    private Texture spire2 = new Texture("buttons/spireupgrade2.png");
    private Texture spire3 = new Texture("buttons/spireupgrade3.png");
    private Texture spire4 = new Texture("buttons/spireupgrade4.png");

    private Texture locked = new Texture("buttons/lockedupgrade.png");

    public Menu(Audio aud, UpgradeMenu upgradeMenu) {
        this.audio = aud;
        this.upgradeMenu = upgradeMenu;

    }

    public void update(float deltaTime) {
        if(cooldown > 0) {
            cooldown -= deltaTime;
        } else {
            sw = false;
        }
    }

    public void MainMenu(int mousex, int mousey) {

        if(sw==false) {
            cooldown = intervals;
            sw = true;
        }
        Main.Menus hovering = hover(mousex, mousey);
        SpriteBatch sb = new SpriteBatch();
        sb.begin();
        sb.draw(bg, 0, 0, 1280, 960);
        if(cooldown > intervals / 2) {
            sb.draw(logo, logox, logoy, logow, logoh);
        } else {
            sb.draw(logo2, logox, logoy, logow, logoh);
        }

        if (hovering == Main.Menus.Gameplay) {
            sb.draw(hstart, startx, starty, startw, starth);
        } else {
            sb.draw(start, startx, starty, startw, starth);
        }
        if (hovering == Main.Menus.Settings) {
            sb.draw(hsettings, settingsx, settingsy, settingsw, settingsh);
        } else {
            sb.draw(settings, settingsx, settingsy, settingsw, settingsh);
        }

        if (hovering == Main.Menus.Exit) {
            sb.draw(hexit, exx, exy, exw, exh);
        } else {
            sb.draw(exit, exx, exy, exw, exh);
        }

        if (hovering == Main.Menus.Research) {
            sb.draw(hresearch, resx, resy, resw, resh);
        } else {
            sb.draw(research, resx, resy, resw, resh);
        }

        sb.end();

    }
    public void ResearchMenu(int mousex, int mousey) {
        BitmapFont font = new BitmapFont();
        SpriteBatch sb = new SpriteBatch();

        if(currentUpgrade != toShow.Null) {

            sb.begin();
            if(currentUpgrade == Menu.toShow.TURRET_1) {
                sb.draw(researchbg, 0, 0, 1280, 960);
                upgradeMenu.render(sb, 5, "+ Heavier Rounds");
            }
            if(currentUpgrade == toShow.TURRET_2) {
                sb.draw(researchbg, 0, 0, 1280, 960);
                upgradeMenu.render(sb, 10, "+ Targeting Precision");
            }
            if(currentUpgrade == toShow.TURRET_3) {
                sb.draw(researchbg, 0, 0, 1280, 960);
                upgradeMenu.render(sb, 30, "+ Machine Gun Barrel");
            }
            if(currentUpgrade == toShow.TURRET_4) {
                sb.draw(researchbg, 0, 0, 1280, 960);
                upgradeMenu.render(sb, 60, "+ HYPER BEAM");
            }
            if(currentUpgrade == toShow.SPIRE_1) {
                sb.draw(researchbg, 0, 0, 1280, 960);
                upgradeMenu.render(sb, 10, "+ Fire Magic");
            }
            if(currentUpgrade == toShow.SPIRE_2) {
                sb.draw(researchbg, 0, 0, 1280, 960);
                upgradeMenu.render(sb, 15, "+ Wizard Revision");
            }
            if(currentUpgrade == Menu.toShow.SPIRE_3) {
                sb.draw(researchbg, 0, 0, 1280, 960);
                upgradeMenu.render(sb, 35, "+ Lightning");
            }
            if(currentUpgrade == Menu.toShow.SPIRE_4) {
                sb.draw(researchbg, 0, 0, 1280, 960);
                upgradeMenu.render(sb, 70, "+ ANTIMAGIC");
            }
            sb.end();
        } else {
            sb.begin();
            sb.draw(researchbg, 0, 0, 1280, 960);
            sb.draw(back, backx, backy, backw, backh);
            if (turret_upgrade == 0) {
                sb.draw(turret1, turretup1x, turretup1y, turretup1w, turretup1h);
                sb.draw(locked, turretup2x, turretup2y, turretup2w, turretup2h);
                sb.draw(locked, turretup3x, turretup3y, turretup3w, turretup3h);
                sb.draw(locked, turretup4x, turretup4y, turretup4w, turretup4h);
            }
            if(turret_upgrade == 1) {
                sb.draw(turret1, turretup1x, turretup1y, turretup1w, turretup1h);
                sb.draw(turret2, turretup2x, turretup2y, turretup2w, turretup2h);
                sb.draw(locked, turretup3x, turretup3y, turretup3w, turretup3h);
                sb.draw(locked, turretup4x, turretup4y, turretup4w, turretup4h);
            }
            if(turret_upgrade == 2) {
                sb.draw(turret1, turretup1x, turretup1y, turretup1w, turretup1h);
                sb.draw(turret2, turretup2x, turretup2y, turretup2w, turretup2h);
                sb.draw(turret3, turretup3x, turretup3y, turretup3w, turretup3h);
                sb.draw(locked, turretup4x, turretup4y, turretup4w, turretup4h);
            }
            if(turret_upgrade == 3) {
                sb.draw(turret1, turretup1x, turretup1y, turretup1w, turretup1h);
                sb.draw(turret2, turretup2x, turretup2y, turretup2w, turretup2h);
                sb.draw(turret3, turretup3x, turretup3y, turretup3w, turretup3h);
                sb.draw(turret4, turretup4x, turretup4y, turretup4w, turretup4h);
            }
            if(turret_upgrade == 4) {
                sb.draw(turret1, turretup1x, turretup1y, turretup1w, turretup1h);
                sb.draw(turret2, turretup2x, turretup2y, turretup2w, turretup2h);
                sb.draw(turret3, turretup3x, turretup3y, turretup3w, turretup3h);
                sb.draw(locked, turretup4x, turretup4y, turretup4w, turretup4h);
            }
            if(spire_upgrade == 0) {
                sb.draw(spire1, spire1x, spire1y, spire1w, spire1h);
                sb.draw(locked, spire1x, turretup2y, spire1w, spire1h);
                sb.draw(locked, spire1x, turretup3y, spire1w, spire1h);
                sb.draw(locked, spire1x, turretup4y, spire1w, spire1h);
            }
            if(spire_upgrade == 1) {
                sb.draw(spire1, spire1x, spire1y, spire1w, spire1h);
                sb.draw(spire2, spire1x, turretup2y, spire1w, spire1h);
                sb.draw(locked, spire1x, turretup3y, spire1w, spire1h);
                sb.draw(locked, spire1x, turretup4y, spire1w, spire1h);
            }
            if(spire_upgrade == 2) {
                sb.draw(spire1, spire1x, spire1y, spire1w, spire1h);
                sb.draw(spire2, spire1x, turretup2y, spire1w, spire1h);
                sb.draw(spire3, spire3x, turretup3y, spire3w, spire3h);
                sb.draw(locked, spire1x, turretup4y, spire1w, spire1h);
            }
            if(spire_upgrade == 3) {
                sb.draw(spire1, spire1x, spire1y, spire1w, spire1h);
                sb.draw(spire2, spire1x, turretup2y, spire1w, spire1h);
                sb.draw(spire3, spire3x, turretup3y, spire3w, spire3h);
                sb.draw(spire4, spire1x, turretup4y, spire1w, spire1h);
            }
            if(spire_upgrade == 4) {
                sb.draw(spire1, spire1x, spire1y, spire1w, spire1h);
                sb.draw(spire2, spire1x, turretup2y, spire1w, spire1h);
                sb.draw(spire3, spire3x, turretup3y, spire3w, spire3h);
                sb.draw(spire4, spire1x, turretup4y, spire1w, spire1h);
            }
            font.getData().setScale(1.5f);
            font.draw(sb, "Research Points: " + research_points, 1040, 50);
            sb.end();
        }








    }


    public void SettingsMenu(int mousex, int mousey) {

        if(!sopened) {audio.click(); sopened = true;}

        SpriteBatch sb = new SpriteBatch();
        sb.begin();
        sb.draw(settingsbg, 0, 0, 1280, 960);
        if(volume){
            sb.draw(vol_on, volox, voloy, volow, volow);
        } else {
            sb.draw(vol_off, volox, voloy, volow, volow);
        }
        if(rendermode){
            sb.draw(rendermodenew, rendervx, rendervy, rendervw, rendervh);
        } else {
            sb.draw(rendermodeold, rendervx, rendervy, rendervw, rendervh);
        }

        sb.draw(back, backx, backy, backw, backh);

        sb.end();
    }

    public Main.Menus hover(int mousex, int mousey) {
        if(mousex >= resx && mousex <= resx + resw && mousey >= resy && mousey <= resy + resh) {
            if(!hovered) {audio.hover();hovered = true;}
            return Main.Menus.Research;
        }
        if(mousex >= startx && mousex <= startx + startw && mousey >= starty && mousey <= starty + starth) {
            if(!hovered) {audio.hover();hovered = true;}
            return Main.Menus.Gameplay;
        }
        if(mousex >= exx && mousex <= exx + exw && mousey >= exy && mousey <= exy + exh) {
            if(!hovered) {audio.hover();hovered = true;}
            return Main.Menus.Exit;
        }
        if(mousex >= settingsx && mousex <= settingsx + settingsw && mousey >= settingsy && mousey <= settingsy + settingsh) {
            if(!hovered) {audio.hover();hovered = true;}
            return Main.Menus.Settings;
        }

        hovered = false;
        return(null);

    }

    public Main.Menus MenuHandler(Main.Menus currentmenu, int mousex, int mousey) {

        Main.Menus send = currentmenu;
        if(currentmenu == Main.Menus.Main) {
            if(mousex >= startx && mousex <= startx + startw && mousey >= starty && mousey <= starty + starth) {
                send = Main.Menus.Gameplay;
            }
            if(mousex >= settingsx && mousex <= settingsx + settingsw && mousey >= settingsy && mousey <= settingsy + settingsh) {
                send = Main.Menus.Settings;
            }
            if(mousex >= resx && mousex <= resx + resw && mousey >= resy && mousey <= resy + resh) {
                send = Main.Menus.Research;
                audio.click();
            }
            if(mousex >= exx && mousex <= exx + exw && mousey >= exy && mousey <= exy + exh) {
                send = Main.Menus.Exit;
            }
        }
        if(currentmenu == Main.Menus.Settings) {
            if(mousex >= volox && mousex <= volox + volow && mousey >= voloy && mousey <= voloy + voloh) {
                audio.click();
                if(volume) {
                    volume = false;
                } else {
                    volume = true;
                }
            }
            if(mousex >= rendervx && mousex <= rendervx + rendervw && mousey >= rendervy && mousey <= rendervy + rendervh) {
                audio.click();
                if(rendermode) {
                    rendermode = false;
                } else {
                    rendermode = true;
                }
            }
            if(mousex >= backx && mousex <= backx + backw && mousey >= backy && mousey <= backy + backh) {
                send = Main.Menus.Main;
                audio.back();
                sopened = false;
            }
        }

        if(currentmenu == Main.Menus.Research) {
            if(currentUpgrade != toShow.Null) {
                if(upgradeMenu.backClicked(mousex, mousey)) {
                    currentUpgrade = toShow.Null;
                    audio.back();
                }
                if(upgradeMenu.buyClicked(mousex, mousey)) {
                    if(currentUpgrade == toShow.TURRET_1 && research_points >= 5) {
                        research_points -= 5;
                        turret_upgrade = 1;
                        currentUpgrade = toShow.Null;
                        audio.buy();
                    }
                    if(currentUpgrade == toShow.TURRET_2 && research_points >= 25) {
                        research_points -= 10;
                        turret_upgrade = 2;
                        currentUpgrade = toShow.Null;
                        audio.buy();
                    }
                    if(currentUpgrade == toShow.TURRET_3 && research_points >= 40) {
                        research_points -= 30;
                        turret_upgrade = 3;
                        currentUpgrade = toShow.Null;
                        audio.buy();
                    }
                    if(currentUpgrade == toShow.TURRET_4 && research_points >= 60) {
                        research_points -= 60;
                        turret_upgrade = 3;
                        currentUpgrade = toShow.Null;
                        audio.buy();
                    }
                    if(currentUpgrade == toShow.SPIRE_1 && research_points >= 10) {
                        research_points -= 10;
                        spire_upgrade = 1;
                        currentUpgrade = toShow.Null;
                        audio.buy();
                    }
                    if(currentUpgrade == toShow.SPIRE_2 && research_points >= 15) {
                        research_points -= 15;
                        spire_upgrade = 2;
                        currentUpgrade = toShow.Null;
                        audio.buy();
                    }
                    if(currentUpgrade == toShow.SPIRE_3 && research_points >= 30) {
                        research_points -= 35;
                        spire_upgrade = 3;
                        currentUpgrade = toShow.Null;
                        audio.buy();
                    }
                    if(currentUpgrade == toShow.SPIRE_4 && research_points >= 70) {
                        research_points -= 70;
                        spire_upgrade = 4;
                        currentUpgrade = toShow.Null;
                        audio.buy();
                    }
                }
                return send;
            }

            if(mousex >= backx && mousex <= backx + backw && mousey >= backy && mousey <= backy + backh) {
                send = Main.Menus.Main;
                audio.back();
            }
            if((mousex >= turretup1x && mousex <= turretup1x + turretup1w && mousey >= turretup1y && mousey <= turretup1y + turretup1h) && turret_upgrade == 0) {
                currentUpgrade = Menu.toShow.TURRET_1;
                audio.click();
            }
            if((mousex >= turretup2x && mousex <= turretup2x + turretup2w && mousey >= turretup2y && mousey <= turretup2y + turretup2h) && turret_upgrade == 1) {
                currentUpgrade = Menu.toShow.TURRET_2;
                audio.click();
            }
            if((mousex >= turretup3x && mousex <= turretup3x + turretup3w && mousey >= turretup3y && mousey <= turretup3y + turretup3h) && turret_upgrade == 2) {
                currentUpgrade = toShow.TURRET_3;
                audio.click();
            }
            if((mousex >= turretup4x && mousex <= turretup4x + turretup4w && mousey >= turretup4y && mousey <= turretup4y + turretup4h) && turret_upgrade == 3) {
                currentUpgrade = toShow.TURRET_4;
                audio.click();
            }
            if((mousex >= spire1x && mousex <= spire1x + spire1w && mousey >= spire1y && mousey <= spire1y + spire1h) && spire_upgrade == 0) {
                currentUpgrade = toShow.SPIRE_1;
                audio.click();
            }
            if((mousex >= spire2x && mousex <= spire2x + spire2w && mousey >= spire2y && mousey <= spire2y + spire2h) && spire_upgrade == 1) {
                currentUpgrade = toShow.SPIRE_2;
                audio.click();
            }
            if((mousex >= spire3x && mousex <= spire3x + spire3w && mousey >= spire3y && mousey <= spire3y + spire3h) && spire_upgrade == 2) {
                currentUpgrade = toShow.SPIRE_3;
                audio.click();
            }
            if((mousex >= spire4x && mousex <= spire4x + spire4w && mousey >= spire4y && mousey <= spire4y + spire4h) && spire_upgrade == 3) {
                currentUpgrade = toShow.SPIRE_4;
                audio.click();
            }
        }

        return send;
    }

    public boolean rendermodecheck() {
        return (rendermode);
    }

    public boolean soundcheck() {
        return (volume);
    }

    public int turretc() {
        return (turret_upgrade);
    }

}
