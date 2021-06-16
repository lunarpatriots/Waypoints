package com.lunarpatriots.waypoints.util;

import com.lunarpatriots.waypoints.api.model.Waypoint;
import com.lunarpatriots.waypoints.api.repository.WaypointRepository;
import com.lunarpatriots.waypoints.constants.Constants;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/09/2021
 */
public final class ValidatorUtil {

    private ValidatorUtil() {
    }

    /**
     * Checks if block is a valid waypoint block
     * @param targetBlock Block targeted by event
     * @return If target is a valid waypoint block
     */
    public static boolean isValidWaypointBlock(final Block targetBlock) {
        return Optional.ofNullable(targetBlock).isPresent()
            && Tag.SIGNS.isTagged(targetBlock.getType())
            && Constants.WAYPOINT_PREFIX.equals(((Sign) targetBlock.getState()).getLine(0))
            && StringUtils.isNotBlank(((Sign) targetBlock.getState()).getLine(1));
    }

    public static void removeInvalidWaypoints(final List<Waypoint> waypoints, final WaypointRepository repository) {
        final List<String> invalidUuids = waypoints.stream()
            .filter(waypoint -> !isValidWaypointBlock(waypoint.getLocation().getBlock()))
            .map(Waypoint::getUuid)
            .collect(Collectors.toList());

        if (invalidUuids.size() > 0) {
            try {
                for (final String uuid : invalidUuids) {
                    repository.deleteWaypoint(uuid);
                }
            } catch (final Exception ex) {
                LogUtil.error(ex.getMessage());
            }
        }
    }
}
