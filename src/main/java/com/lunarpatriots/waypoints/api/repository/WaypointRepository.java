package com.lunarpatriots.waypoints.api.repository;

import com.lunarpatriots.waypoints.api.exceptions.DatabaseException;
import com.lunarpatriots.waypoints.api.model.Waypoint;

import java.util.List;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/15/2021
 */
public interface WaypointRepository {

    void initTable() throws DatabaseException;

    List<Waypoint> getWaypoints() throws DatabaseException;

    List<Waypoint> filterWaypoints(String world) throws DatabaseException;

    int saveWaypoint(Waypoint waypoint) throws DatabaseException;

    int updateWaypoint(Waypoint waypoint) throws DatabaseException;

    int deleteWaypoint(String uuid) throws DatabaseException;
}
