package com.teamcraft.tchardcore.handler;

import com.teamcraft.tchardcore.Message;
import com.teamcraft.tchardcore.config.ConfigType;
import com.teamcraft.tchardcore.config.Configs;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;

/**
 * methods to tick player times and load the last known times from file
 */
public class PvPHandler {
    
    /**
     * hashmap with all players and times
     */
    private static final HashMap<Player, Integer> pvpplayers = new HashMap<>();
    
    /**
     * reference to death list file
     */
    private static YamlConfiguration players = Configs.getConfig(ConfigType.PLAYERS).getConfig();
    
    /**
     * get the hashmap of pvp times for players
     *
     * @return hashmap of player pvp times
     */
    public static HashMap<Player, Integer> getPvPTimes() {
        return pvpplayers;
    }
    
    /**
     * get a player's remaining time until they can pvp
     *
     * @param player player to get time for
     * @return time left on their clock
     */
    public static int getPlayerTime(Player player) {
        if (pvpplayers.containsKey(player)) return pvpplayers.get(player);
        return 0;
    }
    
    /**
     * initialize a player's pvp timer, either starting it or resuming it from the last known time
     *
     * @param player player to initialize
     */
    public static void initPlayer(Player player) {
        if (players.getConfigurationSection("pvp-times") != null &&
                players.getConfigurationSection("pvp-times").getKeys(false).contains(player.getUniqueId().toString())) {
            int time = players.getInt("pvp-times." + player.getUniqueId().toString());
            if (time > 0) {
                pvpplayers.put(player, time);
                player.sendMessage(Message.CHECK_TIME.replace("$TIME$",
                        Message.convertTime(PvPHandler.getPlayerTime(player)))
                        .replace("$PLAYER$", "You"));
            }
        } else {
            int time = Configs.getConfig(ConfigType.TIME).getConfig().getInt("pvp-countdown-seconds");
            pvpplayers.put(player, time);
            player.sendMessage(Message.PVP_DISABLED.replace("$TIME$", Message.convertTime(time)));
        }
    }
    
    /**
     * tick a player's time, reduces 1 second or removes them if their timer ends
     */
    public static void tickPlayers() {
        for (Player player : pvpplayers.keySet()) {
            if (pvpplayers.get(player) == 0) {
                players.set("pvp-times." + player.getUniqueId().toString(), 0);
                pvpplayers.remove(player);
                player.sendMessage(Message.PVP_ENABLED);
            }
            pvpplayers.put(player, pvpplayers.get(player) - 1);
        }
    }
    
    /**
     * save a player's time remaining
     *
     * @param player player to save
     */
    public static void savePlayer(Player player) {
        if (pvpplayers.containsKey(player)) {
            players.set("pvp-times." + player.getUniqueId().toString(), PvPHandler.getPlayerTime(player));
        } else {
            players.set("pvp-times." + player.getUniqueId().toString(), 0);
        }
        pvpplayers.remove(player);
    }
    
    /**
     * check if the player or damager can not pvp, and if so, cancel the event
     *
     * @param player  player being hurt
     * @param damager damager
     * @param e       event to check pvp in
     */
    public static void checkPvP(Player player, Player damager, EntityDamageByEntityEvent e) {
        if (pvpplayers.containsKey(player) || pvpplayers.containsKey(damager)) {
            e.setCancelled(true);
            damager.sendMessage(Message.CANT_HURT);
        }
    }
}
