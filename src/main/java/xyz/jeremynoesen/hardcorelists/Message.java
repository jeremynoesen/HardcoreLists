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
    public static String CANT_HURT;
    public static String CHECK_TIME_SELF;
    public static String CHECK_TIME_OTHERS;
    public static String UNKNOWN_ARGS;
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
    public static String HELP_HEADER;
    public static String HELP_PVPTIME_SELF;
    public static String HELP_PVPTIME_OTHERS;
    public static String HELP_HELP;
    public static String HELP_RELOAD;
    public static String HELP_TIMER;
    public static String HELP_LIST;
    public static String HELP_RESET;
    public static String HELP_FOOTER;

    /**
     * loads/reloads messages from file
     */
    public static void reloadMessages() {
        PREFIX = format(messageConfig.getConfig().getString("PREFIX"));
        PVP_ENABLED = PREFIX + format(messageConfig.getConfig().getString("PVP_ENABLED"));
        CANT_HURT = PREFIX + format(messageConfig.getConfig().getString("CANT_HURT"));
        CHECK_TIME_SELF = PREFIX + format(messageConfig.getConfig().getString("CHECK_TIME_SELF"));
        CHECK_TIME_OTHERS = PREFIX + format(messageConfig.getConfig().getString("CHECK_TIME_OTHERS"));
        UNKNOWN_ARGS = PREFIX + format(messageConfig.getConfig().getString("UNKNOWN_ARGS"));
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
        HELP_HEADER = format(messageConfig.getConfig().getString("HELP_HEADER"));
        HELP_PVPTIME_SELF = format(messageConfig.getConfig().getString("HELP_PVPTIME_SELF"));
        HELP_PVPTIME_OTHERS = format(messageConfig.getConfig().getString("HELP_PVPTIME_OTHERS"));
        HELP_HELP = format(messageConfig.getConfig().getString("HELP_HELP"));
        HELP_RELOAD = format(messageConfig.getConfig().getString("HELP_RELOAD"));
        HELP_TIMER = format(messageConfig.getConfig().getString("HELP_TIMER"));
        HELP_LIST = format(messageConfig.getConfig().getString("HELP_LIST"));
        HELP_RESET = format(messageConfig.getConfig().getString("HELP_RESET"));
        HELP_FOOTER = format(messageConfig.getConfig().getString("HELP_FOOTER"));
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
        help.add(HELP_HEADER);

        if (player.hasPermission("hardcorelists.pvptime.self")) help.add(HELP_PVPTIME_SELF);
        if (player.hasPermission("hardcorelists.pvptime.others")) help.add(HELP_PVPTIME_OTHERS);
        if (player.hasPermission("hardcorelists.help")) help.add(HELP_HELP);
        if (player.hasPermission("hardcorelists.reload")) help.add(HELP_RELOAD);
        if (player.hasPermission("hardcorelists.timer")) help.add(HELP_TIMER);
        if (player.hasPermission("hardcorelists.list.dead") ||
                player.hasPermission("hardcorelists.list.alive")) help.add(HELP_LIST);
        if (player.hasPermission("hardcorelists.reset")) help.add(HELP_RESET);

        help.add(HELP_FOOTER);
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