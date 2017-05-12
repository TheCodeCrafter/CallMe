package fr.triinoxys.callme.utils;

import org.bukkit.entity.Player;
import fr.triinoxys.callme.Main;
import fr.triinoxys.callme.handlers.Channel;


public class ChannelUtils{
    
    public static Channel getChannel(Player p){
        Channel channel = null;
        for(Channel c : Main.channels) 
            if(c.hasMember(p)) channel = c;
        return channel;
    }
    
    public static boolean isCaller(Player p){
        if(getChannel(p) != null && getChannel(p).getCaller() == p) return true;
        else return false;
    }
    
}
