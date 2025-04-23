package Managers;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();

    public boolean isOnCooldown(Player player, String action) {
        UUID uuid = player.getUniqueId();
        if (!cooldowns.containsKey(uuid)) return false;

        Map<String, Long> playerCooldowns = cooldowns.get(uuid);
        if (!playerCooldowns.containsKey(action)) return false;

        long expiryTime = playerCooldowns.get(action);
        return System.currentTimeMillis() < expiryTime;
    }

    public long getRemainingTime(Player player, String action) {
        if (!isOnCooldown(player, action)) return 0;

        long expiryTime = cooldowns.get(player.getUniqueId()).get(action);
        return (expiryTime - System.currentTimeMillis()) / 1000; // in seconds
    }

    public void setCooldown(Player player, String action, int seconds) {
        cooldowns.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>())
                .put(action, System.currentTimeMillis() + (seconds * 1000L));
    }

    public void clearCooldown(Player player, String action) {
        Map<String, Long> playerCooldowns = cooldowns.get(player.getUniqueId());
        if (playerCooldowns != null) {
            playerCooldowns.remove(action);
        }
    }
}