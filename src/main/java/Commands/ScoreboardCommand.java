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

    // Stores UUIDs of players who have toggled the scoreboard off
    private final Set<UUID> toggledOff = new HashSet<>();

    // Constructor to inject the custom ScoreboardManager
    public ScoreboardCommand(ScoreboardManager scoreboardManager) {
        this.scoreboardManager = scoreboardManager;
    }

    // Check if a player has toggled off the scoreboard
    public boolean isToggled(Player player) {
        return toggledOff.contains(player.getUniqueId());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Only allow players to run this command (not console)
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        // Toggle scoreboard on/off based on current state
        if (toggledOff.contains(player.getUniqueId())) {
            //Enable scoreboard
            toggledOff.remove(player.getUniqueId());
            scoreboardManager.showScoreboard(player);
            player.sendMessage("§aScoreboard enabled.");
        }
        else {
            //Disable scoreboard
            toggledOff.add(player.getUniqueId());
            scoreboardManager.hideScoreboard(player);
            player.sendMessage("§cScoreboard disabled.");
        }

        return true;
    }
}
