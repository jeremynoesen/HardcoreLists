package com.teamcraft.tchardcore.command;

import com.teamcraft.tchardcore.Message;
import com.teamcraft.tchardcore.config.ConfigType;
import com.teamcraft.tchardcore.config.Configs;
import com.teamcraft.tchardcore.handler.ListHandler;
import com.teamcraft.tchardcore.handler.ListType;
import com.teamcraft.tchardcore.handler.PvPHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * command executor for the plugin's commands
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
                    if (player.hasPermission("tchc.admin")) {
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
                case "tchc":
                    if (player.hasPermission("tchc.admin")) {
                        if (args.length > 0) {
                            switch (args[0].toLowerCase()) {
                                case "reload":
                                    Configs.getConfig(ConfigType.TIME).reloadConfig();
                                    Configs.getConfig(ConfigType.MESSAGE).reloadConfig();
                                    Configs.getConfig(ConfigType.PLAYERS).reloadConfig();
                                    player.sendMessage(Message.RELOAD);
                                    break;
                                case "help":
                                    player.sendMessage(Message.HELP);
                                    break;
                                case "timer":
                                    if (args[1] != null && args[1].equalsIgnoreCase("set") && args[2] != null) {
                                        try {
                                            Configs.getConfig(ConfigType.TIME).getConfig()
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
                                    if (args[1] != null && args[2] != null) {
                                        try {
                                            switch (args[1].toLowerCase()) {
                                                case "dead":
                                                    player.sendMessage(Message.DEAD_LIST_TITLE);
                                                    player.sendMessage(ListHandler.listPlayers(
                                                            Integer.valueOf(args[2]), ListType.DEAD));
                                                    break;
                                                case "alive":
                                                    player.sendMessage(Message.ALIVE_LIST_TITLE);
                                                    player.sendMessage(ListHandler.listPlayers(
                                                            Integer.valueOf(args[2]), ListType.ALIVE));
                                                    break;
                                                case "all":
                                                    player.sendMessage(Message.ALL_LIST_TITLE);
                                                    player.sendMessage(ListHandler.listPlayers(
                                                            Integer.valueOf(args[2]), ListType.ALL));
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
        }
        return true;
    }
}
