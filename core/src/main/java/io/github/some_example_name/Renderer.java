package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;
import java.util.List;

public class Renderer {


    private SpriteBatch spriteBatch;
    private HashMap<MonsterData.Creature, Texture> monsterTextures;
    private HashMap<MonsterData.Creature, Texture> monsterTextures2;

    public Renderer() {
        spriteBatch = new SpriteBatch();
        monsterTextures = new HashMap<>();
        monsterTextures2 = new HashMap<>();
        loadMonsterTextures();
    }

    private void loadMonsterTextures() {
        for (MonsterData.Creature creature : MonsterData.Creature.values()) {
            MonsterData.MonsterDataStorage stats = MonsterData.MonsterDataStorage.getStats(creature);

            if (stats != null && !stats.imagepath.equals("Nil")) {
                try {
                    monsterTextures.put(creature, new Texture(Gdx.files.internal(stats.imagepath)));

                } catch (Exception e) {
                    System.out.println("Failed to load texture: " + stats.imagepath);
                }
            } else {
                System.out.println("No image path for: " + creature.name());
            }

            if (stats != null && !stats.imagepath2.equals("Nil")) {
                try {
                    monsterTextures2.put(creature, new Texture(Gdx.files.internal(stats.imagepath2)));
                } catch (Exception e) {
                    System.out.println("Failed to load texture2: " + stats.imagepath2);
                }
            } else {
                System.out.println("No image path2 for: " + creature.name());
            }

        }
    }

    public void renderBMap(ShapeRenderer sr, Tile[][] map, Tile hover, TowerData.Tower selected) {

        float tilewidth = (float) Gdx.graphics.getWidth() / map.length;
        float tileheight = (float) Gdx.graphics.getHeight() / map[0].length;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {

                Tile t = map[i][j];


                if(t.type == Tile.Type.THORNS) {
                    sr.setColor(106/255f, 238/255f, 255/255f, 1f);
                }
                if(t.type == Tile.Type.PLACED_TOWER && t.tower != null && t.tower.tower_type == TowerData.Tower.Detonator) {
                    sr.setColor(new Color(Color.RED));
                }
                if(t.type == Tile.Type.PLACED_TOWER && t.tower != null && t.tower.tower_type == TowerData.Tower.Turret) {
                    sr.setColor(new Color(Color.SALMON));
                }
                if(t.type == Tile.Type.PLACED_TOWER && t.tower != null && t.tower.tower_type == TowerData.Tower.Spire) {
                    sr.setColor(new Color(Color.PURPLE));
                }
                if(t.type == Tile.Type.GRASS) {
                    sr.setColor(106/255f, 238/255f, 255/255f, 1f);
                }
                if(t.type == Tile.Type.WATER) {
                    sr.setColor(106/255f, 238/255f, 255/255f, 1f);
                }
                if(t.type == Tile.Type.DEEPWATER) {
                    sr.setColor(242/255f, 27/255f, 27/255f, 1f);
                }
                if(t.type == Tile.Type.SAND) {
                    sr.setColor(106/255f, 238/255f, 255/255f, 1f);
                }
                if(t.type == Tile.Type.ROCK) {
                    sr.setColor(242/255f, 27/255f, 27/255f, 1f);
                }
                if(t.type == Tile.Type.BASALT) {
                    sr.setColor(106/255f, 238/255f, 255/255f, 1f);
                }
                if(t.type == Tile.Type.ENTRANCE) {
                    sr.setColor(new Color(Color.GREEN));
                }
                if(t.type == Tile.Type.EXIT) {
                    sr.setColor(106/255f, 238/255f, 255/255f, 1f);
                }
                if(t.type == Tile.Type.DISTRACTION) {
                    sr.setColor(106/255f, 238/255f, 255/255f, 1f);
                }
                if(t.type == Tile.Type.MUD) {
                    sr.setColor(106/255f, 238/255f, 255/255f, 1f);
                }
                if(t.type == Tile.Type.DIRT) {
                    sr.setColor(106/255f, 238/255f, 255/255f, 1f);
                }
                if(t.type == Tile.Type.PATH) {
                    sr.setColor(27/255f, 108/255f, 242/255f, 1f);
                }


                if(t == hover && t.type.walkable) {
                    if(selected == TowerData.Tower.Detonator) {
                        sr.setColor(Color.RED);
                    }
                    if(selected == TowerData.Tower.Spire) {
                        sr.setColor(Color.PURPLE);
                    }
                    if(selected == TowerData.Tower.Turret) {
                        sr.setColor(Color.SALMON);
                    }

                }

                sr.rect(i * tilewidth,j * tileheight, tilewidth, tileheight);

            }

        }

        sr.end();

        sr.begin(ShapeRenderer.ShapeType.Line);

        sr.setColor(Color.BLACK);

        for (int i = 0; i <= map.length; i++) {
            sr.line(
                i * tilewidth, 0,
                i * tilewidth, Gdx.graphics.getHeight()
            );
        }

        for (int j = 0; j <= map[0].length; j++) {
            sr.line(
                0, j * tileheight,
                Gdx.graphics.getWidth(), j * tileheight
            );
        }
    }

    public void renderMap(ShapeRenderer sr, Tile[][] map) {

        float tilewidth = (float) Gdx.graphics.getWidth() / map.length;
        float tileheight = (float) Gdx.graphics.getHeight() / map[0].length;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {

                Tile t = map[i][j];

                if(t.type == Tile.Type.THORNS) {
                    sr.setColor(76/255f, 99/255f, 64/255f, 1f);
                }
                if(t.type == Tile.Type.SNOW) {
                    sr.setColor(Color.LIGHT_GRAY);
                }

                if(t.type == Tile.Type.GRASS) {
                    sr.setColor(new Color(Color.GRAY));
                }
                if(t.type == Tile.Type.WATER) {
                    sr.setColor(42/255f, 116/255f, 168/255f, 1f);
                }
                if(t.type == Tile.Type.DEEPWATER) {
                    sr.setColor(55/255f, 81/255f, 125/255f, 1f);
                }
                if(t.type == Tile.Type.SAND) {
                    sr.setColor(181/255f, 159/255f, 98/255f, 1f);
                }
                if(t.type == Tile.Type.ROCK) {
                    sr.setColor(new Color(Color.DARK_GRAY));
                }
                if(t.type == Tile.Type.BASALT) {
                    sr.setColor(101/255f, 112/255f, 109/255f, 1f);
                }
                if(t.type == Tile.Type.ENTRANCE) {
                    sr.setColor(new Color(Color.GREEN));
                }
                if(t.type == Tile.Type.PLACED_TOWER && t.tower != null && t.tower.tower_type == TowerData.Tower.Detonator) {
                    sr.setColor(new Color(Color.RED));
                }
                if(t.type == Tile.Type.PLACED_TOWER && t.tower != null && t.tower.tower_type == TowerData.Tower.Turret) {
                    sr.setColor(new Color(Color.SALMON));
                }
                if(t.type == Tile.Type.PLACED_TOWER && t.tower != null && t.tower.tower_type == TowerData.Tower.Spire) {
                    sr.setColor(new Color(Color.PURPLE));
                }
                if(t.type == Tile.Type.EXIT) {
                    sr.setColor(new Color(Color.RED));
                }
                if(t.type == Tile.Type.DISTRACTION) {
                    sr.setColor(new Color(Color.RED));
                }
                if(t.type == Tile.Type.MUD) {
                    sr.setColor(new Color(Color.BROWN));
                }
                if(t.type == Tile.Type.DIRT) {
                    sr.setColor(41/255f, 79/255f, 47/255f, 1f);
                }
                if(t.type == Tile.Type.PATH) {
                    sr.setColor(107/255f, 92/255f, 70/255f, 1f);
                }




                sr.rect(i * tilewidth,j * tileheight, tilewidth, tileheight);

            }

        }

        sr.end();

        sr.begin(ShapeRenderer.ShapeType.Line);

        sr.setColor(Color.BLACK);

        for (int i = 0; i <= map.length; i++) {
            sr.line(
                i * tilewidth, 0,
                i * tilewidth, Gdx.graphics.getHeight()
            );
        }

        for (int j = 0; j <= map[0].length; j++) {
            sr.line(
                0, j * tileheight,
                Gdx.graphics.getWidth(), j * tileheight
            );
        }
    }

    public void renderMonsters(List<Monster> mon, ShapeRenderer sr, Map map) {
        sr.end();

        spriteBatch.begin();

        float tileWidth = (float) Gdx.graphics.getWidth() / map.sizex;
        float tileHeight = (float) Gdx.graphics.getHeight() / map.sizey;
        float scale;


        for(Monster m : mon) {

            Texture texture;

            if(m.frame == 1) {
                 texture = monsterTextures.get(m.creature);
            }
            if(m.frame == 2) {
                 texture = monsterTextures2.get(m.creature);
            } else {
                texture = monsterTextures.get(m.creature);
            }

            if (texture != null) {
                if(MonsterData.MonsterDataStorage.getStats(m.creature).tier == MonsterData.Tier.IV) {
                    scale = 10;
                } else {
                     scale = 2f;
                }
                if(MonsterData.MonsterDataStorage.getStats(m.creature).genre == MonsterData.Genre.Swarm) {
                    scale = 1;
                }
                if(MonsterData.MonsterDataStorage.getStats(m.creature).tier == MonsterData.Tier.II) {
                    scale = 4;
                }
                if(MonsterData.MonsterDataStorage.getStats(m.creature).tier == MonsterData.Tier.III) {
                    scale = 4.5f;
                }

                if(m.creature == MonsterData.Creature.Watcher) {
                    scale = 1.5f;
                }

                float width = tileWidth * scale;
                float height = tileHeight * scale;
                float screenX = m.x * tileWidth + (tileWidth - width) / 2;

                float screenY;

                if(MonsterData.MonsterDataStorage.getStats(m.creature).tier == MonsterData.Tier.IV) {
                    screenY = (m.y * tileHeight + (tileHeight - height) / 2) + 90;
                } else {
                     screenY = (m.y * tileHeight + (tileHeight - height) / 2) + 20;
                }
               if(MonsterData.MonsterDataStorage.getStats(m.creature).genre == MonsterData.Genre.Swarm) {
                    screenY = (m.y * tileHeight + (tileHeight - height) / 2) + 1;
                }
                if(m.creature == MonsterData.Creature.Cyclops || m.creature == MonsterData.Creature.Ogre) {
                    screenY = (m.y * tileHeight + (tileHeight - height) / 2) + 35;
                }
                if(m.creature == MonsterData.Creature.Minotaur) {
                    screenY = (m.y * tileHeight + (tileHeight - height) / 2) + 45;
                }


                spriteBatch.draw(texture, screenX, screenY, width, height);
            }
        }

        spriteBatch.end();
        sr.begin(ShapeRenderer.ShapeType.Filled);
    }

    public void dispose() {
        spriteBatch.dispose();
        for (Texture texture : monsterTextures.values()) {
            texture.dispose();
        }
        for (Texture texture : monsterTextures2.values()) {
            texture.dispose();
        }

    }


}
