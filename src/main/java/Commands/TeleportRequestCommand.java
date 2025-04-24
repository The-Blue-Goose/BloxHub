package Commands;

import Managers.TeleportManager;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class TeleportRequestCommand implements CommandExecutor {

    private final TeleportManager tpManager;

    public TeleportRequestCommand(TeleportManager tpManager) {
        this.tpManager = tpManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null && !target.equals(player)) {
                tpManager.requestTeleport(player, target);
            } else {
                player.sendMessage("§cInvalid target.");
            }
        } else {
            player.sendMessage("§cUsage: /tpa [player]");
        }

        return true;
    }
}