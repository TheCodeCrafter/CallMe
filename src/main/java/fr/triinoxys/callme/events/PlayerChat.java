package fr.triinoxys.callme.events;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import fr.triinoxys.callme.Main;
import fr.triinoxys.callme.utils.ChannelUtils;
import fr.triinoxys.callme.utils.ChatUtils;


@SuppressWarnings ("deprecation")
public class PlayerChat implements Listener{
    
    public static ArrayList<String> global = new ArrayList<String>();
    public static ArrayList<String> inGUI = new ArrayList<String>();
    
    public static HashMap<Player, ArrayList<String>> smsReceivers = new HashMap<Player, ArrayList<String>>();
     
    @EventHandler
    public void onAsyncChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        
        String msg = e.getMessage();
        String[] splits = msg.split(" ");
        
        if(inGUI.contains(p.getName())){
            e.setCancelled(true);
            if(Main.guiStatus.get(p.getName()) == null)
                Main.guiStatus.put(p.getName(), "not in GUI");
            
            switch(Main.guiStatus.get(p.getName()).toLowerCase()){
                case "appel":
                    int tour = 1;
                    for(String split : splits){
                        if(tour == 1){
                            Bukkit.dispatchCommand(p, "appel " + split);
                            tour++;
                        }
                        else Bukkit.dispatchCommand(p, "ajout " + split);
                    }
                    break;
                case "ajout":
                    if(ChannelUtils.getChannel(p) != null){
                        if(ChannelUtils.isCaller(p)){
                            for(String split : splits){
                                Bukkit.dispatchCommand(p, "ajout " + split);
                            }
                        }
                        else ChatUtils.sendInfo("sms", p, Main.plugin.getConfig().getString("CALL.NOT_CALL_LEADER").replaceAll("%target%", splits[0]).replaceAll("%caller%", p.getName()));   
                    }
                    else ChatUtils.sendInfo("sms", p, Main.plugin.getConfig().getString("CALL.NOT_IN_CALL").replaceAll("%target%", splits[0]).replaceAll("%caller%", p.getName()));   
                    break;
                case "oui":
                    Bukkit.dispatchCommand(p, "oui " + msg);
                    break;
                case "non":
                    Bukkit.dispatchCommand(p, "non " + msg);
                    break;
                case "global":
                    Bukkit.dispatchCommand(p, "global " + msg);
                    break;
                case "sms name":
                    if(smsReceivers.get(p) == null)
                        smsReceivers.put(p, new ArrayList<String>());
                    for(String split : splits){
                        smsReceivers.get(p).add(split);
                    }
                    Main.guiStatus.put(p.getName(), "sms text");
                    ChatUtils.sendInfo("sms", p, Main.plugin.getConfig().getString("GUI.ENTER_A_MSG").replaceAll("%player%", p.getName()));
                    break;
                case "sms text":
                    if(smsReceivers.get(p) == null)
                        smsReceivers.put(p, new ArrayList<String>());
                    for(String target : smsReceivers.get(p)){
                        Bukkit.dispatchCommand(p, "sms " + target + " " + msg);
                    }
                    smsReceivers.put(p, new ArrayList<String>());
                    break;
                case "smslist":
                    Bukkit.dispatchCommand(p, "smslist " + splits[0]);
                    break;
                case "not in GUI":
                    return;
            }
            if(!Main.guiStatus.get(p.getName()).equals("sms text")){
                Main.guiStatus.put(p.getName(), "not in GUI");
                inGUI.remove(p.getName());
            }
            return;
        }
        
        if(global.contains(p.getName())){
            global.remove(p.getName());
            return;
        }
        
        if(ChannelUtils.getChannel(p) != null){
            e.setCancelled(true);
            ChannelUtils.getChannel(p).broadcastChannel(p, msg);
        }
        
    }
    
    @EventHandler
    public void onChat(PlayerChatEvent e){
        Player p = e.getPlayer();
        String msg = e.getMessage();
        
        if(inGUI.contains(p.getName())){
            e.setCancelled(true);
            if(Main.guiStatus.get(p.getName()) == null)
                Main.guiStatus.put(p.getName(), "not in GUI");
            
            if(Main.guiStatus.get(p.getName()).equals("global")){
                Bukkit.dispatchCommand(p, "global " + msg);
                Main.guiStatus.put(p.getName(), "not in GUI");
                inGUI.remove(p.getName());
            }
        }
    }
}
    

