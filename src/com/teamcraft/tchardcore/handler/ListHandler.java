package com.teamcraft.tchardcore.handler;

import com.teamcraft.tchardcore.Message;
import com.teamcraft.tchardcore.config.ConfigType;
import com.teamcraft.tchardcore.config.Configs;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * class to handle death list data
 */
public class ListHandler {
    
    /**
     * reference to player list file
     */
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
    
    /**
     * get a page from the list of players for the desired list type
     *
     * @param page page of list
     * @param type list type
     * @return formatted string array with the list of names from the specified list
     */
    public static String[] listPlayers(int page, ListType type) {
        switch (type) {
            case DEAD:
                return getListPage(page, new ArrayList<>(players.getConfigurationSection("dead").getKeys(false)), type);
            case ALIVE:
                return getListPage(page, new ArrayList<>(players.getConfigurationSection("alive").getKeys(false)), type);
            case ALL:
                return getListPage(page, new ArrayList<>(players.getConfigurationSection("all").getKeys(false)), type);
            default:
                return null;
        }
    }
    
    /**
     * get a formatted string list of names from the specified list
     *
     * @param page page number
     * @param list list to get page pf
     * @return string array formatted with the list of names
     */
    private static String[] getListPage(int page, ArrayList<String> list, ListType type) {
        int LENGTH = 10;
        String[] stringList = new String[10];
        for (int i = 0; i < Math.min(LENGTH, list.size()); i++) {
            int shift = i + (LENGTH * (page - 1));
            String name = players.getString(type.toString().toLowerCase() + "." + list.get(shift));
            stringList[i] = Message.LIST_FORMAT
                    .replace("$POS$", Integer.toString(shift + 1))
                    .replace("$PLAYER$", name);
        }
        return stringList;
    }
}
