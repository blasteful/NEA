package io.github.some_example_name;
import java.util.EnumMap;
import java.util.Map;
import java.util.HashMap;

public class TowerData {

    public enum Family{
        Barricade,
        Spire,
        Turret,
        Detonator
    }

    public enum Tower{
        Barricade(Family.Barricade, 0),

        Spire(Family.Spire, 0),
        SpireII(Family.Spire, 1),
        SpireIII(Family.Spire, 2),
        SpireIV(Family.Spire, 3),
        SpireV(Family.Spire, 4),

        Turret(Family.Turret, 0),
        TurretII(Family.Turret, 1),
        TurretIII(Family.Turret, 2),
        TurretIV(Family.Turret, 3),
        TurretV(Family.Turret, 4),

        Detonator(Family.Detonator, 0),
        DetonatorII(Family.Detonator, 1),
        DetonatorIII(Family.Detonator, 2),
        DetonatorIV(Family.Detonator, 3),
        DetonatorV(Family.Detonator, 4);

        public final Family family;
        public final int tier;

        Tower(Family family, int tier) {
            this.family = family;
            this.tier = tier;
        }
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
        static Map<Family, Tower[]> tiersByFamily = new EnumMap<>(Family.class);

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
            stats.put(Tower.DetonatorV, new TowerDataStorage(300, 16, 10, 300, AttackType.AOE, false));

            for (Family f : Family.values()) {
                int maxTier = -1;
                for (Tower t : Tower.values()) {
                    if (t.family == f) maxTier = Math.max(maxTier, t.tier);
                }
                Tower[] arr = new Tower[maxTier + 1];
                for (Tower t : Tower.values()) {
                    if (t.family == f) arr[t.tier] = t;
                }
                tiersByFamily.put(f, arr);
            }

        }

        public static Tower nextUpgrade(Tower current) {
            Tower[] tiers = tiersByFamily.get(current.family);
            int nextTier = current.tier + 1;
            if (nextTier >= tiers.length) return null;
            return tiers[nextTier];
        }


    }

}
