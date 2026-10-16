package pokemon;

public class HealAttack extends Attack {

    public HealAttack() {
    }

    public HealAttack(String name, int baseDamage, int accuracy, Type type) {
        super(name, baseDamage, accuracy, type);
    }

    @Override
    public String perform(Pokemon user, Pokemon target) {
        // 25 % chans att healingen lyckas.
        if (Math.random() < 0.25) {
            user.restoreHp();
            return user.getName() + " used " + getName()
                    + " and restored its HP to full!";
        }

        return user.getName() + " used " + getName()
                + ", but the healing failed!";
    }

    @Override
    public String label() {
        return getName();
    }
}