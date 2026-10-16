package menu;

import exception.InvalidPokemonException;
import pokedex.Pokedex;

import battle.BattleResult;
import battle.BattleResultStorage;
import battle.Battle;
import pokemon.BattleAction;
import pokemon.Pokemon;

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
            System.out.println("9. Battle Results");
            System.out.println("10. Exit");
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

                        Pokemon opponent = Battle.chooseWildPokemon();

                        if (player.isDefeated()) {
                            System.out.println("Your Pokémon has no HP and cannot battle.");
                            break;
                        }

                        opponent.restoreHp();

                        // Skapar striden med spelarens och motståndarens Pokémon.
                        Battle battle = new Battle(player, opponent);

                        // Singlar slant för att avgöra vem som börjar.
                        boolean playerStarts = Math.random() < 0.5;
                        if (playerStarts) {
                            System.out.println("Coin flip: You attack first!");
                        } else {
                            System.out.println("Coin flip: CPU attacks first!");
                        }

                        System.out.println("Battle started!");
                        System.out.println(player.getName() + " vs " + opponent.getName());

                        // Håller koll på om spelaren avbryter striden.
                        boolean battleCancelled = false;

                        while (!player.isDefeated() && !opponent.isDefeated()) {

                            if (playerStarts) {
                                // Spelaren väljer en attack.
                                BattleAction chosenAction = battle.choosePlayerAction(input);

                                if (chosenAction == null) {
                                    if (player.getAttacks().isEmpty()) {
                                        System.out.println("Your Pokémon has no attacks. Battle ended.");
                                    } else {
                                        System.out.println("Fled from battle!");
                                    }

                                    battleCancelled = true;
                                    break;
                                }

                                System.out.println(battle.playerAttack(chosenAction));
                                System.out.println("Opponent HP: " + opponent.getCurrentHp());

                                if (opponent.isDefeated()) {
                                    System.out.println("Opponent is defeated!");
                                    break;
                                }

                            } else {
                                // CPU:n väljer en slumpmässig attack.
                                BattleAction opponentAction = battle.chooseOpponentAction();

                                if (opponentAction == null) {
                                    System.out.println("Opponent has no attacks. Battle ended.");
                                    break;
                                }

                                System.out.println(battle.opponentAttack(opponentAction));
                                System.out.println("Your HP: " + player.getCurrentHp());

                                if (player.isDefeated()) {
                                    System.out.println("Your Pokémon is defeated!");
                                    break;
                                }
                            }

                            // Växlar tur efter varje attack.
                            playerStarts = !playerStarts;
                        }

                        String result = null;

                        // Kontrollerar resultatet om striden inte avbröts.
                        if (!battleCancelled) {
                            if (player.isDefeated()) {
                                System.out.println("You lost the battle!");
                                result = "LOSS";
                            } else if (opponent.isDefeated()) {
                                System.out.println("You won the battle!");
                                result = "WIN";
                            }
                        }

                        // Sparar bara resultat om striden slutade med vinst eller förlust.
                        // Om spelaren avbröt striden sparas inget resultat.
                        if (result != null) {
                            BattleResult battleResult = new BattleResult(
                                    player.getName(),
                                    opponent.getName(),
                                    result
                            );

                            BattleResultStorage.saveResult(battleResult);
                            System.out.println("Battle result saved!");
                        }

                        if (!battle.getBattleLog().isEmpty()) {
                            System.out.println("\n=== Battle Log ===");

                            for (String message : battle.getBattleLog()) {
                                System.out.println(message);
                            }
                        }
                        // Återställer spelarens Pokémon efter avslutad strid.
                        player.restoreHp();
                    }

                    case 9 -> {
                        boolean resultsMenu = true;

                        while (resultsMenu) {
                            System.out.println("\n=== Battle Results ===");
                            System.out.println("1. Show Results");
                            System.out.println("2. Reset Results");
                            System.out.println("0. Back");
                            System.out.print("Choose an option: ");

                            int resultsChoice = readInt(input);

                            switch (resultsChoice) {
                                case 1 -> BattleResultStorage.showResults();

                                case 2 -> {
                                    System.out.print("Are you sure you want to reset all results? (yes/no): ");
                                    String confirmation = input.nextLine();

                                    if (confirmation.equalsIgnoreCase("yes")) {
                                        BattleResultStorage.resetResults();
                                    } else {
                                        System.out.println("Reset cancelled.");
                                    }
                                }

                                case 0 -> resultsMenu = false;

                                default -> System.out.println("Invalid choice!");
                            }
                        }
                    }

                    case 10 -> {

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
        } catch (java.util.NoSuchElementException e) {
            System.out.println("Input ended. Exiting program.");
            return 10;
        }
    }
}