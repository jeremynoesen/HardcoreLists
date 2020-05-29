package com.teamcraft.tchardcore.listener;

import com.teamcraft.tchardcore.Message;
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
    public void onPvP(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player damager = (Player) e.getDamager();
            Player player = (Player) e.getEntity();
            if(PvPHandler.getPvPTimes().containsKey(player) || PvPHandler.getPvPTimes().containsKey(damager)) {
                e.setCancelled(true);
                damager.sendMessage(Message.CANT_HURT);
            }
        } else if(e.getDamager() instanceof Projectile && e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            Projectile damager = (Projectile) e.getDamager();
            if(PvPHandler.getPvPTimes().containsKey(player) || (damager.getShooter() instanceof Player &&
                    PvPHandler.getPvPTimes().containsKey((Player) damager.getShooter()))) {
                e.setCancelled(true);
                ((Player) damager.getShooter()).sendMessage(Message.CANT_HURT);
            }
        }
    }
    
    /**
     * starts or reloads a player's clock on join
     *
     * @param e
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        PvPHandler.initPlayer(player);
        player.sendMessage(Message.CHECK_TIME.replace("$TIME$",
                Message.convertTime(PvPHandler.getPvPTimes().get(player))));
    }
    
    /**
     * save a player's time when they leave
     *
     * @param e
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        PvPHandler.savePlayer(player);
    }

}
