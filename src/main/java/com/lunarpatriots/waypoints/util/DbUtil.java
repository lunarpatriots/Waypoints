package com.lunarpatriots.waypoints.util;

import com.lunarpatriots.waypoints.Waypoints;
import com.lunarpatriots.waypoints.constants.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created By: tristan.hamili@novare.com.hk
 * Date created: 06/08/2021
 */
public class DbUtil {

    private DbUtil() {
    }

    public static Connection createConnection(final Waypoints plugin)
            throws SQLException, ClassNotFoundException {
        try {
            Class.forName(Constants.JDBC_DRIVER);
            return DriverManager.getConnection(
                ConfigUtil.getString(plugin, "database.url"),
                ConfigUtil.getString(plugin, "database.username"),
                ConfigUtil.getString(plugin, "database.password"));
        } catch (final SQLException ex) {
           LogUtil.error("Failed to establish connection with the database.");
            throw ex;
        } catch (final ClassNotFoundException ex) {
            LogUtil.error("Connection driver not found.");
            throw ex;
        }
    }
}
