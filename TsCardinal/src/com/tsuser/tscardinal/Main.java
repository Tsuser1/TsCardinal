package com.tsuser.tscardinal;

import java.util.HashMap;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.fusesource.jansi.Ansi;

import com.tsuser.tscardinal.CommandHelp;

public class Main extends JavaPlugin implements Listener{
	Ansi resetAnsi = Ansi.ansi().fg(Ansi.Color.WHITE);
	HashMap<String, Boolean> playerDev = new HashMap<String, Boolean>();
	PluginDescriptionFile pdfFile=this.getDescription();
	PluginManager pm=this.getServer().getPluginManager();
    public static Permission permission = null;
    public static Economy economy = null;
    public static Chat chat = null;
	public void onEnable(){
        setupPermissions();
        setupChat();
		if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		pm.registerEvents(this, Main.getPlugin(com.tsuser.tscardinal.Main.class));
		getLogger().info(Ansi.ansi().fg(Ansi.Color.RED) + "TsCardinal has been enabled!" + resetAnsi);
	}
    private boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    private boolean setupChat()
    {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
    public static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
	@EventHandler(priority=EventPriority.NORMAL) public void onSignChange(SignChangeEvent event){
		  if (!event.isCancelled()) {
		    SignChangeEvent sign=event;
		    Player player=event.getPlayer();
		    if (sign.getLine(0).equalsIgnoreCase("[tscshop]") && (player.hasPermission("tscardinal.admin"))) {
		        if (isInteger(sign.getLine(1),16)) {
		        	//sign.setLine(3,ChatColor.GRAY + player.getName());
		        	//sign.setLine(2, ChatColor.RED + "Not ready yet");
		        	sign.setLine(0, ChatColor.DARK_GREEN + "[" + ChatColor.RED + "TSC Shop" + ChatColor.DARK_GREEN + "]");
		        	player.sendMessage(ChatColor.GREEN + "Shop created! Item: " + sign.getLine(2) + " Ammt: " + sign.getLine(1) + " Price: " + economy.format(Double.valueOf(sign.getLine(3))));
		        	} else {
		        	sign.setLine(0, ChatColor.DARK_RED + "TSC Shop");
		        	sign.setLine(1,ChatColor.RED + "ITEM_AMMOUNT");
		        	sign.setLine(2,ChatColor.RED + "ITEM_NAME");
		        	sign.setLine(3,ChatColor.RED + "ITEM_PRICE");
		        	player.sendMessage(ChatColor.RED + "The sign you attempted to create was invalid. Sorry.");
		        }
		    }
		    if (sign.getLine(0).equalsIgnoreCase("[tsctest]")){
		    	sign.setLine(0, ChatColor.GREEN + "-= Test =-");
		    	sign.setLine(1, ChatColor.MAGIC + "XXXXXXXXXXXXXXX");
		    	sign.setLine(2, ChatColor.RED + "Looking good");
		    	sign.setLine(3, ChatColor.MAGIC + "XXXXXXXXXXXXXXX");
		    	player.sendMessage(ChatColor.GREEN + "Sign events seem to be working properly.")
		    	;
		    }
	
		}
	}
	@EventHandler
	public void onInteract(PlayerInteractEvent Event) {
		Player player = Event.getPlayer();
		Block block = Event.getClickedBlock();
		int ammount;
		Material item;
		double price;
		if (block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST) {
			Sign sign = (Sign) block.getState();
			String signhead = sign.getLine(0);
			if(signhead.equalsIgnoreCase(ChatColor.DARK_GREEN + "[" + ChatColor.RED + "TSC Shop" + ChatColor.DARK_GREEN + "]")){
				ammount = Integer.valueOf(sign.getLine(1));
				item = Material.getMaterial(sign.getLine(2).toUpperCase());
				price = Double.parseDouble(sign.getLine(3));
				if(economy.has(player.getPlayer(), price)){
					economy.bankWithdraw(player.getName(), price);
					player.getInventory().addItem(new ItemStack(item, ammount));
				} else {
					player.sendMessage(ChatColor.RED + "You have insufficient funds!");
				}
			}
		}
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("cardinal")){
			if(sender instanceof Player){
				//Player player = (Player) sender;
				String uuid = sender.getName();
				if(!(playerDev.containsKey(uuid))){
					playerDev.put(uuid, false); //by default make them a non-dev
				}
				commandHandler(sender, args, null, playerDev, uuid, null, null, null, pdfFile);
			} else {
				sender.sendMessage(Ansi.ansi().fg(Ansi.Color.RED) + "TsCARDINAL commands can't be executed from the console.");
				getLogger().warning("TsCARDINAL couldn't execute the command it got from Console. Sorry about that.");
				getLogger().warning("To authorize a high security operation use \"authorize\" in console.");
			}
		}
		if(cmd.getName().equalsIgnoreCase("authorize")){
			Boolean hddwipeOkay = false;
			Boolean worldwipeOkay = false;
			if(sender instanceof Player){
				sender.sendMessage(ChatColor.RED + "This command may only be used by Console.");
			} else {
				if(args.length > 1){
					if(args[1] == "hddwipe"){
						hddwipeOkay = true; //HDDWIPE AUTHORIZED
						sender.sendMessage(ChatColor.GOLD + "Successfully authorized an HDD secure wipe. I bid you farewell kind server.");
						getLogger().warning("HDD wipe was authorized at this time.");
					}
					if(args[1] == "worldwipe"){
						worldwipeOkay = true; //WORLDWIPE AUTHORIZED
						sender.sendMessage(ChatColor.GOLD + "Successfully authorized an secure world wipe. Goodbye cruel world!");
						getLogger().warning("World wipe was authorized at this time.");
					}
				} else {
					sender.sendMessage("Ready for authorization, run \"authorize hddwipe\" or \"authorize worldwipe\"");
					sender.sendMessage("Status:");
					sender.sendMessage("Worldwipe: " + worldwipeOkay.toString());
					sender.sendMessage("HDD wipe: " + hddwipeOkay.toString());
				}
			}
		}
			
		return false;
	}
	public void commandHandler(CommandSender sender, String[] args, CommandHelp command, HashMap<String, Boolean> playerDev, String uuid, CommandAdmin commandAdm, CommandGM commandGM, CommandUser commandUser, PluginDescriptionFile pdfFile){
		Boolean isDev = playerDev.get(sender.getName());
		if(args.length < 1){
			sender.sendMessage(ChatColor.GOLD + "Unrecognized command subset!");
			sender.sendMessage(ChatColor.RED + "Please use /cardinal help");
		} else {
			switch (args[0]){
				case "help":
					CommandHelp.subPanel(sender, args, playerDev);
					break;
				case "user":
					sender.sendMessage(ChatColor.RED + "Sorry, but this feature is still under development!");
					if(sender.hasPermission("tscardinal.user")){
						sender.sendMessage(ChatColor.GREEN + "You have permission to use this module");
					}
					CommandUser.subPanel(sender, args, playerDev, pdfFile);
					break;
				case "admin":
					sender.sendMessage(ChatColor.RED + "Sorry, but this feature is still under development!");
					if(sender.hasPermission("tscardinal.admin") || isDev){
						sender.sendMessage(ChatColor.GREEN + "You have permission to use this module");
					}
					CommandAdmin.subPanel(sender, args, playerDev);
					break;
				case "gm":
					sender.sendMessage(ChatColor.RED + "Sorry, but this feature is still under development!");
					if(sender.hasPermission("tscardinal.gm") || isDev){
						sender.sendMessage(ChatColor.GREEN + "You have permission to use this module");
					}
					CommandGM.subPanel(sender, args, playerDev);
					break;
				case "dev":
					if(!(sender.getServer().getPlayer(sender.getName()).getUniqueId().toString().equalsIgnoreCase("942ebd1f-cbc4-47b4-bf4f-f7cd897444fb"))){
						sender.sendMessage(ChatColor.RED + "You're not the developer, this action has been logged.");
						getLogger().warning(Ansi.ansi().fg(Ansi.Color.YELLOW) + "The user " + sender.getName() + " attempted to access TsCardinal DEVMODE!" + resetAnsi);
						Bukkit.broadcast(ChatColor.YELLOW + "User " + sender.getName() + " attempted to access DEVMODE!", "tscardinal.admin");
					} else {
						//sender.addAttachment(this, "dev", true); //outdated, memory leech
						sender.sendMessage(ChatColor.GOLD + "Developer mode was activated successfully. Welcome back GM!");
						Bukkit.broadcast(ChatColor.RED + "User " + sender.getName() + " has entered DEVMODE and is a temporary GM, please don't be alarmed.", "tscardinal.admin");
						if(sender.hasPermission("tscardinal.gm")){
							sender.sendMessage(ChatColor.GOLD + "You were already a GM on this server.");
						}
						playerDev.put(uuid, true);
					}
					break;
				case "myuuid":
					sender.sendMessage(ChatColor.GRAY + "Your UUID is: " + sender.getServer().getPlayer(sender.getName()).getUniqueId());
					break;
				default:
					sender.sendMessage(ChatColor.RED + "I'm sorry, but that command is invalid. Try \"/cardinal help\"");
					break;
			}
		}
	}
}