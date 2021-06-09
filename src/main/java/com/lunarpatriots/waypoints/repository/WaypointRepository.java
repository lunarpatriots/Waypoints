package com.lunarpatriots.waypoints.repository;

import com.lunarpatriots.waypoints.MainApp;
import com.lunarpatriots.waypoints.model.Waypoint;
import com.lunarpatriots.waypoints.util.DbUtil;
import com.lunarpatriots.waypoints.util.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/08/2021
 */
public class WaypointRepository {

    private final MainApp plugin;

    public WaypointRepository(MainApp plugin) {
        this.plugin = plugin;
    }

    public List<Waypoint> getWaypoints(final String worldName) throws Exception {
        LogUtil.info("Retrieving waypoints...");
        try (final Connection dbCon = DbUtil.createConnection(plugin)) {
            final PreparedStatement statement = dbCon.prepareStatement("SELECT * FROM waypoints WHERE world = ?");
            statement.setString(1, worldName);
            final ResultSet resultSet = statement.executeQuery();

            final List<Waypoint> waypoints = new ArrayList<>();
            while(resultSet.next()) {
                final Waypoint waypoint = new Waypoint();
                waypoint.setUuid(resultSet.getString("uuid"));
                waypoint.setWorld(resultSet.getString("world"));
                waypoint.setName(resultSet.getString("name"));
                waypoint.setX(resultSet.getInt("x_coordinate"));
                waypoint.setY(resultSet.getInt("y_coordinate"));
                waypoint.setZ(resultSet.getInt("z_coordinate"));

                waypoints.add(waypoint);
            }

            statement.close();

            return waypoints;
        }
    }

    public void saveWaypoint(final Waypoint waypoint) throws Exception {
        LogUtil.info("Saving waypoint...");
        try (final Connection dbCon = DbUtil.createConnection(plugin)) {
            final PreparedStatement statement = dbCon.prepareStatement(
                "INSERT INTO waypoints(uuid, name, x_coordinate, y_coordinate, z_coordinate) VALUES (? ,?, ?, ?, ?, ?)");
            statement.setString(1, UUID.randomUUID().toString());
            statement.setString(2, waypoint.getName());
            statement.setString(3, waypoint.getWorld());
            statement.setInt(4, waypoint.getX());
            statement.setInt(5, waypoint.getY());
            statement.setInt(6, waypoint.getZ());

            statement.execute();
            statement.close();
        }
    }

    public void updateWaypoint(final Waypoint waypoint) throws Exception {
        LogUtil.info("Updating waypoint...");
        try (final Connection dbCon = DbUtil.createConnection(plugin)) {
            final PreparedStatement statement = dbCon.prepareStatement(
                "UPDATE waypoints set x_coordinate = ?, y_coordinate = ?, z_coordinate = ? WHERE uuid = ?");
            statement.setInt(1, waypoint.getX());
            statement.setInt(2, waypoint.getY());
            statement.setInt(3, waypoint.getZ());
            statement.setString(4, waypoint.getUuid());

            statement.execute();
            statement.close();
        }
    }

    public void deleteData(final String uuid) throws Exception {
        LogUtil.info("Deleting waypoint...");
        try (final Connection dbCon = DbUtil.createConnection(plugin)) {
            final PreparedStatement statement = dbCon.prepareStatement("DELETE FROM waypoints WHERE uuid = ?");
            statement.setString(1, uuid);

            statement.execute();
        }
    }
}
