package Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TabListAnimator {

    private final JavaPlugin plugin;
    private final List<String> headers;
    private final List<String> footers;
    private int intervalTicks;
    private AtomicInteger currentIndex = new AtomicInteger(0);

    public TabListAnimator(JavaPlugin plugin, List<String> headers, List<String> footers, int intervalSeconds) {
        this.plugin = plugin;
        this.headers = headers;
        this.footers = footers;
        this.intervalTicks = intervalSeconds * 20;
    }

    public void start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                int index = currentIndex.getAndUpdate(i -> (i + 1) % headers.size());
                String header = headers.get(index).replace("\\n", "\n");
                String footer = footers.get(index).replace("\\n", "\n");

                for (Player player : Bukkit.getOnlinePlayers()) {
                    String finalHeader = PlaceholderUtil.setPlaceholders(player, header);
                    String finalFooter = PlaceholderUtil.setPlaceholders(player, footer);
                    player.setPlayerListHeaderFooter(finalHeader, finalFooter);
                }
            }
        }.runTaskTimer(plugin, 0, intervalTicks);
    }
}