package it.zpeppoh.docs.providers;

import com.google.common.io.ByteStreams;
import it.zpeppoh.docs.Docs;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {
    private final File dataFolder;

    private FileConfiguration config;

    private final String configName;

    public FileManager(File dataFolder, String configName) {
        this.dataFolder = dataFolder;
        this.configName = configName;
        saveDefaultConfig();
    }

    public FileConfiguration getConfig() {
        if (this.config == null)
            this.config = getConfigFromFile();
        return this.config;
    }

    private FileConfiguration getConfigFromFile() {
        File file = new File(this.dataFolder, this.configName);
        return (FileConfiguration)YamlConfiguration.loadConfiguration(file);
    }

    public void saveDefaultConfig() {
        if (!this.dataFolder.exists())
            this.dataFolder.mkdir();
        File file = new File(this.dataFolder, this.configName);
        if (!file.exists())
            try {
                file.createNewFile();
                InputStream is = Docs.getInstance().getResource(this.configName);
                OutputStream os = new FileOutputStream(file);
                ByteStreams.copy(is, os);
            } catch (IOException ex) {
                throw new RuntimeException("There was an error during the creation of file " + this.configName, ex);
            }
    }

    public void reloadConfig() {
        File file = new File(this.dataFolder, this.configName);
        this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
    }

    public void saveConfig() {
        File file = new File(this.dataFolder, this.configName);
        try {
            this.config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
