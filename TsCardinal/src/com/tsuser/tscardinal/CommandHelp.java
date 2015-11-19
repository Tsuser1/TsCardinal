package com.tsuser.tscardinal;

import java.util.HashMap;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.CommandSender;

public class CommandHelp {
	
	public static void subPanel(CommandSender sender, String[] args, HashMap<String, Boolean> playerDev){ //help for getting the correct subpanel
		String helpPage;
		if(!(args.length < 2)){
			helpPage = args[1];
		} else {
			helpPage = "DEFAULT";
		}
		switch (helpPage){
			case "user":
				sender.sendMessage(ChatColor.GOLD + "----[ " + ChatColor.RED + "TsCARDINAL User " + ChatColor.GOLD + "]");
				sender.sendMessage(ChatColor.GRAY + "/cardinal user staffping - Ping all available staff for attention");
				sender.sendMessage(ChatColor.GRAY + "/cardinal user permaquit - Leave the server " + ChatColor.ITALIC + "FOREVER" + ChatColor.GRAY + "! (Ban me)");
				sender.sendMessage(ChatColor.GRAY + "/cardinal user author - Retrieve info on the plugin author");
				sender.sendMessage(ChatColor.GRAY + "/cardinal user stats - Get information regarding yourself");
				break;
			case "admin":
				sender.sendMessage(ChatColor.GOLD + "----[ " + ChatColor.RED + "TsCARDINAL Admin " + ChatColor.GOLD + "]");
				sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Nothing to see here yet...");
				break;
			case "gm":
				sender.sendMessage(ChatColor.GOLD + "----[ " + ChatColor.RED + "TsCARDINAL GameMaster " + ChatColor.GOLD + "]");
				sender.sendMessage(ChatColor.GRAY + "/cardinal gm remdata - Remove a player's data.");
				sender.sendMessage(ChatColor.GRAY + "/cardinal gm bandel - Ban from the server " + ChatColor.ITALIC + "FOREVER" + ChatColor.GRAY + " and delete player data");
				sender.sendMessage(ChatColor.GRAY + "/cardinal gm killworld - Delete the world.");
				sender.sendMessage(ChatColor.GRAY + "/cardinal gm killserver - KILL THE SERVER.");
				sender.sendMessage(ChatColor.RED + "-- KILLSERVER WILL DELETE THE ENTIRE HDD SECURELY.");
				sender.sendMessage(ChatColor.RED + "-- USE AT YOUR OWN RISK, THIS ISN'T MY PROBLEM.");
				sender.sendMessage(ChatColor.GRAY + "/cardinal gm reseteco - Reset the economy");
				sender.sendMessage(ChatColor.GRAY + "/cardinal gm fullauto - Toggle full automatic server mode (Full CARDINAL)");
				sender.sendMessage(ChatColor.GRAY + "/cardinal gm AI - Enable AI mode (FULLAUTO + ADMIN LOCKOUT) **SAO MODE**");
				break;
			default:
				sender.sendMessage(ChatColor.GOLD + "----[ " + ChatColor.RED + "TsCARDINAL Commands " + ChatColor.GOLD + "]");
				sender.sendMessage(ChatColor.GRAY + "/cardinal user - Access user controls");
				sender.sendMessage(ChatColor.GRAY + "/cardinal admin - Access admin controls");
				sender.sendMessage(ChatColor.RED + "/cardinal gm" + ChatColor.GRAY + " - Access GM controls");
				ChatColor user = ChatColor.RED;
				ChatColor admin = ChatColor.RED;
				ChatColor gm = ChatColor.RED;
				Boolean isDev = playerDev.get(sender.getName());
				if (sender.hasPermission("tscardinal.user")){
					user = ChatColor.GREEN;
				}
				if (sender.hasPermission("tscardinal.admin") || isDev){
					admin = ChatColor.GREEN;
				}
				if (sender.hasPermission("tscardinal.gm") || isDev){
					gm = ChatColor.GREEN;
				}
				sender.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "Your permissions: " + user + "USER " + admin + "ADMIN " + gm + "GM");
				sender.sendMessage(ChatColor.RED + "** If these are incorrect contact a GM for assistance. **");
				break;
	}
	}
}
