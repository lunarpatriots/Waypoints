package com.lunarpatriots.waypoints.api.constants;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/15/2021
 */
public final class SqlConstants {

    public static final String JDBC_DRIVER_CLASS_NAME = "org.sqlite.JDBC";

    public static final String URL = "jdbc:sqlite:%s";

    public static final String CREATE_WAYPOINTS_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS waypoints ("
        + "uuid VARCHAR(255) NOT NULL, "
        + "name VARCHAR(255) NOT NULL, "
        + "world VARCHAR(255) NOT NULL, "
        + "x_coordinate INT NOT NULL, "
        + "y_coordinate INT NOT NULL, "
        + "z_coordinate INT NOT NULL, "
        + "PRIMARY KEY(uuid), "
        + "UNIQUE(name, world));";

    public static final String CREATE_USERS_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS users("
        + "uuid VARCHAR(255) NOT NULL, "
        + "user_uuid VARCHAR(255) NOT NULL, "
        + "waypoints_uuid VARCHAR(255) NOT NULL, "
        + "PRIMARY KEY(uuid), "
        + "FOREIGN KEY(waypoints_uuid) REFERENCES waypoints(uuid) "
        + "ON DELETE CASCADE);";

    public static final String CREATE_REFERENCE_QUERY = "INSERT INTO users VALUES(?, ?, ?)";

    public static final String INSERT_QUERY = "INSERT INTO waypoints VALUES(?, ?, ?, ?, ?, ?);";

    public static final String UPDATE_QUERY = "UPDATE waypoints "
        + "SET x_coordinate = ?, y_coordinate = ?, z_coordinate = ? WHERE uuid = ?;";

    public static final String DELETE_QUERY = "DELETE FROM waypoints WHERE `uuid` = ?;";

    public static final String GET_QUERY = "SELECT * FROM waypoints;";

    public static final String GET_FILTERED_QUERY = "SELECT * FROM waypoints WHERE world = ?";

    public static final String GET_FILTERERD_PER_PLAYER_QUERY = "SELECT "
        + "w.uuid, w.name, w.world, w.x_coordinate, w.y_coordinate, w.z_coordinate "
        + "FROM users u "
        + "LEFT JOIN waypoints w ON u.waypoints_uuid = w.uuid "
        + "WHERE u.user_uuid = ? "
        + "AND w.world = ?";

    private SqlConstants() {
    }
}
