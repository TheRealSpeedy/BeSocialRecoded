package de.outlook.therealspeedy.besocial.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Cooldown extends JavaPlugin {
    private static Plugin plugin = Bukkit.getPluginManager().getPlugin("BeSocial");
    private static Long cooldownInConfig = plugin.getConfig().getLong("commands.CooldownSeconds");
    private static boolean privateCooldown = plugin.getConfig().getBoolean("commands.everyCommandHasOwnCooldown");
    private static int rejoinCooldownTime = plugin.getConfig().getInt("commands.RejoinCooldownSeconds");

    private static HashMap<UUID, Long> cooldownGlobal = new HashMap<UUID, Long>();

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

        if (!privateCooldown)
            return cooldownGlobal;

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
            remainingTime = 0L;
        }
        return remainingTime;
    }

    public static int rejoinCooldownSecondsLeft(Player p){
        Long timeLeftAt = Database.getPlayerLeftTime(p);
        Long timeNow = System.currentTimeMillis();
        int cooldownTime = rejoinCooldownTime;

        Long timePassedLong = (timeNow - timeLeftAt)/1000;
        int timePassedInt = Math.toIntExact(timePassedLong);

        if (timePassedInt > cooldownTime) return 0;
        else return cooldownTime-timePassedInt;
    }

}
