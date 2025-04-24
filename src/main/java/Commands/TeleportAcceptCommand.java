package Commands;

import Managers.TeleportManager;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class TeleportAcceptCommand implements CommandExecutor {

    private final TeleportManager tpManager;

    public TeleportAcceptCommand(TeleportManager tpManager) {
        this.tpManager = tpManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player target)) return true;

        Player requester = tpManager.getRequester(target);
        if (requester != null) {
            requester.teleport(target);
            requester.sendMessage("§aTeleporting to " + target.getName());
            target.sendMessage("§aAccepted teleport request from " + requester.getName());
        } else {
            target.sendMessage("§cNo pending teleport requests.");
        }

        return true;
    }
}