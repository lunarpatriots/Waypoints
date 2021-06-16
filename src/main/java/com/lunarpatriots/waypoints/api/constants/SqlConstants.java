package com.lunarpatriots.waypoints.api.constants;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/15/2021
 */
public final class SqlConstants {

    public static final String JDBC_DRIVER_CLASS_NAME = "org.sqlite.JDBC";

    public static final String URL = "jdbc:sqlite:%s";

    public static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS waypoints ("
        + "uuid VARCHAR(255) NOT NULL, "
        + "name VARCHAR(255) NOT NULL, "
        + "world VARCHAR(255) NOT NULL, "
        + "x_coordinate INT NOT NULL, "
        + "y_coordinate INT NOT NULL, "
        + "z_coordinate INT NOT NULL, "
        + "PRIMARY KEY(uuid), "
        + "UNIQUE(name, world));";

    public static final String INSERT_QUERY = "INSERT INTO waypoints VALUES(?, ?, ?, ?, ?, ?);";

    public static final String UPDATE_QUERY = "UPDATE waypoints "
        + "SET x_coordinate = ?, y_coordinate = ?, z_coordinate = ? WHERE uuid = ?;";

    public static final String DELETE_QUERY = "DELETE FROM waypoints WHERE `uuid` = ?;";

    public static final String GET_QUERY = "SELECT * FROM waypoints;";

    public static final String GET_FILTERED_QUERY = "SELECT * FROM waypoints WHERE world = ?";

    private SqlConstants() {
    }
}
