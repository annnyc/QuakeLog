import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Game {
    private int totalKills;
    private Set<String> players;
    private Map<String, Integer> kills;

    public Game() {
        this.totalKills = 0;
        this.players = new HashSet<>();
        this.kills = new HashMap<>();
    }

    public void incrementTotalKills() {
        this.totalKills++;
    }

    public void addPlayer(String player) {
        this.players.add(player);
    }

    public void addKill(String player) {
        this.kills.put(player, this.kills.getOrDefault(player, 0) + 1);
    }

    public void subtractKill(String player) {
        this.kills.put(player, this.kills.getOrDefault(player, 0) - 1);
    }

    public int getTotalKills() {
        return totalKills;
    }

    public Set<String> getPlayers() {
        return players;
    }

    public Map<String, Integer> getKills() {
        return kills;
    }
}
