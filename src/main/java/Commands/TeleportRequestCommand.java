package Commands;

import Managers.TeleportManager;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class TeleportRequestCommand implements CommandExecutor {

    // Reference to the TeleportManager for managing teleport requests
    private final TeleportManager tpManager;

    // Constructor to inject the TeleportManager dependency
    public TeleportRequestCommand(TeleportManager tpManager) {
        this.tpManager = tpManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Ensure the sender is a player
        if (!(sender instanceof Player player)) return true;

        // Command usage: /tpa [player]
        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);

            // Validate target: must exist and not be the sender
            if (target != null && !target.equals(player)) {
                // Send the teleport request through the TeleportManager
                tpManager.requestTeleport(player, target);
            }
            else {
                // Invalid target: either player not found or sender targeting themselves
                player.sendMessage("§cInvalid target.");
            }
        }
        else {
            // Incorrect usage message
            player.sendMessage("§cUsage: /tpa [player]");
        }

        return true;
    }
}