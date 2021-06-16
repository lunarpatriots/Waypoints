package com.lunarpatriots.waypoints.commands;

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
import java.util.stream.Collectors;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/13/2021
 */
public final class CleanCommand implements TabExecutor {

    private final WaypointRepository repository;

    public CleanCommand(final WaypointRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean onCommand(final CommandSender commandSender,
                             final Command command,
                             final String string,
                             final String[] strings) {
        final Player player = (Player) commandSender;

        try {
            final List<Waypoint> waypoints = repository.getWaypoints();
            final List<String> uuids = waypoints.stream()
                .filter(waypoint -> !ValidatorUtil.isValidWaypointBlock(waypoint.getLocation().getBlock()))
                .map(Waypoint::getUuid)
                .collect(Collectors.toList());

            if (!uuids.isEmpty()) {
                for (final String uuid : uuids) {
                    repository.deleteWaypoint(uuid);
                }
                MessageUtil.success(player, String.format("Removed %s invalid waypoints.", uuids.size()));
            } else {
                MessageUtil.fail(player, "No invalid waypoints to remove.");
            }
        } catch (final DatabaseException ex) {
            LogUtil.error(ex.getMessage());
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(final CommandSender commandSender,
                                      final Command command,
                                      final String string,
                                      final String[] strings) {

        return null;
    }
}
