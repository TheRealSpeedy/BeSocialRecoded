package de.outlook.therealspeedy.besocial.util;

import de.outlook.therealspeedy.besocial.BeSocial;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

import static org.bukkit.Bukkit.getServer;

public class Database {

    private static FileConfiguration database = BeSocial.getDatabase();
    private static Plugin plugin = getServer().getPluginManager().getPlugin("BeSocial");

    public static void savePlayerLeftTime(Player player){
        String playerID = player.getUniqueId().toString();
        Long time = System.currentTimeMillis();

        String savePath = ""+playerID+".leftAt";

        database.set(savePath, time);
    }

    public static Long getPlayerLeftTime(Player player){
        String playerID = player.getUniqueId().toString();
        String loadPath = ""+playerID+".leftAt";

        return database.getLong(loadPath);
    }

    public static void logAction(Player player, String action){
        String playerID = player.getUniqueId().toString();
        String key = null;
        try {
            key = getKey(action);
        } catch (Exception e) {
            System.out.println(e);
            plugin.getLogger().log(Level.SEVERE, "DATABASE ERROR. INFORM THE PLUGIN AUTHOR(S) ABOUT THIS!");
            plugin.getLogger().log(Level.SEVERE, "ERROR INFO logAction,getKey for:" + action);
        }

        String path = playerID + "." + key;
        if (database.contains(path)){
            int oldInt = database.getInt(path);
            int newInt = oldInt++;
            database.set(path, newInt);
        } else {
            database.set(path, 1);
        }

    }

    public static int getStatistic(Player player, String source){
        String playerID = player.getUniqueId().toString();
        String key = null;
        try {
            key = getKey(source);
        } catch (Exception e) {
            System.out.println(e);
            plugin.getLogger().log(Level.SEVERE, "DATABASE ERROR. INFORM THE PLUGIN AUTHOR(S) ABOUT THIS!");
            plugin.getLogger().log(Level.SEVERE, "ERROR INFO logAction,getKey for:" + source);
        }

        String path = playerID + "." + key;
        if (database.contains(path)){
            return database.getInt(path);
        } else {
            return 0;
        }

    }

    private static String getKey(String input){
        switch (input){
            case "sendCuddle":
                return "scu";
            case "receiveCuddle":
                return "rcu";
            case "sendHandshake":
                return "shs";
            case "receiveHandshake":
                return "rhs";
            case "sendHighfive":
                return "shf";
            case "receiveHighfive":
                return "rhf";
            case "sendHug":
                return "shu";
            case "receiveHug":
                return "rhu";
            case "sendKiss":
                return "ski";
            case "receiveKiss":
                return "rki";
            case "sendLick":
                return "sli";
            case "receiveLick":
                return "rli";
            case "sendPet":
                return "spe";
            case "receivePet":
                return "rpe";
            case "sendPoke":
                return "spo";
            case "receivePoke":
                return "rpo";
            case "sendSlap":
                return "ssl";
            case "receiveSlap":
                return "rsl";
        }
        return null;
    }

}
