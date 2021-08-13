package com.lunarpatriots.waypoints.api.util;

import com.lunarpatriots.waypoints.MainApp;
import com.lunarpatriots.waypoints.api.constants.SqlConstants;
import com.lunarpatriots.waypoints.api.exceptions.DatabaseException;
import com.lunarpatriots.waypoints.api.model.Waypoint;
import com.lunarpatriots.waypoints.util.ConfigUtil;
import com.lunarpatriots.waypoints.util.LogUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/14/2021
 */
public final class SqlUtil {

    private SqlUtil() {
    }

    /**
     * Creates an instance of <code>Connection</code>
     * @param plugin Instance of <code>MainApp</code>
     * @return Instance of <code>Connection</code>
     * @throws DatabaseException thrown when driver class is not found, database file is not found, or connection
     * cannot be established
     */
    public static Connection getConnection(final MainApp plugin) throws DatabaseException {
        try {
            Class.forName(SqlConstants.JDBC_DRIVER_CLASS_NAME);
            final String host = ConfigUtil.getString(plugin, "host", "");
            final String port = ConfigUtil.getString(plugin, "port", "");
            final String database = ConfigUtil.getString(plugin, "database", "");
            final String username = ConfigUtil.getString(plugin, "username", "");
            final String password = ConfigUtil.getString(plugin, "password", "");

            final String url = String.format(SqlConstants.URL, host, port, database);

            return DriverManager.getConnection(url, username, password);
        } catch (final ClassNotFoundException ex) {
            LogUtil.error(ex.getMessage());
            throw new DatabaseException("Database driver not found!", ex);
        } catch (final SQLException ex) {
            LogUtil.error(ex.getMessage());
            throw new DatabaseException("Database connection could not be established!", ex);
        }
    }

    /**
     * Creates an instance of <code>PreparedStatement</code> with no parameters
     * @param connection Database connection
     * @param query SQL statement to be used
     * @return Instance of <code>PreparedStatement</code>
     * @throws DatabaseException - thrown when database access error occurs, statement is called on a closed connection,
     *  or there is an issue with the sql statement used
     */
    public static PreparedStatement buildPreparedStatement(final Connection connection,
                                                           final String query) throws DatabaseException {

        try {
            return connection.prepareStatement(query);
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }

    /**
     * Creates an instance of <code>PreparedStatement</code> with one parameter
     * @param connection Database connection
     * @param query SQL statement to be used
     * @param stringParam Parameter at index 1 of type <code>String</code>
     * @return Instance of <code>PreparedStatement</code>
     * @throws DatabaseException - thrown when database access error occurs, statement is called on a closed connection,
     *  or there is an issue with the sql statement used
     */
    public static PreparedStatement buildPreparedStatement(final Connection connection,
                                                      final String query,
                                                      final String stringParam) throws DatabaseException {
        try {
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, stringParam);

            return statement;
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }

    /**
     * Creates an instance of <code>PreparedStatement</code> with two parameters
     * @param connection Database connection
     * @param query SQL statement to be used
     * @param userUuid Parameter at index 1 of type <code>String</code>
     * @param world Parameter at index 2 of type <code>String</code>
     * @return Instance of <code>PreparedStatement</code>
     * @throws DatabaseException - thrown when database access error occurs, statement is called on a closed connection,
     *  or there is an issue with the sql statement used
     */
    public static PreparedStatement buildPreparedStatement(final Connection connection,
                                                           final String query,
                                                           final String userUuid,
                                                           final String world) throws DatabaseException {
        try {
            final PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userUuid);
            statement.setString(2, world);

            return statement;
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }

    /**
     * Creates an instance of <code>PreparedStatement</code> wtih five parameters
     * @param connection Database connection
     * @param query SQL statement to be used
     * @param uuid Parameter at index 1 of type <code>String</code>
     * @param userUuid Parameter at index 2 of type <code>String</code>
     * @param waypointUuid Parameter at index 3 of type <code>String</code>
     * @return Instance of <code>PreparedStatement</code>
     * @throws DatabaseException - thrown when database access error occurs, statement is called on a closed connection,
     *  or there is an issue with the sql statement used
     */
    public static PreparedStatement buildPreparedStatement(final Connection connection,
                                                           final String query,
                                                           final String uuid,
                                                           final String userUuid,
                                                           final String waypointUuid) throws DatabaseException {
        try {
            final PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, uuid);
            statement.setString(2, userUuid);
            statement.setString(3, waypointUuid);

            return statement;
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }

    /**
     * Creates an instance of <code>PreparedStatement</code> with a <code>Waypoint</code> parameter
     * @param connection Database connection
     * @param query SQL statement to be used
     * @param waypoint Instance of <code>Waypoint</code>
     * @return Instance of <code>PreparedStatement</code>
     * @throws DatabaseException Thrown when database access error occurs, statement is called on a closed connection,
     *  or there is an issue with the sql statement used
     */
    public static PreparedStatement buildPreparedStatement(final Connection connection,
                                                           final String query,
                                                           final Waypoint waypoint) throws DatabaseException {

        try {
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, waypoint.getUuid());
            statement.setString(2, waypoint.getName());
            statement.setString(3, waypoint.getWorld());
            statement.setInt(4, waypoint.getXCoordinate());
            statement.setInt(5, waypoint.getYCoordinate());
            statement.setInt(6, waypoint.getZCoordinate());

            return statement;
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }

    /**
     * Creates an instance of <code>PreparedStatement</code> with 4 parameters.
     * @param connection Database connection
     * @param query SQL statement to be used
     * @param firstIntParam Parameter at index 1 of type <code>int</code>
     * @param secondIntParam Parameter at index 2 of type <code>int</code>
     * @param thirdIntParma Parameter at index 3 of type <code>int</code>
     * @param stringParam Parameter at index 4 of type <code>String</code>
     * @return Instance of <code>PreparedStatement</code>
     * @throws DatabaseException Thrown when database access error occurs, statement is called on a closed connection,
     *  or there is an issue with the sql statement used
     */
    public static PreparedStatement buildPreparedStatement(final Connection connection,
                                                            final String query,
                                                            final int firstIntParam,
                                                            final int secondIntParam,
                                                            final int thirdIntParma,
                                                            final String stringParam) throws DatabaseException {
        try {
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, firstIntParam);
            statement.setInt(2, secondIntParam);
            statement.setInt(3, thirdIntParma);
            statement.setString(4, stringParam);

            return statement;
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }

    /**
     * Reads the <code>ResultSet</code> retrieved from query and converts it to a list of <code>Waypoint</code>
     * @param resultSet Instance of <code>ResultSet</code> retrieved from SQL execution
     * @return List of <code>Waypoint</code>
     * @throws DatabaseException Thrown when database access error occurs or <code>ResultSet</code> is closed
     */
    public static List<Waypoint> readResult(final ResultSet resultSet) throws DatabaseException {
        try {
            final List<Waypoint> waypoints = new ArrayList<>();
            while (resultSet.next()) {
                final Waypoint waypoint = resultSetToObj(resultSet);
                waypoints.add(waypoint);
            }

            return waypoints;
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }

    }

    /**
     * Converts an instance of <code>ResultSet</code> to a <code>Waypoint</code> object
     * @param resultSet Instance of <code>ResultSet</code> retrieved from database
     * @return Instance of <code>Waypoint</code>
     * @throws DatabaseException Thrown when the column does not exist, database access error occurs,
     * or <code>ResultSet </code> is closed.
     */
    private static Waypoint resultSetToObj(final ResultSet resultSet) throws DatabaseException {
        try {
            final Waypoint waypoint = new Waypoint();
            waypoint.setUuid(resultSet.getString("uuid"));
            waypoint.setName(resultSet.getString("name"));
            waypoint.setWorld(resultSet.getString("world"));
            waypoint.setXCoordinate(resultSet.getInt("x_coordinate"));
            waypoint.setYCoordinate(resultSet.getInt("y_coordinate"));
            waypoint.setZCoordinate(resultSet.getInt("z_coordinate"));

            return waypoint;
        } catch (final SQLException ex) {
            LogUtil.error("Could not extract result set.");
            throw new DatabaseException("SQL Error: " + ex.getErrorCode(), ex);
        }
    }
}
