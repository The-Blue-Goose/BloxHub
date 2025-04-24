package Commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Ensure the command is being run by a player (not console or command block)
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        // Get information needed about the player, world, and location
        Player player = (Player) sender;
        World world = player.getWorld();
        Location spawn = world.getSpawnLocation();

        // Teleport the player to the spawn point
        player.teleport(spawn);
        // Confirm the teleport to the player
        player.sendMessage("Teleported to spawn!");
        return true;
    }
}
