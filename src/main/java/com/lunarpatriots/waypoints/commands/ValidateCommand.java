package com.lunarpatriots.waypoints.commands;

import com.lunarpatriots.waypoints.model.Waypoint;
import com.lunarpatriots.waypoints.util.DataFileUtil;
import com.lunarpatriots.waypoints.util.MessageUtil;
import com.lunarpatriots.waypoints.util.ValidatorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/12/2021
 */
public class ValidateCommand implements TabExecutor {

    @Override
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] strings) {
        final Player player = (Player) commandSender;

        final List<Waypoint> waypoints = DataFileUtil.data;
        final long count = waypoints.stream()
            .filter(waypoint -> ValidatorUtil.isValidWaypointBlock(waypoint.getLocation().getBlock()))
            .count();

        final String msg = String.format("%s/%s valid waypoints", count, waypoints.size());

        if (count == waypoints.size()) {
            MessageUtil.success(player, msg);
        } else {
            MessageUtil.fail(player, msg);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(final CommandSender commandSender,
                                      final Command command,
                                      final String s, final String[] strings) {

        return null;
    }
}
