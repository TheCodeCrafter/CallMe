package net.fathomtech.plugins.CityPlus.Utilities;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import net.fathomtech.plugins.CityPlus.Main;
import net.fathomtech.plugins.CityPlus.Handlers.SMS;

public class ChatUtils {
    
    private static FileConfiguration config = Main.plugin.getConfig();
    
    private static String callPrefix = config.getString("CALL.PREFIX");
    private static String callFormat = config.getString("CALL.FORMAT");
    
    private static String smsPrefix = config.getString("SMS.PREFIX");
    private static String smsFormatCaller = config.getString("SMS.FORMAT_CALLER");
    private static String smsFormatTarget = config.getString("SMS.FORMAT_TARGET");
    
	
	public static void sendInfo(String type, Player target, String msg){
	    target.sendMessage((config.getString("INFO_FORMAT").replaceAll("%prefix%", type == "call" ? callPrefix : smsPrefix)
	            .replaceAll("%message%", msg))
	            .replaceAll("&", "§"));
	}
	
	public static void sendCall(Player caller, Player target, String msg){
	    target.sendMessage((callFormat
	            .replaceAll("%prefix%", callPrefix)
	            .replaceAll("%message%", msg)
	            .replaceAll("%sender%", caller.getName())
	            .replaceAll("%target%", target.getName()))
	            .replace('&', '§'));
	}
	
	public static void sendSMS(Player sender, Player target, String msg){
	    target.sendMessage((smsFormatTarget
	            .replaceAll("%prefix%", smsPrefix)
	            .replaceAll("%message%", msg)
	            .replaceAll("%sender%", sender.getName())
	            .replaceAll("%target%", target.getName()))
	            .replace('&', '§'));
	    
	    sender.sendMessage((smsFormatCaller
                .replaceAll("%prefix%", smsPrefix)
                .replaceAll("%message%", msg)
                .replaceAll("%sender%", sender.getName())
                .replaceAll("%target%", target.getName()))
                .replace('&', '§'));
	    
	    SMS.saveSMS(sender, target, msg);
	}
	
} 
