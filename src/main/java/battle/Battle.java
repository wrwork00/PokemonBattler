package battle;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public void playerAttack(BattleAction attack) {
        attack.perform(playerPokemon, opponentPokemon);
    }

    public void opponentAttack(BattleAction attack) {
        attack.perform(opponentPokemon, playerPokemon);
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

        System.out.println("=== Choose Attack ===");

        for (int i = 0; i < actions.size(); i++) {
            System.out.println((i + 1) + ". " + actions.get(i).label());
        }

        System.out.println("0. Cancel");
    }

    public BattleAction choosePlayerAction(Scanner input) {
        List<BattleAction> actions = getPlayerActions();

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

    // Väljer slumpmässigt en av motståndarens attacker.
    public BattleAction chooseOpponentAction() {
        List<BattleAction> actions = getOpponentActions();

        int randomIndex = (int) (Math.random() * actions.size());

        return actions.get(randomIndex);
    }

    // Visar och väljer Pokémon
    public Pokemon choosePlayerPokemon(List<Pokemon> pokemons, Scanner input) {
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

}
