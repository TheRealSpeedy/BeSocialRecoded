package de.outlook.therealspeedy.besocial.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import de.outlook.therealspeedy.besocial.BeSocial;

import java.util.logging.Level;

import static de.outlook.therealspeedy.besocial.util.Basic.stringArrayContainsString;
import static de.outlook.therealspeedy.besocial.util.Database.getIgnored;

public class Players {
	private static final Plugin plugin = Bukkit.getPluginManager().getPlugin(BeSocial.name);
	private static final FileConfiguration config = plugin.getConfig();
	
	public static boolean samePlayer(CommandSender sender, Player target) {
		return ((Player) sender).getUniqueId().toString().equalsIgnoreCase(target.getUniqueId().toString());
	}
	
	public static boolean isNotMember(Player player) {
		String playerID = player.getUniqueId().toString();

		return BeSocial.notMembers.contains(playerID);
	}

	public static boolean isMember(Player player) {
		String playerID = player.getUniqueId().toString();

		return !BeSocial.notMembers.contains(playerID);
	}

	public static boolean targetIsIgnoringSender(Player sender, Player target) {
		return stringArrayContainsString(getIgnored(target), sender.getUniqueId().toString());
	}

	private static Location playerHeartLocation (Player player){
		Location playerLocation = player.getLocation();
		playerLocation = playerLocation.add(0, 1.5, 0);
		return playerLocation;
	}

	public static void spawnParticles(Player sender, Player target, String command){
		if (!plugin.getConfig().getBoolean("particles.enableParticleEffect")){
			return;
		}

		int particleAmount = config.getInt("particles.particleAmount");
		double areaModifier = config.getDouble("particles.particleSpawnAreaSize");
		double areaX = 0.3 * areaModifier;
		double areaY = 0.4 * areaModifier;
		double areaZ = 0.3 * areaModifier;

		if (samePlayer(sender, target)){
			if (config.getBoolean("particles.onlyShowParticlesToParticipants")){
				sender.spawnParticle(particleForCommand(command), playerHeartLocation(sender), particleAmount, areaX, areaY, areaZ);
			} else {
				sender.getWorld().spawnParticle(particleForCommand(command), playerHeartLocation(sender), particleAmount, areaX, areaY, areaZ);
			}
			return;
		}

		if (config.getBoolean("particles.onlyShowParticlesToParticipants")){
			sender.spawnParticle(particleForCommand(command), playerHeartLocation(sender), particleAmount, areaX, areaY, areaZ);
			sender.spawnParticle(particleForCommand(command), playerHeartLocation(target), particleAmount, areaX, areaY, areaZ);
			target.spawnParticle(particleForCommand(command), playerHeartLocation(sender), particleAmount, areaX, areaY, areaZ);
			target.spawnParticle(particleForCommand(command), playerHeartLocation(target), particleAmount, areaX, areaY, areaZ);
		} else {
			World senderWorld = sender.getWorld();
			World targetWorld = target.getWorld();
			senderWorld.spawnParticle(particleForCommand(command), playerHeartLocation(sender), particleAmount, areaX, areaY, areaZ);
			targetWorld.spawnParticle(particleForCommand(command), playerHeartLocation(target), particleAmount, areaX, areaY, areaZ);
		}
	}

	private static Particle particleForCommand(String command){
		String configPath = "particles.usedParticle."+command;
		return stringToParticle(config.getString(configPath));
	}

	private static Particle stringToParticle(String string){
		switch (string){
			case "heart":
			case "hearts":
				return Particle.HEART;
			case "composter":
				return Particle.COMPOSTER;
			case "happyVillager":
				return Particle.VILLAGER_HAPPY;
			case "fallingWater":
				return Particle.FALLING_WATER;
			case "angryVillager":
				return Particle.VILLAGER_ANGRY;
			default:
				plugin.getLogger().log(Level.WARNING, "Some interactions are configured with invalid particle effects. Using default particle 'hearts' for these. Please repair your config.yml asap.");
				return Particle.HEART;
		}
	}

}
