package io.github.some_example_name;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Monster{

    int x;
    int y;
    Tile current;

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

        Start(map);

    }

    public void Start(Map map) {
        x = map.exit.x;
        y = map.exit.y;
        current = map.exit;
    }

    public void Move(Map map) {

        boolean nextitle = true;
        int targetx = current.parent.x;
        int targety = current.parent.y;

        if(x > targetx) {
            x = x-1;
        } if(x < targetx) {
            x = x+1;
        }

        if(y > targety) {
            y = y-1;
        } if(y < targety) {
            y = y+1;
        }

        if(x == current.parent.x && y == current.parent.y) {
            current = current.parent;
        }





    }

    public void check() {
        System.out.println(creature);
    }

}
