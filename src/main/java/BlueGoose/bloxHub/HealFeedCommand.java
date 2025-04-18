package BlueGoose.bloxHub;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealFeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        boolean isHeal = cmd.getName().equalsIgnoreCase("heal");

        Player target;

        if (args.length > 0) {
            target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage("§cPlayer not found: " + args[0]);
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cYou must specify a player.");
                return true;
            }
            target = (Player) sender;
        }

        if (isHeal) {
            target.setHealth(target.getMaxHealth());
            target.setFoodLevel(20);
            target.setSaturation(3.0f);
            target.sendMessage("§aYou have been fully healed.");
        } else {
            target.setFoodLevel(20);
            target.setSaturation(20.0f);
            target.sendMessage("§aYou have been fed.");
        }

        if (!target.equals(sender)) {
            sender.sendMessage("§aYou " + (isHeal ? "healed" : "fed") + " §e" + target.getName() + "§a.");
        }

        return true;
    }
}