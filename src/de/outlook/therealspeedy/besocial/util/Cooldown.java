package de.outlook.therealspeedy.besocial.util;

import de.outlook.therealspeedy.besocial.BeSocial;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;

public class Cooldown extends JavaPlugin {
    private static final Plugin plugin = Bukkit.getPluginManager().getPlugin(BeSocial.name);
    private static final Long cooldownInConfig = plugin.getConfig().getLong("commands.CooldownSeconds");
    private static final boolean privateCooldown = plugin.getConfig().getBoolean("commands.everyCommandHasOwnCooldown");
    private static final int rejoinCooldownTime = plugin.getConfig().getInt("commands.RejoinCooldownSeconds");

    private static final HashMap<UUID, Long> cooldownGlobal = new HashMap<>();

    private static final HashMap<UUID, Long> cooldownCuddle = new HashMap<>();
    private static final HashMap<UUID, Long> cooldownHandshake = new HashMap<>();
    private static final HashMap<UUID, Long> cooldownHighfive = new HashMap<>();
    private static final HashMap<UUID, Long> cooldownHug = new HashMap<>();
    private static final HashMap<UUID, Long> cooldownKiss = new HashMap<>();
    private static final HashMap<UUID, Long> cooldownLick = new HashMap<>();
    private static final HashMap<UUID, Long> cooldownPoke = new HashMap<>();
    private static final HashMap<UUID, Long> cooldownSlap = new HashMap<>();
    private static final HashMap<UUID, Long> cooldownStroke = new HashMap<>();
    private static final HashMap<UUID, Long> cooldownSharehealth = new HashMap<>();





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
            case "pet":
                return cooldownStroke;
            case "sharehealth":
                return cooldownSharehealth;
            default:
                return null;
        }
    }

    public static boolean cooldownActive(Player p, String command) {
        UUID playeruuid = p.getUniqueId();
        HashMap<UUID, Long> cooldown = getCooldownHashMap(command);

        try {

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

        } catch (Exception exception) {
            plugin.getLogger().log(Level.SEVERE, "FATAL: HASHMAP MISSING OR CORRUPTED, INFORM THE PLUGIN'S AUTHOR!\ncommand cooldown hashmap: " + command);
            return false;
        }
    }

    public static void setNewCooldownNow(Player p, String command){
        UUID playeruuid = p.getUniqueId();
        HashMap<UUID, Long> cooldown = getCooldownHashMap(command);

        cooldown.remove(playeruuid);
        cooldown.put(playeruuid, System.currentTimeMillis());

    }

    public static Long remainingCooldownSeconds(Player p, String command){
        UUID playeruuid = p.getUniqueId();
        HashMap<UUID, Long> cooldown = getCooldownHashMap(command);
        long remainingTime;

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

        long timePassedLong = (timeNow - timeLeftAt)/1000;
        int timePassedInt = Math.toIntExact(timePassedLong);

        if (timePassedInt > cooldownTime) return 0;
        else return cooldownTime-timePassedInt;
    }

}
