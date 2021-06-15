package com.lunarpatriots.waypoints.api.util;

import com.lunarpatriots.waypoints.MainApp;
import com.lunarpatriots.waypoints.api.constants.SqlConstants;
import com.lunarpatriots.waypoints.api.exceptions.DatabaseException;
import com.lunarpatriots.waypoints.api.model.Waypoint;
import com.lunarpatriots.waypoints.exceptions.DataFileException;
import com.lunarpatriots.waypoints.util.DataFileUtil;
import com.lunarpatriots.waypoints.util.LogUtil;

import java.io.File;
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
public class SqlUtil {

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
            final String filename = plugin.getConfig().getString("filename");
            final File dbFile = DataFileUtil.getDataFile(plugin, filename);

            return DriverManager.getConnection(String.format(SqlConstants.URL, dbFile));
        } catch (final DataFileException ex) {
            LogUtil.error(ex.getMessage());
            throw new DatabaseException("Database file not found!", ex);
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
     * @param param1 parameter at index 1 of type <code>String</code>
     * @return Instance of <code>PreparedStatement</code>
     * @throws DatabaseException - thrown when database access error occurs, statement is called on a closed connection,
     *  or there is an issue with the sql statement used
     */
    public static PreparedStatement buildPreparedStatement(final Connection connection,
                                                      final String query,
                                                      final String param1) throws DatabaseException {
        try {
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, param1);

            return statement;
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }

    /**
     * Creates an instance of <code>PreparedStatement</code> with 6 parameters
     * @param connection Database connection
     * @param query SQL statement to be used
     * @param param1 Parameter at index 1 of type <code>String</code>
     * @param param2 Parameter at index 2 of type <code>String</code>
     * @param param3 Parameter at index 3 of type <code>String</code>
     * @param param4 Parameter at index 4 of type <code>String</code>
     * @param param5 Parameter at index 5 of type <code>String</code>
     * @param param6 Parameter at index 6 of type <code>String</code>
     * @return Instance of <code>PreparedStatement</code>
     * @throws DatabaseException Thrown when database access error occurs, statement is called on a closed connection,
     *  or there is an issue with the sql statement used
     */
    public static PreparedStatement buildPreparedStatement(final Connection connection,
                                                           final String query,
                                                           final String param1,
                                                           final String param2,
                                                           final String param3,
                                                           final int param4,
                                                           final int param5,
                                                           final int param6) throws DatabaseException {

        try {
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, param1);
            statement.setString(2, param2);
            statement.setString(3, param3);
            statement.setInt(4, param4);
            statement.setInt(5, param5);
            statement.setInt(6, param6);

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
     * @param param1 Parameter at index 1 of type <code>int</code>
     * @param param2 Parameter at index 2 of type <code>int</code>
     * @param param3 Parameter at index 3 of type <code>int</code>
     * @param param4 Parameter at index 4 of type <code>String</code>
     * @return Instance of <code>PreparedStatement</code>
     * @throws DatabaseException Thrown when database access error occurs, statement is called on a closed connection,
     *  or there is an issue with the sql statement used
     */
    public static PreparedStatement buildPreparedStatement(final Connection connection,
                                                            final String query,
                                                            final int param1,
                                                            final int param2,
                                                            final int param3,
                                                            final String param4) throws DatabaseException {
        try {
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, param1);
            statement.setInt(2, param2);
            statement.setInt(3, param3);
            statement.setString(4, param4);

            return statement;
        } catch (final SQLException ex) {
            LogUtil.error("SQL Error: " + ex.getErrorCode());
            throw new DatabaseException(ex.getMessage(), ex);
        }
    }

    /**
     * Reads the <code>ResultSet</code> retrieved from query and converts it to a list of <code>Waypoint</code>
     * @param resultSet <code>ResultSet</code> retrieved from SQL execution
     * @return List of <code>Waypoint</code>
     * @throws DatabaseException Thrown when database access error occurs or <code>ResultSet</code> is closed
     */
    public static List<Waypoint> readResult(final ResultSet resultSet) throws DatabaseException {
        try {
            final List<Waypoint> waypoints = new ArrayList<>();
            while(resultSet.next()) {
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
     * @param resultSet <code>ResultSet</code> row retrieved from database
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
            waypoint.setX(resultSet.getInt("x_coordinate"));
            waypoint.setY(resultSet.getInt("y_coordinate"));
            waypoint.setZ(resultSet.getInt("z_coordinate"));

            return waypoint;
        } catch (final SQLException ex) {
            LogUtil.error("Could not extract result set.");
            throw new DatabaseException("SQL Error: " + ex.getErrorCode(), ex);
        }
    }
}
