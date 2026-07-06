package io.github.some_example_name;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Monster{

    int frame = 1;
    float x;
    float y;
    Tile current;

    float cooldown = 0f;
    boolean charging = false;
    int holymantle;

    float baseSpeed;
    float speed;
    float footstepTimer = 0f;
    float footstepInterval = 1.2f;

    boolean footstep;
    boolean alternatefootstep = MathUtils.randomBoolean();
    int hp;

    MonsterData.Genre genre;
    MonsterData.Tier tier;
    MonsterData.Creature creature;

    public Monster(MonsterData.Genre reqgenre, MonsterData.Tier reqtier, Map map, Boolean random) {
        initializeMonster(reqgenre, reqtier, map, true, random);
        tier = reqtier;
    }

    public Monster(MonsterData.Genre reqgenre, MonsterData.Tier reqtier, Map map, float summonX, float summonY) {
        initializeMonster(reqgenre, reqtier, map, false, false);
        this.x = summonX;
        this.y = summonY;
        this.current = findClosestTile(map);
        tier = reqtier;
    }


    private Tile findClosestTile(Map map) {
        Tile closest = map.entrance;
        float closestDistance = Float.MAX_VALUE;

        Tile tile = map.entrance;
        while(tile != null) {
            float distance = Math.abs(x - tile.x) + Math.abs(y - tile.y);
            if(distance < closestDistance) {
                closestDistance = distance;
                closest = tile;
            }
            tile = tile.child;
        }

        return closest;
    }

        private void initializeMonster(MonsterData.Genre reqgenre, MonsterData.Tier reqtier, Map map, boolean startAtEntrance, boolean random) {
            boolean loop = true;
            int num2 = 0;

            if(random == true) {
                num2 = MathUtils.random(1,4);
            }
            if(num2 == 1) {
                reqgenre = MonsterData.Genre.Flying;
            }
            if(num2 == 2) {
                reqgenre = MonsterData.Genre.Ground;
            }
            if(num2 == 3) {
                reqgenre = MonsterData.Genre.Ethereal;
            }
            if(num2 == 4) {
                reqgenre = MonsterData.Genre.Swarm;
            }

            genre = reqgenre;

            while(loop) {
                int num = MathUtils.random(0, MonsterData.MonsterDataStorage.stats.size() - 1);
                MonsterData.Creature[] creatures = MonsterData.Creature.values();
                MonsterData.Creature creature1 = creatures[num];

                if(MonsterData.MonsterDataStorage.getStats(creature1).genre == reqgenre && MonsterData.MonsterDataStorage.getStats(creature1).tier == reqtier) {
                    loop = false;
                    creature = creature1;
                }
            }

            if(MonsterData.MonsterDataStorage.getStats(creature).gimmick == MonsterData.Gimmick.Holy_Mantle) {
                holymantle = 1;
            }
            if(MonsterData.MonsterDataStorage.getStats(creature).gimmick == MonsterData.Gimmick.Holier_Mantle) {
                holymantle = 2;
            }
            if(MonsterData.MonsterDataStorage.getStats(creature).gimmick == MonsterData.Gimmick.Holiest_Mantle) {
                holymantle = 3;
            }

            hp = MonsterData.MonsterDataStorage.getStats(creature).health;
            baseSpeed = MonsterData.MonsterDataStorage.getStats(creature).speed / 200f;
            speed = baseSpeed;

            if(startAtEntrance) {
                Start(map);
            }

        }

    public void Start(Map map) {
        x = map.entrance.x;
        y = map.entrance.y;
        current = map.entrance;
    }

    public boolean Move(Map map) {

        if(current == map.exit) {
            return(true);
        }

        boolean nextitle = true;
        if(current.child != null) {
            int targetx = current.child.x;
            int targety = current.child.y;

            if(x > targetx) {
                x = (x-speed);
            } if(x < targetx) {
                x = (x+speed);
            }

            if(y > targety) {
                y = (y-speed);
            } if(y < targety) {
                y = (y+speed);
            }

            float distance = Math.abs(x - targetx) + Math.abs(y - targety);
            if(distance < 0.2) {
                current = current.child;

            }

        }

        return(false);
    }

    public void update(float deltaTime) {
        if(cooldown > 0) {
            cooldown -= deltaTime;
        }
        if(footstepTimer > 0) {
            footstepTimer -= deltaTime;
        }
    }

    public boolean shouldPlayFootstep() {
        if(footstepTimer <= 0 + MathUtils.random(0.1f, 0.4f) && genre == MonsterData.Genre.Ground) {
            if(alternatefootstep) {
                alternatefootstep = false;
            } else {
                alternatefootstep = true;
            }
            footstepTimer = footstepInterval;
            return true;
        }
        return false;
    }

    public void gimmickhandler(List<Monster> Monsters, Map map) {


        if(holymantle > 0 && hp <= 0) {
                    hp = 1;
                    holymantle --;
        }
        if(cooldown <= 0) {
            if (MonsterData.MonsterDataStorage.getStats(creature).gimmick == MonsterData.Gimmick.Charge) {
                if (charging) {
                    speed = baseSpeed;
                    charging = false;
                    cooldown = 10f;
                } else {
                    speed = baseSpeed * 6;
                    charging = true;
                    cooldown = 0.5f;
                }
            }
            if (MonsterData.MonsterDataStorage.getStats(creature).gimmick == MonsterData.Gimmick.Summoner) {
                Monster sum = new Monster(MonsterData.Genre.Ground, MonsterData.Tier.I, map, this.x, this.y);
                Monsters.add(sum);
                cooldown = 5f;
            }
        }
    }

    public void check() {
        System.out.println(creature);
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }
}
