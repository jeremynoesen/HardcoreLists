package com.teamcraft.tchardcore;

import com.teamcraft.tchardcore.config.Config;
import com.teamcraft.tchardcore.config.ConfigType;
import com.teamcraft.tchardcore.config.Configs;
import org.bukkit.ChatColor;

/**
 * All messages used within the plugin
 */
public class Message {
    
    private static final Config messageConfig = Configs.getConfig(ConfigType.MESSAGE);
    
    public static String PREFIX;
    public static String PVP_ENABLED;
    public static String PVP_DISABLED;
    public static String CANT_HURT;
    public static String CHECK_TIME;
    public static String UNKNOWN_COMMAND;
    public static String NO_PERMISSION;
    public static String RELOAD;
    public static String SET_TIME;
    
    /**
     * loads/reloads messages from file
     */
    public static void reloadMessages() {
        PREFIX = format(messageConfig.getConfig().getString("PREFIX"));
        PVP_ENABLED = PREFIX + format(messageConfig.getConfig().getString("PVP_ENABLED"));
        PVP_DISABLED = PREFIX + format(messageConfig.getConfig().getString("PVP_DISABLED"));
        CANT_HURT = PREFIX + format(messageConfig.getConfig().getString("CANT_HURT"));
        CHECK_TIME = PREFIX + format(messageConfig.getConfig().getString("CHECK_TIME"));
        UNKNOWN_COMMAND = PREFIX + format(messageConfig.getConfig().getString("UNKNOWN_COMMAND"));
        NO_PERMISSION = PREFIX + format(messageConfig.getConfig().getString("NO_PERMISSION"));
        RELOAD = PREFIX + format(messageConfig.getConfig().getString("RELOAD"));
        SET_TIME = PREFIX + format(messageConfig.getConfig().getString("SET_TIME"));
    
    }
    
    /**
     * Apply color codes and line breaks to a message
     *
     * @param msg message to format with color codes and line breaks
     * @return formatted message
     */
    public static String format(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
    
    /**
     * convert a time in seconds to a string for messages in minutes and seconds
     *
     * @param seconds seconds to convert
     * @return string formatted as 0m 0s
     */
    public static String convertTime(int seconds) {
        return (seconds/60) + "m" + (seconds%60) + "s";
    }
    
    /**
     * help message for admins
     */
    public static String[] HELP = new String[]{
            "",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "-------------[" + PREFIX +
                    ChatColor.GRAY + "" + ChatColor.BOLD + "Help"
                    + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]-------------",
            ChatColor.GRAY + "/pvptime" + ChatColor.WHITE + ": View time remaining on your pvp timer",
            ChatColor.GRAY + "/tchc reload" + ChatColor.WHITE + ": Reload all files for plugin",
            ChatColor.GRAY + "/tchc settime <seconds>" + ChatColor.WHITE + ": Update the duration of the starting pvp timer",
            ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------------------",
            ""
    };
}