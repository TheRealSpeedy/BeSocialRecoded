package de.outlook.therealspeedy.besocial.commands;

import de.outlook.therealspeedy.besocial.util.*;
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

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.getPrefix() + "§cOnly players can interact with players.");
            return false;
        }

        /*
        tests to pass:
            sender is member
            target is set
            cooldown is not active
            target is online
            target is sender >> selfsocial message and particles
            target is member
            target is not ignoring sender
            sender is not ignoring target
            >> all passed >> social messages and particles
         */

        if (Players.isNotMember((Player) sender)) {
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
                    if (Players.isNotMember(target)) {
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
                        GlobalHandler.announceInteraction((Player) sender, target, cmd);
                        databaseLogBridge(cmd, (Player) sender, target);
                        return true;
                    }
                }
            }
        }
    }


    private static void databaseLogBridge(String command, Player sender, Player target) {
        Database.logAction(sender, "send"+command);
        Database.logAction(target, "receive"+command);
    }


}
