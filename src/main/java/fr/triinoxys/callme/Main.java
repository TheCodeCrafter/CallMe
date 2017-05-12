package fr.triinoxys.callme;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import fr.triinoxys.callme.commands.CallCmds;
import fr.triinoxys.callme.commands.MiscCmds;
import fr.triinoxys.callme.commands.SmsCmds;
import fr.triinoxys.callme.events.GuiEvents;
import fr.triinoxys.callme.events.PlayerChat;
import fr.triinoxys.callme.handlers.Channel;
import fr.triinoxys.callme.handlers.SMSFile;
import fr.triinoxys.callme.utils.Updater;

public class Main extends JavaPlugin{
    
    /*
     * @author TriiNoxYs
     */
    
    public static Main plugin;
    public static SMSFile sms;
    
    public Updater updater = new Updater(this);
    
    public static ArrayList<Channel> channels = new ArrayList<Channel>();
    
    public static HashMap<String, String> waitingCall = new HashMap<String, String>();
    public static HashMap<String, String> waitingAdd = new HashMap<String, String>();
    
    public static HashMap<String, String> guiStatus = new HashMap<String, String>();
    
    public void onEnable(){
        
        plugin = this;
        
        if(!getDataFolder().exists())
            getDataFolder().mkdir();
        
        saveDefaultConfig();
        sms = new SMSFile(this);
        
        Updater.checkUpdate(true);
        
        Bukkit.getPluginManager().registerEvents(new PlayerChat(), this);
        Bukkit.getPluginManager().registerEvents(new GuiEvents(), this);
        
        //----- CALL COMMANDS -----\\
        getCommand("appel").setExecutor(new CallCmds());
        getCommand("ajout").setExecutor(new CallCmds());
        getCommand("global").setExecutor(new CallCmds());
        getCommand("raccrocher").setExecutor(new CallCmds());
        getCommand("oui").setExecutor(new CallCmds());
        getCommand("non").setExecutor(new CallCmds());
        getCommand("fin").setExecutor(new CallCmds());
        
        //------ SMS COMMANDS ------\\
        getCommand("sms").setExecutor(new SmsCmds());
        getCommand("smslist").setExecutor(new SmsCmds());
        
        //----- MISC COMMANDS -----\\
        getCommand("callme").setExecutor(new MiscCmds(this));
        getCommand("heure").setExecutor(new MiscCmds(this));
    }
    
    public void onDisable(){ 
        saveDefaultConfig();
        sms.save();
    }
    
}
