package de.outlook.therealspeedy.besocial.commands;

import de.outlook.therealspeedy.besocial.util.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.outlook.therealspeedy.besocial.util.Messages;
import de.outlook.therealspeedy.besocial.util.Players;

public class Cuddle implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (Players.notMember((Player) sender)) {
			sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.senderNotMember"));
		}
		else {
			if (args.length == 0) {
				sender.sendMessage(Messages.getPrefix() + ChatColor.RESET + "" + ChatColor.RED + "/cuddle <playername>");
			}
			else {

				if (Cooldown.cooldownActive((Player) sender, "cuddle")){
					sender.sendMessage(Messages.getCooldownErrorMessage());
					return false;
				}

				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.targetOffline"));
				}
				else if (Players.samePlayer(sender, target)) {
					sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.selfSocial.cuddle"));
					Players.spawnParticles((Player) sender, target, cmd.getName());
				}
				else {
					if (Players.notMember(target)) {
						sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.targetNotMember"));
					}
					else if (Players.targetIsIgnoringSender((Player) sender, target)) {
						sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.targetIgnoringSender"));
					}
					else if (Players.targetIsIgnoringSender(target, (Player) sender)) {
						sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.senderIgnoringTarget"));
					}
					else {
						sender.sendMessage(Messages.getPrefix() + Messages.getSocialMessage("messages.sender.success.cuddle", (Player) sender, target));
						target.sendMessage(Messages.getPrefix() + Messages.getSocialMessage("messages.target.success.cuddle", (Player) sender, target));
						Players.spawnParticles((Player) sender, target, cmd.getName());
					}
				}
			}
		}
		
		return false;
	}

}
