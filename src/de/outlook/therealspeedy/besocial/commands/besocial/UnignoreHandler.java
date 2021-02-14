package de.outlook.therealspeedy.besocial.commands.besocial;

import de.outlook.therealspeedy.besocial.util.Database;
import de.outlook.therealspeedy.besocial.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnignoreHandler {

    public static void fire(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            sender.sendMessage(Messages.getPrefix() + "/beso unignore <playername>");
        }

        else {
            Player target = Bukkit.getPlayerExact(args[1]);
            if (target == null) {
                sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.targetOffline"));
            }
            else {
                if (Database.removeIgnored((Player) sender, target)) {
                    sender.sendMessage(Messages.getPrefix() + Messages.getSocialMessage("messages.special.unignoreSuccessful", (Player) sender, target));
                }
                else {
                    sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.ignoreNotIgnoring"));
                }
            }
        }
    }

}
