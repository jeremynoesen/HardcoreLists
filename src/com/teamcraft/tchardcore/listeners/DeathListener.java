package com.teamcraft.tchardcore.listeners;

import com.teamcraft.tchardcore.TCHardcore;
import com.teamcraft.tchardcore.config.Config;
import com.teamcraft.tchardcore.config.ConfigType;
import com.teamcraft.tchardcore.config.Configs;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * listener class related to the death and player lists for the list file
 */
public class DeathListener implements Listener {
    
    /**
     * event called when a player dies, will remove player from alive list and add to dead list
     *
     * @param e
     */
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        YamlConfiguration players = Configs.getConfig(ConfigType.PLAYERS).getConfig();
        if (players.getConfigurationSection("alive") != null &&
                players.getConfigurationSection("alive").getKeys(false).contains(player.getUniqueId().toString())) {
            players.set("alive." + player.getUniqueId(), null);
        }
        players.set("dead." + player.getUniqueId(), player.getName());
    }
    
    /**
     * event called when a player joins, will add them to the list of all players, as well as the alive list if they're
     * not dead
     *
     * @param e
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        YamlConfiguration players = Configs.getConfig(ConfigType.PLAYERS).getConfig();
        if (players.getConfigurationSection("dead") == null ||
                !players.getConfigurationSection("dead").getKeys(false).contains(player.getUniqueId().toString())) {
            players.set("alive." + player.getUniqueId(), player.getName());
        }
        players.set("all." + player.getUniqueId(), player.getName());
    }
    
}
