package com.tsuser.tscardinal;

import java.util.HashMap;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.CommandSender;

public class CommandGM {
	
	public static void subPanel(CommandSender sender, String[] args, HashMap<String, Boolean> playerDev){ //help for getting the correct subpanel
		String command;
		if(!(args.length < 2)){
			command = args[1];
		} else {
			command = "DEFAULT";
		}
		switch (command){
			case "staffping":
				//TODO: Add content
				break;
			case "permaquit":
				//TODO: Add content
				break;
			case "author":
				//TODO: Add content
				break;
			case "stats":
				//TODO: Add content
				break;
			default:
				sender.sendMessage(ChatColor.RED + "We're sorry, but that command isn't recognized! Try \"/cardinal help user\".");
				break;
		}
	}
}
