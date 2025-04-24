package BlueGoose.bloxHub;

import Commands.*;
import Listeners.*;
import Managers.*;
import Utils.NametagEdit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class BloxHub extends JavaPlugin {

    private PermissionManager permissionManager;
    private UnknownCommandListener unknownCommandListener;

    @Override
    public void onEnable() {
        saveDefaultConfig(); // Ensure config is created before use

        // Instantiate managers with config access
        permissionManager = new PermissionManager(this);
        unknownCommandListener = new UnknownCommandListener(this);
        TeleportManager teleportManager = new TeleportManager(this);

        // Register event listeners
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new RespawnListener(), this);
        getServer().getPluginManager().registerEvents(new TabListListener(this), this);
        getServer().getPluginManager().registerEvents(new GodModeListener(new FlyGodCommand()), this);
        getServer().getPluginManager().registerEvents(unknownCommandListener, this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);

        // Register scoreboard
        ScoreboardManager scoreboardManager = new ScoreboardManager(this);
        ScoreboardCommand scoreboardCommand = new ScoreboardCommand(scoreboardManager);
        if (getCommand("scoreboard") != null)
            getCommand("scoreboard").setExecutor(scoreboardCommand);
        getServer().getPluginManager().registerEvents(
                new ScoreboardListener(scoreboardManager, scoreboardCommand), this);

        // NameTagEdit
        NametagEdit.setupTeam(); // Setup team once on enable

        // Commands - setup and register them + add to unknownCommandListener
        registerCommand("setspawn", new SetSpawnCommand());
        registerCommand("spawn", new SpawnCommand());
        registerCommand("gmc", new GamemodeCommand());
        registerCommand("gms", new GamemodeCommand());
        registerCommand("gma", new GamemodeCommand());
        registerCommand("gmsp", new GamemodeCommand());
        registerCommand("gm", new GamemodeCommand());
        registerCommand("heal", new HealFeedCommand());
        registerCommand("feed", new HealFeedCommand());
        registerCommand("fly", new FlyGodCommand());
        registerCommand("god", new FlyGodCommand());
        registerCommand("scoreboard", scoreboardCommand);
        registerCommand("tp", new TeleportCommand());
        registerCommand("tpa", new TeleportRequestCommand(teleportManager));
        registerCommand("tpaccept", new TeleportAcceptCommand(teleportManager));
        registerCommand("tpdeny", new TeleportDenyCommand(teleportManager));
        registerCommand("tph", new TeleportHereCommand());
        registerCommand("bloxreload", new ReloadCommand(scoreboardManager, scoreboardCommand));

        //Reset tags if plugin gets reloaded (using plugman)
        for (Player player : Bukkit.getOnlinePlayers()) {
            NametagEdit.addPlayer(player);
        }
        NametagEdit.updateAll();

        // Enable log
        getLogger().info("BloxHub is enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("BloxHub is disabled!");
    }

    //Helper method to register commands and track them for unknown command handling
    private void registerCommand(String name, Object executor) {
        if (getCommand(name) != null) {
            getCommand(name).setExecutor((org.bukkit.command.CommandExecutor) executor);
            unknownCommandListener.registerCommand(name);
        }
    }
}