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

    boolean volume = true;
    boolean rendermode = true;

    float cooldown = 0f;
    final float intervals = 0.6f;

    boolean sw = false;

    private Texture start = new Texture("buttons/start.png");
    private Texture settings = new Texture("buttons/settings.png");
    private Texture research = new Texture("buttons/research.png");
    private Texture exit = new Texture("buttons/exit.png");
    private Texture bg = new Texture("buttons/background.png");

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

    public void SettingsMenu(int mousex, int mousey) {
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
            return Main.Menus.Research;
        }
        if(mousex >= startx && mousex <= startx + startw && mousey >= starty && mousey <= starty + starth) {
            return Main.Menus.Gameplay;
        }
        if(mousex >= exx && mousex <= exx + exw && mousey >= exy && mousey <= exy + exh) {
            return Main.Menus.Exit;
        }
        if(mousex >= settingsx && mousex <= settingsx + settingsw && mousey >= settingsy && mousey <= settingsy + settingsh) {
            return Main.Menus.Settings;
        }
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
                System.out.println("res");
            }
            if(mousex >= exx && mousex <= exx + exw && mousey >= exy && mousey <= exy + exh) {
                send = Main.Menus.Exit;
            }
        }
        if(currentmenu == Main.Menus.Settings) {
            if(mousex >= volox && mousex <= volox + volow && mousey >= voloy && mousey <= voloy + voloh) {
                if(volume) {
                    volume = false;
                } else {
                    volume = true;
                }
            }
            if(mousex >= rendervx && mousex <= rendervx + rendervw && mousey >= rendervy && mousey <= rendervy + rendervh) {
                if(rendermode) {
                    rendermode = false;
                } else {
                    rendermode = true;
                }
            }
            if(mousex >= backx && mousex <= backx + backw && mousey >= backy && mousey <= backy + backh) {
                send = Main.Menus.Main;
            }

        }
        return send;


    }

    public boolean rendermodecheck() {
        return (rendermode);

    }

}
