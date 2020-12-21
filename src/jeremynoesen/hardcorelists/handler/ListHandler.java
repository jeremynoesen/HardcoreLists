package jeremynoesen.hardcorelists.handler;

import jeremynoesen.hardcorelists.Config;
import jeremynoesen.hardcorelists.Message;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

/**
 * class to handle death list data
 *
 * @author Jeremy Noesen
 */
public class ListHandler implements Listener {
    
    /**
     * reference to player list file
     */
    private static final YamlConfiguration players = Config.getPlayersConfig().getConfig();
    
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
     * @param listName list name
     * @return formatted string array with the list of names from the specified list
     */
    public static String[] listPlayers(int page, String listName) {
        switch (listName) {
            case "dead":
                return getListPage(page, new ArrayList<>(players.getConfigurationSection("dead").getKeys(false)), listName);
            case "alive":
                return getListPage(page, new ArrayList<>(players.getConfigurationSection("alive").getKeys(false)), listName);
            case "all":
                return getListPage(page, new ArrayList<>(players.getConfigurationSection("all").getKeys(false)), listName);
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
    private static String[] getListPage(int page, ArrayList<String> list, String listName) {
        int LENGTH = 10;
        int actualPages = (list.size() / LENGTH) + (list.size() % LENGTH > 0 ? 1 : 0);
        if (page > actualPages) page = actualPages;
        String[] stringList = new String[10];
        for (int i = 0; i < Math.min(LENGTH, list.size()); i++) {
            int shift = i + (LENGTH * (page - 1));
            String name = players.getString(listName + "." + list.get(shift));
            stringList[i] = Message.LIST_FORMAT
                    .replace("$POS$", Integer.toString(shift + 1))
                    .replace("$PLAYER$", name);
        }
        return stringList;
    }
    
    /**
     * event called when a player dies, will remove player from alive list and add to dead list
     *
     * @param e
     */
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        ListHandler.addDeath(e.getEntity());
    }
    
    /**
     * event called when a player joins, will add them to the list of all players, as well as the alive list if they're
     * not dead
     *
     * @param e
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        ListHandler.initPlayer(e.getPlayer());
    }
}
