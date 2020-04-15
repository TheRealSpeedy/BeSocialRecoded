package de.outlook.therealspeedy.besocial.util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.outlook.therealspeedy.besocial.BeSocial;

public class Players {
	static Plugin plugin = Bukkit.getPluginManager().getPlugin("BeSocial");
	
	public static boolean samePlayer(CommandSender sender, Player target) {
		Player s = (Player) sender;
		String senderID = s.getUniqueId().toString();
		String targetID = target.getUniqueId().toString();
		
		if (senderID.equals(targetID)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean notMember(Player player) {
		String playerID = player.getUniqueId().toString();
		
		if (BeSocial.notMembers.contains(playerID)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static int commandCooldown() {
        return plugin.getConfig().getInt("commands.CooldownSeconds");
	}

}
