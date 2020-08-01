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

public class Highfive implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (Players.notMember((Player) sender)) {
			sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.senderNotMember"));
		}
		else {
			if (args.length == 0) {
				sender.sendMessage(Messages.getPrefix() + ChatColor.RESET + "" + ChatColor.RED + "/highfive <playername>");
			}
			else {

				if (Cooldown.cooldownActive((Player) sender, "highfive")){
					sender.sendMessage(Messages.getCooldownErrorMessage());
					return false;
				}

				Player target = Bukkit.getPlayerExact(args[0]);
				if (target == null) {
					sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.targetOffline"));
				}
				else if (Players.samePlayer(sender, target)) {
					sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.selfSocial.highfive"));
					Players.spawnParticles((Player) sender, target, cmd.getName());
				}
				else {
					if (Players.notMember(target)) {
						sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.targetNotMember"));
					}
					else {
						sender.sendMessage(Messages.getPrefix() + Messages.getSocialMessage("messages.sender.success.highfive", (Player) sender, target));
						target.sendMessage(Messages.getPrefix() + Messages.getSocialMessage("messages.target.success.highfive", (Player) sender, target));
						Players.spawnParticles((Player) sender, target, cmd.getName());
					}
				}
			}
		}
		
		return false;
	}

}
