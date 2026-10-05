package menu;

import exception.InvalidPokemonException;
import exception.PokemonNotFoundException;
import pokedex.Pokedex;
import pokemon.Attack;
import pokemon.Pokemon;
import pokemon.Type;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class PokedexMenu {

    public static void start(Scanner input, Pokedex pokedex) {
        pokemonMenu(input, pokedex);
    }

    private static void pokemonMenu(Scanner input, Pokedex pokedex) {

        // Boolean som håller koll på om menyn ska fortsätta köras
        boolean pokemonMenuRunning = true;

        while (pokemonMenuRunning) {

            System.out.println();
            System.out.println("=== Pokémon Menu ===");
            System.out.println("1. List Pokémon");
            System.out.println("2. Search Pokémon");
            System.out.println("3. Filter Pokémon");
            System.out.println("4. Sort Pokémon");
            System.out.println("5. Statistics");
            System.out.println("0. Back");
            System.out.print("Choose an option: ");

            int pokemonChoice = readInt(input, "Invalid input!");

            // Hanterar ogiltig text
            if (pokemonChoice == Integer.MIN_VALUE) {
                continue;
            }

            switch (pokemonChoice) {

                case 1 -> {
                    if (pokedex.getPokemons().isEmpty()) {
                        System.out.println("No Pokémon in Pokédex.");
                        break;
                    }

                    displayPokemons(pokedex);
                }

                case 2 -> searchPokemon(input, pokedex);

                case 3 -> filterPokemon(input, pokedex);

                case 4 -> sortPokemon(input, pokedex);

                case 5 -> {
                    if (pokedex.getPokemons().isEmpty()) {
                        System.out.println("No Pokémon in Pokédex.");
                        break;
                    }

                    displayStatistics(pokedex);
                }

                case 0 -> pokemonMenuRunning = false;

                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void searchPokemon(Scanner input, Pokedex pokedex) {

        System.out.print("Search Pokémon by name (0 to cancel): ");

        String searchName = input.nextLine();

        if (searchName.equals("0")) {
            return;
        }

        if (searchName.trim().isEmpty()) {
            System.out.println("Search cannot be empty!");
            return;
        }

        try {
            List<Pokemon> results = pokedex.searchPokemonByName(searchName);

            for (Pokemon pokemon : results) {
                System.out.println(pokemon);
            }

        } catch (PokemonNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void filterPokemon(Scanner input, Pokedex pokedex) {

        System.out.println("Choose type to filter:");
        typeList();
        System.out.print("Enter type (0 to cancel): ");

        String typeChoice = input.nextLine().toUpperCase();

        if (typeChoice.equals("0")) {
            return;
        }

        try {
            // Gör om texten från inputen till en Type från enum.
            Type selectedType = Type.valueOf(typeChoice);

            boolean found = false;

            for (Pokemon pokemon : pokedex.getPokemons()) {
                if (pokemon.getType() == selectedType) {
                    System.out.println(pokemon);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No Pokémon found with that type.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid type!");
        }
    }

    private static void sortPokemon(Scanner input, Pokedex pokedex) {

        System.out.println("1. Name");
        System.out.println("2. HP");
        System.out.println("3. Type");
        System.out.print("Sort by (0 to cancel): ");

        int sortChoice = readInt(input, "Invalid input!");

        if (sortChoice == Integer.MIN_VALUE || sortChoice == 0) {
            return;
        }

        // Sorterar namn, HP eller type i alfabetisk ordning
        switch (sortChoice) {

            case 1 -> {
                pokedex.getPokemons().sort(
                        Comparator.comparing(Pokemon::getName)
                );
                displaySortedPokemons(pokedex);
            }

            case 2 -> {
                pokedex.getPokemons().sort(
                        Comparator.comparing(Pokemon::getMaxHp)
                );
                displaySortedPokemons(pokedex);
            }

            case 3 -> {
                pokedex.getPokemons().sort(
                        Comparator.comparing(pokemon -> pokemon.getType().toString())
                );
                displaySortedPokemons(pokedex);
            }

            default -> System.out.println("Invalid sort choice!");
        }
    }

    public static void displayPokemons(Pokedex pokedex) {
        for (int i = 0; i < pokedex.getPokemons().size(); i++) {
            System.out.println((i + 1) + ". " + pokedex.getPokemons().get(i));
        }
    }

    private static void displayStatistics(Pokedex pokedex) {
        int totalPokemon = pokedex.getPokemons().size();
        int totalHp = 0;

        for (Pokemon pokemon : pokedex.getPokemons()) {
            totalHp += pokemon.getMaxHp();
        }

        // Gör om till double för att få exakt värde på average HP
        double averageHp = (double) totalHp / totalPokemon;

        System.out.println("=== Pokémon Statistics ===");
        System.out.println("Total Pokémon: " + totalPokemon);
        System.out.printf("Average HP: %.1f%n", averageHp);
    }

    private static void displaySortedPokemons(Pokedex pokedex) {
        for (Pokemon pokemon : pokedex.getPokemons()) {
            System.out.println(pokemon);
        }
    }

    private static void displayAttacks(List<Attack> attacks) {
        System.out.println("--------------------------------------------------------------------------");
        System.out.printf("%2s. %-20s | %-7s | %-12s | %-8s%n",
                "#", "Attack", "DMG", "Accuracy", "Type");
        System.out.println("--------------------------------------------------------------------------");

        // Hämtar en attack i taget
        for (int i = 0; i < attacks.size(); i++) {
            Attack attack = attacks.get(i);

            System.out.printf(
                    "%2d. %-20s | DMG: %3d | Accuracy: %3d%% | Type: %-8s%n",
                    i + 1,
                    attack.getName(),
                    attack.getBaseDamage(),
                    attack.getAccuracy(),
                    attack.getType()
            );
        }

        System.out.println("--------------------------------------------------------------------------");
    }

    public static int choosePokemon(Scanner input, Pokedex pokedex) {
        displayPokemons(pokedex);

        System.out.print("Choose Pokémon (0 to cancel): ");

        while (true) {

            int pokemonChoice = readInt(input, "");

            // readInt() skickar Integer.MIN_VALUE när användaren skriver fel input.
            if (pokemonChoice == Integer.MIN_VALUE) {
                System.out.println("Invalid Pokémon choice!");
                System.out.printf(
                        "Please choose a number between 1 - %s (0 to cancel): ",
                        pokedex.getPokemons().size()
                );
                continue;
            }

            if (pokemonChoice == 0) {
                return 0;
            }

            if (pokemonChoice < 1 || pokemonChoice > pokedex.getPokemons().size()) {
                System.out.println("Invalid Pokémon choice!");
                System.out.printf(
                        "Please choose a number between 1 - %s (0 to cancel): ",
                        pokedex.getPokemons().size()
                );
                continue;
            }

            return pokemonChoice;
        }
    }

    public static void addPokemon(Scanner input, Pokedex pokedex) {

        // Håller koll på om användaren avbryter
        boolean cancelled = false;

        System.out.print("Choose your new Pokémon (0 to cancel): ");

        String name = readName(input);

        if (name == null) {
            System.out.println("Add Pokémon cancelled.");
            return;
        }

        // Väljer type
        String type;
        Type pokemonType = null;
        boolean validType = false;

        while (!validType) {

            typeList();
            System.out.print("Choose type (0 to cancel): ");
            type = input.nextLine().toUpperCase();

            // 0 används för att avbryta
            if (type.equals("0")) {
                System.out.println("Add Pokémon cancelled.");
                cancelled = true;
                break;
            }

            try {
                pokemonType = Type.valueOf(type);
                validType = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid type!");
            }
        }

        // Väljer HP
        int maxHp = 0;
        boolean validHp = false;

        while (!validHp && !cancelled) {

            System.out.print("Choose max HP (0 to cancel): ");

            maxHp = readInt(input, "Invalid HP!");

            if (maxHp == Integer.MIN_VALUE) {
                continue;
            }

            // 0 används för att avbryta
            if (maxHp == 0) {
                System.out.println("Add Pokémon cancelled.");
                break;
            }
            // Regler för maxHP
            if (maxHp >= 1000) {
                System.out.println("HP must be less than 1000!");
            } else if (maxHp < 0) {
                System.out.println("HP must be greater than 0!");
            } else {
                validHp = true;
            }
        }

        if (validHp) {

            // Pokémon maxHP och currentHp samma vid start
            try {
                Pokemon newPokemon = new Pokemon(name, pokemonType, maxHp, maxHp);

                // Lägger till standard attacker vid skapande av ny pokemon
                for (Attack attack : Attack.createDefaultAttacks()) {
                    newPokemon.addAttack(attack);
                }

                pokedex.addPokemon(newPokemon);

                System.out.println(newPokemon.getName() + " was added to the Pokédex!");

            } catch (InvalidPokemonException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void editPokemon(Scanner input, Pokedex pokedex) {

        if (pokedex.getPokemons().isEmpty()) {
            System.out.println("No Pokémon in Pokédex.");
            return;
        }

        System.out.println("Choose a Pokémon to edit:");

        int pokemonChoice = choosePokemon(input, pokedex);

        if (pokemonChoice == 0) {
            System.out.println("Edit cancelled.");
            return;
        }

        Pokemon pokemon = pokedex.getPokemons().get(pokemonChoice - 1);

        System.out.println("What do you want to edit?");
        System.out.println("1. Name");
        System.out.println("2. Type");
        System.out.println("3. Max HP");
        System.out.println("4. Add Attack");
        System.out.println("5. Remove Attack");
        System.out.print("Choose an option (0 to cancel): ");

        int editChoice;

        while (true) {

            editChoice = readInt(input, "Invalid input!");

            if (editChoice == Integer.MIN_VALUE) {
                System.out.print("Please choose a number between 1 - 5 (0 to cancel): ");
                continue;
            }

            if (editChoice == 0) {
                System.out.println("Edit cancelled.");
                return;
            }

            if (editChoice < 1 || editChoice > 5) {
                System.out.println("Invalid edit choice!");
                System.out.print("Please choose a number between 1 - 5 (0 to cancel): ");
                continue;
            }

            break;
        }

        switch (editChoice) {

            case 1 -> {

                System.out.print("Choose new name (0 to cancel): ");

                String newName = readName(input);

                if (newName == null) {
                    System.out.println("Edit cancelled.");
                    break;
                }

                boolean nameExists = false;

                // Går igenom Pokémon för att kontrollera om namnet redan finns.
                for (Pokemon existingPokemon : pokedex.getPokemons()) {

                    // Hoppar över Pokémon vi redigerar.
                    if (existingPokemon == pokemon) {
                        continue;
                    }

                    if (existingPokemon.getName().equalsIgnoreCase(newName)) {
                        nameExists = true;
                        break;
                    }
                }

                if (nameExists) {
                    System.out.println("A Pokémon with this name already exists.");
                    break;
                }

                try {
                    pokemon.setName(newName);
                    System.out.println("Name updated!");
                } catch (InvalidPokemonException e) {
                    System.out.println(e.getMessage());
                }
            }

            case 2 -> {

                boolean validType = false;

                while (!validType) {

                    typeList();
                    System.out.print("Choose new type (0 to cancel): ");
                    String newType = input.nextLine().toUpperCase();

                    // 0 används för att avbryta
                    if (newType.equals("0")) {
                        System.out.println("Edit cancelled.");
                        break;
                    }

                    try {
                        Type pokemonType = Type.valueOf(newType);

                        if (pokemon.getType() == pokemonType) {
                            System.out.printf("%s is already a %s Pokémon!%n",
                                    pokemon.getName(), pokemonType);
                            break;
                        }

                        pokemon.setType(pokemonType);
                        validType = true;

                        System.out.printf("%s is now a %s Pokémon!%n",
                                pokemon.getName(), pokemonType);

                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid type!");
                    } catch (InvalidPokemonException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

            case 3 -> {

                int newMaxHp;
                boolean validNewHp = false;

                while (!validNewHp) {

                    System.out.print("Choose new max HP (0 to cancel): ");

                    newMaxHp = readInt(input, "");

                    if (newMaxHp == Integer.MIN_VALUE) {
                        System.out.println("Invalid HP!");
                        continue;
                    }

                    // 0 används för att avbryta
                    if (newMaxHp == 0) {
                        System.out.println("Edit cancelled.");
                        break;
                    }

                    // Kontrollerar regler för HP
                    if (newMaxHp >= 1000) {
                        System.out.println("HP must be less than 1000!");
                        continue;
                    }

                    if (newMaxHp <= 0) {
                        System.out.println("HP must be greater than 0!");
                        continue;
                    }

                    try {
                        pokemon.setMaxHp(newMaxHp);
                        validNewHp = true;
                    } catch (InvalidPokemonException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

            case 4 -> {

                // Lägger till en attack
                // En Pokémon kan ha högst 4 attacker
                if (pokemon.getAttacks().size() >= 4) {
                    System.out.println("Pokemon already has 4 attacks. Remove 1 attack atleast before adding a new one.");
                    break;
                }

                boolean validAttack = false;

                List<Attack> attacks = Attack.allAttacks();

                displayAttacks(attacks);

                while (!validAttack) {

                    System.out.print("Choose an attack (0 to cancel): ");

                    int attackChoice = readInt(input, "");

                    if (attackChoice == 0) {
                        System.out.println("Edit cancelled.");
                        break;
                    }

                    if (attackChoice == Integer.MIN_VALUE) {
                        System.out.println("Please choose a number between 1 - "
                                + attacks.size() + ".");
                        continue;
                    }

                    if (attackChoice < 1 || attackChoice > attacks.size()) {
                        System.out.println("Please choose a number between 1 - "
                                + attacks.size() + ".");
                        continue;
                    }

                    Attack selectedAttack = attacks.get(attackChoice - 1);

                    boolean alreadyHasAttack = false;

                    // Kontrollerar att samma attack inte läggs till flera gånger.
                    for (Attack attack : pokemon.getAttacks()) {
                        if (attack.getName().equals(selectedAttack.getName())) {
                            alreadyHasAttack = true;
                            break;
                        }
                    }

                    if (alreadyHasAttack) {
                        System.out.println("Pokemon already has this attack.");
                        continue;
                    }

                    try {
                        pokemon.addAttack(selectedAttack);
                        System.out.println(selectedAttack + " added to " + pokemon.getName());
                        validAttack = true;
                    } catch (InvalidPokemonException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

            case 5 -> {

                boolean removeAttack = false;

                while (!removeAttack) {

                    displayAttacks(pokemon.getAttacks());

                    System.out.print("Choose an attack to remove (0 to cancel): ");

                    int attackChoice = readInt(input, "");

                    // 0 används för att avbryta
                    if (attackChoice == 0) {
                        System.out.println("Edit cancelled.");
                        break;
                    }

                    if (attackChoice == Integer.MIN_VALUE) {
                        System.out.println("Please choose a number between 1 - "
                                + pokemon.getAttacks().size() + ".");
                        continue;
                    }

                    if (attackChoice < 1 || attackChoice > pokemon.getAttacks().size()) {
                        System.out.println("Please choose a number between 1 - "
                                + pokemon.getAttacks().size() + ".");
                        continue;
                    }

                    if (pokemon.getAttacks().size() == 1) {
                        System.out.println("Pokémon must have at least 1 attack.");
                        break;
                    }

                    Attack removedAttack = pokemon.getAttacks().get(attackChoice - 1);
                    pokemon.getAttacks().remove(removedAttack);

                    System.out.println(removedAttack + " removed from " + pokemon.getName());

                    removeAttack = true;
                }
            }
        }
    }

    public static void deletePokemon(Scanner input, Pokedex pokedex) {

        if (pokedex.getPokemons().isEmpty()) {
            System.out.println("No Pokémon in Pokédex.");
            return;
        }

        System.out.println("Choose Pokémon to delete:");

        int pokemonChoice = choosePokemon(input, pokedex);

        if (pokemonChoice == 0) {
            System.out.println("Delete cancelled.");
            return;
        }

        pokedex.getPokemons().remove(pokemonChoice - 1);
        System.out.println("Pokémon deleted.");
    }

    private static int readInt(Scanner input, String errorMessage) {
        try {
            return Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {

            // Integer.MIN_VALUE används som ett specialvärde för felaktig input.
            if (!errorMessage.isEmpty()) {
                System.out.println(errorMessage);
            }
            return Integer.MIN_VALUE;
        }
    }

    private static String readName(Scanner input) {

        while (true) {

            String name = input.nextLine();

            // 0 används för att avbryta
            if (name.equals("0")) {
                return null;
            }

            // Regler för namngivning
            if (name.trim().isEmpty()) {
                System.out.println("Name cannot be empty!");
                System.out.print("Try again (0 to cancel): ");
                continue;
            }

            if (name.length() > 30) {
                System.out.println("Name cannot be longer than 30 characters!");
                System.out.print("Try again (0 to cancel): ");
                continue;
            }

            return name;
        }
    }

    static void typeList() {
        System.out.println("FIRE");
        System.out.println("WATER");
        System.out.println("GRASS");
        System.out.println("ELECTRIC");
        System.out.println("NORMAL");
        System.out.println("GHOST");
    }
}