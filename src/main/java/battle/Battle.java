package battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exception.InvalidPokemonException;
import pokemon.Type;
import pokemon.Attack;
import pokemon.Pokemon;
import pokemon.BattleAction;

public class Battle {

    private Pokemon playerPokemon;
    private Pokemon opponentPokemon;

    public Battle(Pokemon playerPokemon, Pokemon opponentPokemon) {
        this.playerPokemon = playerPokemon;
        this.opponentPokemon = opponentPokemon;
    }

    public String playerAttack(BattleAction attack) {

        if (playerPokemon.isDefeated()) {
            return playerPokemon.getName() + " has no HP left and cannot attack.";
        }

        // Utför spelarens attack och sparar resultatet.
        String message = attack.perform(playerPokemon, opponentPokemon);
        battleLog.add(message);
        return message;
    }

    public String opponentAttack(BattleAction attack) {

        if (opponentPokemon.isDefeated()) {
            return opponentPokemon.getName() + " has no HP left and cannot attack.";
        }

        // Utför motståndarens attack och sparar resultatet.
        String message = attack.perform(opponentPokemon, playerPokemon);
        battleLog.add(message);
        return message;
    }

    public List<BattleAction> getPlayerActions() {
        List<BattleAction> actions = new ArrayList<>();

        // Går igenom spelarens attacker och lägger varje attack som ett BattleAction.
        for (Attack attack : playerPokemon.getAttacks()) {
            actions.add(attack);
        }

        return actions;
    }

    // // Visar spelarens attacker och val
    public void showPlayerActions() {
        List<BattleAction> actions = getPlayerActions();

        System.out.println();
        System.out.println("=== Choose Attack ===");

        for (int i = 0; i < actions.size(); i++) {
            System.out.println((i + 1) + ". " + actions.get(i).label());
        }

        System.out.println("0. Cancel");
    }

    public BattleAction choosePlayerAction(Scanner input) {

        List<BattleAction> actions = getPlayerActions();

        // Kontrollerar att Pokémon har minst en attack.
        if (actions.isEmpty()) {
            System.out.println("Your Pokémon has no attacks!");
            return null;
        }

        showPlayerActions();

        while (true) {
            System.out.print("Choose an attack (0 to cancel): ");

            try {
                int choice = Integer.parseInt(input.nextLine());

                if (choice == 0) {
                    return null;
                }

                if (choice < 1 || choice > actions.size()) {
                    System.out.println("Invalid attack choice!");
                    continue;
                }

                return actions.get(choice - 1);

            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
            }
        }
    }

    public List<BattleAction> getOpponentActions() {
        List<BattleAction> actions = new ArrayList<>();

        // Går igenom motståndarens attacker och lägger varje attack som ett BattleAction.
        for (Attack attack : opponentPokemon.getAttacks()) {
            actions.add(attack);
        }

        return actions;
    }

    public BattleAction chooseOpponentAction() {
        List<BattleAction> actions = getOpponentActions();

        // Kontrollerar att motståndaren har minst en attack.
        if (actions.isEmpty()) {
            return null;
        }

        // Väljer slumpmässigt en av motståndarens attacker.
        int randomIndex = (int) (Math.random() * actions.size());
        return actions.get(randomIndex);
    }


    // Visar och väljer Pokémon
    public Pokemon choosePlayerPokemon(List<Pokemon> pokemons, Scanner input) {
        System.out.println();
        System.out.println("=== Choose Your Pokémon ===");

        for (int i = 0; i < pokemons.size(); i++) {
            System.out.println((i + 1) + ". " + pokemons.get(i).getName());
        }

        System.out.println("0. Cancel");

        while (true) {
            System.out.print("Choose a Pokémon: ");

            try {
                int choice = Integer.parseInt(input.nextLine());

                if (choice == 0) {
                    return null;
                }

                if (choice < 1 || choice > pokemons.size()) {
                    System.out.println("Invalid choice!");
                    continue;
                }

                return pokemons.get(choice - 1);

            } catch (NumberFormatException e) {
                System.out.println("Invalid input!");
            }
        }
    }

    // Skapar en pool med vilda Pokémon som CPU:n kan välja mellan.
    private static List<Pokemon> createWildPokemonPool() {
        List<Pokemon> wildPokemon = new ArrayList<>();

        String[][] pokemonData = {
                {"Wild Charmander", "FIRE", "100", "Tackle", "Flame Blast", "Flame Throw"},
                {"Wild Squirtle", "WATER", "110", "Tackle", "Water Blast", "Bubble Beam"},
                {"Wild Bulbasaur", "GRASS", "120", "Tackle", "Vine Whip", "Grass Dust"},
                {"Wild Pikachu", "ELECTRIC", "90", "Tackle", "Thunder Bolt", "Lightning Strike"},
                {"Wild Eevee", "NORMAL", "80", "Tackle", "Heavy Attack", "Tail Whip"},
                {"Wild Gengar", "GHOST", "150", "Tackle", "Shadow Ball", "Psychosis"},
                {"Wild Magmar", "FIRE", "130", "Tackle", "Flame Blast", "Flame Throw"},
                {"Wild Golduck", "WATER", "140", "Tackle", "Water Blast", "Bubble Beam"},
                {"Wild Gloom", "GRASS", "125", "Tackle", "Vine Whip", "Grass Dust"},
                {"Wild Raichu", "ELECTRIC", "100", "Tackle", "Thunder Bolt", "Lightning Strike"}
        };

        try {
            for (String[] data : pokemonData) {
                Pokemon pokemon = new Pokemon(
                        data[0],
                        Type.valueOf(data[1]),
                        Integer.parseInt(data[2]),
                        Integer.parseInt(data[2])
                );

                for (int i = 3; i < data.length; i++) {
                    pokemon.addAttack(Attack.getAttackByName(data[i]));
                }

                wildPokemon.add(pokemon);
            }
        } catch (InvalidPokemonException e) {
            throw new IllegalStateException("Could not create wild Pokémon pool.", e);
        }

        return wildPokemon;
    }

    public static Pokemon chooseWildPokemon() {
        List<Pokemon> wildPokemon = createWildPokemonPool();

        int randomIndex = (int) (Math.random() * wildPokemon.size());

        return wildPokemon.get(randomIndex);
    }

    // Sparar meddelanden från striden i den ordning de inträffar.
    private final List<String> battleLog = new ArrayList<>();

    // Returnerar en kopia av stridsloggen så att den inte kan ändras utifrån.
    public List<String> getBattleLog() {
        return new ArrayList<>(battleLog);
    }

}


