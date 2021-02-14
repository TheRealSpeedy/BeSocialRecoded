package de.outlook.therealspeedy.besocial.commands.besocial;

import de.outlook.therealspeedy.besocial.util.Players;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class HelpHandler {

    public static void fire(CommandSender sender, Command cmd, String label, String[] args, FileConfiguration config) {
        if (Players.isMember((Player) sender)) {
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "---BeSocial command list---");
            sender.sendMessage("     /besocial = /beso");
            sender.sendMessage("     /hug <playername>");
            sender.sendMessage("     /cuddle <playername>");
            sender.sendMessage("     /kiss <playername>");
            sender.sendMessage("     /lick <playername>");
            sender.sendMessage("     /poke <playername>");
            sender.sendMessage("     /pet <playername>");
            sender.sendMessage("     /slap <playername>");
            sender.sendMessage("     /handshake <playername>");
            sender.sendMessage("     /highfive <playername>");
            sender.sendMessage("");
            sender.sendMessage("     /beso ignore <playername>");
            sender.sendMessage("     /beso unignore <playername>");
            sender.sendMessage("     /beso info");
            if (sender.hasPermission("besocial.admin")) sender.sendMessage(ChatColor.ITALIC + "     /besocial admin = /beso a");
            sender.sendMessage("");
            sender.sendMessage("     To leave the BeSocial program,");
            sender.sendMessage("     use the following command:" + ChatColor.BOLD + " /besocial leave");
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "-----------------------");
        }

        else if (config.getBoolean("enableCommand.besocialRejoin") && sender.hasPermission("besocial.rejoin")){
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "---BeSocial command list---");
            sender.sendMessage("     /beso ignore");
            sender.sendMessage("     /beso unignore");
            sender.sendMessage("     /beso info");
            if (sender.hasPermission("besocial.admin")) {sender.sendMessage(ChatColor.ITALIC + "     /besocial admin = /beso a");}
            sender.sendMessage("");
            sender.sendMessage("     To rejoin the BeSocial program,");
            sender.sendMessage("     use the following command:" + ChatColor.BOLD + " /besocial rejoin");
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "-----------------------");
        }

        else {
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "---BeSocial command list---");
            sender.sendMessage("     /beso ignore");
            sender.sendMessage("     /beso unignore");
            sender.sendMessage("     /beso info");
            if (sender.hasPermission("besocial.admin")) sender.sendMessage(ChatColor.ITALIC + "     /besocial admin = /beso a");
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "-----------------------");
        }
    }

}
