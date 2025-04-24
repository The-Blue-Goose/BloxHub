package Listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import Utils.PlaceholderUtil;

public class TabListListener implements Listener {
    private final JavaPlugin plugin;

    public TabListListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        String header = plugin.getConfig().getString("tablist.header", "").replace("\\n", "\n");
        String footer = plugin.getConfig().getString("tablist.footer", "").replace("\\n", "\n");

        // Replace PlaceholderAPI placeholders
        header = PlaceholderUtil.setPlaceholders(player, header);
        footer = PlaceholderUtil.setPlaceholders(player, footer);

        player.setPlayerListHeaderFooter(header, footer);
    }
}