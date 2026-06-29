package io.github.some_example_name;

public class Weather {

    public enum Weather_events{

        Sunny,
        Bank_Holiday,
        Snow

    }

    Weather_events current_event;
    Map map;

    public Weather(Map map) {
        this.map = map;
    }

    public void event_handler(){
        if(current_event == Weather_events.Snow) {
            snow(map);
        }
        if(current_event == Weather_events.Sunny) {
            sunny(map);
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
}
