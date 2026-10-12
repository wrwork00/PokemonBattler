package pokemon;

public class DmgAttack extends Attack {

    public DmgAttack() {
    }

    public DmgAttack(String name, int baseDamage, int accuracy, Type type) {
        super(name, baseDamage, accuracy, type);
    }

    @Override
    public void perform(Pokemon user, Pokemon target) {

        int hitChance = (int) (Math.random() * 100) + 1;


        if (hitChance > getAccuracy()) {
            System.out.println(getName() + " missed!");
            return;
        }


        target.takeDmg(getBaseDamage());

        System.out.println(
                user.getName() + " hit " + target.getName()
                        + " for " + getBaseDamage() + " damage."
        );
    }

    @Override
    public String label() {
        // Hämtar attackens namn.
        return getName();
    }
}
