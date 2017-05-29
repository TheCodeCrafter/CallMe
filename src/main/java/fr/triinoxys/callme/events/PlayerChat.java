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
    
    public static HashMap<Player, ArrayList<String>> smsReceivers = new HashMap<Player, ArrayList<String>>();
     
    @EventHandler
    public void onAsyncChat(AsyncPlayerChatEvent e){
        ProxiedPlayer p = e.getPlayer();
        
        String msg = e.getMessage();
        String[] splits = msg.split(" ");
        
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
    

