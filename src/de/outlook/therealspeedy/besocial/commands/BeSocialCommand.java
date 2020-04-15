package de.outlook.therealspeedy.besocial.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.outlook.therealspeedy.besocial.BeSocial;
import de.outlook.therealspeedy.besocial.util.Messages;
import de.outlook.therealspeedy.besocial.util.Players;

public class BeSocialCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		String senderID = ((Player) sender).getUniqueId().toString();
		
		if (Players.notMember((Player) sender) && !sender.hasPermission("besocial.admin")) {
			sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.special.leaveBeSocial2"));
		}
		else {
		if (ishelp(args)) {
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
			sender.sendMessage("     /beso info - Shows information about BeSocial.");
			if (sender.hasPermission("besocial.admin")) {sender.sendMessage(ChatColor.ITALIC + "     /besocial admin = /beso a");}
			sender.sendMessage("");
			sender.sendMessage("     To leave the BeSocial program,");
			sender.sendMessage("     use the following command:" + ChatColor.BOLD + " /besocial leave");
			sender.sendMessage(ChatColor.LIGHT_PURPLE + "-----------------------");
			}
			else if (args[0].equalsIgnoreCase("leave")) {
				sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.special.leaveBeSocial"));

				
				sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.special.leaveBeSocial2"));

				String notMember = senderID;
				BeSocial.notMembers.add(notMember);
				
			}
			else if (args[0].equalsIgnoreCase("admin") || args[0].equalsIgnoreCase("a")) {
				if (sender.hasPermission("besocial.admin")) {
					if (args.length == 1) {
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "---BeSocial admin commands---");
						sender.sendMessage("/beso a users - Commands for user management.");
						sender.sendMessage(ChatColor.LIGHT_PURPLE + "----------------------------");
					}
					else {
						if (args[1].equalsIgnoreCase("users")) {
							if (args.length == 2) {
								sender.sendMessage(ChatColor.LIGHT_PURPLE + "---BeSocial admin command list---");
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
						else {
							sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.special.error"));
						}
					}
				}
				else {
					sender.sendMessage(Messages.getPrefix() + Messages.getInfoMessage("messages.admin.notAnAdmin"));
				}
			}
			else if (args[0].equalsIgnoreCase("info")) {
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "---BeSocial information---");
				sender.sendMessage(ChatColor.ITALIC + "" + ChatColor.GREEN + " This plugin was originally written for the RainbowRED Network.");
				sender.sendMessage(ChatColor.ITALIC + "" + ChatColor.GREEN + " This plugin can be used on any server for free!");
				sender.sendMessage("");
				sender.sendMessage(" Plugin name: BeSocial");
				sender.sendMessage(" Version: 10.2");
				sender.sendMessage(" Author: RainbowSpeedy");
				sender.sendMessage(" Description: Make love, not war. <3");
				sender.sendMessage(ChatColor.LIGHT_PURPLE + "----------------------------------------------");
				
			}
		}
		return false;
	}
	
	
	private boolean ishelp(String[] args) {
		if (args.length == 0) return true;
		else if (args[0].equalsIgnoreCase("help")) return true;
		else return false;
	}

}
