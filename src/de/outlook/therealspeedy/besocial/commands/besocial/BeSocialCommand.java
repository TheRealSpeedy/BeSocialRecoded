package de.outlook.therealspeedy.besocial.commands.besocial;

import de.outlook.therealspeedy.besocial.util.Cooldown;
import de.outlook.therealspeedy.besocial.util.Database;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import de.outlook.therealspeedy.besocial.BeSocial;
import de.outlook.therealspeedy.besocial.util.Messages;
import de.outlook.therealspeedy.besocial.util.Players;

import static org.bukkit.Bukkit.getServer;

public class BeSocialCommand implements CommandExecutor {

	private static FileConfiguration config = getServer().getPluginManager().getPlugin("BeSocial").getConfig();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		String senderID = ((Player) sender).getUniqueId().toString();
		
		if (Players.notMember((Player) sender) && !sender.hasPermission("besocial.admin") && !config.getBoolean("enableCommand.besocialRejoin")) {
			sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.special.leaveBeSocial2"));
		}
		else {
		if (ishelp(args)) {
			HelpHandler.fire(sender, cmd, label, args, config);
		}

		else if (args[0].equalsIgnoreCase("leave")) {
			sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.special.leaveBeSocial"));

			BeSocial.notMembers.add(senderID);
			Database.savePlayerLeftTime((Player) sender);

			sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.special.leaveBeSocial2"));
		}

		else if (args[0].equalsIgnoreCase("rejoin")) {

			if (config.getBoolean("enableCommand.besocialRejoin") || sender.hasPermission("besocial.rejoin")){
				sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.special.rejoinForbidden"));
				return true;
			}

			if (Players.notMember((Player) sender)) {
				if (Cooldown.rejoinCooldownSecondsLeft((Player) sender) > 0) {
					sender.sendMessage(Messages.getRejoinCooldownErrorMessage((Player) sender));
				} else {
					BeSocial.notMembers.remove(senderID);
					sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.special.rejoinBeSocial1"));
				}
			} else {
				sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.rejoinAlreadyMember"));
			}
		}

		else if (args[0].equalsIgnoreCase("ignore")) {
			IgnoreHandler.fire(sender, cmd, label, args);
		}

		else if (args[0].equalsIgnoreCase("unignore")) {
			UnignoreHandler.fire(sender, cmd, label, args);
		}

		else if (args[0].equalsIgnoreCase("admin") || args[0].equalsIgnoreCase("a")) {
			AdminHandler.fire(sender, cmd, label, args);
		}

		else if (args[0].equalsIgnoreCase("info")) {
			sender.sendMessage(ChatColor.LIGHT_PURPLE + "---BeSocial information---");
			sender.sendMessage(ChatColor.ITALIC + "" + ChatColor.GREEN + " This plugin was originally written for the RainbowRED Network.");
			sender.sendMessage(ChatColor.ITALIC + "" + ChatColor.GREEN + " This plugin can be used on any server for free!");
			sender.sendMessage("");
			sender.sendMessage(" Plugin name: BeSocial");
			sender.sendMessage(" Version: " + Messages.getPluginVersion());
			sender.sendMessage(" Author: RainbowSpeedy");
			sender.sendMessage(" Description: Make love, not war. <3");
			sender.sendMessage(ChatColor.LIGHT_PURPLE + "----------------------------------------------");
				
		}
	}
	return true;
}
	
	
	private boolean ishelp(String[] args) {
		if (args.length == 0) return true;
		else return args[0].equalsIgnoreCase("help");
	}

}
