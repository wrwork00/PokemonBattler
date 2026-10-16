package pokemon;

public class DmgAttack extends Attack {

    public DmgAttack() {
    }

    public DmgAttack(String name, int baseDamage, int accuracy, Type type) {
        super(name, baseDamage, accuracy, type);
    }

    @Override
    public String perform(Pokemon user, Pokemon target) {

        // Slumpar ett tal mellan 1 och 100 för att avgöra om attacken träffar.
        int hitChance = (int) (Math.random() * 100) + 1;

        // Om slumpvärdet är högre än träffsäkerheten missar attacken.
        if (hitChance > getAccuracy()) {
            return user.getName() + "'s " + getName() + " missed!";
        }

        // Hämtar typens skadefaktor mot motståndarens typ.
        double typeMultiplier = getType().getDamageMultiplier(target.getType());

        // Hanterar attacker som inte har någon effekt.
        if (typeMultiplier == 0.0) {
            return user.getName() + " used " + getName()
                    + ", but it had no effect on " + target.getName() + "!";
        }

        // Neutral skada är alltid basskadan.
        int damage = getBaseDamage();

        // Typfördel eller typnackdel ändrar basskadan.
        if (typeMultiplier != 1.0) {
            damage = (int) (getBaseDamage() * typeMultiplier);
        }

        // 15 % chans till en kritisk träff.
        boolean criticalHit = Math.random() < 0.15;

        if (criticalHit) {
            damage = (int) (damage * 1.5);
        }

        int hpBefore = target.getCurrentHp();

        target.takeDmg(damage);

        int actualDamage = hpBefore - target.getCurrentHp();

        String message = user.getName() + " used " + getName()
                + " on " + target.getName()
                + " for " + actualDamage + " damage.";

        if (criticalHit) {
            message += " Critical hit!";
        }

        return message;
    }

    @Override
    public String label() {
        return getName();
    }
}