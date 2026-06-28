package io.github.some_example_name;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Monster{

    int frame = 1;
    float x;
    float y;
    Tile current;

    int hp;

    float speed;
    MonsterData.Creature creature;

    public Monster(MonsterData.Genre reqgenre, MonsterData.Tier reqtier, Map map) {
        boolean loop = true;

        while(loop) {

            int num = MathUtils.random(0, MonsterData.MonsterDataStorage.stats.size() - 1);

            MonsterData.Creature[] creatures = MonsterData.Creature.values();
            MonsterData.Creature creature1 = creatures[num];

            if(MonsterData.MonsterDataStorage.getStats(creature1).genre == reqgenre && MonsterData.MonsterDataStorage.getStats(creature1).tier == reqtier) {
                loop = false;
                creature = creature1;
            }
        }

        hp = MonsterData.MonsterDataStorage.getStats(creature).health;
        float basespeed = MonsterData.MonsterDataStorage.getStats(creature).speed;
        speed = (float) basespeed / 200;
        Start(map);

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

    public void check() {
        System.out.println(creature);
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }
}
