package net.fathomtech.plugins.CityPlus.Utilities;

import org.bukkit.entity.Player;
import net.fathomtech.plugins.CityPlus.Main;
import net.fathomtech.plugins.CityPlus.Handlers.Channel;


public class ChannelUtils {
    
    public static Channel getChannel(Player p) {
        Channel channel = null;
        for(Channel c : Main.channels) 
            if(c.hasMember(p)) channel = c;
        return channel;
    }
    
    public static boolean isCaller(Player p) {
        if(getChannel(p) != null && getChannel(p).getCaller() == p) return true;
        else return false;
    }
    
}
