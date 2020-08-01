package de.outlook.therealspeedy.besocial.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;

import static org.bukkit.Bukkit.getServer;

public class BeSocialTabCompleter implements TabCompleter {

	private static FileConfiguration config = getServer().getPluginManager().getPlugin("BeSocial").getConfig();
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("besocial") || cmd.getName().equalsIgnoreCase("beso")) {
			
			ArrayList<String> tabComplete = new ArrayList<String>();
			
			if (args.length == 1) {
				if (args[0].startsWith("")) {
					tabComplete.add("help");
					tabComplete.add("info");
					if (sender.hasPermission("besocial.admin")) {
						tabComplete.add("admin");
						tabComplete.add("a");
					}
					if (config.getBoolean("enableCommand.besocialRejoin") && sender.hasPermission("besocial.rejoin")) {
						tabComplete.add("rejoin");
					}
					return tabComplete;
				}
			}
			else if (args.length == 2) {
				if (args[1].startsWith("")) {
					if (args[0].startsWith("a")) {
						tabComplete.add("users");
					}
				}
				return tabComplete;
			}
			else if (args.length == 3) {
				if (args[2].startsWith("") && args[0].startsWith("a") && args[1].equalsIgnoreCase("users")) {
					tabComplete.add("reload");
					tabComplete.add("save");
					tabComplete.add("block");
					tabComplete.add("free");
					
					return tabComplete;
				}
			}
		}
		return null;
	}

}
