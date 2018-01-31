package net.fathomtech.plugins.CityPlus.Commands;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.fathomtech.plugins.CityPlus.Main;
import net.fathomtech.plugins.CityPlus.Events.PlayerChat;
import net.fathomtech.plugins.CityPlus.Handlers.Channel;
import net.fathomtech.plugins.CityPlus.Utilities.ChannelUtils;
import net.fathomtech.plugins.CityPlus.Utilities.ChatUtils;

public class CallCommands implements CommandExecutor {
  
  private void sendInfo(String type, Player target, String msg) {
    ChatUtils.sendInfo(type, target, msg);
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if(!(sender instanceof Player)) return false;
    
    Player player = (Player) sender;
    Player target = null;
    Player caller = null;
    
    if(p.getItemInHand().getTypeId() !== Main.plugin.getConfig().GetInt("ID")) {
      p.sendMessage((Main.plugin.getConfig().getString("NO_ITEM").replaceAll("%id%", Main.plugin.getConfig().getString("ID").replaceAll("%player%", p.getName())).replace('&', '§')));
      return false;
    }
    
    if(label.equalsIgnoreCase("call")) {
      if(args.length != 1) {
        // There isn't 1 argument
        sendInfo("call", p, ChatColor.RED + "Usage: /call <player>");
      } else {
        // There is 1 argument
        target = Bukkit.getPlayer(args[0]);
        
        // If they're trying to call themselves
        if(target == p) {
          sendInfo("call", p, Main.plugin.getConfig().getString("CALL.CANT_CALL_YOURSELF").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName()));
          return false;
        }
        
        if(target != null) {
          // The player is online on the server
          if(ChannelUtils.getChannel(p) != null) {
            // The caller (one who executed the command) is already in a call
            sendInfo("call", p, Main.plugin.getConfig().getString("CALL.CALLER_ALREADY_IN_CALL").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName()));
            sendInfo("call", p, Main.plugin.getConfig().getString("CALL.USE_ADD").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName()));
          } else {
            if(ChannelUtils.getChannel(target) == null) {
              Main.waitingCall.put(p.getName(), args[0]);
              sendInfo("call", p, Main.plugin.getConfig().getString("CALL.CALLER_SEND_CALL").replaceAll("%caller%", p.getName()).replaceAll("%target%", args[0]));
              target.sendMessage("");
              sendInfo("call", target, Main.plugin.getConfig().getString("CALL.TARGET_RECEIVE_CALL").replaceAll("%caller%", p.getName()).replaceAll("%target%", args[0]));
              sendInfo("call", target, Main.plugin.getConfig().getString("CALL.TYPE_YES").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName()));
              sendInfo("call", target, Main.plugin.getConfig().getString("CALL.TYPE_NO").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName()));
            } else {
              // Target is already in a call
              sendInfo("call", p, Main.plugin.getConfig().getString("CALL.TARGET_ALREADY_IN_CALL").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName()));
            }
          }
        } else {
          // Target's name doesn't exist on the server.
          sendInfo("call", p, Main.plugin.getConfig().getString("CALL.TARGET_OFFLINE").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName()));
        }
      }
    } else if(label.equalsIgnoreCase("add")) {
      if(args.length < 1) {
        // No arguments passed
        sendInfo("call", p, ChatColor.RED + "Usage: /add <player> [player2] [player3]...");
      } else {
        
        if(ChannelUtils.isCaller(p)) {
          if(target == p) {
            sendInfo("call", p, Main.plugin.getConfig().getString("CALL.CANT_ADD_YOURSELF").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName()));
            return false;
          }

          for(String target : args) {
            Player pTarget = Bukkit.getPlayer(target);

            if(pTarget != null) {
              if(ChannelUtils.getChannel(target) == null) {
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
              } else {
                sendInfo("call", p, Main.plugin.getConfig().getString("CALL.TARGET_ALREADY_IN_CALL").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName()));
              }
            } else {
              sendInfo("call", p, Main.plugin.getConfig().getString("CALL.TARGET_OFFLINE").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName()));
            }
          }
        } else {
          sendInfo("call", p, Main.plugin.getConfig().getString("CALL.NOT_CALL_LEADER").replaceAll("%target%", args[0]).replaceAll("%caller%", p.getName()));
        }
      }
    } else if(label.equalsIgnoreCase("answer")) {
      if(args.length != 1) {
        // No or more than 1 argument(s) was/were passed
        sendInfo("call", p, ChatColor.RED + "Usage: /answer <player>");
        return false;
      } else {
        // 1 Argument was passed
        caller = Bukkit.getPlayer(args[0]);
        
        if(caller != null) {
          if(Main.waitingCall.containsKey(caller.getName()) && Main.waitingCall.get(caller.getName()).equalsIgnoreCase(p.getName())) {
            // Caller is actually calling target
            Main.waitingCall.put(caller.getName(), "");
            Channel channel = new Channel(caller, new ArrayList<String>());
            channel.addMember(p);
            Main.channels.add(channel);
            
            sendInfo("call", caller, Main.plugin.getConfig().getString("CALL.CALLER_ACCEPT_CALL").replaceAll("%target%", p.getName()).replaceAll("%caller%", caller.getName()));
            sendInfo("call", p, Main.plugin.getConfig().getString("CALL.TARGET_ACCEPT_CALL").replaceAll("%target%", p.getName()).replaceAll("%caller%", caller.getName()));
            
            return true;
          } else if(Main.waitingAdd.containsKey(caller.getName()) && Main.waitingAdd.get(caller.getName()).equalsIgnoreCase(p.getName())) {
            // Target is being added to call by caller
            ChannelUtils.getChannel(caller).addMember(p);
            endInfo("call", caller, Main.plugin.getConfig().getString("CALL.CALLER_ACCEPT_ADD").replaceAll("%target%", p.getName()).replaceAll("%caller%", caller.getName()));
            sendInfo("call", p, Main.plugin.getConfig().getString("CALL.TARGET_ACCEPT_ADD").replaceAll("%target%", p.getName()).replaceAll("%caller%", caller.getName()));
            
            return true;
          } else {
            sendInfo("call", p, Main.plugin.getConfig().getString("CALL.NOT_CALLED_YOU").replaceAll("%target%", p.getName()).replaceAll("%caller%", args[0]));
          }
        } else {
          sendInfo("call", p, Main.plugin.getConfig().getString("CALL.CALLER_OFFLINE").replaceAll("%target%", p.getName()).replaceAll("%caller%", args[0]));
          return false;
        }
      }
    } else if(label.equalsIgnoreCase("deny")) {
      if(args.length != 1) {
        sendInfo("call", p, ChatColor.RED + "Usage: /deny <player>");
      } else {
        caller = Bukkit.getServer().getPlayer(args[0]);
        
        if(caller != null) {
          if(Main.waitingCall.containsKey(caller.getName()) && Main.waitingCall.get(caller.getName()).equalsIgnoreCase(p.getName())) {
            Main.waitingCall.put(caller.getName(), "");
          } else if(Main.waitingAdd.containsKey(caller.getName()) && Main.waitingAdd.get(caller.getName()).equalsIgnoreCase(p.getName())) {
            Main.waitingAdd.put(caller.getName(), "");
          } else {
            sendInfo("call", p, Main.plugin.getConfig().getString("CALL.NOT_CALLED_YOU").replaceAll("%target%", p.getName()).replaceAll("%caller%", args[0]));
            return true;
          }
          
          sendInfo("call", caller, Main.plugin.getConfig().getString("CALL.CALLER_DENY_CALL").replaceAll("%target%", p.getName()).replaceAll("%caller%", args[0]));
          sendInfo("call", p, Main.plugin.getConfig().getString("CALL.TARGET_DENY_CALL").replaceAll("%target%", p.getName()).replaceAll("%caller%", args[0]));
          
        } else {
          sendInfo("call", p, Main.plugin.getConfig().getString("CALL.CALLER_OFFLINE").replaceAll("%target%", p.getName()).replaceAll("%caller%", args[0]));
        }
      } // END OF ARGS LENGTH
      
    } else if(label.equalsIgnoreCase("hangup")) {
      
      if(ChannelUtils.getChannel(p) == null) {
        ChatUtils.sendInfo("call", p, Main.plugin.getConfig().getString("CALL.NOT_IN_CALL").replaceAll("%player%", p.getName()));
        return true;
      }
      
      if(ChanneUtils.isCaller(p)) {
        for(String pl : ChannelUtils.getChannel(p).getMembers()) {
          if(pl.equals(p.getName())) continue;
          sendInfo("call", Bukkit.getPlayer(pl), Main.plugin.getConfig().getString("CALL.END_CALL").replaceAll("%caller%", p.getName()));
        } // END OF FOR
        
        sendInfo("call", p, Main.plugin.getConfig().getString("CALL.END_CALL_CALLER").replaceAll("%caller%", p.getName()));
        ChannelUtils.getChannel(p).removeAll();
      } else {
        for(String pl : ChannelUtils.getChannel(p).getMembers()){
            ChatUtils.sendInfo("call", Bukkit.getPlayer(pl), Main.plugin.getConfig().getString("CALL.QUIT_CALL").replaceAll("%target%", p.getName()).replaceAll("%caller%", ChannelUtils.getChannel(p).getCaller().getName()));
        }
        ChannelUtils.getChannel(p).removeMember(p);
      }
    } else if(label.equalsIgnoreCase("stopcall")) {
      if(ChannelUtils.getChannel(p) == null){
        sendInfo("call", p, Main.plugin.getConfig().getString("CALL.NOT_IN_CALL").replaceAll("%target%", p.getName()));
        return true;
      }
      
      if(ChannelUtils.isCaller(p)) {
        for(String pl : ChannelUtils.getChannel(p).getMembers()) {
          if(pl.equals(p.getName())) {
            continue;
          }
          
          sendInfo("call", Bukkit.getPlayer(pl), Main.plugin.getConfig().getString("CALL.END_CALL").replaceAll("%caller%", p.getName()));
        }
        
        sendInfo("call", p, Main.plugin.getConfig().getString("CALL.END_CALL_CALLER").replaceAll("%caller%", p.getName()));
        ChannelUtils.getChannel(p).removeAll();
      } else {
        sendInfo("call", p, Main.plugin.getConfig().getString("CALL.CANT_END").replaceAll("%caller%", p.getName()));
      }
    } else if(label.equalsIgnoreCase("global")) {
      if(ChannelUtils.getChannel(p) != null) {
        String msg = " ";
        for(int i = 0; i < args.length; i++) {
          msg += " " + args[i];
        }
        PlayerChat.global.add(p.getName());
        p.chat(msg.trim());
      } else {
        sendInfo("call", p, Main.plugin.getConfig().getString("CALL.NOT_IN_CALL").replaceAll("%target%", p.getName()));
      }
      return true;
    } // END OF LABEL
  } // END OF onCommand
} // END OF CLASS
