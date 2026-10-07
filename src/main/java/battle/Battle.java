package battle;

import pokemon.Pokemon;
import pokemon.BattleAction;

public class Battle {

    private Pokemon playerPokemon;
    private Pokemon opponentPokemon;

    public Battle(Pokemon playerPokemon, Pokemon opponentPokemon) {
        this.playerPokemon = playerPokemon;
        this.opponentPokemon = opponentPokemon;
    }

    public void playerAttack(BattleAction attack) {
        attack.perform(playerPokemon, opponentPokemon);
    }



}
