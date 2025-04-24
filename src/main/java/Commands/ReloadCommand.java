package Commands;

import Utils.NametagEdit;
import Managers.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    // References to the plugin's custom scoreboard manager and scoreboard command
    private final ScoreboardManager scoreboardManager;
    private final ScoreboardCommand scoreboardCommand;

    // Constructor to inject dependencies for scoreboard handling
    public ReloadCommand(ScoreboardManager scoreboardManager, ScoreboardCommand scoreboardCommand) {
        this.scoreboardManager = scoreboardManager;
        this.scoreboardCommand = scoreboardCommand;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Reload the plugin's config file
        Bukkit.getPluginManager().getPlugin("BloxHub").reloadConfig();

        // Re-initialize the custom name tag team
        NametagEdit.setupTeam();

        // Re-add all online players to the name tag team
        for (Player player : Bukkit.getOnlinePlayers()) {
            NametagEdit.addPlayer(player);
        }

        // Ensure scoreboards are updated for all players
        NametagEdit.updateAll();

        // Reapply the scoreboard to all players who haven't toggled it off
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!scoreboardCommand.isToggled(player)) {
                scoreboardManager.showScoreboard(player);
            }
        }

        // Confirm reload success to command sender
        sender.sendMessage("§a[§bBloxHub§a] Plugin successfully reloaded");
        return true;
    }
}