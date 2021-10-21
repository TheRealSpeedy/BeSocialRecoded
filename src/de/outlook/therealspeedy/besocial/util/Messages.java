package de.outlook.therealspeedy.besocial.util;

import de.outlook.therealspeedy.besocial.BeSocial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Messages extends JavaPlugin {
	private static final Plugin plugin = Bukkit.getPluginManager().getPlugin(BeSocial.name);
	private static final FileConfiguration pluginConfig = plugin.getConfig();
	private static YamlConfiguration langConfig = BeSocial.getLangConfig();

	//TODO: change to lang file
	
	public static String getPrefix() {
		
		String prefix = langConfig.getString("messages.prefix");
		
		prefix = ChatColor.translateAlternateColorCodes('&', prefix);
		prefix = prefix + " §r";
		
		return prefix;
	}

	public static String getPluginVersion() {
		return plugin.getDescription().getVersion();
	}

	public static String getWebsite() {
		return plugin.getDescription().getWebsite();
	}
	
	public static String getInfoMessage(String path) {
		
		String message = langConfig.getString(path);
		
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public static String getSocialMessage(String path, Player sender, Player target) {
		
		String message = langConfig.getString(path);
		String sName = sender.getName();
		String tName = target.getName();
		
		message = message.replaceAll("%sender", sName);
		message = message.replaceAll("%target", tName);
		
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public static String getSharehealthMessage(String path, Player sender, Player target, double healthSend) {

		String message = langConfig.getString(path);
		String sName = sender.getName();
		String tName = target.getName();

		message = message.replaceAll("%sender", sName);
		message = message.replaceAll("%target", tName);
		message = message.replaceAll("%healthsend", ""+healthSend);

		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public static String getAdminMessage(String path, Player target) {
		
		String message = langConfig.getString(path);
		String tName = target.getName();
		
		message = message.replaceAll("%player", tName);
		
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public static String getCooldownErrorMessage(){

		String message = langConfig.getString("messages.sender.error.cooldown");
		String cooldownTime = pluginConfig.getString("commands.CooldownSeconds");

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
		String message = langConfig.getString("messages.sender.error.rejoinCooldown");

		while (cooldownSecondsLeft >= 86400) {
			days++;
			cooldownSecondsLeft -= 86400;
		}
		while (cooldownSecondsLeft >= 3600){
			hours++;
			cooldownSecondsLeft -= 3600;
		}
		while (cooldownSecondsLeft >= 60){
			minutes++;
			cooldownSecondsLeft -= 60;
		}
		seconds = cooldownSecondsLeft;

		String timeString = ""+days+"d "+hours+"h "+minutes+"min "+seconds+"s";

		message = message.replaceAll("%time", timeString);
		message = getPrefix() + message;

		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public static void update() {
		langConfig = BeSocial.getLangConfig();
	}

}
