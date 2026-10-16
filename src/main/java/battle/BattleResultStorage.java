package battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BattleResultStorage {

    private static final String FILE_NAME = "battle-results.json";

    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    // Sparar ett nytt resultat tillsammans med tidigare resultat.
    public static void saveResult(BattleResult newResult) {
        List<BattleResult> results = loadResults();

        results.add(newResult);

        try {
            mapper.writeValue(new File(FILE_NAME), results);
        } catch (IOException e) {
            System.out.println("Could not save battle results: " + e.getMessage());
        }
    }

    // Läser tidigare sparade resultat från JSON filen.
    public static List<BattleResult> loadResults() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        if (file.length() == 0) {
            return new ArrayList<>();
        }

        try {
            BattleResult[] results = mapper.readValue(file, BattleResult[].class);
            return new ArrayList<>(Arrays.asList(results));
        } catch (IOException e) {

            return new ArrayList<>();
        }
    }

    public static void showResults() {

        // Hämtar tidigare sparade stridsresultat från JSON-filen.
        List<BattleResult> results = loadResults();

        if (results.isEmpty()) {
            System.out.println("No battle results found.");
            return;
        }

        System.out.println("\n=== Battle Results ===");

        // Går igenom alla sparade resultat och skriver ut dem.
        for (BattleResult result : results) {
            System.out.println(
                    result.getPlayerPokemon()
                            + " vs " + result.getOpponentPokemon()
                            + " - " + result.getResult()
            );
        }
    }

    public static void resetResults() {
        try {
            mapper.writeValue(new File(FILE_NAME), new ArrayList<BattleResult>());
            System.out.println("Battle results have been reset!");
        } catch (IOException e) {
            System.out.println("Could not reset battle results: " + e.getMessage());
        }
    }

}