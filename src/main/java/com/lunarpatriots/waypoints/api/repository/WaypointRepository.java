package com.lunarpatriots.waypoints.api.repository;

import com.lunarpatriots.waypoints.api.exceptions.DatabaseException;
import com.lunarpatriots.waypoints.api.model.Waypoint;

import java.util.List;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/15/2021
 */
public interface WaypointRepository {

    void initTable(String query) throws DatabaseException;

    List<Waypoint> getWaypoints() throws DatabaseException;

    List<Waypoint> filterWaypoints(String world) throws DatabaseException;

    List<Waypoint> filterWaypointsPerPlayer(String userUuid, String world) throws DatabaseException;

    int saveWaypoint(Waypoint waypoint) throws DatabaseException;

    int saveReference(String uuid, String userUuid, String waypointUuid) throws DatabaseException;

    int updateWaypoint(Waypoint waypoint) throws DatabaseException;

    int deleteWaypoint(String uuid) throws DatabaseException;
}
