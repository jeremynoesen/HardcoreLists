package xyz.jeremynoesen.hardcorelists.handler;

import xyz.jeremynoesen.hardcorelists.Config;
import xyz.jeremynoesen.hardcorelists.Message;
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
    private static final HashMap<UUID, Integer> pvptimes = new HashMap<>();
    
    /**
     * reference to death list file
     */
    private static final YamlConfiguration players = Config.getPlayersConfig().getConfig();
    
    /**
     * get the hashmap of pvp times for players
     *
     * @return hashmap of player pvp times
     */
    public static HashMap<UUID, Integer> getPvPTimes() {
        return pvptimes;
    }
    
    /**
     * load the player times from file
     */
    public static void load() {
        for (String s : players.getConfigurationSection("pvp-times").getKeys(false)) {
            pvptimes.put(UUID.fromString(s), players.getInt("pvp-times." + s));
        }
    }
    
    /**
     * save player times to file
     */
    public static void save() {
        for (String s : players.getConfigurationSection("pvp-times").getKeys(false)) {
            players.set("pvp-times." + s, null);
        }
        for (UUID player : pvptimes.keySet()) {
            players.set("pvp-times." + player, pvptimes.get(player));
        }
        Config.getPlayersConfig().saveConfig();
    }
    
    /**
     * get a player's remaining time until they can pvp
     *
     * @param player player to get time for
     * @return time left on their clock
     */
    public static int getPlayerTime(UUID player) {
        if (pvptimes.containsKey(player) && pvptimes.get(player) > 0) return pvptimes.get(player);
        return 0;
    }
    
    /**
     * tick a player's time, reduces 1 second
     */
    public static void tickPlayers() {
        for (UUID player : pvptimes.keySet()) {
            if(pvptimes.containsKey(player) && Bukkit.getOfflinePlayer(player).isOnline()) {
                if (pvptimes.get(player) == 0) Bukkit.getOfflinePlayer(player).getPlayer().sendMessage(Message.PVP_ENABLED);
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
    public static boolean canPvP(UUID player, UUID damager) {
        if ((pvptimes.containsKey(player) && pvptimes.get(player) > 0) ||
                (pvptimes.containsKey(damager) && pvptimes.get(damager) > 0)) {
            Bukkit.getOfflinePlayer(damager).getPlayer().sendMessage(Message.CANT_HURT);
            return false;
        }
        return true;
    }
    
    /**
     * cancels the pvp event if one or the other parties has pvp disabled
     *
     * @param e entity damage by entity event
     */
    @EventHandler
    public void onPvP(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            e.setCancelled(!canPvP(e.getEntity().getUniqueId(), e.getDamager().getUniqueId()));
        } else if (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter() instanceof Player
                && e.getEntity() instanceof Player) {
            e.setCancelled(!canPvP(e.getEntity().getUniqueId(), ((Player) ((Projectile) e.getDamager()).getShooter()).getUniqueId()));
        }
    }
    
    /**
     * initialize a new player's clock
     *
     * @param e player join event
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (!pvptimes.containsKey(player.getUniqueId())) {
            pvptimes.put(player.getUniqueId(), Config.getTimeConfig().getConfig().getInt("pvp-countdown-seconds"));
            e.getPlayer().sendMessage(Message.PVP_DISABLED
                    .replace("$TIME$", Message.convertTime(pvptimes.get(player.getUniqueId()))));
        } else {
            player.sendMessage(Message.CHECK_TIME.replace("$TIME$",
                    Message.convertTime(PvPHandler.getPlayerTime(player.getUniqueId())))
                    .replace("$PLAYER$", "You"));
        }
    }
}
