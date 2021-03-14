package jeremynoesen.hardcorelists.command;

import jeremynoesen.hardcorelists.Config;
import jeremynoesen.hardcorelists.Message;
import jeremynoesen.hardcorelists.handler.ListHandler;
import jeremynoesen.hardcorelists.handler.PvPHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

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
                    if (player.hasPermission("hardcorelists.admin")) {
                        if (args.length > 0) {
                            try {
                                Player other = Bukkit.getPlayer(args[0]);
                                assert other.isOnline();
                                player.sendMessage(Message.CHECK_TIME.replace("$TIME$",
                                        Message.convertTime(PvPHandler.getPlayerTime(other)))
                                        .replace("$PLAYER$", other.getName()));
                            } catch (Exception e) {
                                player.sendMessage(Message.PLAYER_OFFLINE);
                            }
                        } else {
                            player.sendMessage(Message.CHECK_TIME.replace("$TIME$",
                                    Message.convertTime(PvPHandler.getPlayerTime(player)))
                                    .replace("$PLAYER$", "You"));
                        }
                    } else {
                        player.sendMessage(Message.CHECK_TIME.replace("$TIME$",
                                Message.convertTime(PvPHandler.getPlayerTime(player)))
                                .replace("$PLAYER$", "You"));
                    }
                    break;
                case "hardcorelists":
                case "hl":
                    if (player.hasPermission("hardcorelists.admin")) {
                        if (args.length > 0) {
                            switch (args[0].toLowerCase()) {
                                case "reload":
                                    Config.getTimeConfig().reloadConfig();
                                    Config.getMessageConfig().reloadConfig();
                                    Config.getPlayersConfig().reloadConfig();
                                    player.sendMessage(Message.RELOAD);
                                    break;
                                case "reset":
                                    player.sendMessage(Message.CANT_RESET);
                                    break;
                                case "help":
                                    player.sendMessage(Message.HELP);
                                    break;
                                case "timer":
                                    if (args.length > 2 && args[1] != null && args[1].equalsIgnoreCase("set") && args[2] != null) {
                                        try {
                                            Config.getTimeConfig().getConfig()
                                                    .set("pvp-countdown-seconds", Integer.valueOf(args[2]));
                                            player.sendMessage(Message.SET_TIME.replace("$TIME$",
                                                    Message.convertTime(Integer.valueOf(args[2]))));
                                        } catch (Exception e) {
                                            player.sendMessage(Message.UNKNOWN_COMMAND);
                                        }
                                    } else {
                                        player.sendMessage(Message.UNKNOWN_COMMAND);
                                    }
                                    break;
                                case "list":
                                    if (args.length > 1 && args[1] != null) {
                                        try {
                                            int page = 1;
                                            if (args.length > 2 && args[2] != null) page = Integer.parseInt(args[2]);
                                            switch (args[1].toLowerCase()) {
                                                case "dead":
                                                    player.sendMessage(Message.DEAD_LIST_TITLE);
                                                    player.sendMessage(ListHandler.getListPage(
                                                            page, ListHandler.getDead()));
                                                    break;
                                                case "alive":
                                                    player.sendMessage(Message.ALIVE_LIST_TITLE);
                                                    player.sendMessage(ListHandler.getListPage(
                                                            page, ListHandler.getAlive()));
                                                    break;
                                                default:
                                                    player.sendMessage(Message.UNKNOWN_COMMAND);
                                            }
                                        } catch (Exception e) {
                                            player.sendMessage(Message.UNKNOWN_COMMAND);
                                        }
                                    } else {
                                        player.sendMessage(Message.UNKNOWN_COMMAND);
                                    }
                                    break;
                                default:
                                    player.sendMessage(Message.UNKNOWN_COMMAND);
                            }
                        } else {
                            player.sendMessage(Message.UNKNOWN_COMMAND);
                        }
                    } else {
                        player.sendMessage(Message.NO_PERMISSION);
                    }
                    break;
                default:
                    player.sendMessage(Message.UNKNOWN_COMMAND);
            }
        } else if (commandSender instanceof ConsoleCommandSender) {
            ConsoleCommandSender console = (ConsoleCommandSender) commandSender;
            if(args.length > 0 && args[0].equalsIgnoreCase("reset")) {
                if (Bukkit.getOnlinePlayers().size() > 0) {
                    console.sendMessage(Message.CANT_RESET);
                } else {
                    PvPHandler.getPvPTimes().clear();
                    Config players = Config.getPlayersConfig();
                    for (String key : players.getConfig().getConfigurationSection("dead").getKeys(false)) {
                        players.getConfig().set("dead." + key, null);
                    }
                    for (String key : players.getConfig().getConfigurationSection("alive").getKeys(false)) {
                        players.getConfig().set("alivw." + key, null);
                    }
                    for (String key : players.getConfig().getConfigurationSection("pvp-times").getKeys(false)) {
                        players.getConfig().set("pvp-times." + key, null);
                    }
                    console.sendMessage(Message.RESET);
                }
            } else {
                console.sendMessage(Message.UNKNOWN_COMMAND);
            }
        }
        return true;
    }
}
