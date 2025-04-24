package Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TabListAnimator {

    // Reference to the main plugin, needed for scheduling tasks
    private final JavaPlugin plugin;

    // List of header frames to rotate through
    private final List<String> headers;

    // List of footer frames to rotate through
    private final List<String> footers;

    // Interval between updates in ticks (20 ticks = 1 second)
    private int intervalTicks;

    // Thread-safe index for current frame
    private AtomicInteger currentIndex = new AtomicInteger(0);

    // Constructs a new TabListAnimator.
    public TabListAnimator(JavaPlugin plugin, List<String> headers, List<String> footers, int intervalSeconds) {
        this.plugin = plugin;
        this.headers = headers;
        this.footers = footers;
        this.intervalTicks = intervalSeconds * 20; //Convert seconds to ticks
    }

    // Starts animation by scheduling a repeating task.
    public void start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                // Get the current index and advance it in a circular fashion
                int index = currentIndex.getAndUpdate(i -> (i + 1) % headers.size());

                // Replace escaped newlines with actual line breaks
                String header = headers.get(index).replace("\\n", "\n");
                String footer = footers.get(index).replace("\\n", "\n");

                // Apply placeholders and set header/footer for each online player
                for (Player player : Bukkit.getOnlinePlayers()) {
                    String finalHeader = PlaceholderUtil.setPlaceholders(player, header);
                    String finalFooter = PlaceholderUtil.setPlaceholders(player, footer);
                    player.setPlayerListHeaderFooter(finalHeader, finalFooter);
                }
            }
        }.runTaskTimer(plugin, 0, intervalTicks); //Start immediately, repeat every intervalTicks
    }
}