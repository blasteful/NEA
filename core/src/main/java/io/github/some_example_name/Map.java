package io.github.some_example_name;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class Map {

    int sizex,sizey;
    Tile[][] map;
    Tile exit;
    Tile entrance;

    ArrayList<Tile> openlist;
    ArrayList<Tile> closedlist;


    int[] dx = {-1, 1, 0, 0};
    int[] dy = {0, 0, -1, 1};


    int waterpools = MathUtils.random(0,6);

    public Map(int sizex, int sizey) {
        this.sizex = sizex;
        this.sizey = sizey;

        createbaseMap();
        createPath();

    }

    public void createbaseMap() {

        map = new Tile[sizex][sizey];
        int spawning_structures = 15;

        for (int i = 0; i < sizex; i++) {
            for (int j = 0; j < sizey; j++) {

                int num = MathUtils.random(1, 100);
                Tile.Type type;
                type = Tile.Type.DIRT;


                map[i][j] = new Tile(i, j, type);
            }
        }

        while (spawning_structures >= 0) {

            spawning_structures--;
            int randomx = MathUtils.random(0, sizex - 1);
            int randomy = MathUtils.random(0, sizey - 1);

            int structure = MathUtils.random(1, 5);

            if (structure <= 2) {
                // rocks
                int x = randomx;
                int y = randomy;

                for (int i = 0; i < 20; i++) {

                    map[x][y].setType(Tile.Type.ROCK);

                    x += MathUtils.random(-1, 1);
                    y += MathUtils.random(-1, 1);

                    x = MathUtils.clamp(x, 0, sizex - 1);
                    y = MathUtils.clamp(y, 0, sizey - 1);
                }
            }
            if (structure == 3) {
                int x = MathUtils.random(0, sizex - 1);
                int y = MathUtils.random(0, sizey - 1);
                int size = MathUtils.random(30, 75);
                for (int i = 0; i < size; i++) {

                    int radius = MathUtils.random(1, 1);

                    for (int dx = -radius; dx <= radius; dx++) {
                        for (int dy = -radius; dy <= radius; dy++) {

                            int xx = MathUtils.clamp(x + dx, 0, sizex - 1);
                            int yy = MathUtils.clamp(y + dy, 0, sizey - 1);

                            if (MathUtils.randomBoolean(0.8f)) {
                                map[xx][yy].setType(Tile.Type.WATER);
                            }
                        }
                    }
                    x += MathUtils.random(-1, 1);
                    y += MathUtils.random(-1, 1);

                    x = MathUtils.clamp(x, 0, sizex - 1);
                    y = MathUtils.clamp(y, 0, sizey - 1);
                }
            }

            if(structure == 4) {
                // basalt lane

                boolean vertical = MathUtils.randomBoolean();

                int startX = MathUtils.random(0, sizex - 1);
                int startY = MathUtils.random(0, sizey - 1);

                int length = MathUtils.random(5, 15);

                if (vertical) {
                    for (int i = 0; i < length; i++) {
                        int y = startY + i;

                        if (y >= 0 && y < sizey) {
                            map[startX][y].setType(Tile.Type.BASALT);
                        }
                    }

                } else {
                    for (int i = 0; i < length; i++) {
                        int x = startX + i;

                        if (x >= 0 && x < sizex) {
                            map[x][startY].setType(Tile.Type.BASALT);
                        }
                    }
                }
            }

            for (int x = 0; x < sizex; x++) {
                for (int y = 0; y < sizey; y++) {

                    if (map[x][y].type != Tile.Type.WATER)
                        continue;

                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {

                            int xx = x + dx;
                            int yy = y + dy;

                            if (xx < 0 || yy < 0 || xx >= sizex || yy >= sizey)
                                continue;

                            if (map[xx][yy].type == Tile.Type.DIRT) {
                                map[xx][yy].setType(Tile.Type.SAND);
                            }
                        }
                    }
                }
            }
        }
    }

    public void createPath() {
        int enterancex = 0;
        int enterancey = MathUtils.random(7, sizey - 1);
        int exitx = sizex - 1;
        int exity = MathUtils.random(7, sizey - 1);

        entrance = map[enterancex][enterancey];
        exit = map[exitx][exity];

        map[enterancex][enterancey].type = Tile.Type.ENTRANCE;
        map[exitx][exity].type = Tile.Type.EXIT;

        // create ui border bruh
        for (int i = 0; i < sizex; i++) {
            for (int j = 0; j < sizey; j++) {

                if(j <= 7) {
                    map[i][j].type = Tile.Type.VOID;
                }

            }

        }

    }

    public boolean pathfind() {
        resetPathfinding();
        entrance.gcost = 0;

        openlist = new ArrayList<>();
        closedlist = new ArrayList<>();
        openlist.add(entrance);

        boolean pathFound = false;

        while (!openlist.isEmpty()) {

            Tile current = openlist.get(0);
            for (int i = 1; i < openlist.size(); i++) {
                Tile current2 = openlist.get(i);
                if (current2.fcost() < current.fcost() ||
                    (current2.fcost() == current.fcost() && current2.hcost < current.hcost)) {
                    current = current2;
                }
            }

            if (current == exit) {
                pathFound = true;
                break;
            }

            openlist.remove(current);
            closedlist.add(current);


            for (int i = 0; i < 4; i++) {
                int newx = current.x + dx[i];
                int newy = current.y + dy[i];

                if ((newx >= sizex || newx < 0) || (newy >= sizey || newy < 0)) {
                    continue;
                }
                if (!map[newx][newy].type.walkable) {
                    continue;
                }

                Tile neighbour = map[newx][newy];

                if (closedlist.contains(neighbour)) {
                    continue;
                }

                int newG = current.gcost + neighbour.getPathingcost();

                if (!openlist.contains(neighbour)) {
                    neighbour.hcost = heuristic(neighbour);
                    neighbour.gcost = newG;
                    neighbour.parent = current;
                    openlist.add(neighbour);
                } else if (newG < neighbour.gcost) {
                    neighbour.gcost = newG;
                    neighbour.parent = current;
                }
            }
        }

        if (pathFound) {
            markPath();
        } else {
            System.out.println("No path found");
        }
        return pathFound;
    }


    public void view_pathfind(Tile blocked) {
        for (int i = 0; i < sizex; i++) {
            for (int j = 0; j < sizey; j++) {
                map[i][j].predicted = false;
            }
        }
        Tile.Type originalType = blocked.type;
        blocked.type = Tile.Type.ROCK;

        resetPathfinding();
        entrance.gcost = 0;



        openlist = new ArrayList<>();
        closedlist = new ArrayList<>();
        openlist.add(entrance);

        boolean pathFound = false;

        while (!openlist.isEmpty()) {

            Tile current = openlist.get(0);
            for (int i = 1; i < openlist.size(); i++) {
                Tile current2 = openlist.get(i);
                if (current2.fcost() < current.fcost() ||
                    (current2.fcost() == current.fcost() && current2.hcost < current.hcost)) {
                    current = current2;
                }
            }



            if (current == exit) {
                pathFound = true;
                break;
            }

            openlist.remove(current);
            closedlist.add(current);


            for (int i = 0; i < 4; i++) {
                int newx = current.x + dx[i];
                int newy = current.y + dy[i];

                if ((newx >= sizex || newx < 0) || (newy >= sizey || newy < 0)) {
                    continue;
                }
                if (!map[newx][newy].type.walkable) {
                    continue;
                }

                Tile neighbour = map[newx][newy];

                if (closedlist.contains(neighbour)) {
                    continue;
                }

                int newG = current.gcost + neighbour.getPathingcost();

                if (!openlist.contains(neighbour)) {
                    neighbour.hcost = heuristic(neighbour);
                    neighbour.gcost = newG;
                    neighbour.parent = current;
                    openlist.add(neighbour);
                } else if (newG < neighbour.gcost) {
                    neighbour.gcost = newG;
                    neighbour.parent = current;
                }
            }
        }
        if (pathFound && exit.parent != null) {
            Tile current = exit;
            while (current != null) {
                current.predicted = true;
                current = current.parent;
            }
        }

        blocked.type = originalType;
    }

    public void resetPathfinding() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Tile t = map[i][j];
                t.gcost = 0;
                t.hcost = 0;
                t.parent = null;
            }
        }
    }

    public void markPath() {
        if (exit.parent == null) {
            return;
        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Tile t = map[i][j];
                if (t.type == Tile.Type.PATH && t.previous != Tile.Type.PATH) {
                    t.type = t.previous;
                    t.path = false;
                }
            }
        }

        Tile current = exit;
        Tile child = null;

        while (current != null) {
            if (current.type != Tile.Type.ENTRANCE && current.type != Tile.Type.EXIT) {
                Tile.Type temp = current.type;
                current.type = Tile.Type.PATH;
                current.previous = temp;
            }
            current.path = true;
            current.child = child;
            child = current;
            current = current.parent;
        }
    }


    public int heuristic(Tile item) {
        int mandis = (Math.abs(item.x - exit.x) + Math.abs(item.y - exit.y));
        return (int) (mandis * 0.5f);

    }



    public Tile[][] getMap() {
        return map;
    }
    public Tile getTile(int x, int y) {
        return map[x][y];
    }
    public Tile getExit() {
        return exit;
    }
    public Tile getEntrance() {
        return entrance;
    }
}
