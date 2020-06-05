package com.teamcraft.tchardcore.handler;

import com.teamcraft.tchardcore.config.ConfigType;
import com.teamcraft.tchardcore.config.Configs;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 * class to handle death list data
 */
public class DeathListHandler {
    
    private static YamlConfiguration players = Configs.getConfig(ConfigType.PLAYERS).getConfig();
    
    /**
     * add player tyo list of all players, as well as the alive list if they are not in the dead list
     *
     * @param player player to initialize
     */
    public static void initPlayer(Player player) {
        if (players.getConfigurationSection("dead") == null ||
                !players.getConfigurationSection("dead").getKeys(false).contains(player.getUniqueId().toString())) {
            players.set("alive." + player.getUniqueId(), player.getName());
        }
        players.set("all." + player.getUniqueId(), player.getName());
    }
    
    /**
     * add player to death list and remove from alive if they are in it
     *
     * @param player player to set as dead
     */
    public static void addDeath(Player player) {
        if (players.getConfigurationSection("alive") != null &&
                players.getConfigurationSection("alive").getKeys(false).contains(player.getUniqueId().toString())) {
            players.set("alive." + player.getUniqueId(), null);
        }
        players.set("dead." + player.getUniqueId(), player.getName());
    }
    
    
    
}
