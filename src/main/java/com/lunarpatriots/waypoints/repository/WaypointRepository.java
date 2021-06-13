package com.lunarpatriots.waypoints.repository;

import com.lunarpatriots.waypoints.model.Waypoint;
import com.lunarpatriots.waypoints.util.DataFileUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/08/2021
 */
public class WaypointRepository {

    public List<Waypoint> getWaypoints(final String worldName) {
        final List<Waypoint> waypoints = DataFileUtil.data;

        return waypoints.stream()
            .filter(waypoint -> worldName.equals(waypoint.getWorld()))
            .collect(Collectors.toList());
    }

    public void saveWaypoint(final Waypoint waypoint) {
        DataFileUtil.data.add(waypoint);
    }

    public void updateWaypoint(final Waypoint waypoint) {
        final List<Waypoint> waypoints = DataFileUtil.data;

        DataFileUtil.data.stream()
            .filter(current -> waypoint.getUuid().equals(current.getUuid()))
            .findFirst()
            .ifPresent(current -> {
                final int index = waypoints.indexOf(current);
                DataFileUtil.data.set(index, waypoint);
            });
    }

    public void deleteWaypoint(final Waypoint waypoint) {
        DataFileUtil.data.removeIf(currentWaypoint -> waypoint.getUuid().equals(currentWaypoint.getUuid()));
    }
}
