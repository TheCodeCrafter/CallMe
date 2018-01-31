package net.fathomtech.plugins.CityPlus.Handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class GUI{
    
    public static void openMenu(Player p){
        Inventory menu = Bukkit.createInventory(null, 9, "§6§liHaven");
         
        ItemStack appel = new ItemStack(Material.RECORD_11);
        ItemMeta appelMeta = appel.getItemMeta();
        appelMeta.setDisplayName(ChatColor.GOLD + "Call");
        appel.setItemMeta(appelMeta);
        menu.setItem(0, appel);
        
        ItemStack ajout = new ItemStack(Material.RECORD_12);
        ItemMeta ajoutMeta = ajout.getItemMeta();
        ajoutMeta.setDisplayName(ChatColor.GOLD + "Add");
        ajout.setItemMeta(ajoutMeta);
        menu.setItem(1, ajout);
        
        ItemStack oui = new ItemStack(Material.RECORD_3);
        ItemMeta ouiMeta = oui.getItemMeta();
        ouiMeta.setDisplayName(ChatColor.GOLD + "Answer");
        oui.setItemMeta(ouiMeta);
        menu.setItem(2, oui);
        
        ItemStack non = new ItemStack(Material.RECORD_4);
        ItemMeta nonMeta = non.getItemMeta();
        nonMeta.setDisplayName(ChatColor.GOLD + "Deny");
        non.setItemMeta(nonMeta);
        menu.setItem(3, non);
        
        ItemStack raccrocher = new ItemStack(Material.RECORD_5);
        ItemMeta raccrocherMeta = raccrocher.getItemMeta();
        raccrocherMeta.setDisplayName(ChatColor.GOLD + "Hangup");
        raccrocher.setItemMeta(raccrocherMeta);
        menu.setItem(4, raccrocher);
        
        ItemStack fin = new ItemStack(Material.RECORD_6);
        ItemMeta finMeta = fin.getItemMeta();
        finMeta.setDisplayName(ChatColor.GOLD + "Stop the Call");
        fin.setItemMeta(finMeta);
        menu.setItem(5, fin);
        
        ItemStack global = new ItemStack(Material.RECORD_7);
        ItemMeta globalMeta = global.getItemMeta();
        globalMeta.setDisplayName(ChatColor.GOLD + "Talk in Global");
        global.setItemMeta(globalMeta);
        menu.setItem(6, global);
        
        ItemStack sms = new ItemStack(Material.RECORD_8);
        ItemMeta smsMeta = sms.getItemMeta();
        smsMeta.setDisplayName(ChatColor.GOLD + "Send a Message");
        sms.setItemMeta(smsMeta);
        menu.setItem(7, sms);
        
        ItemStack smslist = new ItemStack(Material.RECORD_9);
        ItemMeta smslistMeta = smslist.getItemMeta();
        smslistMeta.setDisplayName(ChatColor.GOLD + "Message History");
        smslist.setItemMeta(smslistMeta);
        menu.setItem(8, smslist);
        
        p.openInventory(menu);
    }
    
}
