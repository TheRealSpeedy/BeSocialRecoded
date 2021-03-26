package de.outlook.therealspeedy.besocial.commands;

import de.outlook.therealspeedy.besocial.util.Cooldown;
import de.outlook.therealspeedy.besocial.util.Database;
import de.outlook.therealspeedy.besocial.util.Messages;
import de.outlook.therealspeedy.besocial.util.Players;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShareHealth implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.getPrefix() + "§cCan't divide infinity by 2.");
            return false;
        }

        /*
        tests to pass:
            sender is member
            target is set
            cooldown is not active
            target is online
            target is sender >> cancel
            target is member
            target is not ignoring sender
            sender is not ignoring target
            sender has more than 0.5 health
            target doesn't have full health
            >> passed
         share maths:
            calculate half the hearts of sender, round down to next half heart >> shareable
            calculate 20 minus targets health >> missing
            if shareable > missing then sending = missing
            else sending = shareable
            take sending from sender
            give sending to target
         */

        String cmd = command.getName();


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
                    return false;
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
                        return false;
                    }
                    else {
                        if (((Player) sender).getHealth() < 1) {
                            sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.sharehealth.notEnoughHealth"));
                            return true;
                        }
                        else if (target.getHealth() == target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) {
                            sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.sharehealth.targetFullHealth"));
                            return true;
                        }
                        else {
                            //firing command
                            //calculate health to send
                            double senderHealth = ((Player) sender).getHealth();
                            double targetHealth = target.getHealth();
                            double shareable;
                            double missing;
                            double sending;

                            shareable = senderHealth / 2.0;
                            missing = target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - targetHealth;

                            sending = Math.min(shareable, missing);

                            //transaction
                            ((Player) sender).setHealth(senderHealth - sending);
                            target.setHealth(targetHealth + sending);

                            sender.sendMessage(Messages.getPrefix() + Messages.getSharehealthMessage("messages.sender.success.sharehealth", (Player) sender, target, sending));
                            target.sendMessage(Messages.getPrefix() + Messages.getSharehealthMessage("messages.target.success.sharehealth", (Player) sender, target, sending));
                            Players.spawnParticles((Player) sender, target, cmd);

                            Database.logAction((Player) sender, "sendHealth");
                            Database.logAction(target, "receiveHealth");

                            return true;
                        }
                    }
                }
            }
        }
    }
}
