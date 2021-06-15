package com.lunarpatriots.waypoints.api.exceptions;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/14/2021
 */
public class DatabaseException extends Exception {

    public DatabaseException(final String message, final Exception ex) {
        super(message, ex);
    }
}
