package com.teamcraft.tchardcore.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * tab completer for the tchc command
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
            if (label.equalsIgnoreCase("tchc") && player.hasPermission("tchc.admin") && args.length == 1) {
                if (args[0].toLowerCase().startsWith("h")) list.add("help");
                else if (args[0].toLowerCase().startsWith("r")) list.add("reload");
                else if (args[0].toLowerCase().startsWith("s")) list.add("settime");
                else if (args[0].toLowerCase().startsWith("")) {
                    list.add("help");
                    list.add("reload");
                    list.add("settime");
                }
            }
        }
        
        return list;
    }
}
