package com.lunarpatriots.waypoints.commands;

import com.lunarpatriots.waypoints.model.Waypoint;
import com.lunarpatriots.waypoints.repository.WaypointRepository;
import com.lunarpatriots.waypoints.util.DataFileUtil;
import com.lunarpatriots.waypoints.util.LogUtil;
import com.lunarpatriots.waypoints.util.MessageUtil;
import com.lunarpatriots.waypoints.util.ValidatorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created By: theLANister Date created: 06/13/2021
 */

public class ListCommand implements TabExecutor {

    @Override
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s,
            final String[] strings) {
        final Player player = (Player) commandSender;
        final WaypointRepository waypointrepo = new WaypointRepository();
        String msg = "";
        String waypoints = "";
        switch (strings[0]) {
            case ("overworld"):
                final List<Waypoint> waypointOver = waypointrepo.getWaypoints(player.getWorld().getName());

                waypoints = waypointOver.stream().map(Waypoint::getName).collect(Collectors.joining("\n"));

                if (waypoints.length() != 0) {
                    msg = ChatColor.AQUA + "Overworld " + ChatColor.GREEN + "Waypoints: \n";
                    msg += String.format("- %s", waypoints);
                    MessageUtil.success(player, msg);
                } else {
                    msg += "There are no waypoints available for this region!";
                    MessageUtil.fail(player, msg);
                }
                break;
            case ("nether"):
                final List<Waypoint> waypointNether = waypointrepo
                        .getWaypoints(player.getWorld().getName() + "_nether");
                waypoints = waypointNether.stream().map(Waypoint::getName).collect(Collectors.joining("\n"));

                if (waypoints.length() != 0) {
                    msg = ChatColor.RED + "Nether " + ChatColor.GREEN + "Waypoints: \n";
                    msg += String.format("- %s", waypoints);
                    MessageUtil.success(player, msg);
                } else {
                    msg += "There are no waypoints available for this region!";
                    MessageUtil.fail(player, msg);
                }

                MessageUtil.success(player, msg);
                break;
            case ("end"):
                final List<Waypoint> waypointEnd = waypointrepo.getWaypoints(player.getWorld().getName() + "_the_end");
                ;

                waypoints = waypointEnd.stream().map(Waypoint::getName).collect(Collectors.joining("\n"));
                if (waypoints.length() != 0) {
                    msg = ChatColor.LIGHT_PURPLE + "End " + ChatColor.GREEN + "Waypoints: \n";
                    msg += String.format("- %s", waypoints);

                    MessageUtil.success(player, msg);
                } else {
                    msg += "There are no waypoints available for this region!";
                    MessageUtil.fail(player, msg);
                }

                break;
            default:
                MessageUtil.fail(player, "Invalid use of /list");
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(final CommandSender commandSender, final Command command, final String s,
            final String[] strings) {

        return null;
    }
}
