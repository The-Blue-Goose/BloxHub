package Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UnknownCommandListener implements Listener {

    // Set of known valid command names (can be dynamically registered)
    private final Set<String> knownCommands = new HashSet<>();

    // List of blocked/sensitive commands that should not be accessible to players
    private final Set<String> blockedCommands = new HashSet<>(Arrays.asList(
            "pl", "plugins", "version", "ver"
    ));

    private final JavaPlugin plugin;

    // Constructor to inject the plugin instance for config access
    public UnknownCommandListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    // Allow external registration of known commands to avoid false positives
    public void registerCommand(String command) {
        knownCommands.add(command.toLowerCase());
    }

    // Event Handler - player command input before Bukkit processes it
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        // Ensure the message is a command (starts with /)
        if (!message.startsWith("/")) return;

        // Split command input to extract the base command (without arguments)
        String[] parts = message.substring(1).split(" ");
        String baseCommand = parts[0].toLowerCase();

        // Get custom unknown command message from config
        String unknownMsg = plugin.getConfig().getString("chat.unknown_command", "§cUnknown command. Type /help for help.");

        // Block sensitive commands unless player has permission to bypass
        if (blockedCommands.contains(baseCommand)) {
            if (!player.hasPermission("blox.bypass.blockedcommands")) {
                event.setCancelled(true);
                player.sendMessage(unknownMsg);
                return;
            }
        }

//        // Block unknown commands (currently disabled)
//        if (!knownCommands.contains(baseCommand)) {
//            event.setCancelled(true);
//            player.sendMessage(unknownMsg);
//        }
    }
}