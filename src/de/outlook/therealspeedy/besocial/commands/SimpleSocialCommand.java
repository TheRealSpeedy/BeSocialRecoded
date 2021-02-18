package de.outlook.therealspeedy.besocial.commands;

import de.outlook.therealspeedy.besocial.util.Cooldown;
import de.outlook.therealspeedy.besocial.util.Messages;
import de.outlook.therealspeedy.besocial.util.Players;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SimpleSocialCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmdRaw, String label, String[] args) {
        String cmd = cmdRaw.getName().toLowerCase();

        if (Players.notMember((Player) sender)) {
            sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.senderNotMember"));
            return false;
        }
        else {
            if (args.length == 0) {
                sender.sendMessage(Messages.getPrefix() + ChatColor.RESET + "" + ChatColor.RED + "/"+cmd+" <playername>");
                return false;
            }
            else {

                if (Cooldown.cooldownActive((Player) sender, cmd)){
                    sender.sendMessage(Messages.getCooldownErrorMessage());
                    return false;
                }

                Player target = Bukkit.getPlayerExact(args[0]);
                if (target == null) {
                    sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.targetOffline"));
                    return false;
                }
                else if (Players.samePlayer(sender, target)) {
                    sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.selfSocial."+cmd));
                    Players.spawnParticles((Player) sender, target, cmd);
                    return true;
                }
                else {
                    if (Players.notMember(target)) {
                        sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.targetNotMember"));
                        return true;
                    }
                    else if (Players.targetIsIgnoringSender((Player) sender, target)) {
                        sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.targetIgnoringSender"));
                        return true;
                    }
                    else if (Players.targetIsIgnoringSender(target, (Player) sender)) {
                        sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.senderIgnoringTarget"));
                        return true;
                    }
                    else {
                        sender.sendMessage(Messages.getPrefix() + Messages.getSocialMessage("messages.sender.success."+cmd, (Player) sender, target));
                        target.sendMessage(Messages.getPrefix() + Messages.getSocialMessage("messages.target.success."+cmd, (Player) sender, target));
                        Players.spawnParticles((Player) sender, target, cmd);
                        return true;
                    }
                }
            }
        }
    }


}
