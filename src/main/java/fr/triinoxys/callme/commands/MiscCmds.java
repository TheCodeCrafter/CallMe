package net.fathomtech.plugins.CityPlus.Commands;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import fr.triinoxys.callme.Main;


public class MiscCmds implements CommandExecutor{
    
    private Main plugin;
    
    public MiscCmds(Main instance){
        plugin = instance;
    }
    
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[]args){  
        if(!(sender instanceof Player))
            return false; 
        
        Player p = (Player) sender;
        
        //!\\ PLEASE DO NOT CHANGE THESE INFOS, THE PLUGIN IS FREE SO LET ME THIS ONLY THING //!\\
        if(label.equalsIgnoreCase("havenphone")){
            if(args.length >= 1){
                if(args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("infos")){
                    p.sendMessage("");
                    p.sendMessage("§8-------------------------");
                    p.sendMessage("§a Développeur: §eTheCodeCrafter");
                    p.sendMessage("§a Site: §efathomtech.net");
                    p.sendMessage("§a Plugin: §eHavenPhone");
                    p.sendMessage("§a Version: §e" + plugin.getDescription().getVersion());
                    p.sendMessage("§8-------------------------");
                }
                else if(args[0].equalsIgnoreCase("update")) try{
                    plugin.updater.updateCommand(sender, args);
                }catch(IOException | URISyntaxException e){
                    e.printStackTrace();
                }
                else if(args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("ver"))
                    sender.sendMessage(plugin.getDescription().getFullName()); 
                else sender.sendMessage("§cUsage: /" + plugin.getDescription().getName() + " <infos | version | update>");
            }
            else sender.sendMessage("§cUsage: /" + plugin.getDescription().getName() + " <infos | version | update>");
            return true;
        }
        else if(label.equalsIgnoreCase("hour")){
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(plugin.getConfig().getString("HOUR_FORMAT"));
            p.sendMessage(plugin.getConfig().getString("HOUR_MESSAGE").replaceAll("%hour%", sdf.format(cal.getTime())).replaceAll("%player%", p.getName()).replace('&', '§'));
        }
        return false;
    }
    
    
    
}


