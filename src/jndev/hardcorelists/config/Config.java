package jndev.hardcorelists.config;

import jndev.hardcorelists.HardcoreLists;
import jndev.hardcorelists.Message;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

/**
 * class used to manage all config files in plugin
 *
 * @author JNDev (Jeremaster101)
 */
public class Config {
    
    /**
     * file representing the config
     */
    private File configFile;
    
    /**
     * file loaded in YAML format
     */
    private YamlConfiguration YMLConfig;
    
    /**
     *type of config
     */
    private final ConfigType configType;
    
    /**
     * create a new config with specified type
     *
     * @param type config type
     */
    public Config(ConfigType type) {
        configType = type;
    }
    
    /**
     * reloads a configuration file, will load if the file is not loaded. Also saves defaults when they're missing
     */
    public void reloadConfig() {
        if (configFile == null) {
            configFile = configType.getFile();
        }
        
        YMLConfig = YamlConfiguration.loadConfiguration(configFile);
        
        Reader defConfigStream = new InputStreamReader(configType.getResource(), StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        YMLConfig.setDefaults(defConfig);
        YMLConfig.options().copyDefaults(true);
        saveConfig();
        
        if (configType == ConfigType.MESSAGE) Message.reloadMessages();
    }
    
    /**
     * reloads config if YMLConfig is null
     *
     * @return YMLConfig YamlConfiguration
     */
    public YamlConfiguration getConfig() {
        if (YMLConfig == null) {
            reloadConfig();
        }
        return YMLConfig;
    }
    
    /**
     * saves a config file
     */
    public void saveConfig() {
        if (YMLConfig == null || configFile == null) {
            return;
        }
        try {
            getConfig().save(configFile);
        } catch (IOException ex) {
            HardcoreLists.getInstance().getLogger().log(Level.SEVERE, "A config file failed to save!", ex);
        }
    }
    
    /**
     * saves the default config from the plugin jar if the file doesn't exist in the plugin folder
     */
    public void saveDefaultConfig() {
        if (configFile == null) {
            configFile = configType.getFile();
        }
        if (!configFile.exists()) {
            configType.saveResource();
            YMLConfig = YamlConfiguration.loadConfiguration(configFile);
        }
    }
}
