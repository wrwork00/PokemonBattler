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

        int hpBefore = target.getCurrentHp();

        target.takeDmg(getBaseDamage());

        int actualDamage = hpBefore - target.getCurrentHp();

        // Returnerar ett meddelande som kan visas och sparas i loggen.
        return user.getName() + " used " + getName()
                + " on " + target.getName()
                + " for " + actualDamage + " damage.";
    }

    @Override
    public String label() {
        // Hämtar attackens namn.
        return getName();
    }
}
