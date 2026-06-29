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

    List<Monster> Monsters = new ArrayList<>();
    List<Tower> Towers = new ArrayList<>();
    List<Monster> toSpawn = new ArrayList<>();

    boolean menu_active = false;

    private float frametimer = 0f;
    private float intervals = 0.3f;
    private float spawnTimer = 0f;

    int sizex = 64;
    int sizey = 48;
    int mode;

    TowerData.Tower selected;

    public enum Phase {
        BUILD,
        FIGHT,
        Nil,
    }

    Phase phase = Phase.BUILD;

    int wave;
    int cash = 10000;
    int hp = 100;


    @Override
    public void create() {

        batch = new SpriteBatch();
        font = new BitmapFont();
        wd = new WaveDirector();
        font.setColor(Color.WHITE);
        font.getData().setScale(2f);


        menu = new Menu();
        renderer = new Renderer();
        map = new Map(sizex,sizey);
        map.pathfind();
        weather = new Weather(map);
        sr = new ShapeRenderer();
        ms = new Mouseclick(sizex, sizey, map.getMap());

        int basehp = 100;
        wave = 1;
        mode = 1;


    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);


        if(menu_active) {
            menu.Menu_Open(sr);
        } else {


            weather.setCurrent_event(Weather.Weather_events.Snow);
            weather.event_handler();
            float delta = Gdx.graphics.getDeltaTime();
            frametimer += delta;
            spawnTimer += delta;

            List<Monster> toRemove = new ArrayList<>();
            List<Monster> toRemoveFromSpawn = new ArrayList<>();


            for (Monster m : Monsters) {
                boolean end = m.Move(map);
                m.update(Gdx.graphics.getDeltaTime());
                m.gimmickhandler();
                if (end) {
                    toRemove.add(m);
                    hp--;
                }
                if (m.hp <= 0) {
                    toRemove.add(m);
                }
            }


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
            if (Gdx.input.isKeyJustPressed(Input.Keys.Z) && phase == Phase.BUILD) {
                selected = TowerData.Tower.Turret;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.X) && phase == Phase.BUILD) {
                selected = TowerData.Tower.Spire;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.C) && phase == Phase.BUILD) {
                selected = TowerData.Tower.Detonator;
            }


            if (Monsters.isEmpty() && toSpawn.isEmpty() && phase == Phase.FIGHT) {
                phase = Phase.BUILD;
                wave++;
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7) && phase == Phase.FIGHT) {
                Monsters.add(new Monster(MonsterData.Genre.Ground, MonsterData.Tier.III, map));
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_ENTER) && phase == Phase.FIGHT) {
                Monsters.add(new Monster(MonsterData.Genre.Secret, MonsterData.Tier.IV, map));
            }


            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && phase == Phase.BUILD) {
                phase = Phase.FIGHT;
                mode = 1;
                toSpawn = wd.createWave(wave, map);
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

                }


                Tile.Type originalType = t.originalType;
                t.setType(Tile.Type.PLACED_TOWER);
                t.originalType = Tile.Type.PLACED_TOWER;


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
                renderer.renderMap(sr, map.getMap());
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
            batch.end();

            if (mode == 2) {
                batch.begin();
                font.draw(batch, "" + selected, ((float) Gdx.graphics.getWidth() / 2 - 100), 920); // (x, y) position
                batch.end();
            }

            if (phase == Phase.FIGHT) {
                for (Tower tower : Towers) {
                    tower.GetEnemiesInRange(Monsters);
                    tower.attackhandler();
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
