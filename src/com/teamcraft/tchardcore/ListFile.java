package com.teamcraft.tchardcore;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

/**
 * list file for deaths
 */
public class ListFile {
    
    /**
     * file with the list of players
     */
    private File file;
    
    /**
     * file loaded as a yaml config
     */
    private YamlConfiguration yml;
    
    /**
     * name of list file
     */
    private String fileName = "players.yml";
    
    /**
     * reload the list file, reset it if it is missing
     */
    public void reload() {
        if (file == null) {
            file = new File(TCHardcore.getInstance().getDataFolder(), fileName);
        }
        
        yml = YamlConfiguration.loadConfiguration(file);
        
        Reader defConfigStream = new InputStreamReader(TCHardcore.getInstance().getResource(fileName), StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        yml.setDefaults(defConfig);
        yml.options().copyDefaults(true);
        save();
    }
    
    /**
     * get the YAML file with the list
     *
     * @return yml YamlConfiguration
     */
    public YamlConfiguration getFile() {
        if (yml == null) {
            reload();
        }
        return yml;
    }
    
    /**
     * saves the file
     */
    public void save() {
        if (yml == null || file == null) {
            return;
        }
        try {
            getFile().save(file);
        } catch (IOException ex) {
            TCHardcore.getInstance().getLogger().log(Level.SEVERE, "A config file failed to save!", ex);
        }
    }
    
    /**
     * saves the default file from the plugin jar if the file doesn't exist in the plugin folder
     */
    public void initialize() {
        if (file == null) {
            file = new File(TCHardcore.getInstance().getDataFolder(), fileName);
        }
        if (!file.exists()) {
            TCHardcore.getInstance().saveResource(fileName, false);
            yml = YamlConfiguration.loadConfiguration(file);
        }
    }
}