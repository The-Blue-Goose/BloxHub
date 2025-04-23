package BlueGoose.bloxHub;

import Commands.*;
import Listeners.*;
import Managers.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

public final class BloxHub extends JavaPlugin {

    @Override
    public void onEnable() {
        //Spawn Commands
        if (getCommand("setspawn") != null)
            getCommand("setspawn").setExecutor(new SetSpawnCommand());

        if (getCommand("spawn") != null)
            getCommand("spawn").setExecutor(new SpawnCommand());

        //Gamemode Commands
        GamemodeCommand gamemodeCommand = new GamemodeCommand();
        getCommand("gmc").setExecutor(gamemodeCommand);
        getCommand("gms").setExecutor(gamemodeCommand);
        getCommand("gma").setExecutor(gamemodeCommand);
        getCommand("gmsp").setExecutor(gamemodeCommand);
        getCommand("gm").setExecutor(gamemodeCommand);

        //Heal+Feed Commands
        HealFeedCommand healFeedCommand = new HealFeedCommand();
        getCommand("heal").setExecutor(healFeedCommand);
        getCommand("feed").setExecutor(healFeedCommand);

        //Fly+God Command
        FlyGodCommand flyGodCommand = new FlyGodCommand();
        getCommand("fly").setExecutor(flyGodCommand);
        getCommand("god").setExecutor(flyGodCommand);
        getServer().getPluginManager().registerEvents(new GodModeListener(flyGodCommand), this);

        //TabList
        getServer().getPluginManager().registerEvents(new SpawnJoinListener(), this);
        getServer().getPluginManager().registerEvents(new RespawnListener(), this);
        getServer().getPluginManager().registerEvents(new TabListListener(this), this);

        saveDefaultConfig(); // Ensure config is created

        //Scoreboard
        ScoreboardManager scoreboardManager = new ScoreboardManager(this);
        ScoreboardCommand scoreboardCommand = new ScoreboardCommand(scoreboardManager);

        if (getCommand("scoreboard") != null)
            getCommand("scoreboard").setExecutor(scoreboardCommand);

        getServer().getPluginManager().registerEvents(
                new ScoreboardListener(scoreboardManager, scoreboardCommand), this);

        //NameTagEdit
        NametagEdit tag = new NametagEdit("hotblox", "HotBlox", "&a[HOT] ", "");
        for (Player player : Bukkit.getOnlinePlayers()) {
            tag.addPlayer(player);
        }
        tag.updateAll();

        //Enable Log
        getLogger().info("BloxHub is enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("BloxHub is disabled!");
    }
}
