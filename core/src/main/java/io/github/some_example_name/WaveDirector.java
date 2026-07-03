package io.github.some_example_name;

import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class WaveDirector {

    int budget = 0;
    int permbosscheck = 0;

    private Monster mon;
    Map map;

    private boolean inRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    private int getHighestDefense(int detonators, int spires, int turrets, int barricades) {
        return Math.max(Math.max(detonators, spires), Math.max(turrets, barricades));
    }

    public List<Monster> createWave(int wavenum, Map map, Weather.Weather_events weather, float pressure, int detonators, int spires, int turrets, int barricades) {

        List<Monster> toSpawn = new ArrayList<>();

        float budget1 = 10 * wavenum + (wavenum * wavenum * 0.5f) + (pressure * 10);
        int budget = (int) Math.floor(budget1);
        System.out.println(budget);
        int boss_check = 0;

        int tierI;
        int tierII;
        int tierIII;
        int costI = 1;
        int costII = 3;
        int costIII = 6;

        int weightI = Math.max(20, 80 - (wavenum * 2));
        int weightII = Math.max(10, 20 - (wavenum / 2));
        int weightIII = 100 - weightI - weightII;



        TowerData.Tower popular = null;
        MonsterData.Genre threatening = null;

        int highestnum = getHighestDefense(detonators, spires, turrets, barricades);

        if (highestnum == detonators) {
            popular = TowerData.Tower.Detonator;
            threatening = MonsterData.Genre.Flying;
        } else if (highestnum == spires) {
            popular = TowerData.Tower.Spire;
            threatening = MonsterData.Genre.Swarm;
        } else if (highestnum == turrets) {
            popular = TowerData.Tower.Turret;
            threatening = MonsterData.Genre.Ethereal;
        } else if (highestnum == barricades) {
            popular = TowerData.Tower.Barricade;
            threatening = MonsterData.Genre.Ground;
        }

        while (budget > 0) {
            int roll = MathUtils.random(0, 100);
            int roll2 = MathUtils.random(1, 3);

            if (roll <= weightIII) {

                budget = budget - costIII;
                if (roll2 == 3) {
                    toSpawn.add(new Monster(threatening, MonsterData.Tier.III, map, false));
                } else {
                    toSpawn.add(new Monster(MonsterData.Genre.Swarm, MonsterData.Tier.III, map, true));
                }

            } else if (roll > weightIII && roll <= weightII) {

                budget = budget - costII;
                if (roll2 == 3) {
                    toSpawn.add(new Monster(threatening, MonsterData.Tier.II, map, false));
                } else {
                    toSpawn.add(new Monster(MonsterData.Genre.Swarm, MonsterData.Tier.II, map, true));
                }

            } else if (roll > weightII && roll <= weightI) {

                budget = budget - costI;
                if (roll2 == 3) {
                    toSpawn.add(new Monster(threatening, MonsterData.Tier.I, map, false));
                } else {
                    toSpawn.add(new Monster(MonsterData.Genre.Swarm, MonsterData.Tier.I, map, true));
                }

            }

        }

        if (wavenum % 10 == 0) {
            boss_check++;
        }

        if (wavenum > 25) {
            int randomvar = MathUtils.random(1, 2);
                if (randomvar == 1) {
                    boss_check++;
                }
            }


        if(wavenum > 60) {
            boss_check ++;
            if(boss_check % 10 == 0) {
                permbosscheck ++;
            }
        }

        for (int i = 0; i < permbosscheck; i++) {
            boss_check++;
        }


        for (int i = 0; i < boss_check; i++) {
            int random = MathUtils.random(1,3);
            if(random == 3) {
                toSpawn.add(new Monster(threatening, MonsterData.Tier.IV, map, false));
            } else {
                toSpawn.add(new Monster(threatening, MonsterData.Tier.IV, map, true));
            }
        }

        System.out.println(threatening);
        System.out.println(popular);




        return(toSpawn);


    }



}
