package Managers;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class TeleportManager {
    private final HashMap<UUID, UUID> pendingRequests = new HashMap<>();

    public void requestTeleport(Player from, Player to) {
        pendingRequests.put(to.getUniqueId(), from.getUniqueId());
        from.sendMessage("§aTeleport request sent to " + to.getName());
        to.sendMessage("§e" + from.getName() + " wants to teleport to you. Type /tpaccept to allow.");
    }

    public Player getRequester(Player receiver) {
        UUID requesterUUID = pendingRequests.remove(receiver.getUniqueId());
        if (requesterUUID == null) return null;
        return receiver.getServer().getPlayer(requesterUUID);
    }

    public boolean hasRequest(Player receiver) {
        return pendingRequests.containsKey(receiver.getUniqueId());
    }
}