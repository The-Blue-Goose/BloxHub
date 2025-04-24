package Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealFeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Determine whether the command is "heal" or "feed"
        boolean isHeal = cmd.getName().equalsIgnoreCase("heal");

        Player target;

        // If a player name is provided as an argument, try to get that player
        if (args.length > 0) {
            target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage("§cPlayer not found: " + args[0]);
                return true;
            }
        }
        else {
            // If no argument is provided, ensure the sender is a player
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cYou must specify a player.");
                return true;
            }
            target = (Player) sender;
        }

        if (isHeal) {
            // Heal the player: restore health, food level, and saturation
            target.setHealth(target.getMaxHealth());
            target.setFoodLevel(20);
            target.setSaturation(6.0f); //Moderate saturation to avoid overfilling
            target.sendMessage("§aYou have been fully healed.");
        }
        else {
            // Feed the player: max out food level and saturation
            target.setFoodLevel(20);
            target.setSaturation(20.0f);
            target.sendMessage("§aYou have been fed.");
        }

        // Notify the sender if they healed or fed another player
        if (!target.equals(sender)) {
            sender.sendMessage("§aYou " + (isHeal ? "healed" : "fed") + " §e" + target.getName() + "§a.");
        }

        return true;
    }
}