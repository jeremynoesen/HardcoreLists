package jeremynoesen.hardcorelists.handler;

import jeremynoesen.hardcorelists.Config;
import jeremynoesen.hardcorelists.Message;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.UUID;

/**
 * methods to tick player times and load the last known times from file
 *
 * @author Jeremy Noesen
 */
public class PvPHandler implements Listener {
    
    /**
     * hashmap with all players and times
     */
    private static final HashMap<Player, Integer> pvptimes = new HashMap<>();
    
    /**
     * reference to death list file
     */
    private static final YamlConfiguration players = Config.getPlayersConfig().getConfig();
    
    /**
     * get the hashmap of pvp times for players
     *
     * @return hashmap of player pvp times
     */
    public static HashMap<Player, Integer> getPvPTimes() {
        return pvptimes;
    }
    
    /**
     * load the player times from file
     */
    public static void load() {
        for (String s : players.getConfigurationSection("pvp-times").getKeys(false)) {
            pvptimes.put(Bukkit.getOfflinePlayer(UUID.fromString(s)).getPlayer(), players.getInt("pvp-times." + s));
        }
    }
    
    /**
     * save player times to file
     */
    public static void save() {
        for (String s : players.getConfigurationSection("pvp-times").getKeys(false)) {
            players.set("pvp-times." + s, null);
        }
        for (Player player : pvptimes.keySet()) {
            players.set("pvp-times." + player.getUniqueId().toString(), pvptimes.get(player));
        }
        Config.getPlayersConfig().saveConfig();
    }
    
    /**
     * get a player's remaining time until they can pvp
     *
     * @param player player to get time for
     * @return time left on their clock
     */
    public static int getPlayerTime(Player player) {
        if (pvptimes.containsKey(player) && pvptimes.get(player) > 0) return pvptimes.get(player);
        return 0;
    }
    
    /**
     * tick a player's time, reduces 1 second
     */
    public static void tickPlayers() {
        for (Player player : pvptimes.keySet()) {
            if(pvptimes.get(player) != null && player.isOnline()) {
                if (pvptimes.get(player) == 0) player.sendMessage(Message.PVP_ENABLED);
                if (pvptimes.get(player) > -1) pvptimes.put(player, pvptimes.get(player) - 1);
            }
        }
    }
    
    /**
     * check if the player or damager can not pvp
     *
     * @param player  player being hurt
     * @param damager damager
     */
    public static boolean canPvP(Player player, Player damager) {
        if ((pvptimes.containsKey(player) && pvptimes.get(player) > 0) ||
                (pvptimes.containsKey(damager) && pvptimes.get(damager) > 0)) {
            damager.sendMessage(Message.CANT_HURT);
            return false;
        }
        return true;
    }
    
    /**
     * cancels the pvp event if one or the other parties has pvp disabled
     *
     * @param e
     */
    @EventHandler
    public void onPvP(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            e.setCancelled(canPvP((Player) e.getEntity(), (Player) e.getDamager()));
        } else if (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter() instanceof Player
                && e.getEntity() instanceof Player) {
            e.setCancelled(canPvP((Player) e.getEntity(), (Player) ((Projectile) e.getDamager()).getShooter()));
        }
    }
    
    /**
     * initialize a new player's clock
     *
     * @param e
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (!pvptimes.containsKey(player)) {
            pvptimes.put(player, Config.getTimeConfig().getConfig().getInt("pvp-countdown-seconds"));
            e.getPlayer().sendMessage(Message.PVP_DISABLED
                    .replace("$TIME$", Message.convertTime(pvptimes.get(player))));
        } else {
            player.sendMessage(Message.CHECK_TIME.replace("$TIME$",
                    Message.convertTime(PvPHandler.getPlayerTime(player)))
                    .replace("$PLAYER$", "You"));
        }
    }
}
