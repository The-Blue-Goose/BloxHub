package Commands;

import Commands.NametagEdit;
import Managers.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    private final ScoreboardManager scoreboardManager;
    private final ScoreboardCommand scoreboardCommand;

    public ReloadCommand(ScoreboardManager scoreboardManager, ScoreboardCommand scoreboardCommand) {
        this.scoreboardManager = scoreboardManager;
        this.scoreboardCommand = scoreboardCommand;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Bukkit.getPluginManager().getPlugin("BloxHub").reloadConfig();

        NametagEdit.setupTeam();

        for (Player player : Bukkit.getOnlinePlayers()) {
            NametagEdit.addPlayer(player);
        }

        NametagEdit.updateAll();

        // Re-apply scoreboard to all players
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!scoreboardCommand.isToggled(player)) {
                scoreboardManager.showScoreboard(player);
            }
        }

        sender.sendMessage("§a[§bBloxHub§a] Plugin successfully reloaded");
        return true;
    }
}