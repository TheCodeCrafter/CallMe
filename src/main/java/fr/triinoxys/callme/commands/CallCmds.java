package fr.triinoxys.callme.commands;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import fr.triinoxys.callme.Main;
import fr.triinoxys.callme.events.PlayerChat;
import fr.triinoxys.callme.handlers.Channel;
import fr.triinoxys.callme.utils.ChannelUtils;
import fr.triinoxys.callme.utils.ChatUtils;
 

public class CallCmds implements CommandExecutor{
    
    private void sendInfo(String type, Player target, String msg){
        ChatUtils.sendInfo(type, target, msg);
    }

    @SuppressWarnings ("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        
        if(!(sender instanceof Player))
            return false;
        
        Player p = (Player) sender;
        Player target = null;
        Player caller = null;
        
        if(p.getItemInHand().getTypeId() == Main.plugin.getConfig().getInt("ID")){
            
            /* ------------------ */
            /* | CALL  COMMANDS | */
            /* ------------------ */
            
            if(label.equalsIgnoreCase("appel")){
                if(args.length != 1)
                    sendInfo("call", p, ChatColor.RED + "Usage: /appel <joueur>");
                else{
                    target = Bukkit.getPlayer(args[0]);
                    
                    if(target == p){ 
                        sendInfo("call", p, Main.plugin.getConfig().getString("CALL.CANT_CALL_YOURSELF").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName())); 
                        return false;
                    }
                    
                    if(target != null){
                        if(ChannelUtils.getChannel(p) != null){
                            sendInfo("call", p, Main.plugin.getConfig().getString("CALL.CALLER_ALREADY_IN_CALL").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName()));
                            sendInfo("call", p, Main.plugin.getConfig().getString("CALL.USE_ADD").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName()));
                        }else{
                            if(ChannelUtils.getChannel(target) == null){
                                Main.waitingCall.put(p.getName(), args[0]);
                                sendInfo("call", p, Main.plugin.getConfig().getString("CALL.CALLER_SEND_CALL").replaceAll("%caller%", p.getName()).replaceAll("%target%", args[0]));
                                target.sendMessage("");
                                sendInfo("call", target, Main.plugin.getConfig().getString("CALL.TARGET_RECEIVE_CALL").replaceAll("%caller%", p.getName()).replaceAll("%target%", args[0]));
                                sendInfo("call", target, Main.plugin.getConfig().getString("CALL.TYPE_YES").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName()));
                                sendInfo("call", target, Main.plugin.getConfig().getString("CALL.TYPE_NO").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName()));
                            }
                            else sendInfo("call", p, Main.plugin.getConfig().getString("CALL.TARGET_ALREADY_IN_CALL").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName()));
                        }
                    }else sendInfo("call", p, Main.plugin.getConfig().getString("CALL.TARGET_OFFLINE").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName()));
                } 
            }
            
            else if(label.equalsIgnoreCase("ajout")){
                if(args.length != 1)
                    sendInfo("call", p, ChatColor.RED + "Usage: /ajout <joueur>");
                else{
                    target = Bukkit.getPlayer(args[0]);
                    
                    if(ChannelUtils.isCaller(p)){
                        
                        if(target == p){
                            sendInfo("call", p, Main.plugin.getConfig().getString("CALL.CANT_ADD_YOURSELF").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName()));
                            return false;
                        }
                        
                        if(target != null){
                            if(ChannelUtils.getChannel(target) == null){
                                Main.waitingAdd.put(p.getName(), args[0]);
                                sendInfo("call", p, Main.plugin.getConfig().getString("CALL.CALLER_SEND_CALL")
                                        .replaceAll("%target%", target.getName())
                                        .replaceAll("%caller%", p.getName()));
                                
                                sendInfo("call", target, Main.plugin.getConfig().getString("CALL.TARGET_RECEIVE_ADD")
                                        .replaceAll("%target%", target.getName())
                                        .replaceAll("%caller%", p.getName()));
                                
                                sendInfo("call", target, Main.plugin.getConfig().getString("CALL.TYPE_YES")
                                        .replaceAll("%target%", target.getName())
                                        .replaceAll("%caller%", p.getName()));
                                
                                sendInfo("call", target, Main.plugin.getConfig().getString("CALL.TYPE_NO")
                                        .replaceAll("%target%", target.getName())
                                        .replaceAll("%caller%", p.getName()));  
                            }
                            else sendInfo("call", p, Main.plugin.getConfig().getString("CALL.TARGET_ALREADY_IN_CALL").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName()));
                        }
                        else sendInfo("call", p, Main.plugin.getConfig().getString("CALL.TARGET_OFFLINE").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName()));  
                    }
                    else{
                        sendInfo("call", p, Main.plugin.getConfig().getString("CALL.NOT_CALL_LEADER").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName()));  
                    }
                    
                }
            }
            
            else if(label.equalsIgnoreCase("oui")){
                if(args.length != 1)sendInfo("call", p, ChatColor.RED + "Usage: /oui <joueur>");
                else{
                    caller = Bukkit.getPlayer(args[0]);
                    if(caller != null){
                        if(Main.waitingCall.containsKey(caller.getName()) && Main.waitingCall.get(caller.getName()).equalsIgnoreCase(p.getName())){
                            Main.waitingCall.put(caller.getName(), "");
                            Channel channel = new Channel(caller, new ArrayList<String>());
                            channel.addMember(p);
                            Main.channels.add(channel);
                            
                            sendInfo("call", caller, Main.plugin.getConfig().getString("CALL.CALLER_ACCEPT_CALL").replaceAll("%target%", p.getName()).replaceAll("%caller%", caller.getName()));
                            sendInfo("call", p, Main.plugin.getConfig().getString("CALL.TARGET_ACCEPT_CALL").replaceAll("%target%", p.getName()).replaceAll("%caller%", caller.getName()));
                            return true;
                        }
                        else if(Main.waitingAdd.containsKey(caller.getName()) && Main.waitingAdd.get(caller.getName()).equalsIgnoreCase(p.getName())){
                                ChannelUtils.getChannel(caller).addMember(p);
                                sendInfo("call", caller, Main.plugin.getConfig().getString("CALL.CALLER_ACCEPT_ADD").replaceAll("%target%", p.getName()).replaceAll("%caller%", caller.getName()));
                                sendInfo("call", p, Main.plugin.getConfig().getString("CALL.TARGET_ACCEPT_ADD").replaceAll("%target%", p.getName()).replaceAll("%caller%", caller.getName()));
                                return true;
                        }
                        else{
                            sendInfo("call", p, Main.plugin.getConfig().getString("CALL.NOT_CALLED_YOU").replaceAll("%target%", p.getName()).replaceAll("%caller%", args[0]));
                        }
                    }
                    else{
                        sendInfo("call", p, Main.plugin.getConfig().getString("CALL.CALLER_OFFLINE").replaceAll("%target%", p.getName()).replaceAll("%caller%", args[0]));
                        return false;
                    }
                }
            }
            
            else if(label.equalsIgnoreCase("non")){
                if(args.length != 1)sendInfo("call", p, ChatColor.RED + "Usage: /non <joueur>");
                else{
                    caller = Bukkit.getServer().getPlayer(args[0]);
                    if(caller != null){
                        if(Main.waitingCall.containsKey(caller.getName()) && Main.waitingCall.get(caller.getName()).equalsIgnoreCase(p.getName())) Main.waitingCall.put(caller.getName(), "");
                        else if(Main.waitingAdd.containsKey(caller.getName()) && Main.waitingAdd.get(caller.getName()).equalsIgnoreCase(p.getName())) Main.waitingAdd.put(caller.getName(), "");
                        else{
                            sendInfo("call", p, Main.plugin.getConfig().getString("CALL.NOT_CALLED_YOU").replaceAll("%target%", p.getName()).replaceAll("%caller%", args[0]));
                            return true;
                        }
                        sendInfo("call", caller, Main.plugin.getConfig().getString("CALL.CALLER_DENY_CALL").replaceAll("%target%", p.getName()).replaceAll("%caller%", args[0]));
                        sendInfo("call", p, Main.plugin.getConfig().getString("CALL.TARGET_DENY_CALL").replaceAll("%target%", p.getName()).replaceAll("%caller%", args[0]));
                        
                    }
                    else sendInfo("call", p, Main.plugin.getConfig().getString("CALL.CALLER_OFFLINE").replaceAll("%target%", p.getName()).replaceAll("%caller%", args[0]));
                }
            }
            
            else if(label.equalsIgnoreCase("raccrocher")){
                if(ChannelUtils.getChannel(p) == null){
                    ChatUtils.sendInfo("call", p, Main.plugin.getConfig().getString("CALL.NOT_IN_CALL").replaceAll("%player%", p.getName()));
                    return true;
                }
                if(ChannelUtils.isCaller(p)){
                    for(String pl : ChannelUtils.getChannel(p).getMembers()){
                        if(pl.equals(p.getName())) continue;
                        sendInfo("call", Bukkit.getPlayer(pl), Main.plugin.getConfig().getString("CALL.END_CALL").replaceAll("%caller%", p.getName()));
                    }
                    sendInfo("call", p, Main.plugin.getConfig().getString("CALL.END_CALL_CALLER").replaceAll("%caller%", p.getName()));
                    ChannelUtils.getChannel(p).removeAll();
                    
                }
                else{
                    for(String pl : ChannelUtils.getChannel(p).getMembers()){
                        ChatUtils.sendInfo("call", Bukkit.getPlayer(pl), Main.plugin.getConfig().getString("CALL.QUIT_CALL").replaceAll("%target%", p.getName()).replaceAll("%caller%", ChannelUtils.getChannel(p).getCaller().getName()));
                    }
                    ChannelUtils.getChannel(p).removeMember(p);
                }
            }
            
            else if(label.equalsIgnoreCase("fin")){
                if(ChannelUtils.getChannel(p) == null){
                    sendInfo("call", p, Main.plugin.getConfig().getString("CALL.NOT_IN_CALL").replaceAll("%target%", p.getName()));
                    return true;
                }
                
                if(ChannelUtils.isCaller(p)){
                    for(String pl : ChannelUtils.getChannel(p).getMembers()){
                        if(pl.equals(p.getName()))
                            continue;
                        sendInfo("call", Bukkit.getPlayer(pl), Main.plugin.getConfig().getString("CALL.END_CALL").replaceAll("%caller%", p.getName()));
                    }
                    sendInfo("call", p, Main.plugin.getConfig().getString("CALL.END_CALL_CALLER").replaceAll("%caller%", p.getName()));
                    ChannelUtils.getChannel(p).removeAll();
                    
                }else sendInfo("call", p, Main.plugin.getConfig().getString("CALL.CANT_END").replaceAll("%caller%", p.getName()));
                
            }
            
            else if(label.equalsIgnoreCase("global")){
                if(ChannelUtils.getChannel(p) != null){
                    String msg = " ";
                    for(int i = 0; i < args.length; i++)
                        msg += " " + args[i];
                    PlayerChat.global.add(p.getName());
                    p.chat(msg.trim());
                }
                else sendInfo("call", p, Main.plugin.getConfig().getString("CALL.NOT_IN_CALL").replaceAll("%target%", p.getName()));
            }
            return true;
        }
        else p.sendMessage((Main.plugin.getConfig().getString("NO_ITEM").replaceAll("%id%", Main.plugin.getConfig().getString("ID").replaceAll("%player%", p.getName())).replace('&', '§')));
        return false;
    }
    
}
