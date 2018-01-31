package net.fathomtech.plugins.CityPlus.HavenPhone;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import net.fathomtech.plugins.CityPlus.Main;
import net.fathomtech.plugins.CityPlus.Handlers.GUI;
import net.fathomtech.plugins.CityPlus.Utilities.ChatUtils;

 
public class GuiEvents implements Listener{
    
    private void sendInfo(String type, Player target, String msg){
        ChatUtils.sendInfo(type, target, msg);
    }
    
    @SuppressWarnings ("deprecation")
    @EventHandler
    public void onInterractWithItem(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Action a = e.getAction();
        ItemStack is = e.getItem();
        
        if(a == Action.PHYSICAL || is == null || is.getType() == Material.AIR){
            return;
        }
        
        if(is.getTypeId() == Main.plugin.getConfig().getInt("ID")){
            e.setCancelled(true);
            GUI.openMenu(p);
        }
        
    }
    
    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        
        if(e.getInventory().getName().equalsIgnoreCase("§6§liHaven")){
            e.setCancelled(true);
            
            if(e.getCurrentItem() == null) return;
            
            if(e.getCurrentItem().getType() == Material.RECORD_11){
                p.closeInventory();
                PlayerChat.inGUI.add(p.getName());
                Main.guiStatus.put(p.getName(), "call");
                sendInfo("call", p, Main.plugin.getConfig().getString("GUI.ENTER_MULTIPLE_NAMES").replaceAll("%player%", p.getName()));
            }
            
            else if(e.getCurrentItem().getType() == Material.RECORD_12){
                p.closeInventory();
                PlayerChat.inGUI.add(p.getName());
                Main.guiStatus.put(p.getName(), "add");
                sendInfo("call", p, Main.plugin.getConfig().getString("GUI.ENTER_MULTIPLE_NAMES").replaceAll("%player%", p.getName()));
            }
            
            else if(e.getCurrentItem().getType() == Material.RECORD_3){
                p.closeInventory();
                PlayerChat.inGUI.add(p.getName());
                Main.guiStatus.put(p.getName(), "answer");
                sendInfo("call", p, Main.plugin.getConfig().getString("GUI.ENTER_A_NAME").replaceAll("%player%", p.getName()));
            }
            
            else if(e.getCurrentItem().getType() == Material.RECORD_4){
                p.closeInventory();
                PlayerChat.inGUI.add(p.getName());
                Main.guiStatus.put(p.getName(), "deny");
                sendInfo("call", p, Main.plugin.getConfig().getString("GUI.ENTER_A_NAME").replaceAll("%player%", p.getName()));
            }
            
            else if(e.getCurrentItem().getType() == Material.RECORD_5){
                p.closeInventory();
                Bukkit.dispatchCommand(p, "hangup");
            }
            
            else if(e.getCurrentItem().getType() == Material.RECORD_6){
                p.closeInventory();
                Bukkit.dispatchCommand(p, "stopcall");
            }
            
            else if(e.getCurrentItem().getType() == Material.RECORD_7){
                p.closeInventory();
                PlayerChat.inGUI.add(p.getName());
                Main.guiStatus.put(p.getName(), "global");
                sendInfo("call", p, Main.plugin.getConfig().getString("GUI.ENTER_A_MSG").replaceAll("%player%", p.getName()));
            }
            
            else if(e.getCurrentItem().getType() == Material.RECORD_8){
                p.closeInventory();
                PlayerChat.inGUI.add(p.getName());
                Main.guiStatus.put(p.getName(), "sms name");
                sendInfo("sms", p, Main.plugin.getConfig().getString("GUI.ENTER_MULTIPLE_NAMES").replaceAll("%player%", p.getName()));
            }
            
            else if(e.getCurrentItem().getType() == Material.RECORD_9){
                p.closeInventory();
                PlayerChat.inGUI.add(p.getName());
                Main.guiStatus.put(p.getName(), "smslist");
                sendInfo("sms", p, Main.plugin.getConfig().getString("GUI.ENTER_A_NAME").replaceAll("%player%", p.getName()));
            }
            
        }
    }
     
    
}
