package io.github.some_example_name;
import java.util.Map;
import java.util.HashMap;

public class TowerData {

    public enum Tower{
        Barricade,
        Spire,
        Turret,
        Detonator,

        BarricadeII,
        SpireII,
        TurretII,
        DetonatorII,
    }

    public enum AttackType{
        AOE,
        Single,
        Multi
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
            stats.put(Tower.Turret, new TowerDataStorage(50, 3, 1.5f, 75, AttackType.Single, false));
            stats.put(Tower.Spire, new TowerDataStorage(150, 7, 2f, 200, AttackType.Single, true));
            stats.put(Tower.Detonator, new TowerDataStorage(125, 4, 3, 25, AttackType.AOE, false));
        }

    }

}
