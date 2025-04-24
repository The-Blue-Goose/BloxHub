package Commands;

import Managers.TeleportManager;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class TeleportAcceptCommand implements CommandExecutor {

    // Reference to the TeleportManager that handles request tracking
    private final TeleportManager tpManager;

    // Constructor to inject the TeleportManager dependency
    public TeleportAcceptCommand(TeleportManager tpManager) {
        this.tpManager = tpManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Ensure the command is only run by a player (not console or command blocks)
        if (!(sender instanceof Player target)) return true;

        // Retrieve the player who sent a teleport request to this player
        Player requester = tpManager.getRequester(target);

        if (requester != null) {
            // If a valid request exists, teleport the requester to the target
            requester.teleport(target);
            requester.sendMessage("§aTeleporting to " + target.getName());
            target.sendMessage("§aAccepted teleport request from " + requester.getName());
        }
        else {
            // If no request is found, notify the player
            target.sendMessage("§cNo pending teleport requests.");
        }

        return true;
    }
}