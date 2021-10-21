package de.outlook.therealspeedy.besocial.util;

import de.outlook.therealspeedy.besocial.BeSocial;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class GlobalHandler {

    private static final FileConfiguration pluginConfig = Bukkit.getPluginManager().getPlugin(BeSocial.name).getConfig();
    private static final String globalMsgPathPrefix = "global.";
    private static final boolean globalAnnouncementsEnabled = pluginConfig.getBoolean("messages.announceInteractionGlobally");
    private static final boolean logToConsole = pluginConfig.getBoolean("messages.logGlobalAnnouncementsToConsole");

    public static boolean announceInteraction(Player sender, Player target, String interaction) {
        if (!globalAnnouncementsEnabled) return false;
        if (interaction.equalsIgnoreCase("sharehealth")) throw new UnsupportedOperationException("Tried to announce sharehealth interaction in non-sharehealth context. Aborted.");

        String msg = Messages.getSocialMessage(globalMsgPathPrefix+interaction, sender, target);
        announcer(msg);
        return true;
    }

    public static boolean announceInteraction(Player sender, Player target, String interaction, double healthsend) {
        if (!globalAnnouncementsEnabled) return false;
        if (!interaction.equalsIgnoreCase("sharehealth")) throw new UnsupportedOperationException("Tried to announce non-sharehealth interaction in sharehealth context. Aborted.");

        String msg = Messages.getSharehealthMessage(globalMsgPathPrefix+interaction, sender, target, healthsend);
        announcer(msg);
        return true;
    }

    private static int announcer(String msg) {
        int sent = 0;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Players.isMember(player)) {
                player.sendMessage(Messages.getPrefix() + msg);
                sent++;
            }
        }

        if (logToConsole) Bukkit.getPluginManager().getPlugin(BeSocial.name).getLogger().log(Level.INFO, msg + " [sent to "+sent+" recipients]");

        return sent;
    }

}
