package de.outlook.therealspeedy.besocial.commands;

import de.outlook.therealspeedy.besocial.util.Database;
import de.outlook.therealspeedy.besocial.util.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetSocialStats implements CommandExecutor {

    private static final String[] sources = {"sendhandshake", "receivehandshake", "sendhighfive", "receivehighfive", "sendpoke", "receivepoke", "sendhug", "receivehug", "sendcuddle", "receivecuddle", "sendpet", "receivepet", "sendkiss", "receivekiss", "sendlick", "receivelick", "sendslap", "receiveslap", "sendhealth", "receivehealth"};
    private static final String[] sourceActions = {"Handshakes", "Handshakes", "Highfives", "Highfives", "Pokes", "Pokes", "Hugs", "Hugs", "Cuddles", "Cuddles", "Pets", "Pets", "Kisses", "Kisses", "Licks", "Licks", "Slaps", "Slaps", "Health", "Health"};

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.getPrefix() + "§cAborted. Running this command from console could damage the database.");
            return false;
        }

        sender.sendMessage(Messages.getPrefix() + "§2§oYour statistics:");
        for (int i = 0; i < sources.length; i++) {
            if (i % 2 == 0) {
                sender.sendMessage("§b§o" + sourceActions[i] + " §r§9send: §b§o" + Database.getStatistic(((Player) sender), sources[i]) + " §r§9times");
            } else {
                sender.sendMessage("§b§o" + sourceActions[i] + " §r§9received: §b§o" + Database.getStatistic(((Player) sender), sources[i]) + " §r§9times");
            }
        }

        return false;
    }

}
