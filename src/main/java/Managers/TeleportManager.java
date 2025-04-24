package Managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class TeleportManager {
    private final Map<UUID, List<UUID>> pendingRequests = new HashMap<>();
    private final Map<UUID, BukkitRunnable> expiryTasks = new HashMap<>();
    private final Plugin plugin;
    private final int timeout;

    public TeleportManager(Plugin plugin) {
        this.plugin = plugin;
        this.timeout = plugin.getConfig().getInt("teleport.timeout-seconds", 60); // Default 60s
    }

    public void requestTeleport(Player from, Player to) {
        pendingRequests.computeIfAbsent(to.getUniqueId(), k -> new ArrayList<>()).add(from.getUniqueId());
        from.sendMessage("§aTeleport request sent to " + to.getName());
        to.sendMessage("§e" + from.getName() + " wants to teleport to you. Type /tpaccept or /tpdeny.");
        to.sendMessage("§7This request will expire in " + timeout + " seconds.");

        // Cancel existing task if the player sends multiple requests before one expires
        BukkitRunnable oldTask = expiryTasks.remove(from.getUniqueId());
        if (oldTask != null) oldTask.cancel();

        // Expiration task
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

        task.runTaskLater(plugin, timeout * 20L); // delay in ticks
        expiryTasks.put(from.getUniqueId(), task);
    }

    public Player getRequester(Player receiver) {
        List<UUID> list = pendingRequests.get(receiver.getUniqueId());
        if (list == null || list.isEmpty()) return null;
        UUID requesterId = list.remove(0);
        if (list.isEmpty()) pendingRequests.remove(receiver.getUniqueId());

        cancelExpiry(requesterId);
        return Bukkit.getPlayer(requesterId);
    }

    public boolean denyRequest(Player receiver, String requesterName) {
        UUID receiverId = receiver.getUniqueId();
        List<UUID> requests = pendingRequests.get(receiverId);
        if (requests == null || requests.isEmpty()) return false;

        Player requester = null;
        UUID toRemove = null;

        if (requesterName != null) {
            for (UUID id : requests) {
                Player p = Bukkit.getPlayer(id);
                if (p != null && p.getName().equalsIgnoreCase(requesterName)) {
                    requester = p;
                    toRemove = id;
                    break;
                }
            }
        } else {
            toRemove = requests.get(0);
            requester = Bukkit.getPlayer(toRemove);
        }

        if (toRemove != null) {
            requests.remove(toRemove);
            if (requests.isEmpty()) pendingRequests.remove(receiverId);
            cancelExpiry(toRemove);
            if (requester != null) requester.sendMessage("§cYour teleport request to " + receiver.getName() + " was denied.");
            return true;
        }

        return false;
    }

    private void cancelExpiry(UUID requesterId) {
        BukkitRunnable task = expiryTasks.remove(requesterId);
        if (task != null) task.cancel();
    }

    public boolean hasRequest(Player receiver) {
        return pendingRequests.containsKey(receiver.getUniqueId());
    }
}