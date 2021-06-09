package com.lunarpatriots.waypoints;

import com.lunarpatriots.waypoints.listener.PlayerInteraction;
import com.lunarpatriots.waypoints.listener.WaypointGuiInteraction;
import com.lunarpatriots.waypoints.util.DbUtil;
import com.lunarpatriots.waypoints.util.LogUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/08/2021
 */
public class Waypoints extends JavaPlugin {

    @Override
    public void onEnable() {
        loadConfig();
        try {
            testDbConnection();
            registerEvents();
        } catch (final Exception ex) {
            LogUtil.error(ex.getMessage());
        }
    }

    @Override
    public void onDisable() {
    }

    private void loadConfig() {
        LogUtil.info("Verifying config file...");
        final File directory = getDataFolder();

        try {
            if (!directory.exists()) {
                directory.mkdir();
            }

            final File config = new File(directory, "config.yml");
            if (!config.exists()) {
                this.saveDefaultConfig();
            }
        } catch (final Exception ex) {
            LogUtil.error("Config directory not found!");
            throw ex;
        }

        final FileConfiguration config = this.getConfig();

        try {
            config.load(new File(directory, "config.yml"));
        } catch (final Exception ex) {
            LogUtil.error("Unable to load config file!");
        }
    }

    private void registerEvents() {
        LogUtil.info("Registering events...");
        final PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerInteraction(this), this);
        pluginManager.registerEvents(new WaypointGuiInteraction(this), this);
    }

    private void testDbConnection() throws Exception {
        LogUtil.info("Testing database connection...");
        DbUtil.createConnection(this);
    }
}
