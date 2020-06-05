package com.teamcraft.tchardcore.listener;

import com.teamcraft.tchardcore.handler.PvPHandler;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * listener class related to the anti-pvp mechanic
 */
public class PVPListener implements Listener {
    
    /**
     * cancels the pvp event if one or the other parties has pvp disabled
     *
     * @param e
     */
    @EventHandler
    public void onPvP(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player damager = (Player) e.getDamager();
            Player player = (Player) e.getEntity();
            PvPHandler.checkPvP(player, damager, e);
        } else if (e.getDamager() instanceof Projectile &&
                ((Projectile) e.getDamager()).getShooter() instanceof Player &&
                e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            Player damager = (Player) ((Projectile) e.getDamager()).getShooter();
            PvPHandler.checkPvP(player, damager, e);
        }
    }
    
    /**
     * starts or reloads a player's clock on join
     *
     * @param e
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        PvPHandler.initPlayer(e.getPlayer());
    }
    
    /**
     * save a player's time when they leave
     *
     * @param e
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        PvPHandler.savePlayer(e.getPlayer());
    }
    
}
