package io.github.some_example_name;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.math.MathUtils.random;

public class Weather {

    Tile[] ava_rocks = new Tile[1000];
    int counter = 0;

    public enum Weather_events{

        Sunny,
        Snow,
        Avalanche,
        BankHoliday,
        SandStorm,
    }

    Weather_events current_event;
    Map map;
    Tile[] sandy;

    public Weather(Map map) {
        this.map = map;
    }

    public void Weather_Random() {
        Weather_events[] events = Weather_events.values();
        current_event = events[random.nextInt(events.length)];
    }

    public void event_handler() {

        sunny(map);

        if (current_event == Weather_events.Snow) {
            snow(map);
        }
        if (current_event == Weather_events.Sunny) {
            sunny(map);
        }
        if (current_event == Weather_events.Avalanche) {
            avalanche(map);
        }
        if (current_event == Weather_events.SandStorm) {
            sandstorm(map);
        }
    }

    private void sunny(Map map) {
        for (int i = 0; i < map.sizex; i++) {
            for (int j = 0; j < map.sizey; j++) {
                Tile target = map.getTile(i, j);
                if (target.type == Tile.Type.SNOW) {
                    target.setType(Tile.Type.DIRT);
                }
                if (target.type == Tile.Type.QuickSand) {
                    target.setType(Tile.Type.DIRT);
                }
                if(target.type == Tile.Type.PATH) {
                    target.previous = Tile.Type.DIRT;
                }
            }
        }

        clear_rocks();
    }

    private void flood(Map map) {

        sunny(map);

        for (int i = 0; i < map.sizex; i++) {
            for (int j = 0; j < map.sizey; j++) {
                Tile target = map.getTile(i, j);
                if (target.type == Tile.Type.DIRT) {
                    target.setType(Tile.Type.WATER);
                }
                if(target.type == Tile.Type.PATH) {
                    target.previous = Tile.Type.DIRT;
                }
            }
        }

        clear_rocks();
    }

    private void avalanche(Map map) {

        sunny(map);
        clear_rocks();

        for (int i = 0; i < ava_rocks.length; i++) {
            ava_rocks[i] = null;
        }

        counter = 0;

        for (int i = 0; i < map.sizex; i++) {
            for (int j = 0; j < map.sizey; j++) {
                Tile target = map.getTile(i, j);
                if (target.type == Tile.Type.DIRT || target.type == Tile.Type.SNOW) {
                    int ran_var = MathUtils.random(1,20);
                    if(ran_var == 1){
                        ava_rocks[counter] = target;
                        target.previous = target.type;
                        target.setType(Tile.Type.ROCK);
                        counter ++;
                    }
                }

            }
        }

    }

    private void sandstorm(Map map) {
        for (int i = 0; i < map.sizex; i++) {
            for (int j = 0; j < map.sizey; j++) {
                Tile target = map.getTile(i, j);
                if(target.type == Tile.Type.DIRT) {
                    target.setType(Tile.Type.QuickSand);
                    target.originalType = Tile.Type.QuickSand;

                }
                if(target.type == Tile.Type.PATH) {
                    target.previous = Tile.Type.QuickSand;
                }
            }
        }
        clear_rocks();
    }

    private void snow(Map map) {
        for (int i = 0; i < map.sizex; i++) {
            for (int j = 0; j < map.sizey; j++) {
                Tile target = map.getTile(i, j);
                if(target.type == Tile.Type.DIRT) {
                    target.setType(Tile.Type.SNOW);
                    target.originalType = Tile.Type.SNOW;
                }
                if(target.type == Tile.Type.PATH) {
                    target.previous = Tile.Type.SNOW;
                }
            }
        }
        clear_rocks();
    }

    public void clear_rocks() {
        for (int i = 0; i < counter; i++) {
            Tile target = ava_rocks[i];

            if(target.type == Tile.Type.ROCK && target != null) {
                target.setType(target.previous);
            }

        }
    }



    public void setCurrent_event(Weather_events current_event) {
        this.current_event = current_event;
    }

    public Weather_events getCurrent_event() {
        return current_event;
    }
}
