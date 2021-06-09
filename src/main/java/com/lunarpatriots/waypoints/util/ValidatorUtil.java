package com.lunarpatriots.waypoints.util;

import com.lunarpatriots.waypoints.model.Waypoint;
import com.lunarpatriots.waypoints.repository.WaypointRepository;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created By: tristan.hamili@novare.com.hk
 * Date created: 06/09/2021
 */
public class ValidatorUtil {

    private ValidatorUtil() {
    }

    public static boolean validateWaypointPrefix(final Sign sign, final String waypointSignPrefix) {
        return sign.getLine(0).equals(waypointSignPrefix);
    }


    public static boolean validateTriggeredWaypoint(final PlayerInteractEvent event, final Block targetBlock) {
        return Action.RIGHT_CLICK_BLOCK == event.getAction()
            && Optional.ofNullable(targetBlock).isPresent()
            && Tag.SIGNS.isTagged(targetBlock.getType());
    }

    public static void validateInteractedWaypoint(final WaypointRepository repository,
                                                  final List<Waypoint> waypoints,
                                                  final String name,
                                                  final int x,
                                                  final int y,
                                                  final int z) throws Exception {
        final Waypoint waypointInput = new Waypoint();
        waypointInput.setName(name);
        waypointInput.setX(x);
        waypointInput.setY(y);
        waypointInput.setZ(z);

        final Optional<Waypoint> existingWaypoint = waypoints.stream()
            .filter(waypoint -> waypoint.getName().equals(name))
            .findFirst();

        if (existingWaypoint.isPresent()) {
            waypointInput.setUuid(existingWaypoint.get().getUuid());

            if (waypoints.stream()
                .noneMatch(waypoint -> waypoint.getX() == x
                    && waypoint.getY() == y
                    && waypoint.getZ() == z)) {
                LogUtil.info("Waypoint coordinates have been changed!");
                repository.updateWaypoint(waypointInput);
            } else {
                LogUtil.info("Waypoint is already registered!");
            }
        } else {
            LogUtil.info("New waypoint found!");
            waypointInput.setUuid(UUID.randomUUID().toString());
            repository.saveWaypoint(waypointInput);
        }
    }

    public static int computeFastTravelPenalty(final Player player,
                                               final double costPerBlock,
                                               final int minBlockDistance,
                                               final Location destination) {
        final Location playerLocation = player.getLocation();
        final int distance = (int) Math.round(playerLocation.distance(destination));

        LogUtil.info("Distance to travel: " + distance);

        if (distance > minBlockDistance) {
            LogUtil.info("Penalty: " + (distance - minBlockDistance) * costPerBlock);
        }

        return (int) Math.round(distance <= minBlockDistance ? 0 : (distance - minBlockDistance) * costPerBlock);
    }
}
