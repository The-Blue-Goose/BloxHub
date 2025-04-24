package Listeners;

import Commands.ScoreboardCommand;
import Managers.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardListener implements Listener {

    private final ScoreboardManager scoreboardManager;
    private final ScoreboardCommand scoreboardCommand;

    // Constructor to inject the ScoreboardManager and ScoreboardCommand dependencies
    public ScoreboardListener(ScoreboardManager scoreboardManager, ScoreboardCommand scoreboardCommand) {
        this.scoreboardManager = scoreboardManager;
        this.scoreboardCommand = scoreboardCommand;
    }

    // Event Handler - Refresh scoreboards when a player joins the server
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        refreshAllScoreboards(); //Ensure all players get the correct scoreboard
    }

    // Event Handler - Refresh scoreboards when a player quits, with a slight delay to ensure proper updates
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Run the refresh task with a slight delay to handle any changes after the player quits
        Bukkit.getScheduler().runTaskLater(scoreboardManager.plugin, this::refreshAllScoreboards, 1L);
    }

    // Helper method to refresh the scoreboard for all online players
    private void refreshAllScoreboards() {
        // Loop through all online players
        for (Player player : Bukkit.getOnlinePlayers()) {
            // Only show the scoreboard if the player hasn't toggled it off
            if (!scoreboardCommand.isToggled(player)) {
                scoreboardManager.showScoreboard(player); //Show the scoreboard
            }
        }
    }
}
