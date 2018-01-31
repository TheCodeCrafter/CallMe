package net.fathomtech.plugins.CityPlus.Handlers.Channel;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import net.fathomtech.plugins.CityPlus.Utilities.ChatUtils;


public class Channel{
    
    private Player caller;
    private ArrayList<String> members = new ArrayList<String>();
    
    public Channel(Player caller, ArrayList<String> members){
        this.caller = caller;
        this.members = members;
        
        members.add(caller.getName());
    }
    
    public ArrayList<String> getMembers(){
        return members;
    }
    
    public boolean hasMember(Player player){
        boolean hasMember = false;
        for(String pl : members) if(pl.equalsIgnoreCase(player.getName())) hasMember = true;
        return hasMember;
    }
    
    public void addMember(Player player){
        members.add(player.getName());
    }
    
    public void removeMember(Player player){
        members.remove(player.getName());
    }
    
    public void removeAll(){
        this.members.clear();
    }
    
    public void broadcastChannel(Player caller, String msg){
        for(String pl : getMembers()){
            ChatUtils.sendCall(caller, Bukkit.getPlayer(pl), msg);
        }
    }
    
    public Player getCaller(){
        return caller;
    }
    
}
