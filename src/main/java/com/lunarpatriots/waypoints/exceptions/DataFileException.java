package com.lunarpatriots.waypoints.exceptions;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/12/2021
 */
public class DataFileException extends Exception {

    public DataFileException(final String message) {
        super(message);
    }

    public DataFileException(final String message, final Exception ex) {
        super(message, ex);
    }
}
