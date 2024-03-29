package xyz.jeremynoesen.hardcorelists;

import xyz.jeremynoesen.hardcorelists.command.CommandExec;
import xyz.jeremynoesen.hardcorelists.command.CommandTabComplete;
import xyz.jeremynoesen.hardcorelists.handler.ListHandler;
import xyz.jeremynoesen.hardcorelists.handler.PvPHandler;
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
        PvPHandler.load();
        ListHandler.load();
        Message.reloadMessages();
        
        PluginManager pm = plugin.getServer().getPluginManager();

        pm.registerEvents(new ListHandler(), plugin);
        pm.registerEvents(new PvPHandler(), plugin);

        pm.addPermission(new Permission("hardcorelists.pvptime.self"));
        pm.addPermission(new Permission("hardcorelists.pvptime.others"));
        pm.addPermission(new Permission("hardcorelists.help"));
        pm.addPermission(new Permission("hardcorelists.reload"));
        pm.addPermission(new Permission("hardcorelists.timer"));
        pm.addPermission(new Permission("hardcorelists.list.dead"));
        pm.addPermission(new Permission("hardcorelists.list.alive"));
        pm.addPermission(new Permission("hardcorelists.reset"));


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
        PvPHandler.save();
        ListHandler.save();
        Config.getTimeConfig().saveConfig();
        plugin = null;
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
