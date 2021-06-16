package com.lunarpatriots.waypoints.util;

import com.lunarpatriots.waypoints.constants.Constants;
import org.bukkit.Bukkit;

import java.util.logging.Level;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/08/2021
 */
public final class LogUtil {

    private LogUtil() {
    }

    public static void info(final String message) {
        Bukkit.getLogger().log(Level.INFO, String.format(Constants.LOG_FORMAT, message));
    }

    public static void warn(final String message) {
        Bukkit.getLogger().log(Level.WARNING, String.format(Constants.LOG_FORMAT, message));
    }

    public static void error(final String message) {
        Bukkit.getLogger().log(Level.SEVERE, String.format(Constants.LOG_FORMAT, message));
    }
}
