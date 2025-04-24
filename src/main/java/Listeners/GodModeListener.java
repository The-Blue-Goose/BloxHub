package Listeners;

import Commands.FlyGodCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.entity.Player;

public class GodModeListener implements Listener {

    private final FlyGodCommand commandHandler;

    // Constructor to inject the FlyGodCommand handler for checking God mode status
    public GodModeListener(FlyGodCommand commandHandler) {
        this.commandHandler = commandHandler;
    }

    // Event Handler - cancel damage if the player is in God mode
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        // Check if the entity receiving damage is a player
        if (event.getEntity() instanceof Player player) {
            // If the player has God mode enabled, cancel the damage event
            if (commandHandler.isGod(player)) {
                event.setCancelled(true);
            }
        }
    }
}