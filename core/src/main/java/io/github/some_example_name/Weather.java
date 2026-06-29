package io.github.some_example_name;

import com.badlogic.gdx.math.MathUtils;

import static com.badlogic.gdx.math.MathUtils.random;

public class Weather {

    public enum Weather_events{

        Sunny,
        Snow,
        Avalanche

    }

    Weather_events current_event;
    Map map;

    public Weather(Map map) {
        this.map = map;
    }

    public void Weather_Random() {
        Weather_events[] events = Weather_events.values();
        current_event = events[random.nextInt(events.length)];
    }

    public void event_handler() {
        if (current_event == Weather_events.Snow) {
            snow(map);
        }
        if (current_event == Weather_events.Sunny) {
            sunny(map);
        }
        if (current_event == Weather_events.Avalanche) {
            avalanche(map);
        }
    }

    private void sunny(Map map) {
        for (int i = 0; i < map.sizex; i++) {
            for (int j = 0; j < map.sizey; j++) {
                Tile target = map.getTile(i, j);
                if (target.type == Tile.Type.SNOW) {
                    target.setType(Tile.Type.DIRT);
                }
            }
        }
    }

    private void avalanche(Map map) {
        for (int i = 0; i < map.sizex; i++) {
            for (int j = 0; j < map.sizey; j++) {
                Tile target = map.getTile(i, j);
                if (target.type == Tile.Type.DIRT) {
                    int ran_var = MathUtils.random(1,10);
                    if(ran_var == 1){
                        target.setType(Tile.Type.ROCK);
                    }
                }
            }
        }
    }

    private void snow(Map map) {
        for (int i = 0; i < map.sizex; i++) {
            for (int j = 0; j < map.sizey; j++) {
                Tile target = map.getTile(i, j);
                if(target.type == Tile.Type.DIRT) {
                    target.setType(Tile.Type.SNOW);
                }
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
