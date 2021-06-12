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
import java.util.stream.Collectors;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/13/2021
 */
public class CleanCommand implements TabExecutor {


    @Override
    public boolean onCommand(final CommandSender commandSender,
                             final Command command,
                             final String s,
                             final String[] strings) {
        final Player player = (Player) commandSender;

        final List<Waypoint> waypoints = DataFileUtil.data;
        final List<Waypoint> validWaypoints = waypoints.stream()
            .filter(waypoint -> ValidatorUtil.isValidWaypointBlock(waypoint.getLocation().getBlock()))
            .collect(Collectors.toList());

        if (validWaypoints.size() < waypoints.size()) {
            DataFileUtil.data = validWaypoints;
            final int invalidCount = waypoints.size() - validWaypoints.size();
            MessageUtil.success(player, String.format("Removed %s invalid waypoints.", invalidCount));
        } else {
            MessageUtil.fail(player, "No invalid waypoints to remove.");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(final CommandSender commandSender,
                                      final Command command,
                                      final String s,
                                      final String[] strings) {

        return null;
    }
}
