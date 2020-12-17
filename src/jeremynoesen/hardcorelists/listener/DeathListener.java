package jeremynoesen.hardcorelists.listener;

import jeremynoesen.hardcorelists.handler.ListHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * listener class related to the death and player lists for the list file
 *
 * @author Jeremy Noesen
 */
public class DeathListener implements Listener {
    
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
