package Commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Only allow players to use this command
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        World world = player.getWorld();
        Location spawnLocation;

        // If coordinates are provided, parse them
        if (args.length == 3) {
            try {
                // Support relative (~) and absolute coordinates
                double x = parseCoordinate(player.getLocation().getX(), args[0]);
                double y = parseCoordinate(player.getLocation().getY(), args[1]);
                double z = parseCoordinate(player.getLocation().getZ(), args[2]);

                // Floor coordinates and center on block (for nicer spawn alignment)
                spawnLocation = new Location(world,
                        Math.floor(x) + 0.5,
                        Math.floor(y),
                        Math.floor(z) + 0.5);
            } catch (NumberFormatException e) {
                player.sendMessage("Invalid coordinates.");
                return true;
            }
        }
        else {
            // If no coordinates provided, use the player's current location
            Location loc = player.getLocation();
            spawnLocation = new Location(world,
                    loc.getBlockX() + 0.5,
                    loc.getBlockY(),
                    loc.getBlockZ() + 0.5);
        }

        // Set the world's spawn point to the chosen location
        world.setSpawnLocation(
                spawnLocation.getBlockX(),
                spawnLocation.getBlockY(),
                spawnLocation.getBlockZ());

        // Notify player of the new spawn location
        player.sendMessage("Spawn set to " +
                spawnLocation.getBlockX() + ", " +
                spawnLocation.getBlockY() + ", " +
                spawnLocation.getBlockZ());
        return true;
    }

    // Helper method to parse coordinate input, allowing for relative (~) positions
    private double parseCoordinate(double base, String input) {
        if (input.startsWith("~")) {
            if (input.length() == 1) {
                return base; //Just "~" means no offset
            }
            return base + Double.parseDouble(input.substring(1)); //"~<offset>" means relative
        }
        return Double.parseDouble(input); //Absolute coordinate
    }
}
