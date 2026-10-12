package pokemon;

import exception.InvalidPokemonException;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {

    // info om Pokémon
    private String name;
    private Type type;
    private int maxHp;
    private int currentHp;

    // Attack lista
    private final List<Attack> attacks;


    public String getName() {

        return name;
    }
    public void setName(String name) throws InvalidPokemonException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidPokemonException("Pokémon name cannot be empty.");
        }

        name = name.trim();

        this.name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

    }

    public Type getType() {

        return type;
    }
    public void setType(Type type) throws InvalidPokemonException {
        if (type == null) {
            throw new InvalidPokemonException("Pokémon type must be selected.");
        }

        this.type = type;
    }

    public int getMaxHp() {

        return maxHp;
    }

    public void setMaxHp(int maxHp) throws InvalidPokemonException{
        if (maxHp <= 0 || maxHp >= 1000) {
            throw new InvalidPokemonException("Pokémon HP must be between 1 and 999.");
        }
        this.maxHp = maxHp;
        this.currentHp = maxHp;
    }



    // Attack lista
    public void addAttack(Attack attack) throws InvalidPokemonException {
        if (attack == null) {
            throw new InvalidPokemonException("Attack cannot be null.");
        }

        if (attacks.size() >= 4) {
            throw new InvalidPokemonException("Pokémon cannot have more than 4 attacks.");
        }

        attacks.add(attack);
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    public Pokemon() {
        this.attacks = new ArrayList<>();
    }

    public Pokemon(String name, Type type, int maxHp, int currentHp) throws InvalidPokemonException {
        this.name = name;
        this.type = type;
        this.maxHp = maxHp;
        this.currentHp = currentHp;

        // Tom lista som sparar attacker
        this.attacks = new ArrayList<>();
    }

    public String toString() {

        return name + " - " + type + " - " + maxHp + " HP" +
                " - Attacks: " + attacks;
    }

    public void takeDmg (int dmg) {
        currentHp -= dmg;

        if (currentHp < 0) {
            currentHp = 0;
        }
    }

    public int getCurrentHp() {
        return currentHp;
    }

    // Kontrollerar om Pokémon HP är 0 eller lägre, för att se om den är besegrad eller ej
    public boolean isDefeated() {
        return currentHp <= 0;
    }

    public void restoreHp() {
        currentHp = maxHp;
    }

}
