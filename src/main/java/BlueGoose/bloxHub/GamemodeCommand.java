package BlueGoose.bloxHub;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        GameMode targetMode = null;
        String cmdName = cmd.getName().toLowerCase();

        // Determine the target gamemode based on the command or first argument
        switch (cmdName) {
            case "gmc":
                targetMode = GameMode.CREATIVE;
                break;
            case "gms":
                targetMode = GameMode.SURVIVAL;
                break;
            case "gma":
                targetMode = GameMode.ADVENTURE;
                break;
            case "gmsp":
                targetMode = GameMode.SPECTATOR;
                break;
            case "gm":
                if (args.length == 0) {
                    sender.sendMessage("§cUsage: /gm <mode> [player]");
                    return true;
                }
                switch (args[0].toLowerCase()) {
                    case "c": case "1": targetMode = GameMode.CREATIVE; break;
                    case "s": case "0": targetMode = GameMode.SURVIVAL; break;
                    case "a": case "2": targetMode = GameMode.ADVENTURE; break;
                    case "sp": case "3": targetMode = GameMode.SPECTATOR; break;
                    default:
                        sender.sendMessage("§cInvalid gamemode. Use 0-3 or c/s/a/sp.");
                        return true;
                }
                break;
            default:
                sender.sendMessage("§cUnknown command.");
                return true;
        }

        // Determine the target player
        Player target;
        if ((cmdName.equals("gm") && args.length >= 2) || (!cmdName.equals("gm") && args.length == 1)) {
            // A target player was specified
            String playerName = args[args.length - 1];
            target = Bukkit.getPlayerExact(playerName);
            if (target == null) {
                sender.sendMessage("§cPlayer not found: " + playerName);
                return true;
            }
        } else {
            // No target player specified, default to self
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cOnly players can change their own gamemode.");
                return true;
            }
            target = (Player) sender;
        }

        // Set gamemode
        target.setGameMode(targetMode);
        target.sendMessage("§aYour gamemode has been set to §e" + targetMode.name() + "§a.");
        if (!target.equals(sender)) {
            sender.sendMessage("§aSet §e" + target.getName() + "§a's gamemode to §e" + targetMode.name() + "§a.");
        }

        return true;
    }
}