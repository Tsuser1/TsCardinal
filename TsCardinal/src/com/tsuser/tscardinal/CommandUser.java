package com.tsuser.tscardinal;

import java.util.HashMap;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class CommandUser {
	
	public static void subPanel(CommandSender sender, String[] args, HashMap<String, Boolean> playerDev, PluginDescriptionFile pdfFile){ //help for getting the correct subpanel
		String command;
		if(!(args.length < 2)){
			command = args[1];
		} else {
			command = "DEFAULT";
		}
		if(!sender.hasPermission("tscardinal.user")){
			command = "noperm";
		}
		switch (command){
			case "staffping":
				Bukkit.broadcast(ChatColor.RED + "Staff ping! User " + sender.getName() + " needs your attention!", "tscardinal.admin");
				sender.sendMessage(ChatColor.GREEN + "Ping to staff was sent successfully.");
				break;
			case "permaquit":
				//TODO: Add content
				break;
			case "author":
				sender.sendMessage(ChatColor.GREEN + "TsCardinal for Bukkit");
				sender.sendMessage(ChatColor.RED + "Author: " + pdfFile.getAuthors().get(0));
				sender.sendMessage(ChatColor.RED + "Version: " + pdfFile.getVersion());
				sender.sendMessage(ChatColor.RED + "Type: " + "Experimental");
				break;
			case "stats":
				sender.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "Your user information");
				sender.sendMessage(ChatColor.RED + "Username: " + sender.getName().toString());
				sender.sendMessage(ChatColor.RED + "Access level: " + findPerm(sender));
				sender.sendMessage(ChatColor.RED + "Level: " + findLevel(sender));
				break;
			case "noperm":
				if(args[2] == "noperm"){
					sender.sendMessage(ChatColor.RED + "Nice try, this can't be directly referenced.");
				} else {
					sender.sendMessage(ChatColor.RED + "You lack the required permissions to execute user commands!");
				}
				break;
			default:
				sender.sendMessage(ChatColor.RED + "We're sorry, but that command isn't recognized! Try \"/cardinal help user\".");
				break;
		}
	}
	public static String findPerm(CommandSender sender){
		String permLevelStr;
		if(sender.hasPermission("tscardinal.gm")){
			permLevelStr = ChatColor.DARK_RED + "GameMaster";
		} else if(sender.hasPermission("tscardinal.admin")){
			permLevelStr = ChatColor.YELLOW + "Admin";
		} else if(sender.hasPermission("tscardinal.user")){
			permLevelStr = ChatColor.GREEN + "User";
		} else {
			permLevelStr = ChatColor.RED + "" + ChatColor.ITALIC + "Unknown - Contact a GM";
		}
		return permLevelStr;
	}
	public static String findLevel(CommandSender sender){
		String userLevel;
		if(sender.hasPermission("tscardinal.gm") || sender.hasPermission("tscardinal.admin")){
			userLevel = ChatColor.RED + "" + ChatColor.ITALIC + "Admins don't have a level";
		} else {
			userLevel = ChatColor.RED + "Feature not ready, sorry!";
		}
		return userLevel;
	}
}
