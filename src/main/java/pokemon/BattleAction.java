package pokemon;

public interface BattleAction {

    String perform(Pokemon user, Pokemon target);

    String label();
}