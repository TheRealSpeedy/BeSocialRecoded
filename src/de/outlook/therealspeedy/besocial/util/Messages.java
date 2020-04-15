package de.outlook.therealspeedy.besocial.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Messages extends JavaPlugin {
	static Plugin plugin = Bukkit.getPluginManager().getPlugin("BeSocial");
	
	public static String getPrefix() {
		
		String prefix = plugin.getConfig().getString("messages.prefix");
		
		prefix = ChatColor.translateAlternateColorCodes('&', prefix);
		prefix = prefix + " ";
		
		return prefix;
	}
	
	public static String getInfoMessage(String path) {
		
		String message = plugin.getConfig().getString(path);
		
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public static String getSocialMessage(String path, Player sender, Player target) {
		
		String message = plugin.getConfig().getString(path);
		String sName = sender.getName();
		String tName = target.getName();
		
		message = message.replaceAll("%sender", sName);
		message = message.replaceAll("%target", tName);
		
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public static String getAdminMessage(String path, Player target) {
		
		String message = plugin.getConfig().getString(path);
		String tName = target.getName();
		
		message = message.replaceAll("%player", tName);
		
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public static String getCooldownErrorMessage(){

		String message = plugin.getConfig().getString("messages.sender.error.cooldown");
		String cooldownTime = plugin.getConfig().getString("commands.CooldownSeconds");

		message = message.replaceAll("%time", cooldownTime);

		message = getPrefix() + message;

		return ChatColor.translateAlternateColorCodes('&', message);
	}

}
