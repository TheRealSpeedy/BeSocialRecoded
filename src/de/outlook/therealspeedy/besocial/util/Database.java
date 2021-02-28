package de.outlook.therealspeedy.besocial.util;

import de.outlook.therealspeedy.besocial.BeSocial;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

import static de.outlook.therealspeedy.besocial.util.Basic.*;
import static org.bukkit.Bukkit.getPluginManager;
import static org.bukkit.Bukkit.getServer;

public class Database {

    private static FileConfiguration database = BeSocial.getDatabase();
    private static Plugin plugin = getServer().getPluginManager().getPlugin(BeSocial.name);

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

    public static boolean addIgnored(Player player, Player playerToIgnore){
        String playerID = player.getUniqueId().toString();
        String ignoreID = playerToIgnore.getUniqueId().toString();
        String path = playerID + ".ignoring";
        if (database.contains(path)) {
            String[] oldArray = database.getString(path).split("&");
            if (stringArrayContainsString(oldArray, ignoreID)) {
                return false;
            } else {
                String newDatabaseString = makeDatabaseString(oldArray);
                newDatabaseString = addToDatabaseString(newDatabaseString, ignoreID);
                database.set(path, newDatabaseString);
                return true;
            }

        } else {
            String[] newArray = {ignoreID};
            String newDatabaseString = makeDatabaseString(newArray);
            database.set(path, newDatabaseString);
            return true;
        }

    }

    public static boolean removeIgnored(Player player, Player playerToRemove) {
        String playerID = player.getUniqueId().toString();
        String removeID = playerToRemove.getUniqueId().toString();
        String path = playerID + ".ignoring";
        String[] oldArray = getIgnored(player);
        if (stringArrayContainsString(oldArray, removeID)) {
            String [] newArray = removeFromArray(oldArray, removeID);
            String newDataBaseArray = makeDatabaseString(newArray);
            database.set(path, newDataBaseArray);
            return true;
        } else {
            return false;
        }
    }

    public static String[] getIgnored(Player player) {
        String playerID = player.getUniqueId().toString();
        String path = playerID + ".ignoring";
        if (database.contains(path)) {
            return database.getString(path).split("&");
        } else {
            return new String[0];
        }
    }

    public static void logAction(Player player, String action){
        if (!plugin.getConfig().getBoolean("enablePlayerStatisticsLogging")) {
            return;
        }
        String playerID = player.getUniqueId().toString();
        String key = null;
        try {
            key = getKey(action);
        } catch (Exception e) {
            e.printStackTrace();
            plugin.getLogger().log(Level.SEVERE, "DATABASE ERROR. INFORM THE PLUGIN AUTHOR(S) ABOUT THIS!");
            plugin.getLogger().log(Level.SEVERE, "ERROR INFO logAction,getKey for:" + action);
        }

        String path = playerID + "." + key;
        if (database.contains(path)){
            int newInt = database.getInt(path) + 1;
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
            e.printStackTrace();
            plugin.getLogger().log(Level.SEVERE, "DATABASE ERROR. INFORM THE PLUGIN AUTHOR(S) ABOUT THIS!");
            plugin.getLogger().log(Level.SEVERE, "ERROR INFO getStatistics,getKey for:" + source);
        }

        String path = playerID + "." + key;
        if (database.contains(path)){
            return database.getInt(path);
        } else {
            return 0;
        }

    }

    private static String getKey(String input){
        input = input.toLowerCase();
        switch (input){
            case "sendcuddle":
                return "scu";
            case "receivecuddle":
                return "rcu";
            case "sendhandshake":
                return "shs";
            case "receivehandshake":
                return "rhs";
            case "sendhighfive":
                return "shf";
            case "receivehighfive":
                return "rhf";
            case "sendhug":
                return "shu";
            case "receivehug":
                return "rhu";
            case "sendkiss":
                return "ski";
            case "receivekiss":
                return "rki";
            case "sendlick":
                return "sli";
            case "receivelick":
                return "rli";
            case "sendpet":
                return "spe";
            case "receivepet":
                return "rpe";
            case "sendpoke":
                return "spo";
            case "receivepoke":
                return "rpo";
            case "sendslap":
                return "ssl";
            case "receiveslap":
                return "rsl";
            case "sendhealth":
                return "she";
            case "receivehealth":
                return "rhe";
            default:
                return null;
        }
    }

}
