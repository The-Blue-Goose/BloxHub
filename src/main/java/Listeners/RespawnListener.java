package Listeners;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.entity.Player;

public class RespawnListener implements Listener {

    // Event handler - set respawn location if player doesn't have a bed or anchor spawn
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        // Only override respawn location if player doesn't have a bed or anchor spawn point
        if (player.getBedSpawnLocation() == null) {
            // Set the respawn location to the world's default spawn point
            Location spawn = world.getSpawnLocation();
            event.setRespawnLocation(spawn); //Set the player's respawn location
        }
    }
}
