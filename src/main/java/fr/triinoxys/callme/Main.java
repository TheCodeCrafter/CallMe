package fr.triinoxys.callme;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import net.md_5.bungee.api.plugin.Plugin;

import fr.triinoxys.callme.commands.CallCmds;
import fr.triinoxys.callme.commands.MiscCmds;
import fr.triinoxys.callme.commands.SmsCmds;
import fr.triinoxys.callme.events.GuiEvents;
import fr.triinoxys.callme.events.PlayerChat;
import fr.triinoxys.callme.handlers.Channel;
import fr.triinoxys.callme.handlers.SMSFile;
import fr.triinoxys.callme.utils.UpdaterV2;

public class Main extends Plugin{
    
    /*
     * @author TriiNoxYs
     */
    
    public static Main plugin;
    public static SMSFile sms;
    
    public UpdaterV2 updater = new UpdaterV2(this);
    
    public static ArrayList<Channel> channels = new ArrayList<Channel>();
    
    public static HashMap<String, String> waitingCall = new HashMap<String, String>();
    public static HashMap<String, String> waitingAdd = new HashMap<String, String>();
    
    public static HashMap<String, String> guiStatus = new HashMap<String, String>();
    
    public void onEnable(){
        proxy = this.getProxy();
        
        //Commands
		proxy.getPluginManager().registerCommand(this, new CallCmds());
		proxy.getPluginManager().registerCommand(this, new MiscCmds());
		proxy.getPluginManager().registerCommand(this, new SmsCmds());
		
		
		//Events
        proxy.getPluginManager().registerListener(this, new GuiEvents());
        proxy.getPluginManager().registerListener(this, new PlayerChat());
        
        //Config
		if (!getDataFolder().exists())
            getDataFolder().mkdir();
            
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()){
            try{
                configFile.createNewFile();
                try (InputStream is = getResourceAsStream("config.yml");
                	OutputStream os = new FileOutputStream(configFile)) {
                    ByteStreams.copy(is, os);
                }
            }catch (IOException e){
            	throw new RuntimeException("Impossible de cree le fichier de configuration.", e);
            }
        }
		
		try{
			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
		}catch(IOException e){
			e.printStackTrace();
}    
        
        //old
        saveDefaultConfig();
        sms = new SMSFile(this);
        
        try{
            UpdaterV2.checkUpdate(true);
        }catch(IOException e){
            e.printStackTrace();
        }
        
        
    }
    
    public void onDisable(){ 
        saveDefaultConfig();
        sms.save();
    }
    
}
