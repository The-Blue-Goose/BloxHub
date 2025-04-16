package BlueGoose.bloxHub;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class NametagEdit {

    private final Scoreboard scoreboard;
    private final Team team;

    public NametagEdit(String teamName, String displayName, ChatColor color) {
        this.scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        // Register or get existing team
        Team existing = scoreboard.getTeam(teamName);
        if (existing != null) existing.unregister();

        this.team = scoreboard.registerNewTeam(teamName);
        team.setDisplayName(displayName);
        team.setColor(color);
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
    }

    public NametagEdit(String teamName, String displayName, String prefix, String suffix) {
        this.scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        Team existing = scoreboard.getTeam(teamName);
        if (existing != null) existing.unregister();

        this.team = scoreboard.registerNewTeam(teamName);
        team.setDisplayName(displayName);
        team.setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
        team.setSuffix(ChatColor.translateAlternateColorCodes('&', suffix));
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
    }

    public NametagEdit(String teamName, String displayName, String prefix) {
        this(teamName, displayName, prefix, "");
    }

    public void addPlayer(Player player) {
        team.addEntry(player.getName());
        player.setScoreboard(scoreboard);
    }

    public void updateAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(scoreboard);
        }
    }
}


