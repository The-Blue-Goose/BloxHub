package Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class TeleportHereCommand implements CommandExecutor {

    public TeleportHereCommand() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Ensure the sender is a player (not console or command block)
        if (!(sender instanceof Player target)) return true;

        // Command usage: /tph [player]
        if (args.length == 1) {
            // Get the player to teleport
            Player from = Bukkit.getPlayer(args[0]);
            if (from != null) {
                // Teleport the target player to the sender
                from.teleport(target);
                target.sendMessage("§aTeleported " + from.getName() + " to you.");
                from.sendMessage("§aYou have been teleported to " + target.getName());
            }
            else {
                // Notify sender if player not found
                target.sendMessage("§cPlayer not found.");
            }
        }
        else {
            // Incorrect usage message
            target.sendMessage("§cUsage: /tph [player]");
        }

        return true;
    }
}