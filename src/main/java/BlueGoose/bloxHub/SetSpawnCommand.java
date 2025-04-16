package BlueGoose.bloxHub;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        World world = player.getWorld();
        Location spawnLocation;

        if (args.length == 3) {
            try {
                double x = parseCoordinate(player.getLocation().getX(), args[0]);
                double y = parseCoordinate(player.getLocation().getY(), args[1]);
                double z = parseCoordinate(player.getLocation().getZ(), args[2]);

                spawnLocation = new Location(world,
                        Math.floor(x) + 0.5,
                        Math.floor(y),
                        Math.floor(z) + 0.5);
            } catch (NumberFormatException e) {
                player.sendMessage("Invalid coordinates.");
                return true;
            }
        } else {
            Location loc = player.getLocation();
            spawnLocation = new Location(world,
                    loc.getBlockX() + 0.5,
                    loc.getBlockY(),
                    loc.getBlockZ() + 0.5);
        }

        world.setSpawnLocation(spawnLocation.getBlockX(), spawnLocation.getBlockY(), spawnLocation.getBlockZ());
        player.sendMessage("Spawn set to " +
                spawnLocation.getBlockX() + ", " +
                spawnLocation.getBlockY() + ", " +
                spawnLocation.getBlockZ());
        return true;
    }

    private double parseCoordinate(double base, String input) {
        if (input.startsWith("~")) {
            if (input.length() == 1) {
                return base;
            }
            return base + Double.parseDouble(input.substring(1));
        }
        return Double.parseDouble(input);
    }
}
