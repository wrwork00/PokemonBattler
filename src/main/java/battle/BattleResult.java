package battle;

public class BattleResult {

    private String playerPokemon;
    private String opponentPokemon;
    private String result;

    public BattleResult() {
    }

    // Skapar ett nytt stridsresultat.
    public BattleResult(
            String playerPokemon,
            String opponentPokemon,
            String result) {
        this.playerPokemon = playerPokemon;
        this.opponentPokemon = opponentPokemon;
        this.result = result;
    }

    public String getPlayerPokemon() {
        return playerPokemon;
    }

    public String getOpponentPokemon() {
        return opponentPokemon;
    }

    public String getResult() {
        return result;
    }
}