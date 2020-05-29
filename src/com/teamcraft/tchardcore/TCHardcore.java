package com.teamcraft.tchardcore;

import com.teamcraft.tchardcore.listeners.DeathListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * main class of plugin
 */
public class TCHardcore extends JavaPlugin {
    
    /**
     * instance of plugin itself
     */
    private static TCHardcore plugin;
    
    /**
     * the list file
     */
    private static ConfigFile listFile;
    
    /**
     * run when the server starts up
     */
    @Override
    public void onEnable() {
        plugin = this;
        listFile = new ConfigFile();
        listFile.initialize();
        plugin.getServer().getPluginManager().registerEvents(new DeathListener(), this);
    }
    
    /**
     * run when the server shuts down
     */
    @Override
    public void onDisable() {
        plugin = null;
        listFile.save();
        listFile = null;
    }
    
    /**
     * get the instance of this plugin
     *
     * @return instance of plugin
     */
    public static TCHardcore getInstance() {
        return plugin;
    }
    
    /**
     * get the list file instance
     *
     * @return list file instance
     */
    public static ConfigFile getListFile() {
        return listFile;
    }
}
