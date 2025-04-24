package Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class NametagEdit {

    private static final String TEAM_NAME = "hotblox";
    private static Team team;

    public static void setupTeam() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        // Unregister if exists (only once)
        if (scoreboard.getTeam(TEAM_NAME) != null) {
            scoreboard.getTeam(TEAM_NAME).unregister();
        }

        team = scoreboard.registerNewTeam(TEAM_NAME);
        team.setDisplayName("HotBlox");
        team.setPrefix(ChatColor.translateAlternateColorCodes('&', "&a[HOT] &4"));
        team.setSuffix(""); // Optional
        team.setColor(ChatColor.GREEN);
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
    }

    public static void addPlayer(Player player) {
        if (team == null) {
            setupTeam(); // Ensure the team exists
        }

        team.addPlayer(player);
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }

    public static void updateAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
    }
}