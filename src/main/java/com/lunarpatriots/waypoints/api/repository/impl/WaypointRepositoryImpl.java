package com.lunarpatriots.waypoints.api.repository.impl;

import com.lunarpatriots.waypoints.MainApp;
import com.lunarpatriots.waypoints.api.constants.SqlConstants;
import com.lunarpatriots.waypoints.api.exceptions.DatabaseException;
import com.lunarpatriots.waypoints.api.model.Waypoint;
import com.lunarpatriots.waypoints.api.repository.WaypointRepository;
import com.lunarpatriots.waypoints.api.util.SqlUtil;
import com.lunarpatriots.waypoints.util.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/16/2021
 */
public final class WaypointRepositoryImpl implements WaypointRepository {

    private final MainApp plugin;

    public WaypointRepositoryImpl(final MainApp plugin) {
        this.plugin = plugin;
    }

    @Override
    public void initTable(final String query) throws DatabaseException {
        try (Connection connection = SqlUtil.getConnection(plugin);
             PreparedStatement preparedStatement = SqlUtil
                 .buildPreparedStatement(connection, query)) {

            preparedStatement.executeUpdate();
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }

    @Override
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

    @Override
    public List<Waypoint> filterWaypoints(final String world) throws DatabaseException {
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

    @Override
    public List<Waypoint> filterWaypointsPerPlayer(final String userUuid, final String world) throws DatabaseException {
        try (Connection connection = SqlUtil.getConnection(plugin);
             PreparedStatement preparedStatement = SqlUtil
                 .buildPreparedStatement(connection, SqlConstants.GET_FILTERERD_PER_PLAYER_QUERY, userUuid, world);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            return SqlUtil.readResult(resultSet);
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }

    @Override
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

    @Override
    public int saveReference(final String uuid,
                             final String userUuid,
                             final String waypointUuid) throws DatabaseException {
        try (Connection connection = SqlUtil.getConnection(plugin);
             PreparedStatement preparedStatement = SqlUtil.buildPreparedStatement(
                 connection, SqlConstants.CREATE_REFERENCE_QUERY, uuid, userUuid, waypointUuid)) {

            return preparedStatement.executeUpdate();
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }

    @Override
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

    @Override
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
