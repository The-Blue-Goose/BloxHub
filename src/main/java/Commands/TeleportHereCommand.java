package Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class TeleportHereCommand implements CommandExecutor {

    public TeleportHereCommand() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player target)) return true;

        if (args.length == 1) {
            Player from = Bukkit.getPlayer(args[0]);
            if (from != null) {
                from.teleport(target);
                target.sendMessage("§aTeleported " + from.getName() + " to you.");
                from.sendMessage("§aYou have been teleported to " + target.getName());
            } else {
                target.sendMessage("§cPlayer not found.");
            }
        } else {
            target.sendMessage("§cUsage: /tph [player]");
        }

        return true;
    }
}