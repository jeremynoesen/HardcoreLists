package com.teamcraft.tchardcore.listener;

import com.teamcraft.tchardcore.config.ConfigType;
import com.teamcraft.tchardcore.config.Configs;
import com.teamcraft.tchardcore.handler.DeathListHandler;
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
        DeathListHandler.addDeath(e.getEntity());
    }
    
    /**
     * event called when a player joins, will add them to the list of all players, as well as the alive list if they're
     * not dead
     *
     * @param e
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        DeathListHandler.initPlayer(e.getPlayer());
    }
    
}
