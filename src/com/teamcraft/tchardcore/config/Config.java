package com.teamcraft.tchardcore.config;

import com.teamcraft.tchardcore.Message;
import com.teamcraft.tchardcore.TCHardcore;
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
    
    private File configFile;
    private YamlConfiguration YMLConfig;
    private final ConfigType configType;
    
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
        
        if(configType == ConfigType.MESSAGE) Message.reloadMessages();
    }
    
    /**
     * reloads config if YMLConfig is null
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
            TCHardcore.getInstance().getLogger().log(Level.SEVERE, "A config file failed to save!", ex);
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
