package Listeners;

import Commands.FlyGodCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.entity.Player;

public class GodModeListener implements Listener {

    private final FlyGodCommand commandHandler;

    public GodModeListener(FlyGodCommand commandHandler) {
        this.commandHandler = commandHandler;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (commandHandler.isGod(player)) {
                event.setCancelled(true);
            }
        }
    }
}