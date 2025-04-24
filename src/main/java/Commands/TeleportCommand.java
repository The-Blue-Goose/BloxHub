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
        // Ensure the command is run by a player
        if (!(sender instanceof Player player)) return true;

        if (args.length == 1) {
            // /tp [player] — teleport self to another player
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                player.teleport(target);
                player.sendMessage("§aTeleported to " + target.getName());
            }
            else {
                player.sendMessage("§cPlayer not found.");
            }

        }
        else if (args.length == 2) {
            // /tp [source] [target] — teleport one player to another (admin use)
            Player source = Bukkit.getPlayer(args[0]);
            Player target = Bukkit.getPlayer(args[1]);
            if (source != null && target != null) {
                source.teleport(target);
                sender.sendMessage("§aTeleported " + source.getName() + " to " + target.getName());
            }
            else {
                sender.sendMessage("§cOne or both players not found.");
            }

        }
        else if (args.length == 3) {
            // /tp <x> <y> <z> — teleport self to specific coordinates
            try {
                Location loc = parseCoordinates(player.getLocation(), args[0], args[1], args[2]);
                player.teleport(loc);
                player.sendMessage("§aTeleported to coordinates: " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
            } catch (NumberFormatException e) {
                player.sendMessage("§cInvalid coordinates.");
            }

        }
        else {
            // Invalid usage — send usage instructions
            sender.sendMessage("§cUsage: /tp [player], /tp [player] [target], or /tp <x> <y> <z>");
        }

        return true;
    }

    // Parses 3 coordinate strings into a Location, supporting both relative and absolute values
    private Location parseCoordinates(Location current, String xArg, String yArg, String zArg) throws NumberFormatException {
        double x = parseCoordinate(current.getX(), xArg);
        double y = parseCoordinate(current.getY(), yArg);
        double z = parseCoordinate(current.getZ(), zArg);
        return new Location(current.getWorld(), x, y, z);
    }

    // Parses a single coordinate, supporting relative (~) values
    private double parseCoordinate(double current, String arg) throws NumberFormatException {
        arg = arg.trim();
        if (arg.startsWith("~")) {
            if (arg.equals("~")) {
                return current; //"~" means stay at current position
            }
            else {
                return current + Double.parseDouble(arg.substring(1)); //"~5" means current + 5
            }
        }
        else {
            return Double.parseDouble(arg);
        }
    }
}