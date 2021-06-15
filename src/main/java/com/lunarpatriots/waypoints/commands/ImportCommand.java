package com.lunarpatriots.waypoints.commands;

import com.lunarpatriots.waypoints.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created By: tristan.hamili@novare.com.hk
 * Date created: 06/15/2021
 */
public class ImportCommand implements TabExecutor {

    @Override
    public boolean onCommand(final CommandSender commandSender,
                             final Command command,
                             final String s,
                             final String[] strings) {
        final Player player = (Player) commandSender;

        if (strings.length == 0) {
            MessageUtil.fail(player, "Incorrect usage!");
        } else {
            // final File file = DataFileUtil.getDataFile();
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(final CommandSender commandSender, final Command command, final String s, final String[] strings) {
        return null;
    }
}
