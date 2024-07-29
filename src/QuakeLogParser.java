import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuakeLogParser {
    private List<Game> games;
    private Game currentGame;

    public QuakeLogParser() {
        this.games = new ArrayList<>();
        this.currentGame = null;
    }

    public void parseLine(String line) {
        if (line.contains("InitGame")) {
            if (this.currentGame != null) {
                this.games.add(this.currentGame);
            }
            this.currentGame = new Game();
        } else if (line.contains("Kill")) {
            parseKill(line);
        }
    }

    private void parseKill(String line) {
        this.currentGame.incrementTotalKills();
        Pattern pattern = Pattern.compile(".*Kill: \\d+ \\d+ \\d+: (.+) killed (.+) by .+");
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            String killer = matcher.group(1);
            String killed = matcher.group(2);
            if (killer.equals("<world>")) {
                this.currentGame.subtractKill(killed);
            } else {
                this.currentGame.addKill(killer);
                this.currentGame.addPlayer(killer);
            }
            this.currentGame.addPlayer(killed);
        }
    }

    public void finalizeGame() {
        if (this.currentGame != null) {
            this.games.add(this.currentGame);
        }
    }

    public List<Game> getGames() {
        return games;
    }

    public void parseFile(String filepath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                parseLine(line);
            }
        }
        finalizeGame();
    }
}
