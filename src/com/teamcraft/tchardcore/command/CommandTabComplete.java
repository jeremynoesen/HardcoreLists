package com.teamcraft.tchardcore.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * tab completer for the tchc command
 *
 * @author JNDev (Jeremaster101)
 */
public class CommandTabComplete implements TabCompleter {
    
    /**
     * run when a player tab completes
     *
     * @param commandSender
     * @param command
     * @param label
     * @param args
     * @return
     */
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();
        
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (label.equalsIgnoreCase("tchc") && player.hasPermission("tchc.admin")) {
                if (args.length == 1) {
                    if (args[0].toLowerCase().startsWith("h")) list.add("help");
                    else if (args[0].toLowerCase().startsWith("l")) list.add("list");
                    else if (args[0].toLowerCase().startsWith("r")) list.add("reload");
                    else if (args[0].toLowerCase().startsWith("t")) list.add("timer");
                    else if (args[0].toLowerCase().startsWith("")) {
                        list.add("help");
                        list.add("list");
                        list.add("reload");
                        list.add("timer");
                    }
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("timer")) {
                        list.add("set");
                    } else if (args[0].equalsIgnoreCase("list")) {
                        if (args[1].toLowerCase().startsWith("d")) list.add("dead");
                        else if (args[1].toLowerCase().startsWith("ali")) list.add("alive");
                        else if (args[1].toLowerCase().startsWith("all")) list.add("all");
                        else if (args[1].toLowerCase().startsWith("a")) {
                            list.add("alive");
                            list.add("all");
                        } else if (args[1].toLowerCase().startsWith("")) {
                            list.add("all");
                            list.add("alive");
                            list.add("dead");
                        }
                    }
                }
            }
        }
        
        return list;
    }
}
