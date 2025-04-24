package Commands;

import Managers.TeleportManager;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class TeleportDenyCommand implements CommandExecutor {

    private final TeleportManager tpManager;

    public TeleportDenyCommand(TeleportManager tpManager) {
        this.tpManager = tpManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        String targetName = args.length >= 1 ? args[0] : null;
        boolean success = tpManager.denyRequest(player, targetName);

        if (success) {
            player.sendMessage("§aTeleport request denied" + (targetName != null ? " for §e" + targetName : "") + ".");
        } else {
            player.sendMessage("§cNo pending teleport request" + (targetName != null ? " from §e" + targetName : "") + ".");
        }

        return true;
    }
}