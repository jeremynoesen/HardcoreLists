package xyz.jeremynoesen.hardcorelists.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * tab completer for the hardcorelists command
 *
 * @author Jeremy Noesen
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
            if (label.equalsIgnoreCase("hardcorelists") || label.equalsIgnoreCase("hl")) {
                if (args.length == 1) {
                    if (args[0].toLowerCase().startsWith("h") && player.hasPermission("hardcorelists.help"))
                        list.add("help");
                    else if (args[0].toLowerCase().startsWith("l") && (player.hasPermission("hardcorelists.list.dead") ||
                            player.hasPermission("hardcorelists.list.alive"))) list.add("list");
                    else if (args[0].toLowerCase().startsWith("r") && player.hasPermission("hardcorelists.reload"))
                        list.add("reload");
                    else if (args[0].toLowerCase().startsWith("t") && player.hasPermission("hardcorelists.timer"))
                        list.add("timer");
                    else if (args[0].equalsIgnoreCase("")) {
                        if (player.hasPermission("hardcorelists.help")) list.add("help");
                        if (player.hasPermission("hardcorelists.list.dead") ||
                                player.hasPermission("hardcorelists.list.alive")) list.add("list");
                        if (player.hasPermission("hardcorelists.reload")) list.add("reload");
                        if (player.hasPermission("hardcorelists.timer")) list.add("timer");
                    }
                } else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("list")) {
                        if (args[1].toLowerCase().startsWith("d") && player.hasPermission("hardcorelists.list.dead"))
                            list.add("dead");
                        else if (args[1].toLowerCase().startsWith("a") && player.hasPermission("hardcorelists.list.alive"))
                            list.add("alive");
                        else if (args[1].equalsIgnoreCase("")) {
                            if (player.hasPermission("hardcorelists.list.alive")) list.add("alive");
                            if (player.hasPermission("hardcorelists.list.dead")) list.add("dead");
                        }
                    }
                }
            }
        }

        return list;
    }
}
