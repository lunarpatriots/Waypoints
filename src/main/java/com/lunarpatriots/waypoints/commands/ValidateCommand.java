package com.lunarpatriots.waypoints.commands;

import com.lunarpatriots.waypoints.MainApp;
import com.lunarpatriots.waypoints.api.exceptions.DatabaseException;
import com.lunarpatriots.waypoints.api.model.Waypoint;
import com.lunarpatriots.waypoints.api.repository.WaypointRepository;
import com.lunarpatriots.waypoints.util.LogUtil;
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

    private final WaypointRepository repository;

    public ValidateCommand(final WaypointRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean onCommand(final CommandSender commandSender,
                             final Command command,
                             final String s,
                             final String[] strings) {
        final Player player = (Player) commandSender;

        try {
            final List<Waypoint> waypoints = repository.getWaypoints();

            final long count = waypoints.stream()
                .filter(waypoint -> ValidatorUtil.isValidWaypointBlock(waypoint.getLocation().getBlock()))
                .count();

            final String msg = String.format("%s/%s valid waypoints", count, waypoints.size());

            if (count == waypoints.size()) {
                MessageUtil.success(player, msg);
            } else {
                MessageUtil.fail(player, msg);
            }
        } catch (final DatabaseException ex) {
            LogUtil.error(ex.getMessage());
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
