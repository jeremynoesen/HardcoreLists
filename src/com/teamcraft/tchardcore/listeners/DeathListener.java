package com.teamcraft.tchardcore.listeners;

import com.teamcraft.tchardcore.TCHardcore;
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
        if (TCHardcore.getListFile().getFile().getConfigurationSection("alive") != null &&
                TCHardcore.getListFile().getFile().getConfigurationSection("alive").getKeys(false).contains(player.getUniqueId().toString())) {
            TCHardcore.getListFile().getFile().set("alive." + player.getUniqueId(), null);
        }
        TCHardcore.getListFile().getFile().set("dead." + player.getUniqueId(), player.getName());
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
        if (TCHardcore.getListFile().getFile().getConfigurationSection("dead") == null ||
                !TCHardcore.getListFile().getFile().getConfigurationSection("dead").getKeys(false).contains(player.getUniqueId().toString())) {
            TCHardcore.getListFile().getFile().set("alive." + player.getUniqueId(), player.getName());
        }
        TCHardcore.getListFile().getFile().set("all." + player.getUniqueId(), player.getName());
    }
    
}
