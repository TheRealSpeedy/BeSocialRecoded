package de.outlook.therealspeedy.besocial.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Cooldown extends JavaPlugin {
    static Plugin plugin = Bukkit.getPluginManager().getPlugin("BeSocial");
    static Long cooldownInConfig = plugin.getConfig().getLong("commands.CooldownSeconds");

    private static HashMap<UUID, Long> cooldownCuddle = new HashMap<UUID, Long>();
    private static HashMap<UUID, Long> cooldownHandshake = new HashMap<UUID, Long>();
    private static HashMap<UUID, Long> cooldownHighfive = new HashMap<UUID, Long>();
    private static HashMap<UUID, Long> cooldownHug = new HashMap<UUID, Long>();
    private static HashMap<UUID, Long> cooldownKiss = new HashMap<UUID, Long>();
    private static HashMap<UUID, Long> cooldownLick = new HashMap<UUID, Long>();
    private static HashMap<UUID, Long> cooldownPoke = new HashMap<UUID, Long>();
    private static HashMap<UUID, Long> cooldownSlap = new HashMap<UUID, Long>();
    private static HashMap<UUID, Long> cooldownStroke = new HashMap<UUID, Long>();

    private static HashMap<UUID, Long> getCooldownHashMap(String command){
        switch (command){
            case "cuddle":
                return cooldownCuddle;
            case "handshake":
                return cooldownHandshake;
            case "highfive":
                return cooldownHighfive;
            case "hug":
                return cooldownHug;
            case "kiss":
                return cooldownKiss;
            case "lick":
                return cooldownLick;
            case "poke":
                return cooldownPoke;
            case "slap":
                return cooldownSlap;
            case "stroke":
                return cooldownStroke;
            default:
                return null;
        }
    }

    public static boolean cooldownActive(Player p, String command) {
        UUID playeruuid = p.getUniqueId();
        HashMap<UUID, Long> cooldown = getCooldownHashMap(command);

        if (cooldown.containsKey(playeruuid)) {
            if (cooldown.get(playeruuid) > (System.currentTimeMillis()-(cooldownInConfig*1000))){
                setNewCooldownNow(p, command);
                return true;
            } else {
                setNewCooldownNow(p, command);
                return false;
            }
        } else {
            setNewCooldownNow(p, command);
            return false;
        }
    }

    public static void setNewCooldownNow(Player p, String command){
        UUID playeruuid = p.getUniqueId();
        HashMap<UUID, Long> cooldown = getCooldownHashMap(command);

        if (cooldown.containsKey(playeruuid)) {
            cooldown.remove(playeruuid);
            cooldown.put(playeruuid, System.currentTimeMillis());
        } else {
            cooldown.put(playeruuid, System.currentTimeMillis());
        }

    }

    public static Long remainingCooldownSeconds(Player p, String command){
        UUID playeruuid = p.getUniqueId();
        HashMap<UUID, Long> cooldown = getCooldownHashMap(command);
        Long remainingTime;

        if (cooldown.containsKey(playeruuid)) {
            remainingTime = (cooldown.get(playeruuid) + cooldownInConfig - System.currentTimeMillis())/1000;
        } else {
            remainingTime = Long.valueOf(0);
        }
        return remainingTime;
    }

}
