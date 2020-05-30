package com.teamcraft.tchardcore.command;

import com.teamcraft.tchardcore.Message;
import com.teamcraft.tchardcore.config.ConfigType;
import com.teamcraft.tchardcore.config.Configs;
import com.teamcraft.tchardcore.handler.PvPHandler;
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
        
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            switch (label.toLowerCase()) {
                case "pvptime":
            player.sendMessage(Message.CHECK_TIME.replace("$TIME$",
                    Message.convertTime(PvPHandler.getPvPTimes().get(player))));
                    break;
                case "tchc":
                    if(player.hasPermission("tchc.admin")) {
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
                            case "settime":
                                Configs.getConfig(ConfigType.TIME).getConfig()
                                        .set("pvp-countdown-seconds", Integer.valueOf(args[1]));
                                player.sendMessage(Message.SET_TIME.replace("$TIME$",
                                        Message.convertTime(Integer.valueOf(args[1]))));
                                break;
                            default:
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
