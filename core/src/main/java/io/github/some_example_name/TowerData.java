package io.github.some_example_name;
import java.util.Map;
import java.util.HashMap;

public class TowerData {

    public enum Tower{
        Barricade,
        Spire,
        Turret,
        Detonator,

        SpireII,
        TurretII,
        DetonatorII,

        SpireIII,
        TurretIII,
        DetonatorIII,

        SpireIV,
        TurretIV,
        DetonatorIV,

        SpireV,
        TurretV,
        DetonatorV,
    }

    public enum AttackType{
        AOE,
        Single,
        Multi,
        Laser,
        Nil
    }


    static class TowerDataStorage{

        public int cost;
        public int range;
        public float attackspeed;
        public int damage;
        public boolean flying_vision;

        public AttackType attacktype;
        public Tower type;


        public TowerDataStorage(int cost, int range, float attackspeed, int damage, AttackType attacktype, boolean flying_vision) {
            this.cost = cost;
            this.attacktype = attacktype;
            this.range = range;
            this.attackspeed = attackspeed;
            this.damage = damage;
            this.flying_vision = flying_vision;
        }

        static Map<Tower, TowerDataStorage> stats = new HashMap<>();

        static {

            stats.put(Tower.Barricade, new TowerDataStorage(10, 0, 0, 0, AttackType.Nil, false));

            stats.put(Tower.Turret, new TowerDataStorage(50, 5, 2f, 50, AttackType.Single, false));
            stats.put(Tower.Spire, new TowerDataStorage(150, 7, 2f, 125, AttackType.Single, true));
            stats.put(Tower.Detonator, new TowerDataStorage(125, 4, 5, 25, AttackType.AOE, false));

            stats.put(Tower.TurretII, new TowerDataStorage(100, 5, 2f, 80, AttackType.Single, false));
            stats.put(Tower.SpireII, new TowerDataStorage(200, 7, 2f, 175, AttackType.Single, true));
            stats.put(Tower.DetonatorII, new TowerDataStorage(300, 4, 3, 25, AttackType.AOE, false));

            stats.put(Tower.TurretIII, new TowerDataStorage(100, 8, 2f, 80, AttackType.Single, false));
            stats.put(Tower.SpireIII, new TowerDataStorage(200, 7, 2f, 175, AttackType.Single, true));
            stats.put(Tower.DetonatorIII, new TowerDataStorage(300, 4, 3, 25, AttackType.AOE, false));

            stats.put(Tower.TurretIV, new TowerDataStorage(100, 8, 0.32f, 80, AttackType.Single, false));
            stats.put(Tower.SpireIV, new TowerDataStorage(200, 7, 2f, 175, AttackType.Single, true));
            stats.put(Tower.DetonatorIV, new TowerDataStorage(300, 4, 3, 25, AttackType.AOE, false));

            stats.put(Tower.TurretV, new TowerDataStorage(100, 13, 0.04f, 12, AttackType.Laser, false));
            stats.put(Tower.SpireV, new TowerDataStorage(200, 7, 2f, 175, AttackType.Single, true));
            stats.put(Tower.DetonatorV, new TowerDataStorage(300, 4, 3, 25, AttackType.AOE, false));
        }

    }

}
