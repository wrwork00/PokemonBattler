package pokemon;

public class DmgAttack extends Attack implements BattleAction{

    public DmgAttack() {
    }

    public DmgAttack(String name, int baseDamage, int accuracy, Type type) {
        super(name, baseDamage, accuracy, type);
    }

    @Override
    public void perform(Pokemon user, Pokemon target) {
        // Attack skada på motståndarens Pokémon.
        target.takeDmg(getBaseDamage());

    }

    @Override
    public String label() {
        // Hämtar attackens namn.
        return getName();
    }
}
