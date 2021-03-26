package de.outlook.therealspeedy.besocial.commands;

import de.outlook.therealspeedy.besocial.BeSocial;
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

        //TODO: remove check after beta testing
        if (!BeSocial.unstableModeIsActive()) {
            sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.unstableFeatureNotAvailable"));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.getPrefix() + "Aborted. Running this command from console could damage the database.");
            return false;
        }

        sender.sendMessage(Messages.getPrefix() + "Your statistics:");
        for (int i = 0; i < sources.length; i++) {
            if (i % 2 == 0) {
                sender.sendMessage(sourceActions[i] + " send: " + Database.getStatistic(((Player) sender), sources[i]) + " times");
            } else {
                sender.sendMessage(sourceActions[i] + " received: " + Database.getStatistic(((Player) sender), sources[i]) + " times");
            }
        }

        //TODO
        return false;
    }

}
