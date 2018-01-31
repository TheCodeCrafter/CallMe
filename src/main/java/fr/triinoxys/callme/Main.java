package net.fathomtech.plugins.CityPlus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import net.fathomtech.plugins.CityPlus.Commands.CallCommands;
import net.fathomtech.plugins.CityPlus.Commands.MiscCmds;
import net.fathomtech.plugins.CityPlus.Commands.SmsCmds;
import net.fathomtech.plugins.CityPlus.Events.GuiEvents;
import net.fathomtech.plugins.CityPlus.Events.PlayerChat;
import net.fathomtech.plugins.CityPlus.Handlers.Channel;
import net.fathomtech.plugins.CityPlus.Handlers.SMSFile;
import net.fathomtech.plugins.CityPlus.Utilities.UpdaterV2;

public class Main extends JavaPlugin {
    
    /*
     * @author TheCodeCrafter
     */
    
    public static Main plugin;
    public static SMSFile sms;
    
    public UpdaterV2 updater = new UpdaterV2(this);
    
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
        
        try{
            UpdaterV2.checkUpdate(true);
        }catch(IOException e){
            e.printStackTrace();
        }
        
        Bukkit.getPluginManager().registerEvents(new PlayerChat(), this);
        Bukkit.getPluginManager().registerEvents(new GuiEvents(), this);
        
        //----- CALL COMMANDS -----\\
        getCommand("appel").setExecutor(new CallCommands());
        getCommand("ajout").setExecutor(new CallCommands());
        getCommand("global").setExecutor(new CallCommands());
        getCommand("raccrocher").setExecutor(new CallCommands());
        getCommand("oui").setExecutor(new CallCommands());
        getCommand("non").setExecutor(new CallCommands());
        getCommand("fin").setExecutor(new CallCommands());
        
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
