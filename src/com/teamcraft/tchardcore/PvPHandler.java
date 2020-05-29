package com.teamcraft.tchardcore;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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
     * get the hashmap of pvp times for players
     *
     * @return hashmap of player pvp times
     */
    public static HashMap<Player, Integer> getPvPTimes() {
        return pvpplayers;
    }
    
    /**
     * initialize a player's pvp timer, either starting it or resuming it from the last known time
     *
     * @param player player to initialize
     */
    public static void initPlayer(Player player) {
        if (TCHardcore.getListFile().getFile().getConfigurationSection("pvp-times") != null &&
                TCHardcore.getListFile().getFile().getConfigurationSection("pvp-times").getKeys(false).contains(player.getUniqueId().toString())) {
            int time = TCHardcore.getListFile().getFile().getInt("pvp-times." + player.getUniqueId().toString());
            if (time > 0)
                pvpplayers.put(player, time);
        } else {
            pvpplayers.put(player, 15 * 60);
            player.sendMessage(ChatColor.GREEN + "PvP is disabled for 15 minutes for yourself.");
        }
    }
    
    /**
     * tick a player's time, reduces 1 second or removes them if their timer ends
     */
    public static void tickPlayers() {
        for (Player player : pvpplayers.keySet()) {
            if (pvpplayers.get(player) == 0) {
                TCHardcore.getListFile().getFile().set("pvp-times." + player.getUniqueId().toString(), 0);
                pvpplayers.remove(player);
                player.sendMessage(ChatColor.RED + "PvP is now enabled for yourself.");
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
        if(pvpplayers.containsKey(player)) {
            TCHardcore.getListFile().getFile().set("pvp-times." + player.getUniqueId().toString(), PvPHandler.getPvPTimes().get(player));
        } else {
            TCHardcore.getListFile().getFile().set("pvp-times." + player.getUniqueId().toString(), 0);
        }
        pvpplayers.remove(player);
    }
}
