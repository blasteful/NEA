package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.sun.tools.javac.jvm.Gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tower {

    Tile tile_location;
    TowerData.Tower tower_type;
    Map map;
    TowerData.TowerDataStorage stats;
    TowerData.AttackType atktype;
    List<Monster> inRange = new ArrayList<>();

    ShapeRenderer renderer = new ShapeRenderer();

    float cooldown = 0f;
    float attackAnimationTimer = 0f;
    float attackAnimationDuration = 0.2f;
    Monster lastAttackTarget = null;
    Monster laserTarget = null;
    boolean isAOEAnimation = false;
    private boolean isLaserActive = false;

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
        if(tower_type == TowerData.Tower.Spire) {
            renderer.setColor(Color.PURPLE);
        } else {
            renderer.setColor(1, 1, 0, 1);
        }
        renderer.line(startX, startY, endX, endY);
        renderer.end();
    }

    private void drawAttackAnimation_Laser(Monster target) {
        float tileWidth = (float) Gdx.graphics.getWidth() / 64;
        float tileHeight = (float) Gdx.graphics.getHeight() / 48;

        float startX = this.x * tileWidth + tileWidth / 2;
        float startY = this.y * tileHeight + tileHeight / 2;
        float endX = target.x * tileWidth + tileWidth / 2;
        float endY = target.y * tileHeight + tileHeight / 2;

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.RED);
        renderer.line(startX, startY, endX, endY);
        renderer.line(startX, startY + 2, endX, endY + 2);
        renderer.line(startX, startY - 2, endX, endY - 2);
        renderer.end();
    }

    private void drawAttackAnimation_AOE() {
        float tileWidth = (float) Gdx.graphics.getWidth() / 64;
        float tileHeight = (float) Gdx.graphics.getHeight() / 48;
        float startX = this.x * tileWidth + tileWidth / 2;
        float startY = this.y * tileHeight + tileHeight / 2;

        float tiletoscreen = TowerData.TowerDataStorage.stats.get(tower_type).range * tileWidth;

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(1, 1, 0, 0.4f);
        renderer.circle(startX, startY, tiletoscreen);
        renderer.end();
    }

    public void update(float deltaTime, Audio audio) {
        if(cooldown > 0) {
            cooldown -= deltaTime;
        }

        if(laserTarget != null) {
            float dx = Math.abs(laserTarget.x - this.x);
            float dy = Math.abs(laserTarget.y - this.y);
            float distance = (float) Math.sqrt(dx * dx + dy * dy);

            if (distance > stats.range || laserTarget.hp <= 0) {
                laserTarget = null;

                if (isLaserActive) {
                    audio.stoplaser();
                    isLaserActive = false;
                }
            }
        }

        if(attackAnimationTimer > 0) {
            attackAnimationTimer -= deltaTime;
            if(isAOEAnimation) {
                drawAttackAnimation_AOE();
            } else if(lastAttackTarget != null) {
                drawAttackAnimation_Single(lastAttackTarget);
            }
        }

        if(laserTarget != null && laserTarget.hp > 0) {
            drawAttackAnimation_Laser(laserTarget);
        }
    }

    public void attackhandler(Audio audio) {
        if(cooldown <= 0 + MathUtils.random(0.01f, 0.15f) && !inRange.isEmpty()) {
            if(atktype == TowerData.AttackType.Single) {
                AttackMonsters_Single(audio);
            }
            if(atktype == TowerData.AttackType.AOE) {
                AttackMonsters_AOE(audio);
            }
            if(atktype == TowerData.AttackType.Multi) {
                AttackMonsters_MultiTarget();
            }
            attackAnimationTimer = attackAnimationDuration;
            cooldown = this.stats.attackspeed;
        }
    }

    public void laserAttackHandler(Audio audio) {
        if (inRange.isEmpty() && isLaserActive) {
            audio.stoplaser();
            isLaserActive = false;
        }

        if(!inRange.isEmpty()) {
            if(laserTarget == null || !inRange.contains(laserTarget)) {
                laserTarget = inRange.get(MathUtils.random(inRange.size() - 1));
                if (!isLaserActive) {
                    audio.laser();
                    isLaserActive = true;
                }
            }

            if(laserTarget != null && cooldown <= 0) {
                laserTarget.hp = laserTarget.hp - stats.damage;
                cooldown = this.stats.attackspeed;

            }

        } else {
            laserTarget = null;
            if(isLaserActive) {
                audio.stoplaser();
                isLaserActive = false;
            }
        }


        if(laserTarget == null && isLaserActive) {
            audio.stoplaser();
            isLaserActive = false;
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

    public void AttackMonsters_AOE(Audio audio) {
        for(Monster monster : inRange) {
            if(monster.genre != MonsterData.Genre.Flying) {
                monster.hp = monster.hp - stats.damage;
            }
        }
        isAOEAnimation = true;
        audio.detonator();
    }

    public void AttackMonsters_Single(Audio audio) {
        Monster target = inRange.get(MathUtils.random(inRange.size() - 1));
        lastAttackTarget = target;
        target.hp = target.hp - stats.damage;
        isAOEAnimation = false;
        if(tower_type == TowerData.Tower.Spire) {
            audio.spire();
        }
        if(tower_type == TowerData.Tower.Turret) {
            audio.turret();
        }
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
