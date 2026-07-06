package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private BitmapFont font;

    private Renderer renderer;
    private Map map;
    private ShapeRenderer sr;
    private Mouseclick ms;
    private Monster mon;
    private WaveDirector wd;
    private Tower tow;
    private Menu menu;
    private Weather weather;
    private Formulas formulas;
    private Tile last_tile = null;
    private Audio audio;
    private UpgradeMenu upgradeMenu;

    List<Monster> Monsters = new ArrayList<>();
    List<Tower> Towers = new ArrayList<>();
    List<Monster> toSpawn = new ArrayList<>();

    boolean menu_active = true;

    private float frametimer = 0f;
    private float intervals = 0.3f;
    private float spawnTimer = 0f;

    int sizex = 64;
    int sizey = 48;
    int mode;

    int renderer_type = 2;

    boolean sound = true;

    int randomtip = MathUtils.random(1,5);
    boolean tipswitch = true;

    TowerData.Tower selected;
    Weather.Weather_events selected_event = Weather.Weather_events.Sunny;

    public enum Phase {
        BUILD,
        FIGHT,
        Nil,
    }

    public enum Menus {
        Main,
        Gameplay,
        Settings,
        Research,
        Exit
    }

    Phase phase = Phase.BUILD;
    Menus currentmenu = Menus.Main;

    // statistics
    int wave;
    int cash = 300;
    int hp = 10;
    int totalcash = cash;
    int detonators = 0;
    int spires = 0;
    int turrets = 0;
    int barricades = 0;
    int towers = 0;
    Menus recieved = null;


    @Override
    public void create() {

        batch = new SpriteBatch();
        font = new BitmapFont();
        wd = new WaveDirector();
        audio = new Audio(false);
        font.setColor(Color.WHITE);
        font.getData().setScale(2f);
        UpgradeMenu UpgradeMenu = new UpgradeMenu();


        menu = new Menu(audio, UpgradeMenu);
        renderer = new Renderer();
        map = new Map(sizex,sizey);
        map.pathfind();
        weather = new Weather(map);
        sr = new ShapeRenderer();
        ms = new Mouseclick(sizex, sizey, map.getMap());
        formulas = new Formulas();

        int basehp = 100;
        wave = 1;
        mode = 1;


    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        audio.setMuted(sound);

        if(menu_active) {
            menu.update(Gdx.graphics.getDeltaTime());
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                recieved = menu.MenuHandler(currentmenu, ms.getscreen_x(), ms.getscreen_y());
                currentmenu = recieved;
                System.out.println(currentmenu);
            }

            if(menu.rendermodecheck()) {
                renderer_type = 2;
            } else {
                renderer_type = 1;
            }

            if(menu.soundcheck()) {
                sound = false;
            } else {
                sound = true;
            }

            if(recieved == Menus.Gameplay) {
                menu_active = false;
                audio.click();
            }
            if(recieved == Menus.Exit) {
                Gdx.app.exit();
            }
            if(recieved == Menus.Main && !tipswitch) {
                randomtip = MathUtils.random(1,5);
                tipswitch = true;
            }

            if(currentmenu == Menus.Main) {
                menu.MainMenu(ms.getscreen_x(), ms.getscreen_y());
                font.setColor(Color.RED);
                font.getData().setScale(3f);
                batch.begin();
                if(randomtip == 1) {
                    font.draw(batch, "HELLO ", ((float) Gdx.graphics.getWidth() / 2 - 100), 480);
                }
                if(randomtip == 2) {
                    font.getData().setScale(2f);
                    font.draw(batch, "Press O during the game to switch between render modes!", ((float) Gdx.graphics.getWidth() / 2 - 400), 480);
                }
                if(randomtip == 3) {
                    font.getData().setScale(2f);
                    font.draw(batch, "Razvan can be spawned under certain conditions... ", ((float) Gdx.graphics.getWidth() / 2 - 350), 480);
                }
                if(randomtip == 4) {
                    font.draw(batch, "have you ever beaten wave 100?", ((float) Gdx.graphics.getWidth() / 2 - 325), 480);
                }
                if(randomtip == 5) {
                    font.draw(batch, "please give me an A*", ((float) Gdx.graphics.getWidth() / 2 - 225), 480);
                }

                batch.end();
            }

            if(currentmenu == Menus.Settings) {
                tipswitch = false;
                menu.SettingsMenu(ms.getscreen_x(), ms.getscreen_y());
            }
            if(currentmenu == Menus.Research) {
                tipswitch = false;
                menu.ResearchMenu(ms.getscreen_x(), ms.getscreen_y());
            }


        } else {


            Tile current_tile = ms.getTile();
            if(current_tile == null) {
                last_tile = current_tile;
            }
            if(current_tile != last_tile) {
                last_tile = current_tile;
                map.view_pathfind(current_tile);
            }

            float delta = Gdx.graphics.getDeltaTime();
            frametimer += delta;
            spawnTimer += delta;

            List<Monster> toRemove = new ArrayList<>();
            List<Monster> toAdd = new ArrayList<>();
            List<Monster> toRemoveFromSpawn = new ArrayList<>();


            for (Monster m : Monsters) {
                if(m.shouldPlayFootstep() && m.hp > 0) {
                    if(m.alternatefootstep){
                        audio.footsteps();
                    } else {
                        audio.footsteps2();
                    }

                }
                boolean end = m.Move(map);
                m.update(Gdx.graphics.getDeltaTime());
                m.gimmickhandler(toAdd, map);
                if (end) {
                    toRemove.add(m);
                    audio.losehp();
                    hp--;
                }
                if (m.hp <= 0) {
                    toRemove.add(m);
                    if(m.tier == MonsterData.Tier.IV) {
                        cash += 300;
                    }
                    if(m.tier == MonsterData.Tier.III) {
                        cash += 100;
                    }
                    if(m.tier == MonsterData.Tier.II) {
                        cash += 25;
                    }
                    if(m.tier == MonsterData.Tier.I) {
                        cash += 5;
                    }
                }
            }

            Monsters.addAll(toAdd);

            if (frametimer >= intervals) {
                frametimer = 0f;
                for (Monster m : Monsters) {
                    if (m.frame == 1) {
                        m.setFrame(2);
                    } else {
                        m.setFrame(1);
                    }
                }
            }

            Monsters.removeAll(toRemove);

            // build mode stuff
            if (Gdx.input.isKeyJustPressed(Input.Keys.TAB) && phase == Phase.BUILD) {
                if (mode == 2) {
                    mode = 1;
                } else {
                    mode = 2;
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.Z) && phase == Phase.BUILD && cash >= TowerData.TowerDataStorage.stats.get(TowerData.Tower.Turret).cost) {
                selected = TowerData.Tower.Turret;
                turrets ++;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.X) && phase == Phase.BUILD && cash >= TowerData.TowerDataStorage.stats.get(TowerData.Tower.Spire).cost) {
                selected = TowerData.Tower.Spire;
                spires ++;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.C) && phase == Phase.BUILD && cash >= TowerData.TowerDataStorage.stats.get(TowerData.Tower.Detonator).cost) {
                selected = TowerData.Tower.Detonator;
                detonators ++;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.V) && phase == Phase.BUILD && cash >= TowerData.TowerDataStorage.stats.get(TowerData.Tower.Detonator).cost) {
                selected = TowerData.Tower.Barricade;
                detonators ++;
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
                weather.Weather_Random();
                weather.event_handler();
                selected_event = weather.getCurrent_event();
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.K) ) {
                map = new Map(64, 48);
                map.pathfind();
            }


            if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
                if(renderer_type == 1 ){
                    renderer_type = 2;
                } else {
                    if (renderer_type == 2) {
                        renderer_type = 1;
                    }
                }
            }



            if (Monsters.isEmpty() && toSpawn.isEmpty() && phase == Phase.FIGHT) {
                audio.wavecomplete();
                phase = Phase.BUILD;
                wave++;
                int ran_var = MathUtils.random(1,4);
                if(ran_var == 4) {
                    selected_event = weather.getCurrent_event();
                    weather.event_handler();
                }

            }

            //test featires (REMOVE LATER!!!)
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7) && phase == Phase.FIGHT) {
                Monsters.add(new Monster(MonsterData.Genre.Secret, MonsterData.Tier.IV, map, false));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.B)) {
                wave ++;
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.R) && phase == Phase.FIGHT) {
                Monsters.add(new Monster(MonsterData.Genre.Flying, MonsterData.Tier.IV, map, true));
            }


            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && phase == Phase.BUILD) {
                audio.wavestart();
                phase = Phase.FIGHT;
                mode = 1;
                wave ++;
                float pressure_val = formulas.pressure(hp, totalcash, towers, wave, cash);
                System.out.println(pressure_val);
                toSpawn = wd.createWave(wave, map, selected_event, pressure_val, detonators, spires, turrets, barricades);
                spawnTimer = intervals;
            }

            if (phase == Phase.FIGHT && !toSpawn.isEmpty()) {
                if (spawnTimer >= intervals) {
                    spawnTimer -= intervals;
                    int idx = MathUtils.random(toSpawn.size() - 1);
                    Monster m = toSpawn.remove(idx);
                    Monsters.add(m);
                }
            }

            toSpawn.removeAll(toRemoveFromSpawn);


            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && mode == 1) {
                Tile t = ms.getTile();
                if (t == null) return;
                if (t.type == Tile.Type.ENTRANCE || t.type == Tile.Type.EXIT) {
                    return;
                }
                Tile.Type originalType = t.originalType;
                t.setType(Tile.Type.ROCK);
                t.originalType = Tile.Type.ROCK;

                if (!map.pathfind()) {
                    t.type = originalType;
                    t.originalType = originalType;
                    map.pathfind();
                    System.out.println("would block path");
                }
            }

            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && mode == 2 && ms.getTile().type != Tile.Type.ROCK) {
                Tile t = ms.getTile();
                if (t == null) return;
                if (t.type == Tile.Type.ENTRANCE || t.type == Tile.Type.EXIT) {
                    return;
                }

                if (selected != null && TowerData.TowerDataStorage.stats.get(selected).cost <= cash && t.type != Tile.Type.PLACED_TOWER) {
                    Tower newT = new Tower(ms.getTile(), selected, map, sr);
                    cash = cash - TowerData.TowerDataStorage.stats.get(selected).cost;
                    Towers.add(newT);
                    t.tower = newT;

                    t.setType(Tile.Type.PLACED_TOWER);
                    t.originalType = Tile.Type.PLACED_TOWER;

                }


                Tile.Type originalType = t.originalType;



                if (!map.pathfind()) {
                    t.type = originalType;
                    t.originalType = originalType;
                    t.tower = null;
                    Towers.remove(Towers.size() - 1);
                    map.pathfind();
                    System.out.println("would block path");
                }
            }

            if (Gdx.input.isKeyPressed(Input.Keys.M)) {
                Tile t = ms.getTile();
                if (t == null) return;
                if (t.type == Tile.Type.ENTRANCE || t.type == Tile.Type.EXIT) {
                    return;
                }
                Tile.Type originalType = t.originalType;
                t.setType(Tile.Type.DIRT);
                t.originalType = Tile.Type.DIRT;

                if (!map.pathfind()) {
                    t.type = originalType;
                    t.originalType = originalType;
                    map.pathfind();
                    System.out.println("would block path");
                }
            }

            font.setColor(Color.WHITE);
            font.getData().setScale(2f);

            if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
                Tile t = ms.getTile();

                System.out.println(t.type + " Tile");
                System.out.println("Magnetism: -" + t.getPathingcost());
                System.out.println("Walk damage: " + t.getWalkingDamage());
                System.out.println("Walkable: " + t.type.walkable);
                System.out.println("Previously a " + t.previous + " Tile");
                System.out.println("Parent: " + t.parent);
                System.out.println("Child: " + t.child);
                System.out.println();

            }


            sr.begin(ShapeRenderer.ShapeType.Filled);
            if (mode == 1) {
                if(renderer_type == 1) {
                    renderer.renderMap(sr, map.getMap(), ms.getTile());
                }
                if(renderer_type == 2) {
                    renderer.renderSpritesMap(map.getMap(), sr);

                }
                renderer.renderMonsters(Monsters, sr, map);

            }
            if (mode == 2) {
                renderer.renderBMap(sr, map.getMap(), ms.getTile(), selected);

            }
            sr.end();

            batch.begin();
            font.draw(batch, "PHASE: " + phase, ((float) Gdx.graphics.getWidth() / 2 - 100), 950);
            font.draw(batch, "WAVE: " + wave, ((float) Gdx.graphics.getWidth() / 2 + 500), 950);
            font.draw(batch, "CASH: " + cash, ((float) Gdx.graphics.getWidth() / 2 + 500), 920);
            font.draw(batch, "HP: " + hp, ((float) Gdx.graphics.getWidth() / 2 + 500), 890);
            font.draw(batch, "" + selected_event, ((float) Gdx.graphics.getWidth() / 2 + 500), 860);
            batch.end();

            if (mode == 2) {
                batch.begin();
                font.draw(batch, "" + selected, ((float) Gdx.graphics.getWidth() / 2 - 100), 920); // (x, y) position
                batch.end();
            }

            if (phase == Phase.FIGHT) {
                for (Tower tower : Towers) {
                    tower.GetEnemiesInRange(Monsters);
                    tower.attackhandler(audio);
                    tower.update(Gdx.graphics.getDeltaTime());
                }
            }
        }

    }

    @Override
    public void dispose() {
        sr.dispose();
    }
}
