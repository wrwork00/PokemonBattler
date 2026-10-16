package pokedex;

import com.fasterxml.jackson.databind.ObjectMapper;
import exception.InvalidPokemonException;
import exception.PokemonNotFoundException;
import pokemon.Attack;
import pokemon.HealAttack;
import pokemon.Pokemon;
import pokemon.Type;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class Pokedex {
    // Lista för alla pokemons
    private List<Pokemon> pokemons;

    // Tom lista som sparar pokemons
    public Pokedex() {
        this.pokemons = new ArrayList<>();
    }

    public void addPokemon(Pokemon pokemon) throws InvalidPokemonException {

        if (pokemon.getAttacks() == null || pokemon.getAttacks().isEmpty()) {
            throw new InvalidPokemonException("Pokémon must have at least 1 attack.");
        }

        for (Pokemon existingPokemon : pokemons) {
            if (existingPokemon.getName().equalsIgnoreCase(pokemon.getName())) {
                throw new InvalidPokemonException("A Pokémon with this name already exists.");
            }
        }

        pokemons.add(pokemon);
    }

    public List<Pokemon> searchPokemonByName(String name) throws PokemonNotFoundException {

        List<Pokemon> results = new ArrayList<>();

        for (Pokemon pokemon : pokemons) {
            if (pokemon.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(pokemon);
            }
        }

        if (results.isEmpty()) {
            throw new PokemonNotFoundException("No Pokémon found.");
        }

        return results;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void save(){
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(Path.of("pokedex.json").toFile(), pokemons);
            System.out.println("Pokédex saved!");
        } catch (IOException e) {
            System.out.println("Could not save Pokédex." + e.getMessage());
        }
    }

    public boolean load() {

        List<Pokemon> loadedPokemons;
        ObjectMapper mapper = new ObjectMapper();

        try {
            loadedPokemons = mapper.readValue(
                    Path.of("pokedex.json").toFile(),
                    mapper.getTypeFactory().constructCollectionType(List.class, Pokemon.class)
            );

            pokemons = loadedPokemons;
            System.out.println("Pokédex loaded!");
            return true;

        } catch (IOException e) {
            System.out.println("Could not load Pokédex: " + e.getMessage());
            return false;
        }
    }

    public void addStarterPokemon() throws InvalidPokemonException {
        //Skapar attacker: name, baseDamage, accuracy, type
        //BASIC attacks för alla Pokémon
        Attack tackle = Attack.getAttackByName("Tackle");
        Attack heavyAttack = Attack.getAttackByName("Heavy Attack");
        Attack tailwhip = Attack.getAttackByName("Tail Whip");

        Attack flameBlast = Attack.getAttackByName("Flame Blast");
        Attack flameThrow = Attack.getAttackByName("Flame Throw");

        Attack waterBlast = Attack.getAttackByName("Water Blast");
        Attack bubbleBeam = Attack.getAttackByName("Bubble Beam");

        Attack vineWhip = Attack.getAttackByName("Vine Whip");
        Attack grassDust = Attack.getAttackByName("Grass Dust");

        Attack thunderBolt = Attack.getAttackByName("Thunder Bolt");
        Attack lightningStrike = Attack.getAttackByName("Lightning Strike");

        Attack shadowBall = Attack.getAttackByName("Shadow Ball");
        Attack psychosis = Attack.getAttackByName("Psychosis");
        Attack fullRestore = Attack.getAttackByName("Full Restore");

        //Skapar Pokémon: name, type, maxHp, currentHp
        List<Pokemon> starterPokemon = createStarterPokemon();

        // Lägger till attacker till varje Pokémon
        addAttacks(starterPokemon.get(0), tackle, flameBlast, flameThrow);
        addAttacks(starterPokemon.get(1), tackle, waterBlast, bubbleBeam);
        addAttacks(starterPokemon.get(2), tackle, vineWhip, grassDust);
        addAttacks(starterPokemon.get(3), tackle, thunderBolt, lightningStrike);
        addAttacks(starterPokemon.get(4), tackle, heavyAttack, tailwhip);
        addAttacks(starterPokemon.get(5), tackle, shadowBall, psychosis);
        addAttacks(starterPokemon.get(6), tackle, vineWhip, fullRestore);

        try {
            for (Pokemon pokemon : starterPokemon) {
                addPokemon(pokemon);
            }
        } catch (InvalidPokemonException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<Pokemon> createStarterPokemon() throws InvalidPokemonException {
        List<Pokemon> starterPokemon = new ArrayList<>();

        starterPokemon.add(new Pokemon("Charmander", Type.FIRE, 100, 100));
        starterPokemon.add(new Pokemon("Squirtle", Type.WATER, 110, 110));
        starterPokemon.add(new Pokemon("Bulbasaur", Type.GRASS, 120, 120));
        starterPokemon.add(new Pokemon("Pikachu", Type.ELECTRIC, 90, 90));
        starterPokemon.add(new Pokemon("Eevee", Type.NORMAL, 80, 80));
        starterPokemon.add(new Pokemon("Gengar", Type.GHOST, 150, 150));
        starterPokemon.add(new Pokemon("Celebi", Type.GRASS, 90, 90));

        return starterPokemon;
    }

    private void addAttacks(Pokemon pokemon, Attack... attacks) throws InvalidPokemonException{
        for (Attack attack : attacks) {
            pokemon.addAttack(attack);
        }
    }
}