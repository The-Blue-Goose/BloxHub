package Managers;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PermissionManager {

    private final JavaPlugin plugin;

    public PermissionManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean hasPermission(Player player, String permission) {
        if (player.hasPermission(permission)) {
            return true;
        } else {
            String msg = plugin.getConfig().getString("messages.no-permission", "§cYou do not have permission to use this command.");
            player.sendMessage(msg);
            return false;
        }
    }
}