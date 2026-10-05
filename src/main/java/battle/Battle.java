package battle;

import pokemon.Pokemon;
import pokemon.Attack;

public class Battle {

    private Pokemon playerPokemon;
    private Pokemon opponentPokemon;

    public Battle(Pokemon playerPokemon, Pokemon opponentPokemon) {
        this.playerPokemon = playerPokemon;
        this.opponentPokemon = opponentPokemon;
    }

    public void playerAttack(Attack attack) {
        int damage = attack.getBaseDamage();
        opponentPokemon.takeDmg(damage);
    }



}
