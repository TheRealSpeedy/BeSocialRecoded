package de.outlook.therealspeedy.besocial.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Messages extends JavaPlugin {
	private static Plugin plugin = Bukkit.getPluginManager().getPlugin("BeSocial");
	
	public static String getPrefix() {
		
		String prefix = plugin.getConfig().getString("messages.prefix");
		
		prefix = ChatColor.translateAlternateColorCodes('&', prefix);
		prefix = prefix + " ";
		
		return prefix;
	}

	public static String getPluginVersion() {
		return plugin.getDescription().getVersion();
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

	public static String getRejoinCooldownErrorMessage(Player p){
		int cooldownSecondsLeft = Cooldown.rejoinCooldownSecondsLeft(p);
		int days = 0;
		int hours = 0;
		int minutes = 0;
		int seconds;
		String message = plugin.getConfig().getString("messages.sender.error.rejoinCooldown");

		while (cooldownSecondsLeft >= 86400) {
			days++;
			cooldownSecondsLeft = cooldownSecondsLeft - 86400;
		}
		while (cooldownSecondsLeft >= 3600){
			hours++;
			cooldownSecondsLeft = cooldownSecondsLeft - 3600;
		}
		while (cooldownSecondsLeft >= 60){
			minutes++;
			cooldownSecondsLeft = cooldownSecondsLeft - 60;
		}
		seconds = cooldownSecondsLeft;

		String timeString = ""+days+"d "+hours+"h "+minutes+"min "+seconds+"s";

		message = message.replaceAll("%time", timeString);
		message = getPrefix() + message;

		return ChatColor.translateAlternateColorCodes('&', message);
	}

}
