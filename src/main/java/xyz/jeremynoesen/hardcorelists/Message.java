package xyz.jeremynoesen.hardcorelists;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

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
    public static String UNKNOWN_ARGS;
    public static String NO_PERMISSION;
    public static String RELOAD;
    public static String SET_TIME;
    public static String PLAYER_OFFLINE;
    public static String LIST_FORMAT;
    public static String DEAD_LIST_TITLE;
    public static String ALIVE_LIST_TITLE;
    public static String RESET;
    public static String CANT_RESET;

    /**
     * loads/reloads messages from file
     */
    public static void reloadMessages() {
        PREFIX = format(messageConfig.getConfig().getString("PREFIX"));
        PVP_ENABLED = PREFIX + format(messageConfig.getConfig().getString("PVP_ENABLED"));
        PVP_DISABLED = PREFIX + format(messageConfig.getConfig().getString("PVP_DISABLED"));
        CANT_HURT = PREFIX + format(messageConfig.getConfig().getString("CANT_HURT"));
        CHECK_TIME = PREFIX + format(messageConfig.getConfig().getString("CHECK_TIME"));
        UNKNOWN_ARGS = PREFIX + format(messageConfig.getConfig().getString("UNKNOWN_ARGS"));
        NO_PERMISSION = PREFIX + format(messageConfig.getConfig().getString("NO_PERMISSION"));
        RELOAD = PREFIX + format(messageConfig.getConfig().getString("RELOAD"));
        SET_TIME = PREFIX + format(messageConfig.getConfig().getString("SET_TIME"));
        PLAYER_OFFLINE = PREFIX + format(messageConfig.getConfig().getString("PLAYER_OFFLINE"));
        LIST_FORMAT = format(messageConfig.getConfig().getString("LIST_FORMAT"));
        DEAD_LIST_TITLE = PREFIX + format(messageConfig.getConfig().getString("DEAD_LIST_TITLE"));
        ALIVE_LIST_TITLE = PREFIX + format(messageConfig.getConfig().getString("ALIVE_LIST_TITLE"));
        RESET = PREFIX + format(messageConfig.getConfig().getString("RESET"));
        CANT_RESET = PREFIX + format(messageConfig.getConfig().getString("CANT_RESET"));
    }

    /**
     * get the help message to send to a player, only showing what they are allowed to run
     *
     * @param player player viewing help message
     * @return help message
     */
    public static String[] getHelpMessage(Player player) {
        ArrayList<String> help = new ArrayList<>();

        help.add("");
        help.add(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "----------[" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD +
                "Hardcore" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Lists" +
                ChatColor.GRAY + "" + ChatColor.BOLD + " Help"
                + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]----------");

        if (player.hasPermission("hardcorelists.pvptime.self"))
            help.add(ChatColor.GRAY + "/pvptime" + ChatColor.WHITE + ": View your remaining PVP time");
        if (player.hasPermission("hardcorelists.pvptime.others"))
            help.add(ChatColor.GRAY + "/pvptime <player>" + ChatColor.WHITE + ": View other's remaining PVP time");
        if (player.hasPermission("hardcorelists.help"))
            help.add(ChatColor.GRAY + "/hardcorelists help" + ChatColor.WHITE + ": Show plugin help");
        if (player.hasPermission("hardcorelists.reload"))
            help.add(ChatColor.GRAY + "/hardcorelists reload" + ChatColor.WHITE + ": Reload plugin and configs");
        if (player.hasPermission("hardcorelists.timer"))
            help.add(ChatColor.GRAY + "/hardcorelists timer <seconds>" + ChatColor.WHITE + ": Update the pvp timer");
        if (player.hasPermission("hardcorelists.list.dead") || player.hasPermission("hardcorelists.list.alive"))
            help.add(ChatColor.GRAY + "/hardcorelists list <dead/alive> <page>" + ChatColor.WHITE + ": List players");
        if (player.hasPermission("hardcorelists.reset"))
            help.add(ChatColor.GRAY + "hardcorelists reset" + ChatColor.WHITE + ": Reset data (console only)");

        help.add(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "--------------------------------------");
        help.add("");

        String[] out = new String[help.size()];
        return help.toArray(out);
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