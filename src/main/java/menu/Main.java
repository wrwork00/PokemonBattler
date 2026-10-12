package menu;

import exception.InvalidPokemonException;
import pokedex.Pokedex;

import java.util.List;

import battle.Battle;
import pokemon.Attack;
import pokemon.BattleAction;
import pokemon.Pokemon;
import pokemon.Type;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Welcome to Pokédex!");
        Scanner input = new Scanner(System.in);

        // Skapar en tom Pokédex
        Pokedex pokedex = new Pokedex();
        File file = new File("pokedex.json");

        // Kollar om en sparad fil finns, annars skapas start med start Pokémon
        if (file.exists()) {
            boolean loaded = pokedex.load();

            if (!loaded) {
                try {
                    pokedex.addStarterPokemon();
                } catch (InvalidPokemonException e) {
                    System.out.println(e.getMessage());
                }
            }

        } else {
            try {
                pokedex.addStarterPokemon();
            } catch (InvalidPokemonException e) {
                System.out.println(e.getMessage());
            }
        }


        // Håller koll på om programmet ska fortsätta köras
        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("=== Pokédex Menu ===");
            System.out.println("1. Pokémon");
            System.out.println("2. Add Pokémon");
            System.out.println("3. Edit Pokémon");
            System.out.println("4. Delete Pokémon");
            System.out.println("5. Save");
            System.out.println("6. Load");
            System.out.println("7. Reset");
            System.out.println("8. Start Battle");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");

            int choice = readInt(input);

            if (choice != Integer.MIN_VALUE) {
                switch (choice) {

                    case 1 -> PokedexMenu.start(input, pokedex);

                    //Skapa en ny Pokémon
                    case 2 -> PokedexMenu.addPokemon(input, pokedex);

                    case 3 -> PokedexMenu.editPokemon(input, pokedex);

                    case 4 -> PokedexMenu.deletePokemon(input, pokedex);

                    case 5 -> pokedex.save();
                    case 6 -> pokedex.load();

                    case 7 -> {
                        // Tömmer listan med Pokémon
                        pokedex.getPokemons().clear();

                        // Lägger tillbaka starter Pokémon
                        try {
                            pokedex.addStarterPokemon();
                        } catch (InvalidPokemonException e) {
                            System.out.println(e.getMessage());
                        }

                        System.out.println("Pokédex reset!");
                    }

                    case 8 -> {

                        if (pokedex.getPokemons().isEmpty()) {
                            System.out.println("You don't have any Pokémon!");
                            break;
                        }

                        // Låter spelaren välja vilken Pokémon som ska slåss.
                        Battle selection = new Battle(
                                pokedex.getPokemons().get(0),
                                pokedex.getPokemons().get(0)
                        );

                        Pokemon player = selection.choosePlayerPokemon(
                                pokedex.getPokemons(), input
                        );

                        if (player == null) {
                            System.out.println("Battle cancelled.");
                            break;
                        }

                        Pokemon opponent = null;

                        for (Pokemon pokemon : pokedex.getPokemons()) {
                            if (pokemon != player && !pokemon.isDefeated()) {
                                opponent = pokemon;
                                break;
                            }
                        }

                        if (opponent == null) {
                            System.out.println("No available opponent!");
                            break;
                        }

                        player.restoreHp();
                        opponent.restoreHp();

                        Battle battle = new Battle(player, opponent);

                        System.out.println("Battle started!");

                        // Behåll din befintliga while-loop här.

                        // Battle fortsätter så länge båda Pokémon fortfarande har HP kvar.
                        while (!player.isDefeated() && !opponent.isDefeated()) {

                            // Spelaren väljer en attack.
                            BattleAction chosenAction = battle.choosePlayerAction(input);

                            if (chosenAction == null) {
                                break;
                            }

                            // Spelarens valda attack utförs.
                            battle.playerAttack(chosenAction);

                            System.out.println("Opponent HP: " + opponent.getCurrentHp());

                            if (opponent.isDefeated()) {
                                System.out.println("Opponent is defeated!");
                                break;
                            }

                            // CPU:n väljer slumpmässigt en av sina attacker.
                            BattleAction opponentAction = battle.chooseOpponentAction();

                            // CPU:n attackerar spelarens Pokémon.
                            battle.opponentAttack(opponentAction);

                            System.out.println("Your HP: " + player.getCurrentHp());

                            if (player.isDefeated()) {
                                System.out.println("Your Pokémon is defeated!");
                            }
                        }
                    }

                    case 9 -> {

                        System.out.println("Exit");

                        // Sparar automatiskt innan programmet avslutas
                        pokedex.save();

                        running = false;

                    }

                    default -> System.out.println("Invalid choice");

                }
            }
        }
        input.close();
    }

    private static int readInt(Scanner input) {
        try {
            return Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
            return Integer.MIN_VALUE;
        }
    }
}