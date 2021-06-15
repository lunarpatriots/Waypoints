package com.lunarpatriots.waypoints.api.repository;

import com.lunarpatriots.waypoints.MainApp;
import com.lunarpatriots.waypoints.api.constants.SqlConstants;
import com.lunarpatriots.waypoints.api.exceptions.DatabaseException;
import com.lunarpatriots.waypoints.api.model.Waypoint;
import com.lunarpatriots.waypoints.api.util.SqlUtil;
import com.lunarpatriots.waypoints.util.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/15/2021
 */
public class WaypointRepository {

    final MainApp plugin;

    public WaypointRepository(final MainApp plugin) {
        this.plugin = plugin;
    }

    public List<Waypoint> getWaypoints() throws DatabaseException {
        try (final Connection connection = SqlUtil.getConnection(plugin);
             final PreparedStatement preparedStatement = SqlUtil
                 .buildPreparedStatement(connection, SqlConstants.GET_QUERY);
             final ResultSet resultSet = preparedStatement.executeQuery()) {

            return SqlUtil.readResult(resultSet);
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }

    public List<Waypoint> getWaypoints(final String world) throws DatabaseException {
        try (final Connection connection = SqlUtil.getConnection(plugin);
             final PreparedStatement preparedStatement = SqlUtil
                .buildPreparedStatement(connection, SqlConstants.GET_FILTERED_QUERY, world);
             final ResultSet resultSet = preparedStatement.executeQuery()) {

            return SqlUtil.readResult(resultSet);
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }

    public void saveWaypoint(final Waypoint waypoint) throws DatabaseException {
        try (final Connection connection = SqlUtil.getConnection(plugin);
             final PreparedStatement preparedStatement = SqlUtil
                 .buildPreparedStatement(
                     connection,
                     SqlConstants.INSERT_QUERY,
                     waypoint.getUuid(),
                     waypoint.getName(),
                     waypoint.getWorld(),
                     waypoint.getX(),
                     waypoint.getY(),
                     waypoint.getZ())) {

            final int rows = preparedStatement.executeUpdate();

            LogUtil.info("Query executed with " + rows + " rows affected.");
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }

    public void updateWaypoint(final Waypoint waypoint) throws DatabaseException {
        try (final Connection connection = SqlUtil.getConnection(plugin);
             final PreparedStatement preparedStatement = SqlUtil
                 .buildPreparedStatement(
                     connection,
                     SqlConstants.UPDATE_QUERY,
                     waypoint.getX(),
                     waypoint.getY(),
                     waypoint.getZ(),
                     waypoint.getUuid())) {

            final int rows = preparedStatement.executeUpdate();

            LogUtil.info("Query executed with " + rows + " rows affected.");
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }

    public void deleteWaypoint(final String uuid) throws DatabaseException {
        try (final Connection connection = SqlUtil.getConnection(plugin);
             final PreparedStatement preparedStatement = SqlUtil
                 .buildPreparedStatement(
                     connection,
                     SqlConstants.DELETE_QUERY,
                     uuid)) {

            final int rows = preparedStatement.executeUpdate();

            LogUtil.info("Query executed with " + rows + " rows affected.");
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }
}
