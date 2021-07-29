package com.lunarpatriots.waypoints.commands;

import com.lunarpatriots.waypoints.api.exceptions.DatabaseException;
import com.lunarpatriots.waypoints.api.model.Waypoint;
import com.lunarpatriots.waypoints.api.repository.WaypointRepository;
import com.lunarpatriots.waypoints.util.LogUtil;
import com.lunarpatriots.waypoints.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 07/29/2021
 */
public final class GiveCommand implements TabExecutor {

    private final WaypointRepository repository;

    public GiveCommand(final WaypointRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean onCommand(final CommandSender commandSender,
                             final Command command,
                             final String string,
                             final String[] strings) {

        final Player execPlayer = (Player) commandSender;

        try {
            if (strings.length == 2) {
                final String playerName = strings[0];
                final String waypointName = strings[1];
                final Player player = Bukkit.getPlayerExact(playerName);
                final List<Waypoint> waypoints = repository.getWaypoint(waypointName);

                if (validateParams(execPlayer, player, waypoints)) {
                    final String playerUuid = player.getUniqueId().toString();
                    final String waypointUuid = waypoints.get(0).getUuid();

                    giveWaypointToPlayer(execPlayer, playerUuid, waypointName, waypointUuid);
                }
            }
        } catch (final DatabaseException ex) {
            LogUtil.error(ex.getMessage());

        }

        return false;
    }

    @Override
    public List<String> onTabComplete(final CommandSender commandSender,
                                      final Command command,
                                      final String string,
                                      final String[] strings) {
        return null;
    }

    private boolean validateParams(final Player execPlayer, final Player player, final List<Waypoint> waypoints) {
        boolean isValid = true;

        if (!Optional.ofNullable(player).isPresent()) {
            MessageUtil.fail(execPlayer, "Player does not exist!");
            isValid = false;
        } else if (waypoints.isEmpty()) {
            MessageUtil.fail(execPlayer, "Waypoint does not exist!");
            isValid = false;
        }

        return isValid;
    }

    private void giveWaypointToPlayer(final Player execPlayer,
                                      final String playerUuid,
                                      final String waypointName,
                                      final String waypointUuid) {
        try {
            LogUtil.info(playerUuid);
            final List<Waypoint> playerWaypoints = repository.filterWaypointsPerPlayer(playerUuid);
            final boolean isActive = playerWaypoints
                .stream()
                .anyMatch(waypoint -> waypoint.getName().equals(waypointName));

            if (isActive) {
                MessageUtil.fail(execPlayer, "Player already knows this waypoint!");
            } else {
                repository.saveReference(UUID.randomUUID().toString(), playerUuid, waypointUuid);
                MessageUtil.success(execPlayer, "Waypoint given to player!");
            }
        } catch (final DatabaseException ex) {
            LogUtil.error(ex.getMessage());
            MessageUtil.fail(execPlayer, "Failed to give waypoint!");
        }
    }
}
