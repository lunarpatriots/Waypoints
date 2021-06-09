package com.lunarpatriots.waypoints.util;

import com.lunarpatriots.waypoints.MainApp;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/08/2021
 */
public class ConfigUtil {

    private ConfigUtil() {
    }

    public static int getInt(final MainApp plugin, final String key) {
        final FileConfiguration config = plugin.getConfig();

        return config.getInt(key);
    }

    public static String getString(final MainApp plugin, final String key) {
        final FileConfiguration config = plugin.getConfig();

        return config.getString(key);
    }

    public static double getDouble(final MainApp plugin, final String key) {
        final FileConfiguration config = plugin.getConfig();

        return config.getDouble(key);
    }
}
