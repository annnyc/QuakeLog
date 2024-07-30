import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class ReportGenerator {

    private static final String JSON_FILE_PATH = "parsed_log.json";

    public static void main(String[] args) {
        try {

            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Map<String, Object>>>() {}.getType();
            Map<String, Map<String, Object>> data = gson.fromJson(new FileReader(JSON_FILE_PATH), type);

            // Print match reports
            System.out.println("Match Reports:");
            for (Map.Entry<String, Map<String, Object>> entry : data.entrySet()) {
                String gameName = entry.getKey();
                Map<String, Object> gameData = entry.getValue();
                int totalKills = ((Number) gameData.get("total_kills")).intValue();
                List<String> players = (List<String>) gameData.get("players");
                Map<String, Double> kills = (Map<String, Double>) gameData.get("kills");

                System.out.println("\n" + gameName + ":");
                System.out.println("  Total Kills: " + totalKills);
                System.out.println("  Players: " + players);
                System.out.println("  Kill Statistics:");
                for (String player : players) {
                     int killCount = Math.max(kills.getOrDefault(player, 0.0).intValue(), 0);
                     if (killCount > 0)
                     System.out.println("    " + player + ": " + killCount);
                }
            }

            Map<String, Integer> playerKillCounts = new HashMap<>();
            for (Map<String, Object> gameData : data.values()) {
                Map<String, Double> kills = (Map<String, Double>) gameData.get("kills");
                for (Map.Entry<String, Double> entry : kills.entrySet()) {
                    playerKillCounts.put(entry.getKey(), playerKillCounts.getOrDefault(entry.getKey(), 0) + entry.getValue().intValue());
                }
            }


            List<Map.Entry<String, Integer>> sortedPlayers = new ArrayList<>(playerKillCounts.entrySet());
            sortedPlayers.sort((a, b) -> b.getValue().compareTo(a.getValue()));

            System.out.println("\nPlayer Rankings:");
            int rank = 1;
            for (Map.Entry<String, Integer> entry : sortedPlayers) {
                System.out.println("  Rank " + rank + ": " + entry.getKey() + " with " + entry.getValue() + " kills");
                rank++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
