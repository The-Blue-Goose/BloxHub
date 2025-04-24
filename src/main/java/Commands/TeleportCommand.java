package Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {

    public TeleportCommand() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                player.teleport(target);
                player.sendMessage("§aTeleported to " + target.getName());
            } else {
                player.sendMessage("§cPlayer not found.");
            }

        } else if (args.length == 2) {
            Player source = Bukkit.getPlayer(args[0]);
            Player target = Bukkit.getPlayer(args[1]);
            if (source != null && target != null) {
                source.teleport(target);
                sender.sendMessage("§aTeleported " + source.getName() + " to " + target.getName());
            } else {
                sender.sendMessage("§cOne or both players not found.");
            }

        } else if (args.length == 3) {
            try {
                Location loc = parseCoordinates(player.getLocation(), args[0], args[1], args[2]);
                player.teleport(loc);
                player.sendMessage("§aTeleported to coordinates: " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
            } catch (NumberFormatException e) {
                player.sendMessage("§cInvalid coordinates.");
            }

        } else {
            sender.sendMessage("§cUsage: /tp [player], /tp [player] [target], or /tp <x> <y> <z>");
        }

        return true;
    }

    private Location parseCoordinates(Location current, String xArg, String yArg, String zArg) throws NumberFormatException {
        double x = parseCoordinate(current.getX(), xArg);
        double y = parseCoordinate(current.getY(), yArg);
        double z = parseCoordinate(current.getZ(), zArg);
        return new Location(current.getWorld(), x, y, z);
    }

    private double parseCoordinate(double current, String arg) throws NumberFormatException {
        arg = arg.trim();
        if (arg.startsWith("~")) {
            if (arg.equals("~")) {
                return current;
            } else {
                return current + Double.parseDouble(arg.substring(1));
            }
        } else {
            return Double.parseDouble(arg);
        }
    }
}