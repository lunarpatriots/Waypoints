package com.lunarpatriots.waypoints.util;

import com.lunarpatriots.waypoints.MainApp;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/08/2021
 */
public final class ConfigUtil {

    private ConfigUtil() {
    }

    public static int getInt(final MainApp plugin, final String key) {
        final FileConfiguration config = plugin.getConfig();

        return config.getInt(key);
    }

    public static String getString(final MainApp plugin, final String key, final String defaultVal) {
        final FileConfiguration config = plugin.getConfig();

        return config.getString(key, defaultVal);
    }

    public static boolean getBoolean(final MainApp plugin, final String key) {
        final FileConfiguration config = plugin.getConfig();

        return config.getBoolean(key, true);
    }
}
