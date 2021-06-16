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
public final class WaypointRepository {

    private final MainApp plugin;

    public WaypointRepository(final MainApp plugin) {
        this.plugin = plugin;
    }

    public void initTable() throws DatabaseException {
        try (Connection connection = SqlUtil.getConnection(plugin);
             PreparedStatement preparedStatement = SqlUtil
                 .buildPreparedStatement(connection, SqlConstants.CREATE_TABLE_QUERY)) {

            preparedStatement.executeUpdate();
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }

    public List<Waypoint> getWaypoints() throws DatabaseException {
        try (Connection connection = SqlUtil.getConnection(plugin);
             PreparedStatement preparedStatement = SqlUtil
                 .buildPreparedStatement(connection, SqlConstants.GET_QUERY);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            return SqlUtil.readResult(resultSet);
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }

    public List<Waypoint> getWaypoints(final String world) throws DatabaseException {
        try (Connection connection = SqlUtil.getConnection(plugin);
             PreparedStatement preparedStatement = SqlUtil
                .buildPreparedStatement(connection, SqlConstants.GET_FILTERED_QUERY, world);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            return SqlUtil.readResult(resultSet);
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }

    public int saveWaypoint(final Waypoint waypoint) throws DatabaseException {
        try (Connection connection = SqlUtil.getConnection(plugin);
             PreparedStatement preparedStatement = SqlUtil
                 .buildPreparedStatement(connection, SqlConstants.INSERT_QUERY, waypoint)) {

            return preparedStatement.executeUpdate();
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }

    public int updateWaypoint(final Waypoint waypoint) throws DatabaseException {
        try (Connection connection = SqlUtil.getConnection(plugin);
             PreparedStatement preparedStatement = SqlUtil
                 .buildPreparedStatement(
                     connection,
                     SqlConstants.UPDATE_QUERY,
                     waypoint.getXCoordinate(),
                     waypoint.getYCoordinate(),
                     waypoint.getZCoordinate(),
                     waypoint.getUuid())) {

            return preparedStatement.executeUpdate();
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }

    public int deleteWaypoint(final String uuid) throws DatabaseException {
        try (Connection connection = SqlUtil.getConnection(plugin);
             PreparedStatement preparedStatement = SqlUtil
                 .buildPreparedStatement(
                     connection,
                     SqlConstants.DELETE_QUERY,
                     uuid)) {

            return preparedStatement.executeUpdate();
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }
}
