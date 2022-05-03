package xyz.jeremynoesen.hardcorelists.handler;

import xyz.jeremynoesen.hardcorelists.Config;
import xyz.jeremynoesen.hardcorelists.Message;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * class to handle player list data
 *
 * @author Jeremy Noesen
 */
public class ListHandler implements Listener {
    
    /**
     * alive player list
     */
    private static final ArrayList<UUID> alive = new ArrayList<>();
    
    /**
     * dead player list
     */
    private static final ArrayList<UUID> dead = new ArrayList<>();
    
    /**
     * reference to player list file
     */
    private static final YamlConfiguration players = Config.getPlayersConfig().getConfig();
    
    /**
     * load the player lists from file
     */
    public static void load() {
        for (String s : players.getStringList("alive")) {
            alive.add(UUID.fromString(s));
        }
        for (String s : players.getStringList("dead")) {
            dead.add(UUID.fromString(s));
        }
    }
    
    /**
     * save player lists to file
     */
    public static void save() {
        ArrayList<String> alivelist = new ArrayList<>();
        for (UUID p : alive) {
            alivelist.add(p.toString());
        }
        players.set("alive", alivelist);

        ArrayList<String> deadlist = new ArrayList<>();
        for (UUID p : dead) {
            deadlist.add(p.toString());
        }
        players.set("dead", deadlist);
        Config.getPlayersConfig().saveConfig();
    }
    
    /**
     * get a formatted string list of names from the specified list
     *
     * @param page page number
     * @param list list to get page pf
     * @return string array formatted with the list of names
     */
    public static String[] getListPage(int page, ArrayList<UUID> list) {
        int LENGTH = 10;
        int actualPages = (list.size() / LENGTH) + (list.size() % LENGTH > 0 ? 1 : 0);
        if (page > actualPages) page = actualPages;
        String[] stringList = new String[Math.min(LENGTH, list.size())];
        for (int i = 0; i < stringList.length; i++) {
            int shift = i + (LENGTH * (page - 1));
            String name = Bukkit.getOfflinePlayer(list.get(shift)).getName();
            stringList[i] = Message.LIST_FORMAT
                    .replace("$POS$", Integer.toString(shift + 1))
                    .replace("$PLAYER$", name);
        }
        return stringList;
    }
    
    /**
     * get teh alive players list
     *
     * @return list of alive players
     */
    public static ArrayList<UUID> getAlive() {
        return alive;
    }
    
    /**
     * get the dead players list
     *
     * @return list of dead players
     */
    public static ArrayList<UUID> getDead() {
        return dead;
    }
    
    /**
     * event called when a player dies, will remove player from alive list and add to dead list
     *
     * @param e player death event
     */
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        alive.remove(e.getEntity().getUniqueId());
        if (!dead.contains(e.getEntity().getUniqueId()))
            dead.add(e.getEntity().getUniqueId());
    }
    
    /**
     * event called when a player joins, will add them to the list of all players, as well as the alive list if they're
     * not dead
     *
     * @param e player join event
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!dead.contains(e.getPlayer().getUniqueId()) && !alive.contains(e.getPlayer().getUniqueId()))
            alive.add(e.getPlayer().getUniqueId());
    }
}
