package Listeners;

import Commands.NametagEdit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        NametagEdit tag = new NametagEdit("hotblox", "HotBlox", "§a[HOT] ");
        tag.addPlayer(player);
        tag.updateAll();
    }
}
