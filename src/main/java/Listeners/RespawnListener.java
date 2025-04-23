package Listeners;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.entity.Player;

public class RespawnListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        // Only override if player doesn't have a bed or anchor spawn
        if (player.getBedSpawnLocation() == null) {
            Location spawn = world.getSpawnLocation();
            event.setRespawnLocation(spawn);
        }
    }
}
