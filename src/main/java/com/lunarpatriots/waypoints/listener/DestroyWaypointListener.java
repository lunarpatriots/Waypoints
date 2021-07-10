package com.lunarpatriots.waypoints.listener;

import com.lunarpatriots.waypoints.api.exceptions.DatabaseException;
import com.lunarpatriots.waypoints.api.model.Waypoint;
import com.lunarpatriots.waypoints.api.repository.WaypointRepository;
import com.lunarpatriots.waypoints.util.LogUtil;
import com.lunarpatriots.waypoints.util.ValidatorUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/16/2021
 */
public final class DestroyWaypointListener implements Listener {

    private final WaypointRepository repository;

    public DestroyWaypointListener(final WaypointRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void onWaypointBreak(final BlockBreakEvent event) {
        removeWaypoint(event.getBlock());
    }

    @EventHandler
    public void onWaypointBurn(final BlockBurnEvent event) {
        removeWaypoint(event.getBlock());
    }

    @EventHandler
    public void onWaypointExplode(final EntityExplodeEvent event) {
        final List<Block> blockList = event.blockList();
        blockList.forEach(this::removeWaypoint);
    }

    private void removeWaypoint(final Block targetBlock) {
        if (ValidatorUtil.isValidWaypointBlock(targetBlock)) {
            final Sign sign = (Sign) targetBlock.getState();
            final Waypoint waypoint = new Waypoint(targetBlock.getWorld().getName(), sign);

            try {
                final String uuid = getExistingUuid(waypoint);

                if (StringUtils.isNotBlank(uuid)) {
                    repository.deleteWaypoint(uuid);
                }
            } catch (final Exception ex) {
                LogUtil.error(ex.getMessage());
            }
        }
    }

    private String getExistingUuid(final Waypoint waypoint) throws DatabaseException {
        final String name = waypoint.getName();
        final Location location = waypoint.getLocation();
        final String world = waypoint.getWorld();

        final List<Waypoint> waypoints = repository.getWaypoints();
        return waypoints.stream()
            .filter(existing -> name.equals(existing.getName())
                && location.equals(existing.getLocation()) && world.equals(existing.getWorld()))
            .findFirst()
            .map(Waypoint::getUuid)
            .orElse(null);
    }
}
