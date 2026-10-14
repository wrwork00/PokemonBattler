package menu;

import exception.InvalidPokemonException;
import pokedex.Pokedex;

import java.util.List;

import battle.BattleResult;
import battle.BattleResultStorage;
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
                        // Kontrollerar att spelaren äger minst en Pokémon.
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

                        // Avbryter om spelaren väljer 0.
                        if (player == null) {
                            System.out.println("Battle cancelled.");
                            break;
                        }

                        // CPU:n väljer en slumpmässig vild Pokémon.
                        Pokemon opponent = Battle.chooseWildPokemon();

                        // Återställer båda Pokémon till full HP inför striden.
                        player.restoreHp();
                        opponent.restoreHp();

                        // Skapar striden med spelarens och motståndarens Pokémon.
                        Battle battle = new Battle(player, opponent);

                        System.out.println("Battle started!");
                        System.out.println(player.getName() + " vs " + opponent.getName());

                        // Striden fortsätter tills en Pokémon blir besegrad.
                        while (!player.isDefeated() && !opponent.isDefeated()) {

                            // Spelaren väljer en attack.
                            BattleAction chosenAction = battle.choosePlayerAction(input);

                            // Avslutar striden om spelaren väljer 0.
                            if (chosenAction == null) {
                                System.out.println("Battle cancelled.");
                                break;
                            }

                            // Spelarens Pokémon utför attacken.
                            System.out.println(battle.playerAttack(chosenAction));
                            System.out.println("Opponent HP: " + opponent.getCurrentHp());

                            // Kontrollerar om motståndarens Pokémon har besegrats.
                            if (opponent.isDefeated()) {
                                System.out.println("Opponent is defeated!");
                                break;
                            }

                            // CPU:n väljer slumpmässigt en av sina attacker.
                            BattleAction opponentAction = battle.chooseOpponentAction();

                            // Motståndarens Pokémon attackerar spelarens Pokémon.
                            System.out.println(battle.opponentAttack(opponentAction));
                            System.out.println("Your HP: " + player.getCurrentHp());

                            // Kontrollerar om spelarens Pokémon har besegrats.
                            if (player.isDefeated()) {
                                System.out.println("Your Pokémon is defeated!");
                            }
                        }

                        String result = null;

                        // Kontrollerar resultatet när striden är avslutad.
                        if (player.isDefeated()) {
                            System.out.println("You lost the battle!");
                            result = "LOSS";
                        } else if (opponent.isDefeated()) {
                            System.out.println("You won the battle!");
                            result = "WIN";
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

                        System.out.println("\n=== Battle Log ===");

                        for (String message : battle.getBattleLog()) {
                            System.out.println(message);
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