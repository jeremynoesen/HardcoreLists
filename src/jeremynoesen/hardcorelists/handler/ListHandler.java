package jeremynoesen.hardcorelists.handler;

import jeremynoesen.hardcorelists.Config;
import jeremynoesen.hardcorelists.Message;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
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
    private static ArrayList<Player> alive = new ArrayList<>();
    
    /**
     * dead player list
     */
    private static ArrayList<Player> dead = new ArrayList<>();
    
    /**
     * reference to player list file
     */
    private static YamlConfiguration players = Config.getPlayersConfig().getConfig();
    
    /**
     * load the player lists from file
     */
    public static void load() {
        for (String s : players.getStringList("alive")) {
            alive.add(Bukkit.getPlayer(UUID.fromString(s)));
        }
        for (String s : players.getStringList("dead")) {
            dead.add(Bukkit.getPlayer(UUID.fromString(s)));
        }
    }
    
    /**
     * save player lists to file
     */
    public static void save() {
        players.set("alive", null);
        ArrayList<String> strings = new ArrayList<>();
        for(Player p : alive) {
            strings.add(p.getUniqueId().toString());
        }
        players.set("alive", strings);
        players.set("dead", null);
        strings.clear();
        for(Player p : dead) {
            strings.add(p.getUniqueId().toString());
        }
        players.set("dead", strings);
        Config.getPlayersConfig().saveConfig();
    }
    
    /**
     * get a formatted string list of names from the specified list
     *
     * @param page page number
     * @param list list to get page pf
     * @return string array formatted with the list of names
     */
    public static String[] getListPage(int page, ArrayList<Player> list) {
        int LENGTH = 10;
        int actualPages = (list.size() / LENGTH) + (list.size() % LENGTH > 0 ? 1 : 0);
        if (page > actualPages) page = actualPages;
        String[] stringList = new String[Math.min(LENGTH, list.size())];
        for (int i = 0; i < stringList.length; i++) {
            int shift = i + (LENGTH * (page - 1));
            String name = list.get(shift).getName();
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
    public static ArrayList<Player> getAlive() {
        return alive;
    }
    
    /**
     * get the dead players list
     *
     * @return list of dead players
     */
    public static ArrayList<Player> getDead() {
        return dead;
    }
    
    /**
     * event called when a player dies, will remove player from alive list and add to dead list
     *
     * @param e
     */
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        alive.remove(e.getEntity());
        dead.add(e.getEntity());
    }
    
    /**
     * event called when a player joins, will add them to the list of all players, as well as the alive list if they're
     * not dead
     *
     * @param e
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(!dead.contains(e.getPlayer()) && !alive.contains(e.getPlayer())) alive.add(e.getPlayer());
    }
}
