package com.teamcraft.tchardcore;

import com.teamcraft.tchardcore.listeners.DeathListener;
import com.teamcraft.tchardcore.listeners.PVPListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * main class of plugin
 */
public class TCHardcore extends JavaPlugin {
    
    /**
     * instance of plugin itself
     */
    private static TCHardcore plugin;
    
    /**
     * run when the server starts up
     */
    @Override
    public void onEnable() {
        plugin = this;
        plugin.getServer().getPluginManager().registerEvents(new DeathListener(), this);
        plugin.getServer().getPluginManager().registerEvents(new PVPListener(), this);
        
        // tick player timers every second
        new BukkitRunnable() {
            @Override
            public void run() {
                PvPHandler.tickPlayers();
            }
        }.runTaskTimer(plugin, 20, 20);
    }
    
    /**
     * run when the server shuts down
     */
    @Override
    public void onDisable() {
        plugin = null;
    }
    
    /**
     * get the instance of this plugin
     *
     * @return instance of plugin
     */
    public static TCHardcore getInstance() {
        return plugin;
    }
}
