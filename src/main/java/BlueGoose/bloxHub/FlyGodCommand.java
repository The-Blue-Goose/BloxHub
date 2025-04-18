package BlueGoose.bloxHub;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FlyGodCommand implements CommandExecutor {

    private final Set<UUID> godModePlayers = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        boolean isFly = cmd.getName().equalsIgnoreCase("fly");

        Player target;

        if (args.length > 0) {
            target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage("§cPlayer not found: " + args[0]);
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cYou must specify a player.");
                return true;
            }
            target = (Player) sender;
        }

        if (isFly) {
            boolean newFlight = !target.getAllowFlight();
            target.setAllowFlight(newFlight);
            target.sendMessage("§aFlight has been " + (newFlight ? "enabled" : "disabled") + ".");
            if (!target.isFlying() && newFlight) target.setFlying(true);
        } else {
            UUID id = target.getUniqueId();
            boolean newGod = !godModePlayers.contains(id);
            if (newGod) {
                godModePlayers.add(id);
            } else {
                godModePlayers.remove(id);
            }
            target.sendMessage("§aGod mode has been " + (newGod ? "enabled" : "disabled") + ".");
        }

        if (!target.equals(sender)) {
            sender.sendMessage("§aYou toggled " + (isFly ? "flight" : "god mode") + " for §e" + target.getName() + "§a.");
        }

        return true;
    }

    public boolean isGod(Player player) {
        return godModePlayers.contains(player.getUniqueId());
    }

    public void disableGod(Player player) {
        godModePlayers.remove(player.getUniqueId());
    }
}