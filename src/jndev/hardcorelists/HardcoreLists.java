package jndev.hardcorelists;

import jndev.hardcorelists.command.CommandExec;
import jndev.hardcorelists.command.CommandTabComplete;
import jndev.hardcorelists.config.ConfigType;
import jndev.hardcorelists.config.Configs;
import jndev.hardcorelists.handler.PvPHandler;
import jndev.hardcorelists.listener.DeathListener;
import jndev.hardcorelists.listener.PVPListener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * main class of plugin
 *
 * @author JNDev (Jeremaster101)
 */
public class HardcoreLists extends JavaPlugin {
    
    /**
     * instance of plugin itself
     */
    private static HardcoreLists plugin;
    
    /**
     * initialize all files, commands, listeners, and start player timer ticker
     */
    @Override
    public void onEnable() {
        plugin = this;
        Configs.getConfig(ConfigType.MESSAGE).saveDefaultConfig();
        Configs.getConfig(ConfigType.TIME).saveDefaultConfig();
        Configs.getConfig(ConfigType.PLAYERS).saveDefaultConfig();
        Message.reloadMessages();
        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(new DeathListener(), this);
        pm.registerEvents(new PVPListener(), this);
        pm.addPermission(new Permission("hardcorelists.admin"));
        CommandExec commandExec = new CommandExec();
        getCommand("hardcorelists").setExecutor(commandExec);
        getCommand("pvptime").setExecutor(commandExec);
        getCommand("hardcorelists").setTabCompleter(new CommandTabComplete());
        
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
    public static HardcoreLists getInstance() {
        return plugin;
    }
}
