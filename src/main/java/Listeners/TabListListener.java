package Listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import Utils.PlaceholderUtil;

public class TabListListener implements Listener {
    private final JavaPlugin plugin;

    // Constructor to inject the plugin instance for accessing the config
    public TabListListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    // Event Handler - customize tab list header and footer when a player joins
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Retrieve the tablist header and footer from the plugin config, allowing newlines
        String header = plugin.getConfig().getString("tablist.header", "").replace("\\n", "\n");
        String footer = plugin.getConfig().getString("tablist.footer", "").replace("\\n", "\n");

        // Replace placeholders in the header and footer using PlaceholderAPI (if installed)
        header = PlaceholderUtil.setPlaceholders(player, header);
        footer = PlaceholderUtil.setPlaceholders(player, footer);

        // Set the player's tab list header and footer
        player.setPlayerListHeaderFooter(header, footer);
    }
}