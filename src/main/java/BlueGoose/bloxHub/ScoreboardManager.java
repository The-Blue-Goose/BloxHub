package BlueGoose.bloxHub;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.bukkit.ChatColor;

import java.util.List;

public class ScoreboardManager {

    public final org.bukkit.plugin.java.JavaPlugin plugin;

    public ScoreboardManager(org.bukkit.plugin.java.JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void showScoreboard(Player player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = board.registerNewObjective("hotbox", "dummy",
                plugin.getConfig().getString("scoreboard.title", "§a§lHOT §2§lBLOX"));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        List<String> lines = plugin.getConfig().getStringList("scoreboard.lines");
        int score = lines.size();

        for (String line : lines) {
            // Replace placeholders
            line = line
                    .replace("%player%", player.getName())
                    .replace("%online_players%", String.valueOf(Bukkit.getOnlinePlayers().size()))
                    .replace("%max_players%", String.valueOf(Bukkit.getMaxPlayers()));

            // Make sure the line is unique. You can add invisible color codes
            String uniqueEntry = line + ChatColor.values()[score % ChatColor.values().length];

            Score scoreLine = objective.getScore(uniqueEntry);
            scoreLine.setScore(score); // Still required, but will not show if we manipulate client look

            score--;
        }

        player.setScoreboard(board);
    }


    public void hideScoreboard(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
}
