package Managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.bukkit.ChatColor;
import Utils.PlaceholderUtil;

import java.util.List;

public class ScoreboardManager {

    // Reference to the main plugin instance for accessing config values
    public final org.bukkit.plugin.java.JavaPlugin plugin;

    // Constructor to initialize the manager with the plugin instance
    public ScoreboardManager(org.bukkit.plugin.java.JavaPlugin plugin) {
        this.plugin = plugin;
    }

    // Displays a custom scoreboard to the given player
    public void showScoreboard(Player player) {
        // Create a new scoreboard instance
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        // Register a new objective for the sidebar display
        Objective objective = board.registerNewObjective(
                "hotbox",
                "dummy",
                plugin.getConfig().getString(   //Title from config
                        "scoreboard.title",
                        "§a§lHOT §2§lBLOX"));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR); //Display in sidebar

        // Get lines from config and start scoring from highest to lowest
        List<String> lines = plugin.getConfig().getStringList("scoreboard.lines");
        int score = lines.size();

        for (String line : lines) {
            // Replace placeholders with dynamic values (using PlaceholderAPI or custom util)
            line = PlaceholderUtil.setPlaceholders(player, line);

            // Add a color suffix to make sure each line is unique (scoreboard requires unique entries)
            String uniqueEntry = line + ChatColor.values()[score % ChatColor.values().length];

            // Add the line with a score to control the order
            Score scoreLine = objective.getScore(uniqueEntry);
            scoreLine.setScore(score); // Score controls the vertical order (higher = top)

            score--;
        }

        // Apply the scoreboard to the player
        player.setScoreboard(board);
    }

    // Hides the scoreboard by setting a blank scoreboard
    public void hideScoreboard(Player player) {
        // Set an empty scoreboard to remove the sidebar from view
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
}
