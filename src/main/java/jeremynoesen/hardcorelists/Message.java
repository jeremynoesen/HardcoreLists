package jeremynoesen.hardcorelists;

import org.bukkit.ChatColor;

/**
 * All messages used within the plugin
 *
 * @author Jeremy Noesen
 */
public class Message {
    
    private static final Config messageConfig = Config.getMessageConfig();
    
    public static String PREFIX;
    public static String PVP_ENABLED;
    public static String PVP_DISABLED;
    public static String CANT_HURT;
    public static String CHECK_TIME;
    public static String UNKNOWN_COMMAND;
    public static String NO_PERMISSION;
    public static String RELOAD;
    public static String SET_TIME;
    public static String PLAYER_OFFLINE;
    public static String LIST_FORMAT;
    public static String DEAD_LIST_TITLE;
    public static String ALIVE_LIST_TITLE;
    public static String ALL_LIST_TITLE;
    public static String RESET;
    public static String CANT_RESET;
    public static String[] HELP;
    
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
        PLAYER_OFFLINE = PREFIX + format(messageConfig.getConfig().getString("PLAYER_OFFLINE"));
        LIST_FORMAT = format(messageConfig.getConfig().getString("LIST_FORMAT"));
        DEAD_LIST_TITLE = PREFIX + format(messageConfig.getConfig().getString("DEAD_LIST_TITLE"));
        ALIVE_LIST_TITLE = PREFIX + format(messageConfig.getConfig().getString("ALIVE_LIST_TITLE"));
        ALL_LIST_TITLE = PREFIX + format(messageConfig.getConfig().getString("ALL_LIST_TITLE"));
        RESET = PREFIX + format(messageConfig.getConfig().getString("RESET"));
        CANT_RESET = PREFIX + format(messageConfig.getConfig().getString("CANT_RESET"));
        
        HELP = new String[]{
                "",
                ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "--------[" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD +
                        "Hardcore" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Lists" +
                        ChatColor.GRAY + "" + ChatColor.BOLD + "Help"
                        + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]--------",
                ChatColor.GRAY + "/pvptime <player>" + ChatColor.WHITE + ": View time remaining on an online player's pvp timer",
                ChatColor.GRAY + "/hardcorelists reload" + ChatColor.WHITE + ": Reload all config and data files for the plugin",
                ChatColor.GRAY + "/hardcorelists timer set <seconds>" + ChatColor.WHITE + ": Update the duration of the starting pvp timer",
                ChatColor.GRAY + "/hardcorelists list <dead/alive> <page>" + ChatColor.WHITE + ": Get players in a list by page",
                ChatColor.GRAY + "/hardcorelists reset" + ChatColor.WHITE + ": Reset data, can only be run by console when nobody is online",
                ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "----------------------------------",
                ""
        };
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
        return (seconds / 60) + "m " + (seconds % 60) + "s";
    }
}