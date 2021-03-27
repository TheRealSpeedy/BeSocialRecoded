package de.outlook.therealspeedy.besocial.commands.besocial;

import de.outlook.therealspeedy.besocial.BeSocial;
import de.outlook.therealspeedy.besocial.util.Database;
import de.outlook.therealspeedy.besocial.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminHandler {

    public static void fire(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("besocial.admin")) {
            if (args.length == 1) {
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "---BeSocial admin commands---");
                sender.sendMessage("/beso a users - Commands for user management.");
                sender.sendMessage("/beso a config - Commands for config management.");
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "----------------------------");
            }
            else {
                if (args[1].equalsIgnoreCase("users")) {
                    if (args.length == 2) {
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "---BeSocial admin users command list---");
                        sender.sendMessage(ChatColor.ITALIC + "Admin-Sub-Command 'users'");
                        sender.sendMessage("/beso a users reload - Loads old member list from disk, deletes the one stored in RAM.");
                        sender.sendMessage("/beso a users save - Saves the member list from RAM, deletes the one on disk.");
                        sender.sendMessage("/beso a users block <playername> - Disallow an user to use BeSocial commands.");
                        sender.sendMessage("/beso a users free <playername> - Lets an user re-enter the BeSocial Program.");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "----------------------------");
                    }
                    else {
                        if (args[2].equalsIgnoreCase("reload")) {
                            BeSocial.notMembers.load();
                            sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.admin.listReloaded"));
                        }
                        if (args[2].equalsIgnoreCase("save")) {
                            BeSocial.notMembers.save();
                            sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.admin.listSaved"));
                        }
                        if (args[2].equalsIgnoreCase("block")) {
                            if (args.length == 3) {sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.admin.specifyUser"));}
                            else {
                                Player target = Bukkit.getPlayerExact(args[3]);
                                if (target == null) {sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.targetOffline"));}
                                else {
                                    BeSocial.notMembers.add(target.getUniqueId().toString());
                                    Database.savePlayerLeftTime(target);
                                    sender.sendMessage(Messages.getPrefix() + Messages.getAdminMessage("messages.admin.userBlocked", target));
                                }
                            }
                        }
                        if (args[2].equalsIgnoreCase("free")) {
                            if (args.length == 3) {sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.admin.specifyUser"));}
                            else {
                                Player target = Bukkit.getPlayerExact(args[3]);
                                if (target == null) {sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.sender.error.targetOffline"));}
                                else {
                                    BeSocial.notMembers.remove(target.getUniqueId().toString());
                                    sender.sendMessage(Messages.getPrefix() + Messages.getAdminMessage("messages.admin.userFreed", target));
                                }
                            }
                        }
                    }
                }
                else if (args[1].equalsIgnoreCase("config")) {
                    if (args.length == 2) {
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "---BeSocial admin config command list---");
                        sender.sendMessage(ChatColor.ITALIC + "Admin-Sub-Command 'config'");
                        sender.sendMessage("/beso a config reload - Reloads config file from filesystem.\n§cCaution: This will reset the config file if it is corrupted. Make sure to backup your config beforehand.");
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "----------------------------");
                    }
                    else {
                        if (args[2].equalsIgnoreCase("reload")) {
                            Bukkit.getPluginManager().getPlugin(BeSocial.name).reloadConfig();
                            sender.sendMessage(Messages.getPrefix() + "§aMain config reloaded.");
                            BeSocial.loadLangConfig();
                            Messages.update();
                            sender.sendMessage(Messages.getPrefix() + "§aMessages language file reloaded.");
                            BeSocial.loadHelpPage();
                            sender.sendMessage(Messages.getPrefix() + "§aHelppage file reloaded.");
                        }
                    }
                }
                else {
                    sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.special.error"));
                }
            }
        }
        else {
            sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.admin.notAnAdmin"));
        }

    }
}
