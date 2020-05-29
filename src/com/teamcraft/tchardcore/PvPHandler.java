package com.teamcraft.tchardcore;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PvPHandler {
    
    private static final HashMap<Player, Integer> pvpplayers = new HashMap<>();
    
    public static HashMap<Player, Integer> getPvPTimes() {
        return pvpplayers;
    }
    
    public static void initPlayer(Player player) {
        if (TCHardcore.getListFile().getFile().getConfigurationSection("pvp-times").getKeys(false).contains(player.getUniqueId().toString())) {
            int time = TCHardcore.getListFile().getFile().getInt("pvp-times." + player.getUniqueId().toString());
            if (time > 0)
                pvpplayers.put(player, time);
        } else {
            pvpplayers.put(player, 15 * 60);
            player.sendMessage(ChatColor.GREEN + "PvP is disabled for 15 minutes for yourself.");
        }
    }
    
    public static void tickPlayers() {
        for (Player player : pvpplayers.keySet()) {
            if (pvpplayers.get(player) == 0) {
                TCHardcore.getListFile().getFile().set("pvp-times." + player.getUniqueId().toString(), "0");
                pvpplayers.remove(player);
                player.sendMessage(ChatColor.RED + "PvP is now enabled for yourself.");
            }
            pvpplayers.put(player, pvpplayers.get(player) - 1);
        }
    }
}
