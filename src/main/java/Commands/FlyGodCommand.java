package Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FlyGodCommand implements CommandExecutor {

    // Stores UUIDs of players who have god mode enabled
    private final Set<UUID> godModePlayers = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Check if the command is "fly"; otherwise, assume it's "god"
        boolean isFly = cmd.getName().equalsIgnoreCase("fly");

        Player target;

        // If a player name is specified, attempt to get the Player object
        if (args.length > 0) {
            target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                // Send error message if player not found
                sender.sendMessage("§cPlayer not found: " + args[0]);
                return true;
            }
        }
        else {
            // If no target specified, ensure the sender is a player
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cYou must specify a player.");
                return true;
            }
            target = (Player) sender;
        }

        if (isFly) {
            // Toggle flight ability for the target player
            boolean newFlight = !target.getAllowFlight();
            target.setAllowFlight(newFlight);
            target.sendMessage("§aFlight has been " + (newFlight ? "enabled" : "disabled") + ".");
            // If flight was just enabled, automatically make the player fly
            if (!target.isFlying() && newFlight) target.setFlying(true);
        }
        else {
            // Toggle god mode status for the target player
            UUID id = target.getUniqueId();
            boolean newGod = !godModePlayers.contains(id);
            if (newGod) {
                godModePlayers.add(id);
            } else {
                godModePlayers.remove(id);
            }
            target.sendMessage("§aGod mode has been " + (newGod ? "enabled" : "disabled") + ".");
        }

        // Notify the command sender if they toggled another player's status
        if (!target.equals(sender)) {
            sender.sendMessage("§aYou toggled " + (isFly ? "flight" : "god mode") + " for §e" + target.getName() + "§a.");
        }

        return true;
    }

    // Returns true if the player currently has god mode enabled
    public boolean isGod(Player player) {
        return godModePlayers.contains(player.getUniqueId());
    }

    // Disables god mode for the specified player
    public void disableGod(Player player) {
        godModePlayers.remove(player.getUniqueId());
    }
}