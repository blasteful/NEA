package io.github.some_example_name;

import java.util.ArrayList;

public class Tile {

    int x,y;

    Tile child;
    Tile parent;
    boolean path = false;

    int hcost;
    int gcost;

    Type previous;
    Type originalType;



    public enum Type {
        DIRT(true, 30, 10, 0),
        SAND(true, 5, 10, 0),
        TOWER(false, -1, -1, 0),
        BARRICADE(true, 2, 3, 10),
        ROCK(false, -1, 0, 0),
        WATER(true, 1500, 3, 0),
        BASALT(true, 5, 16, 0),
        MUD(true, 100, 4, 0),
        GRASS(true, -10, 4, 0),
        DISTRACTION(true, -10000, 4, 0),
        ENTRANCE(true, -1 , 10, 0),
        THORNS(true, 1000000, 3, 5),
        EXIT(true, -1 , 10, 0),
        PATH(true, 10 , 10, 0),
        DEEPWATER(false, -1, 10, 0);


        public final boolean walkable;
        public int pathingCost;
        public final int speed;
        public final int walking_damage;

        Type(boolean walkable, int pathingCost, int speed, int walking_damage) {
            this.walkable = walkable;
            this.pathingCost = pathingCost;
            this.speed = speed;
            this.walking_damage = walking_damage;

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



}
