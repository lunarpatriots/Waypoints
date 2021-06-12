package com.lunarpatriots.waypoints;

import com.lunarpatriots.waypoints.exceptions.DataFileException;
import com.lunarpatriots.waypoints.listener.ActivateWaypointListener;
import com.lunarpatriots.waypoints.listener.SelectWaypointListener;
import com.lunarpatriots.waypoints.listener.UseWaypointListener;
import com.lunarpatriots.waypoints.util.WaypointsUtil;
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
public class MainApp extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            loadConfig();
            loadDataFile();
            registerEvents();
        } catch (final Exception ex) {
            LogUtil.error("An error occurred while loading plugin.");
            LogUtil.error(ex.getMessage());
            Bukkit.getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        LogUtil.info("Saving waypoints...");
        try {
            WaypointsUtil.saveToFile(this);
        } catch (final Exception ex) {
            LogUtil.error(ex.getMessage());
        }
    }

    private void loadConfig() throws Exception {
        LogUtil.info("Verifying config file...");
        final File directory = getDataFolder();

        if (!directory.exists()) {
            directory.mkdir();
        }

        final File configFile = new File(directory, "config.yml");
        if (!configFile.exists()) {
            this.saveDefaultConfig();
        }

        final FileConfiguration config = this.getConfig();

        try {
            config.load(new File(directory, "config.yml"));
        } catch (final Exception ex) {
            LogUtil.error("Failed to load config file!");
            throw ex;
        }
    }

    public void loadDataFile() throws DataFileException {
        LogUtil.info("Loading data file...");
        WaypointsUtil.loadFromFile(this);
    }

    private void registerEvents() {
        LogUtil.info("Registering events...");
        final PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new ActivateWaypointListener(), this);
        pluginManager.registerEvents(new UseWaypointListener(this), this);
        pluginManager.registerEvents(new SelectWaypointListener(this), this);
    }
}
