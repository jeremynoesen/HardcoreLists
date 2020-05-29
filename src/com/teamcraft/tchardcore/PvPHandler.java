package com.teamcraft.tchardcore;

import com.teamcraft.tchardcore.config.ConfigType;
import com.teamcraft.tchardcore.config.Configs;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
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
    public static void initPlayer(Player player) { //todo tell remaining time
        YamlConfiguration players = Configs.getConfig(ConfigType.PLAYERS).getConfig();
        if (players.getConfigurationSection("pvp-times") != null &&
                players.getConfigurationSection("pvp-times").getKeys(false).contains(player.getUniqueId().toString())) {
            int time = players.getInt("pvp-times." + player.getUniqueId().toString());
            if (time > 0)
                pvpplayers.put(player, time);
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
                YamlConfiguration players = Configs.getConfig(ConfigType.PLAYERS).getConfig();
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
        YamlConfiguration players = Configs.getConfig(ConfigType.PLAYERS).getConfig();
        if(pvpplayers.containsKey(player)) {
            players.set("pvp-times." + player.getUniqueId().toString(), PvPHandler.getPvPTimes().get(player));
        } else {
            players.set("pvp-times." + player.getUniqueId().toString(), 0);
        }
        pvpplayers.remove(player);
    }
}
