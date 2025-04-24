package Listeners;

import Utils.NametagEdit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player joiningPlayer = event.getPlayer();

        // Add the joining player to the team
        NametagEdit.addPlayer(joiningPlayer);
        // Update scoreboards for *all* players to see the tags correctly
        NametagEdit.updateAll();
    }
}
