package io.github.some_example_name;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class WaveDirector {

    int budget = 0;

    private Monster mon;
    Map map;

    private boolean inRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    public List<Monster> createWave(int wavenum, Map map) {

        List<Monster> toSpawn = new ArrayList<>();

        budget = 10 * wavenum;
        int ran = MathUtils.random(5 + budget/2,25 + budget/2);
        int ran2 = MathUtils.random(1,3);

        for (int i = 0; i < ran; i++) {

            int randomspawn = MathUtils.random(1,100);



            if(inRange(randomspawn, 0, 40)) {
                toSpawn.add(new Monster(MonsterData.Genre.Swarm, MonsterData.Tier.I, map));
            }
            if(inRange(randomspawn, 41, 75)) {
                int rand = MathUtils.random(1,5);
                if(rand==3) {
                    toSpawn.add(new Monster(MonsterData.Genre.Ground, MonsterData.Tier.II, map));
                } if(ran==2) {
                    toSpawn.add(new Monster(MonsterData.Genre.Ground, MonsterData.Tier.III, map));
                } else {
                    toSpawn.add(new Monster(MonsterData.Genre.Ground, MonsterData.Tier.I, map));
                }
            }
            if(inRange(randomspawn, 76, 95)) {
                toSpawn.add(new Monster(MonsterData.Genre.Flying, MonsterData.Tier.I, map));
            }
            if(inRange(randomspawn, 96, 100)) {
                toSpawn.add(new Monster(MonsterData.Genre.Ethereal, MonsterData.Tier.I, map));
            }
        }


        if (ran2 == 1) {
            toSpawn.add(new Monster(MonsterData.Genre.Ground, MonsterData.Tier.IV, map));
        } else if (ran2 == 2) {
            toSpawn.add(new Monster(MonsterData.Genre.Flying, MonsterData.Tier.IV, map));
        }

        System.out.println(toSpawn.get(0));
        return(toSpawn);


    }



}
