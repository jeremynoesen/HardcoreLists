package jeremynoesen.hardcorelists;

import jeremynoesen.hardcorelists.command.CommandExec;
import jeremynoesen.hardcorelists.command.CommandTabComplete;
import jeremynoesen.hardcorelists.handler.PvPHandler;
import jeremynoesen.hardcorelists.listener.DeathListener;
import jeremynoesen.hardcorelists.listener.PVPListener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * main class of plugin
 *
 * @author Jeremy Noesen
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
        Config.getMessageConfig().saveDefaultConfig();
        Config.getTimeConfig().saveDefaultConfig();
        Config.getPlayersConfig().saveDefaultConfig();
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
        Config.getMessageConfig().saveConfig();
        Config.getTimeConfig().saveConfig();
        Config.getPlayersConfig().saveConfig();
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
