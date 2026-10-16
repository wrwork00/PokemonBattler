package pokemon;

// Pokémon-typer och deras typeffektivitet.
public enum Type {
    FIRE,
    WATER,
    GRASS,
    ELECTRIC,
    NORMAL,
    GHOST;

    public double getDamageMultiplier(Type defenderType) {

        if (this == NORMAL) {
            return 1.0;
        }

        // GHOST är effektivt mot GHOST.
        if (this == GHOST && defenderType == GHOST) {
            return randomAdvantage();
        }

        // FIRE
        if (this == FIRE && defenderType == GRASS) {
            return randomAdvantage();
        }

        if (this == FIRE &&
                (defenderType == WATER || defenderType == FIRE)) {
            return randomDisadvantage();
        }

        // WATER
        if (this == WATER && defenderType == FIRE) {
            return randomAdvantage();
        }

        if (this == WATER &&
                (defenderType == GRASS || defenderType == WATER)) {
            return randomDisadvantage();
        }

        // GRASS
        if (this == GRASS && defenderType == WATER) {
            return randomAdvantage();
        }

        if (this == GRASS &&
                (defenderType == FIRE || defenderType == GRASS)) {
            return randomDisadvantage();
        }

        // ELECTRIC
        if (this == ELECTRIC && defenderType == WATER) {
            return randomAdvantage();
        }

        if (this == ELECTRIC &&
                (defenderType == GRASS || defenderType == ELECTRIC)) {
            return randomDisadvantage();
        }

        // Alla övriga kombinationer är neutrala.
        return 1.0;
    }

    // Slumpar en multiplikator mellan 1.0 och 1.5.
    private double randomAdvantage() {
        return 1.0 + Math.random() * 0.5;
    }

    // Slumpar en multiplikator mellan 0.5 och 1.0.
    private double randomDisadvantage() {
        return 0.5 + Math.random() * 0.5;
    }
}