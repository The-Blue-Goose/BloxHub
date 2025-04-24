package Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class NametagEdit {

    private static final String TEAM_NAME = "hotblox"; //Unique name for the team
    private static Team team; //The scoreboard team object

    // Initializes the team with custom display settings
    public static void setupTeam() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        // Remove the team if it already exists to avoid duplication
        if (scoreboard.getTeam(TEAM_NAME) != null) {
            scoreboard.getTeam(TEAM_NAME).unregister();
        }

        // Register a new team with desired name and styling
        team = scoreboard.registerNewTeam(TEAM_NAME);
        team.setDisplayName("HotBlox");

        // Set custom prefix and suffix for player name tags
        team.setPrefix(ChatColor.translateAlternateColorCodes('&', "&a[HOT] &4"));
        team.setSuffix(""); //Can be modified later if needed

        // Set team color (affects chat name display and nametag color)
        team.setColor(ChatColor.GREEN);

        // Make nametags always visible
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);

        // Disable collision between players in the team
        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
    }

    // Adds a player to the team and updates their scoreboard
    public static void addPlayer(Player player) {
        if (team == null) {
            setupTeam(); //Ensure the team is initialized before use
        }

        team.addPlayer(player); //Add player to the team
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard()); //Sync scoreboard
    }

    // Reapplies the scoreboard to all online players to ensure correct team data is shown
    public static void updateAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
    }
}