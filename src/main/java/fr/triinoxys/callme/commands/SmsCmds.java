package net.fathomtech.plugins.CityPlus.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import fr.triinoxys.callme.Main;
import fr.triinoxys.callme.handlers.SMS;
import fr.triinoxys.callme.utils.ChatUtils;


public class SmsCmds implements CommandExecutor{
    
    private void sendSMS(Player caller, Player target, String msg){
        ChatUtils.sendSMS(caller, target, msg);
    }
    
    private void sendInfo(String type, Player target, String msg){
        ChatUtils.sendInfo(type, target, msg);
    }

    @SuppressWarnings ("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        
        if(!(sender instanceof Player))
            return false; 
        
        Player p = (Player) sender;
        Player target = null;
        
        if(p.getItemInHand().getTypeId() == Main.plugin.getConfig().getInt("ID")){
            if(label.equalsIgnoreCase("sms")){
                if(args.length >= 2){
                    target = Bukkit.getPlayer(args[0]);
                    if(target != null){
                            String msg = "";
                            for(int i = 1; i < args.length; i++) msg += " " + args[i];
                            sendSMS(p, target, msg); 
                    }
                    else sendInfo("sms", p, Main.plugin.getConfig().getString("SMS.TARGET_OFFLINE").replaceAll("%target%", args[0]));
                }
                else sendInfo("sms", p, "§cUsage: /sms <player> <message>");
            }
            
            else if(label.equalsIgnoreCase("smslist") || label.equalsIgnoreCase("smsliste")){
                if(args.length >= 1){
                    SMS.loadSMS(p, args[0]);  
                }
                else sendInfo("sms", p, ChatColor.RED + "Usage: /smslist <player>");
            } 
            return true;
        }
        else p.sendMessage((Main.plugin.getConfig().getString("NO_ITEM").replaceAll("%id%", Main.plugin.getConfig().getString("ID").replaceAll("%player%", p.getName())).replace('&', '§')));
        return false;
    }
    
    
    
}
