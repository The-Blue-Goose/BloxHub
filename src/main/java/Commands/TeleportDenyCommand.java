package Commands;

import Managers.TeleportManager;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class TeleportDenyCommand implements CommandExecutor {

    // Reference to the TeleportManager which handles request logic
    private final TeleportManager tpManager;

    // Constructor to inject the TeleportManager dependency
    public TeleportDenyCommand(TeleportManager tpManager) {
        this.tpManager = tpManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Ensure only players can run this command
        if (!(sender instanceof Player player)) return true;

        // Optional: specify a requester name to deny a specific request
        String targetName = args.length >= 1 ? args[0] : null;
        // Attempt to deny the request (with or without specifying a name)
        boolean success = tpManager.denyRequest(player, targetName);

        if (success) {
            // Notify the player that the request was denied
            player.sendMessage("§aTeleport request denied" + (targetName != null ? " for §e" + targetName : "") + ".");
        }
        else {
            // Notify the player if no request was found
            player.sendMessage("§cNo pending teleport request" + (targetName != null ? " from §e" + targetName : "") + ".");
        }

        return true;
    }
}