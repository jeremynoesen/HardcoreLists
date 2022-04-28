package xyz.jeremynoesen.hardcorelists.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import xyz.jeremynoesen.hardcorelists.Config;
import xyz.jeremynoesen.hardcorelists.Message;
import xyz.jeremynoesen.hardcorelists.handler.ListHandler;
import xyz.jeremynoesen.hardcorelists.handler.PvPHandler;

import java.util.ArrayList;

/**
 * command executor for the plugin's commands
 *
 * @author Jeremy Noesen
 */
public class CommandExec implements CommandExecutor {

    /**
     * called when commands are run
     *
     * @param commandSender
     * @param command
     * @param label
     * @param args
     * @return
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            switch (label.toLowerCase()) {
                case "pvptime":
                case "pt":
                    if (args.length > 0) {
                        if (player.hasPermission("hardcorelists.pvptime.others")) {
                            try {
                                OfflinePlayer other = Bukkit.getPlayer(args[0]);
                                assert other.isOnline();
                                player.sendMessage(Message.CHECK_TIME.replace("$TIME$",
                                                Message.convertTime(PvPHandler.getPlayerTime(other.getUniqueId())))
                                        .replace("$PLAYER$", other.getName()));
                            } catch (Exception e) {
                                player.sendMessage(Message.PLAYER_OFFLINE);
                            }
                        } else player.sendMessage(Message.NO_PERMISSION);
                    } else {
                        if (player.hasPermission("hardcorelists.pvptime.self")) {
                            player.sendMessage(Message.CHECK_TIME.replace("$TIME$",
                                            Message.convertTime(PvPHandler.getPlayerTime(player.getUniqueId())))
                                    .replace("$PLAYER$", "You"));
                        } else player.sendMessage(Message.NO_PERMISSION);
                    }
                    break;
                case "hardcorelists":
                case "hl":
                    if (args.length > 0) {
                        switch (args[0].toLowerCase()) {
                            case "reload":
                                if (player.hasPermission("hardcorelists.reload")) {
                                    PvPHandler.save();
                                    ListHandler.save();
                                    Config.getTimeConfig().saveConfig();
                                    Config.getTimeConfig().reloadConfig();
                                    Config.getMessageConfig().reloadConfig();
                                    Config.getPlayersConfig().reloadConfig();
                                    PvPHandler.load();
                                    ListHandler.load();
                                    Message.reloadMessages();
                                    player.sendMessage(Message.RELOAD);
                                } else player.sendMessage(Message.NO_PERMISSION);
                                break;
                            case "reset":
                                if (player.hasPermission("hardcorelists.reset")) {
                                    player.sendMessage(Message.CANT_RESET);
                                } else player.sendMessage(Message.NO_PERMISSION);
                                break;
                            case "help":
                                if (player.hasPermission("hardcorelists.help")) {
                                    player.sendMessage(Message.getHelpMessage(player));
                                } else player.sendMessage(Message.NO_PERMISSION);
                                break;
                            case "timer":
                                if (player.hasPermission("hardcorelists.timer")) {
                                    if (args.length > 1 && args[1] != null) {
                                        try {
                                            Config.getTimeConfig().getConfig()
                                                    .set("pvp-countdown-seconds", Integer.valueOf(args[1]));
                                            player.sendMessage(Message.SET_TIME.replace("$TIME$",
                                                    Message.convertTime(Integer.valueOf(args[1]))));
                                        } catch (Exception e) {
                                            player.sendMessage(Message.UNKNOWN_ARGS);
                                        }
                                    } else {
                                        player.sendMessage(Message.UNKNOWN_ARGS);
                                    }
                                } else player.sendMessage(Message.NO_PERMISSION);
                                break;
                            case "list":
                                if (args.length > 1 && args[1] != null) {
                                    try {
                                        int page = 1;
                                        if (args.length > 2 && args[2] != null) page = Integer.parseInt(args[2]);
                                        switch (args[1].toLowerCase()) {
                                            case "dead":
                                                if (player.hasPermission("hardcorelists.list.dead")) {
                                                    player.sendMessage(Message.DEAD_LIST_TITLE);
                                                    player.sendMessage(ListHandler.getListPage(
                                                            page, ListHandler.getDead()));
                                                } else player.sendMessage(Message.NO_PERMISSION);
                                                break;
                                            case "alive":
                                                if (player.hasPermission("hardcorelists.list.alive")) {
                                                    player.sendMessage(Message.ALIVE_LIST_TITLE);
                                                    player.sendMessage(ListHandler.getListPage(
                                                            page, ListHandler.getAlive()));
                                                } else player.sendMessage(Message.NO_PERMISSION);
                                                break;
                                            default:
                                                player.sendMessage(Message.UNKNOWN_ARGS);
                                        }
                                    } catch (Exception e) {
                                        player.sendMessage(Message.UNKNOWN_ARGS);
                                    }
                                } else {
                                    player.sendMessage(Message.UNKNOWN_ARGS);
                                }
                                break;
                            default:
                                player.sendMessage(Message.UNKNOWN_ARGS);
                        }
                    } else {
                        player.sendMessage(Message.UNKNOWN_ARGS);
                    }
                    break;
                default:
                    player.sendMessage(Message.UNKNOWN_ARGS);
            }
        } else if (commandSender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) commandSender;
            if (args.length > 0 && args[0].equalsIgnoreCase("reset")) {
                if (Bukkit.getOnlinePlayers().size() > 0) {
                    console.sendMessage(Message.CANT_RESET);
                } else {
                    PvPHandler.getPvPTimes().clear();
                    Config players = Config.getPlayersConfig();
                    players.getConfig().set("dead", new ArrayList<String>());
                    ListHandler.getDead().clear();
                    players.getConfig().set("alive", new ArrayList<String>());
                    ListHandler.getAlive().clear();
                    try {
                        for (String key : players.getConfig().getConfigurationSection("pvp-times").getKeys(false)) {
                            players.getConfig().set("pvp-times." + key, null);
                        }
                        PvPHandler.getPvPTimes().clear();
                    } catch (NullPointerException ignored) {
                    }
                    players.saveConfig();
                    console.sendMessage(Message.RESET);
                }
            } else {
                console.sendMessage(Message.UNKNOWN_ARGS);
            }
        }
        return true;
    }
}
