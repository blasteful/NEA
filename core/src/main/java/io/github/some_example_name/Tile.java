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
        DIRT(true, 30, 1, 0, "Tiles/grass.png"),
        SAND(true, 10, 0.8f, 0, "Tiles/sand.png"),
        PLACED_TOWER(false, -1, -1, 0, "Tiles/nil.png"),
        BARRICADE(true, 2, 3, 10, "Tiles/nil.png"),
        ROCK(false, -1, 0, 0, "Tiles/rock.png"),
        WATER(true, 100, 0.2f, 0, "Tiles/water.png"),
        BASALT(true, 5, 2, 0, "Tiles/basalt.png"),
        MUD(true, 100, 4, 0, "Tiles/nil.png"),
        GRASS(true, -10, 4, 0, "Tiles/nil.png"),
        DISTRACTION(true, -10000, 4, 0, "Tiles/grass.png"),
        ENTRANCE(true, -1 , 10, 0, "Tiles/path.png"),
        THORNS(true, 80, 0.3f, 15, "Tiles/thorns.png"),
        EXIT(true, -1 , 10, 0, "Tiles/path.png"),
        PATH(true, 10 , 10, 0, "Tiles/path.png"),
        DEEPWATER(false, -1, 10, 0, "Tiles/water.png"),
        SNOW(true, 20, 0.7f, 0, "Tiles/snow.png"),
        QuickSand(true, 10, 0.6f, 0, "Tiles/sand.png"),
        BOOST(false, 100, 5, 0, "Tiles/boost.png"),
        VOID(false, 20, 5, 0, "Tiles/nil.png");


        public final boolean walkable;
        public int pathingCost;
        public final float speed;
        public final int walking_damage;
        public final String imagepath;

        Type(boolean walkable, int pathingCost, float speed, int walking_damage, String imagepath) {
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

    public float getSpeed() {
        float speed = type.speed;


        return(speed);
    }

    public boolean getwalkable() {
        return type.walkable;
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
