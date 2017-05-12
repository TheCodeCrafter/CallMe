package fr.triinoxys.callme.handlers;

import org.bukkit.configuration.file.YamlConfiguration;
import fr.triinoxys.callme.Main;
import java.io.File;


@SuppressWarnings("unused")
public class SMSFile {

    private final Main main;
    public final YamlConfiguration data = new YamlConfiguration();
    private final File smsFile;

    public SMSFile(Main main) {
        this.main = main;
        smsFile = new File(main.getDataFolder(), "sms.yml");
        if (!smsFile.exists())
            try {
                smsFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        load();
    }

    public void save() {
        try {
            data.save(smsFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            data.load(smsFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
