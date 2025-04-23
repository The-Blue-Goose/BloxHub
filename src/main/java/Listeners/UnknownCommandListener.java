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

    private final Set<String> knownCommands = new HashSet<>();
    private final Set<String> blockedCommands = new HashSet<>(Arrays.asList(
            "pl", "plugins", "version", "ver"
    ));

    private final JavaPlugin plugin;

    public UnknownCommandListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerCommand(String command) {
        knownCommands.add(command.toLowerCase());
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (!message.startsWith("/")) return;

        String[] parts = message.substring(1).split(" ");
        String baseCommand = parts[0].toLowerCase();

        String unknownMsg = plugin.getConfig().getString("messages.unknown_command", "§cUnknown command. Type /help for help.");

        // Block sensitive server commands
        if (blockedCommands.contains(baseCommand)) {
            if (!player.hasPermission("blox.bypass.blockedcommands")) {
                event.setCancelled(true);
                player.sendMessage(unknownMsg);
                return;
            }
        }

        // Block unknown commands
        if (!knownCommands.contains(baseCommand)) {
            event.setCancelled(true);
            player.sendMessage(unknownMsg);
        }
    }
}