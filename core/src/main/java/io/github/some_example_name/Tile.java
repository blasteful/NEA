package io.github.some_example_name;

import java.util.ArrayList;

public class Tile {

    int x,y;

    boolean predicted = false;
    Tile child;
    Tile parent;
    Tower tower;
    boolean path = false;

    int hcost;
    int gcost;

    Type previous;
    Type originalType;



    public enum Type {
        DIRT(true, 30, 10, 0, "Tiles/grass.png"),
        SAND(true, 10, 10, 0, "Tiles/sand.png"),
        PLACED_TOWER(false, -1, -1, 0, "Tiles/nil.png"),
        BARRICADE(true, 2, 3, 10, "Tiles/nil.png"),
        ROCK(false, -1, 0, 0, "Tiles/rock.png"),
        WATER(true, 100, 3, 0, "Tiles/water.png"),
        BASALT(true, 5, 16, 0, "Tiles/bassalt.png"),
        MUD(true, 100, 4, 0, "Tiles/nil.png"),
        GRASS(true, -10, 4, 0, "Tiles/nil.png"),
        DISTRACTION(true, -10000, 4, 0, "Tiles/grass.png"),
        ENTRANCE(true, -1 , 10, 0, "Tiles/path.png"),
        THORNS(true, 1000000, 3, 5, "Tiles/grass.png"),
        EXIT(true, -1 , 10, 0, "Tiles/path.png"),
        PATH(true, 10 , 10, 0, "Tiles/path.png"),
        DEEPWATER(false, -1, 10, 0, "Tiles/water.png"),
        SNOW(true, 20, 5, 0, "Tiles/snow.png");


        public final boolean walkable;
        public int pathingCost;
        public final int speed;
        public final int walking_damage;
        public final String imagepath;

        Type(boolean walkable, int pathingCost, int speed, int walking_damage, String imagepath) {
            this.walkable = walkable;
            this.pathingCost = pathingCost;
            this.speed = speed;
            this.walking_damage = walking_damage;
            this.imagepath = imagepath;

        }

    }

    public enum Effect {
        COLD {
            public int modifySpeed(int speed) {
                return (int) (speed * 0.7);
            }
        },

        FIRE {
            public int modifyWalkingDamage(int walking_damage) {
                return (int) (walking_damage + 4);
            }
        };

        public int modifyWalkingDamage(int walking_damage) {
            return walking_damage;
        }

        public int modifySpeed(int speed) {
            return speed;
        }

        public int modifyPathingCost(int pathingCost) {
            return pathingCost;
        }

    }

    Type type;
    ArrayList<Effect> Active_effects = new ArrayList<>();

    public Tile(int x, int y, Type type){

        this.x = x;
        this.y = y;

        this.type = type;
        this.originalType = type;
    }

    public int getSpeed() {
        int speed = type.speed;

        for(Effect e : Active_effects) {
            speed = e.modifySpeed(speed);
        }

        return(speed);
    }

    public int getPathingcost() {
        int pathingCost = type.pathingCost;

        for(Effect e : Active_effects) {
            pathingCost = e.modifyPathingCost(pathingCost);
        }

        return(pathingCost);
    }



    public int getWalkingDamage() {
        int WalkingDamage = type.walking_damage;

        for(Effect e : Active_effects) {
            WalkingDamage = e.modifyWalkingDamage(WalkingDamage);
        }

        return(WalkingDamage);
    }

    public void addEffect(Effect e) {
        Active_effects.add(e);
    }

    public void removeEffect(Effect e) {
        if(Active_effects.contains(e)) {
            Active_effects.remove(e);
        } else {
            System.out.println("Effect not found");
        }
    }

    public void setType(Type t) {
        this.previous = this.type;
        this.type = t;
    }

    public void setParent(Tile t) {
        this.parent = t;
    }
    public void setChild(Tile t) {
        this.child = t;
    }

    public int fcost() {
        return(hcost + gcost);
    }
    public int tiebreaker() {
        return gcost + hcost + hcost;
    }


}
