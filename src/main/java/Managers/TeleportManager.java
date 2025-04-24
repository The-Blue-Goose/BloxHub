package Managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class TeleportManager {

    // Maps target player UUIDs to a list of pending requesters' UUIDs
    private final Map<UUID, List<UUID>> pendingRequests = new HashMap<>();

    // Tracks expiration tasks for each teleport request (by requester UUID)
    private final Map<UUID, BukkitRunnable> expiryTasks = new HashMap<>();

    private final Plugin plugin;
    private final int timeout; //Timeout duration for teleport requests, in seconds

    // Constructor to initialize the manager with the plugin and load timeout from config
    public TeleportManager(Plugin plugin) {
        this.plugin = plugin;
        this.timeout = plugin.getConfig().getInt("teleport.timeout-seconds", 60); // Default 60s
    }

    // Send a teleport request from one player to another
    public void requestTeleport(Player from, Player to) {
        // Add the request to the target's request list
        pendingRequests.computeIfAbsent(to.getUniqueId(), k -> new ArrayList<>()).add(from.getUniqueId());

        // Notify both players
        from.sendMessage("§aTeleport request sent to " + to.getName());
        to.sendMessage("§e" + from.getName() + " wants to teleport to you. Type /tpaccept or /tpdeny.");
        to.sendMessage("§7This request will expire in " + timeout + " seconds.");

        // Cancel any previous expiration task from the same requester
        BukkitRunnable oldTask = expiryTasks.remove(from.getUniqueId());
        if (oldTask != null) oldTask.cancel();

        // Schedule a task to expire the request after the timeout period
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                List<UUID> requests = pendingRequests.get(to.getUniqueId());
                if (requests != null) {
                    requests.remove(from.getUniqueId());
                    if (requests.isEmpty()) {
                        pendingRequests.remove(to.getUniqueId());
                    }
                }

                from.sendMessage("§cYour teleport request to " + to.getName() + " has expired.");
                if (to.isOnline()) {
                    to.sendMessage("§cTeleport request from " + from.getName() + " has expired.");
                }

                expiryTasks.remove(from.getUniqueId());
            }
        };

        // Schedule the task using ticks (20 ticks = 1 second)
        task.runTaskLater(plugin, timeout * 20L); // delay in ticks
        expiryTasks.put(from.getUniqueId(), task);
    }

    // Accept the earliest teleport request for a player and return the requester
    public Player getRequester(Player receiver) {
        List<UUID> list = pendingRequests.get(receiver.getUniqueId());
        if (list == null || list.isEmpty()) return null;
        UUID requesterId = list.remove(0); //Get and remove the first requester
        if (list.isEmpty()) pendingRequests.remove(receiver.getUniqueId());

        cancelExpiry(requesterId); //Stop expiration task
        return Bukkit.getPlayer(requesterId);
    }

    // Deny a teleport request (optionally by player name)
    public boolean denyRequest(Player receiver, String requesterName) {
        UUID receiverId = receiver.getUniqueId();
        List<UUID> requests = pendingRequests.get(receiverId);
        if (requests == null || requests.isEmpty()) return false;

        Player requester = null;
        UUID toRemove = null;

        // If a specific name is given, find that requester
        if (requesterName != null) {
            for (UUID id : requests) {
                Player p = Bukkit.getPlayer(id);
                if (p != null && p.getName().equalsIgnoreCase(requesterName)) {
                    requester = p;
                    toRemove = id;
                    break;
                }
            }
        }
        else {
            // Otherwise deny the first request in the queue
            toRemove = requests.get(0);
            requester = Bukkit.getPlayer(toRemove);
        }

        if (toRemove != null) {
            requests.remove(toRemove);
            if (requests.isEmpty()) pendingRequests.remove(receiverId);
            cancelExpiry(toRemove); //Cancel expiry task
            if (requester != null) requester.sendMessage("§cYour teleport request to " + receiver.getName() + " was denied.");
            return true;
        }

        return false;
    }

    // Cancel the expiration task for a given requester
    private void cancelExpiry(UUID requesterId) {
        BukkitRunnable task = expiryTasks.remove(requesterId);
        if (task != null) task.cancel();
    }

    // Check if a player has any pending teleport requests
    public boolean hasRequest(Player receiver) {
        return pendingRequests.containsKey(receiver.getUniqueId());
    }
}