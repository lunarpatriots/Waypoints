package com.lunarpatriots.waypoints.util;

import com.lunarpatriots.waypoints.constants.Constants;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;

import java.util.Optional;

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
}
