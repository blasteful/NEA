package io.github.some_example_name;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class WaveDirector {

    int budget = 0;
    List<Monster> toSpawn = new ArrayList<>();
    private Monster mon;
    Map map;

    public List<Monster> createWave(int wavenum, Map map) {

        budget = 10 * wavenum;
        int ran = MathUtils.random(10,25);
        int ran2 = MathUtils.random(1,3);

        for (int i = 0; i < ran; i++) {
            toSpawn.add(new Monster(MonsterData.Genre.Ground, MonsterData.Tier.I, map));
        }

        if(ran2==1) {
            toSpawn.add(new Monster(MonsterData.Genre.Ground, MonsterData.Tier.IV, map));
        }
        if(ran==2){
            toSpawn.add(new Monster(MonsterData.Genre.Flying, MonsterData.Tier.IV, map));
        }

        System.out.println(toSpawn.get(0));
        return(toSpawn);


    }
}
