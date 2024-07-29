import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        QuakeLogParser parser = new QuakeLogParser();
        try {
            parser.parseFile("../quake.log");
            List<Game> games = parser.getGames();
            Map<String, Object> result = new HashMap<>();
            for (int i = 0; i < games.size(); i++) {
                Game game = games.get(i);
                Map<String, Object> gameData = new HashMap<>();
                gameData.put("total_kills", game.getTotalKills());
                gameData.put("players", game.getPlayers());
                gameData.put("kills", game.getKills());
                result.put("game_" + (i + 1), gameData);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonOutput = gson.toJson(result);

            try (FileWriter writer = new FileWriter("parsed_log.json")) {
                writer.write(jsonOutput);
            }

            System.out.println(jsonOutput);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
