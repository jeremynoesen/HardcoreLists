package com.teamcraft.tchardcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * listener class related to the anti-pvp mechanic
 */
public class PVPListener implements Listener {
    
    public void onPvP(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player damager && e.getEntity() instanceof Player player) {
        
        }
    }

}
