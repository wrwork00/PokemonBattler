package pokemon;

import java.util.ArrayList;
import java.util.List;

public abstract class Attack {

    // Attack Info
    private String name;
    private int baseDamage;
    private int accuracy;
    private Type type;

    public Attack() {
    }

    // Konstruktion av attacker (Skapar info till objekt)
    public Attack(String name, int baseDamage, int accuracy, Type type) {
        this.name = name;
        this.baseDamage = baseDamage;
        this.accuracy = accuracy;
        this.type = type;
    }
    public int getBaseDamage() {
        return baseDamage;
    }
    public int getAccuracy() {
        return accuracy;
    }
    public Type getType() {
        return type;
    }

    // Getter (Hämtar objekt info)
    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    public static List<Attack> createDefaultAttacks() {
        List<Attack> attacks = new ArrayList<>();

        attacks.add(getAttackByName("Tackle"));
        attacks.add(getAttackByName("Basic Attack"));

        return attacks;
    }

    public static Attack getAttackByName(String name) {
        return switch (name) {
            case "Tackle" -> new DmgAttack("Tackle", 20, 100, Type.NORMAL);
            case "Quick Attack" -> new DmgAttack("Quick Attack", 35, 70, Type.NORMAL);
            case "Heavy Attack" -> new DmgAttack("Heavy Attack", 50, 50, Type.NORMAL);
            case "Tail Whip" -> new DmgAttack("Tail Whip", 20, 100, Type.NORMAL);
            case "Flame Blast" -> new DmgAttack("Flame Blast", 50, 70, Type.FIRE);
            case "Flame Throw" -> new DmgAttack("Flame Throw", 60, 60, Type.FIRE);
            case "Water Blast" -> new DmgAttack("Water Blast", 50, 70, Type.WATER);
            case "Bubble Beam" -> new DmgAttack("Bubble Beam", 60, 60, Type.WATER);
            case "Vine Whip" -> new DmgAttack("Vine Whip", 50, 70, Type.GRASS);
            case "Grass Dust" -> new DmgAttack("Grass Dust", 40, 100, Type.GRASS);
            case "Thunder Bolt" -> new DmgAttack("Thunder Bolt", 40, 100, Type.ELECTRIC);
            case "Lightning Strike" -> new DmgAttack("Lightning Strike", 50, 70, Type.ELECTRIC);
            case "Shadow Ball" -> new DmgAttack("Shadow Ball", 40, 100, Type.GHOST);
            case "Psychosis" -> new DmgAttack("Psychosis", 200, 20, Type.GHOST);
            case "Basic Attack" -> new DmgAttack("Basic Attack", 35, 70, Type.NORMAL);

            default -> throw new IllegalArgumentException("Unknown attack: " + name);
        };
    }

    public static List<Attack> allAttacks() {
        List<Attack> attacks = new ArrayList<>();

        attacks.add(getAttackByName("Tackle"));
        attacks.add(getAttackByName("Quick Attack"));
        attacks.add(getAttackByName("Heavy Attack"));
        attacks.add(getAttackByName("Tail Whip"));
        attacks.add(getAttackByName("Flame Blast"));
        attacks.add(getAttackByName("Flame Throw"));
        attacks.add(getAttackByName("Water Blast"));
        attacks.add(getAttackByName("Bubble Beam"));
        attacks.add(getAttackByName("Vine Whip"));
        attacks.add(getAttackByName("Grass Dust"));
        attacks.add(getAttackByName("Thunder Bolt"));
        attacks.add(getAttackByName("Lightning Strike"));
        attacks.add(getAttackByName("Shadow Ball"));
        attacks.add(getAttackByName("Psychosis"));
        attacks.add(getAttackByName("Basic Attack"));

        return attacks;
    }
}
