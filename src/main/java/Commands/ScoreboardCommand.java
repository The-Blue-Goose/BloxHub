package Commands;

import Managers.ScoreboardManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ScoreboardCommand implements CommandExecutor {

    private final ScoreboardManager scoreboardManager;
    private final Set<UUID> toggledOff = new HashSet<>();

    public ScoreboardCommand(ScoreboardManager scoreboardManager) {
        this.scoreboardManager = scoreboardManager;
    }

    public boolean isToggled(Player player) {
        return toggledOff.contains(player.getUniqueId());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        if (toggledOff.contains(player.getUniqueId())) {
            toggledOff.remove(player.getUniqueId());
            scoreboardManager.showScoreboard(player);
            player.sendMessage("§aScoreboard enabled.");
        } else {
            toggledOff.add(player.getUniqueId());
            scoreboardManager.hideScoreboard(player);
            player.sendMessage("§cScoreboard disabled.");
        }

        return true;
    }
}
