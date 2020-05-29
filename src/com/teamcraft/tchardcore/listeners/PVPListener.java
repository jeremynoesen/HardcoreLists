package com.teamcraft.tchardcore.listeners;

import com.teamcraft.tchardcore.PvPHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * listener class related to the anti-pvp mechanic
 */
public class PVPListener implements Listener {
    
    public void onPvP(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player damager && e.getEntity() instanceof Player player) {
            if(!PvPHandler.getPvPTimes().containsKey(player) || !PvPHandler.getPvPTimes().containsKey(damager) ||
                    PvPHandler.getPvPTimes().get(player) == 0 || PvPHandler.getPvPTimes().get(damager) == 0) {
                e.setCancelled(true);
                damager.sendMessage(ChatColor.YELLOW + "You cannot hurt this player yet!");
            }
        }
    }
    
    public void onJoin(PlayerJoinEvent e) {
        PvPHandler.initPlayer(e.getPlayer());
    }

}
