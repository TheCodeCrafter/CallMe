package net.fathomtech.plugins.CityPlus.Handlers;

import java.util.ArrayList;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import net.fathomtech.plugins.CityPlus.Main;
import net.fathomtech.plugins.CityPlus.Utilities.ChatUtils;

@SuppressWarnings("unchecked")
public class SMS {
    
    static FileConfiguration data = Main.sms.data;
    static FileConfiguration config = Main.plugin.getConfig();
    
    //TODO: mettre pseudos en minsucules pour pas avoir besoin de mettre les majs quand on /smslist
    public static void saveSMS(Player sender, Player target, String sms){
        String s = sender.getName();
        String t = target.getName();
        ArrayList<String> list = new ArrayList<String>();
        
        System.out.println("saveSMS: " + sms);
        
        if(data.contains(s + " and " + t)){
            list = (ArrayList<String>) data.get(s + " and " + t);
            list.add(sender.getName() + " &6->&r " + sms);
            Main.sms.data.set(s + " and " + t, list);
        }
        else if(data.contains(t + " and " + s)){
            list = (ArrayList<String>) data.get(t + " and " + s);
            list.add(sender.getName() + " &6->&r " + sms);
            Main.sms.data.set(t + " and " + s, list);
        }
        else{
            list.add(sender.getName() + " &6->&r " + sms);
            Main.sms.data.set(s + " and " + t, list);
        }
        
        Main.sms.save();
    }
    
    public static void loadSMS(Player sender, String target){
        String s = sender.getName();
        String t = target;
        ArrayList<String> list = new ArrayList<String>();
        
        if(data.contains(s + " and " + t)){
            list = (ArrayList<String>) data.get(s + " and " + t);
            
            sender.sendMessage("");
            ChatUtils.sendInfo("sms", sender, config.getString(("SMS.ANNOUNCE_LIST")
                    .replaceAll("%sender%", s))
                    .replaceAll("%target%", t));
            
            for(String sms : list){
                ChatUtils.sendInfo("sms", sender, config.getString((("SMS.SMS_LIST")
                        .replaceAll("%sender%", s))
                        .replaceAll("%target%", t))
                        .replaceAll("%sms%", sms));
            }
            ChatUtils.sendInfo("sms", sender, "§7----------------------------------------");
            sender.sendMessage("");
            Main.sms.data.set(s + " and " + t, list);
        }
        else if(data.contains(t + " and " + s)){
            list = (ArrayList<String>) Main.sms.data.get(t + " and " + s);
            
            sender.sendMessage("");
            ChatUtils.sendInfo("sms", sender, config.getString(("SMS.ANNOUNCE_LIST")
                    .replaceAll("%sender%", s))
                    .replaceAll("%target%", t));
            
            for(String sms : list){
                ChatUtils.sendInfo("sms", sender, config.getString((("SMS.SMS_LIST")
                        .replaceAll("%sender%", s))
                        .replaceAll("%target%", t))
                        .replaceAll("%sms%", sms));
            }
            ChatUtils.sendInfo("sms", sender, "§7----------------------------------------");
            sender.sendMessage("");
            Main.sms.data.set(t + " and " + s, list);
        }
        else{
            ChatUtils.sendInfo("sms", sender, config.getString(("SMS.CANT_LOAD")
                    .replaceAll("%sender%", s))
                    .replaceAll("%target%", t));
        }
    }
    
}
