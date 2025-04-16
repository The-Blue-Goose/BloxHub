package BlueGoose.bloxHub;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardListener implements Listener {

    private final ScoreboardManager scoreboardManager;
    private final ScoreboardCommand scoreboardCommand;

    public ScoreboardListener(ScoreboardManager scoreboardManager, ScoreboardCommand scoreboardCommand) {
        this.scoreboardManager = scoreboardManager;
        this.scoreboardCommand = scoreboardCommand;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        refreshAllScoreboards();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Bukkit.getScheduler().runTaskLater(scoreboardManager.plugin, this::refreshAllScoreboards, 1L);
    }

    private void refreshAllScoreboards() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!scoreboardCommand.isToggled(player)) {
                scoreboardManager.showScoreboard(player);
            }
        }
    }
}
