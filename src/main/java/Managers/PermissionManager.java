package Managers;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PermissionManager {

    private final JavaPlugin plugin;

    // Constructor to inject the plugin instance for config access
    public PermissionManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    // Checks if a player has the specified permission
    // If not, send "no permission" message from the config
    public boolean hasPermission(Player player, String permission) {
        if (player.hasPermission(permission)) {
            return true; //Player has permission
        }
        else {
            // Get denial message from config, or use default
            String msg = plugin.getConfig().getString(
                    "chat.no-permission",
                    "§cYou do not have permission to use this command.");
            player.sendMessage(msg); //Notify the player
            return false; //Player doesn't have permission
        }
    }
}