package io.github.some_example_name;

import java.util.HashMap;
import java.util.Map;

public class MonsterData {

    public enum Creature {
        Zombie,
        Skeleton,
        Goblin,
        Ogre,
        Cyclops,
        Minotaur,
        Golem,

        Bat,
        Watcher,
        Harpy,
        Wyvern,
        Spirit,
        Dragon,

        Ant,
        Gnome,
        Spider,
        Rat,
        Dip,
        Mole,
        Brood_Mother,

        Bolt,
        Spectre,
        Ghost,
        JellyFish,
        Memory,
        Doppleganger,

        Infected,

        Razvan


    }

    public enum Gimmick {
        Nil,
        Shield,
        Charge,
        Harden,
        Fire_Breath,
        Resonate,
        Float,
        Summoner,
        Quick_Start,
        Holy_Mantle,
        Holier_Mantle,
        Teleport,
        Split,
        Holiest_Mantle,
    }

    public enum Genre {
        Flying,
        Swarm,
        Ground,
        Ethereal,
        Brood,
        Secret
    }

    public enum Tier {
        I,
        II,
        III,
        IV,
    }


    static class MonsterDataStorage{

        public int health;
        public float speed;
        public Tier tier;
        public Genre genre;
        public String imagepath;
        public String imagepath2;
        public Gimmick gimmick;

        public MonsterDataStorage(int health, float speed, Tier tier, Genre genre, String imagepath, String imagepath2, Gimmick gimmick) {

            this.health = health;
            this.speed = speed;
            this.tier = tier;
            this.genre = genre;
            this.imagepath = imagepath;
            this.imagepath2 = imagepath2;
            this.gimmick = gimmick;

        }

        static Map<Creature, MonsterDataStorage> stats = new HashMap<>();

        static {
            stats.put(Creature.Zombie, new MonsterDataStorage(150, 5, Tier.I, Genre.Ground, "zombie1.png", "zombie2.png", Gimmick.Nil));
            stats.put(Creature.Skeleton, new MonsterDataStorage(50, 3, Tier.I, Genre.Ground, "skeleton1.png", "skeleton2.png", Gimmick.Nil));
            stats.put(Creature.Goblin, new MonsterDataStorage(100, 4, Tier.I, Genre.Ground, "goblin1.png", "goblin2.png", Gimmick.Nil));

            stats.put(Creature.Cyclops, new MonsterDataStorage(300, 4, Tier.II, Genre.Ground, "cyclops1.png", "cyclops2.png", Gimmick.Nil));
            stats.put(Creature.Ogre, new MonsterDataStorage(200, 3, Tier.II, Genre.Ground, "ogre1.png", "ogre2.png", Gimmick.Nil));

            stats.put(Creature.Minotaur, new MonsterDataStorage(450, 8, Tier.III, Genre.Ground, "minotaur1.png", "minotaur2.png", Gimmick.Charge));

            stats.put(Creature.Golem, new MonsterDataStorage(2500, 2, Tier.IV, Genre.Ground, "golem1.png", "golem2.png", Gimmick.Harden));


            stats.put(Creature.Bat, new MonsterDataStorage(50, 10, Tier.I, Genre.Flying, "bat1.png", "bat2.png", Gimmick.Nil));
            stats.put(Creature.Watcher, new MonsterDataStorage(125, 6, Tier.I, Genre.Flying, "watcher1.png", "watcher2.png", Gimmick.Nil));

            stats.put(Creature.Harpy, new MonsterDataStorage(200, 6, Tier.II, Genre.Flying, "Nil", "", Gimmick.Nil));
            stats.put(Creature.Wyvern, new MonsterDataStorage(125, 20, Tier.II, Genre.Flying, "Nil", "", Gimmick.Nil));

            stats.put(Creature.Spirit, new MonsterDataStorage(100, 100, Tier.III, Genre.Flying, "Nil", "", Gimmick.Resonate));

            stats.put(Creature.Dragon, new MonsterDataStorage(2000, 2.5f, Tier.IV, Genre.Flying, "dragon1.png", "dragon2.png", Gimmick.Fire_Breath));


            stats.put(Creature.Ant, new MonsterDataStorage(30, 3, Tier.I, Genre.Swarm, "ant1.png", "ant1.png", Gimmick.Nil));
            stats.put(Creature.Gnome, new MonsterDataStorage(30, 4, Tier.I, Genre.Swarm, "gnome1.png", "gnome2.png", Gimmick.Nil));

            stats.put(Creature.Spider, new MonsterDataStorage(30, 18, Tier.II, Genre.Swarm, "Nil", "", Gimmick.Nil));
            stats.put(Creature.Rat, new MonsterDataStorage(60, 6, Tier.II, Genre.Swarm, "Nil", "", Gimmick.Nil));
            stats.put(Creature.Dip, new MonsterDataStorage(50, 12, Tier.II, Genre.Swarm, "Nil", "", Gimmick.Nil));

            stats.put(Creature.Mole, new MonsterDataStorage(250, 6, Tier.III, Genre.Swarm, "Nil", "", Gimmick.Quick_Start));

            stats.put(Creature.Brood_Mother, new MonsterDataStorage(1500, 2, Tier.IV, Genre.Swarm, "brood1.png", "brood2.png", Gimmick.Summoner));


            stats.put(Creature.Bolt, new MonsterDataStorage(1, 12, Tier.I, Genre.Ethereal, "bolt1.png", "bolt2.png", Gimmick.Nil));
            stats.put(Creature.Spectre, new MonsterDataStorage(1, 16, Tier.I, Genre.Ethereal, "spectre1.png", "spectre2.png", Gimmick.Holy_Mantle));

            stats.put(Creature.Ghost, new MonsterDataStorage(1, 16, Tier.II, Genre.Ethereal, "Nil", "", Gimmick.Holier_Mantle));
            stats.put(Creature.JellyFish, new MonsterDataStorage(1, 6, Tier.II, Genre.Ethereal, "Nil", "", Gimmick.Holiest_Mantle));

            stats.put(Creature.Memory, new MonsterDataStorage(250, 8, Tier.III, Genre.Ethereal, "Nil", "", Gimmick.Teleport));

            stats.put(Creature.Doppleganger, new MonsterDataStorage(750, 6, Tier.IV, Genre.Ethereal, "Nil", "", Gimmick.Split));

            stats.put(Creature.Infected, new MonsterDataStorage(50, 3, Tier.II, Genre.Brood, "Nil", "", Gimmick.Nil));
            stats.put(Creature.Razvan, new MonsterDataStorage(1000000, 2, Tier.IV, Genre.Secret, "raz1.png", "raz2.png", Gimmick.Nil));
        }


        public static MonsterDataStorage getStats(Creature Type) {
            return stats.get(Type);
        }

    }

}
