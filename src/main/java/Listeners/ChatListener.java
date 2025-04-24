package Listeners;

import Commands.NametagEdit;
import Utils.PlaceholderUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ChatListener implements Listener {

    private final JavaPlugin plugin;
    private final Set<UUID> seenPlayers = new HashSet<>();

    public ChatListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = plugin.getConfig();

        String msg;
        if (!seenPlayers.contains(player.getUniqueId())) {
            msg = config.getString("chat.first_join_message", "§bWelcome %player% to the server for the first time!");
            seenPlayers.add(player.getUniqueId());
        } else {
            msg = config.getString("chat.join_message", "§a%player% joined the server!");
        }

        msg = msg.replace("%player%", player.getName());
        msg = PlaceholderUtil.setPlaceholders(player, msg); // Use PlaceholderAPI
        event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = plugin.getConfig();

        String format = config.getString("chat.format", "%player%: %message%");
        String prefix = getPrefix(player);

        format = format
                .replace("%player%", player.getName())
                .replace("%prefix%", prefix)
                .replace("%message%", event.getMessage());

        format = PlaceholderUtil.setPlaceholders(player, format); // Use PlaceholderAPI
        event.setFormat(ChatColor.translateAlternateColorCodes('&', format));
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        FileConfiguration config = plugin.getConfig();

        String msg = config.getString("chat.death_message", "§c%player% died.");
        msg = msg.replace("%player%", player.getName());
        msg = PlaceholderUtil.setPlaceholders(player, msg); // Use PlaceholderAPI
        event.setDeathMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    private String getPrefix(Player player) {
        if (player.getScoreboard() != null && player.getScoreboard().getEntryTeam(player.getName()) != null) {
            return player.getScoreboard().getEntryTeam(player.getName()).getPrefix();
        }
        return "";
    }
}