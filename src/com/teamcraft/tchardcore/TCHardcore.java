package com.teamcraft.tchardcore;

import com.teamcraft.tchardcore.command.CommandExec;
import com.teamcraft.tchardcore.config.ConfigType;
import com.teamcraft.tchardcore.config.Configs;
import com.teamcraft.tchardcore.handler.PvPHandler;
import com.teamcraft.tchardcore.listener.DeathListener;
import com.teamcraft.tchardcore.listener.PVPListener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
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
     * initialize all files, commands, listeners, and start player timer ticker
     */
    @Override
    public void onEnable() {
        plugin = this;
        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(new DeathListener(), this);
        pm.registerEvents(new PVPListener(), this);
        CommandExec commandExec = new CommandExec();
        getCommand("tchcreload").setExecutor(commandExec);
        getCommand("pvptime").setExecutor(commandExec);
        pm.addPermission(new Permission("tchc.admin"));
        Configs.getConfig(ConfigType.MESSAGE).saveDefaultConfig();
        Configs.getConfig(ConfigType.TIME).saveDefaultConfig();
        Configs.getConfig(ConfigType.PLAYERS).saveDefaultConfig();
        Message.reloadMessages();
        
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
        Configs.getConfig(ConfigType.MESSAGE).saveConfig();
        Configs.getConfig(ConfigType.TIME).saveConfig();
        Configs.getConfig(ConfigType.PLAYERS).saveConfig();
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
