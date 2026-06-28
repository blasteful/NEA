package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class Tower {

    Tile tile_location;
    TowerData.Tower tower_type;
    Map map;
    TowerData.TowerDataStorage stats;
    TowerData.AttackType atktype;
    List<Monster> inRange = new ArrayList<>();

    ShapeRenderer renderer = new ShapeRenderer();

    float cooldown = 0f;

    int x;
    int y;

    public Tower(Tile tile_location, TowerData.Tower tower_type, Map map, ShapeRenderer sr) {
        this.tile_location = tile_location;
        this.tower_type = tower_type;
        this.stats = TowerData.TowerDataStorage.stats.get(tower_type);
        this.atktype = stats.attacktype;

        this.x = tile_location.x;
        this.y = tile_location.y;

    }

    private void drawAttackAnimation_Single(Monster target) {

        float tileWidth = (float) Gdx.graphics.getWidth() / 64;
        float tileHeight = (float) Gdx.graphics.getHeight() / 48;

        float startX = this.x * tileWidth + tileWidth / 2;
        float startY = this.y * tileHeight + tileHeight / 2;
        float endX = target.x * tileWidth + tileWidth / 2;
        float endY = target.y * tileHeight + tileHeight / 2;

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(1, 1, 0, 1);
        renderer.line(startX, startY, endX, endY);
        renderer.end();
    }

    private void drawAttackAnimation_AOE() {

        float tileWidth = (float) Gdx.graphics.getWidth() / 64;
        float tileHeight = (float) Gdx.graphics.getHeight() / 48;
        float startX = this.x * tileWidth + tileWidth / 2;
        float startY = this.y * tileHeight + tileHeight / 2;

        float tiletoscreen = TowerData.TowerDataStorage.stats.get(tower_type).range * tileWidth;

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(1, 1, 0, 1);
        renderer.circle(startX, startY, tiletoscreen);
        renderer.end();
    }


    public void update(float deltaTime) {
        if(cooldown > 0) {
            cooldown -= deltaTime;
        }
    }

    public void attackhandler() {
        if(cooldown <= 0 && !inRange.isEmpty()) {
            if(atktype == TowerData.AttackType.Single) {
                AttackMonsters_Single();
            }
            if(atktype == TowerData.AttackType.AOE) {
                AttackMonsters_AOE();
            }
            if(atktype == TowerData.AttackType.Multi) {
                AttackMonsters_MultiTarget();
            }

            cooldown = this.stats.attackspeed;
        }
    }

    public void GetEnemiesInRange(List<Monster> enemies) {
        inRange.clear();
        for(Monster monster : enemies) {
            float dx = Math.abs(monster.x - this.x);
            float dy = Math.abs(monster.y - this.y);
            float distance = (float) Math.sqrt(dx * dx + dy * dy);

            if(distance <=  this.stats.range) {
                inRange.add(monster);
            }
        }
    }

    public void AttackMonsters_AOE() {
            for(Monster monster : inRange) {
                monster.hp = monster.hp - stats.damage;
            }
            drawAttackAnimation_AOE();
    }

    public void AttackMonsters_Single() {
            Monster target = inRange.get(0);
            target.hp = target.hp - stats.damage;
            drawAttackAnimation_Single(target);
    }

    public void AttackMonsters_MultiTarget() {

        int toattack = Math.min(3, inRange.size());

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < inRange.size(); i++) {
            indices.add(i);
        }

        for (int i = 0; i < toattack; i++) {
            int randomIdx = MathUtils.random(0, indices.size() - 1);
            int targetIndex = indices.remove(randomIdx);
            Monster target = inRange.get(targetIndex);
            target.hp = target.hp - stats.damage;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }



}
