package pokemon;

public interface BattleAction {

    void perform(Pokemon user, Pokemon target);

    String label();
}

