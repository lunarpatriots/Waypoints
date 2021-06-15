package com.lunarpatriots.waypoints;

import com.lunarpatriots.waypoints.api.exceptions.DatabaseException;
import com.lunarpatriots.waypoints.api.repository.WaypointRepository;
import com.lunarpatriots.waypoints.commands.CleanCommand;
import com.lunarpatriots.waypoints.commands.ListCommand;
import com.lunarpatriots.waypoints.commands.ImportCommand;
import com.lunarpatriots.waypoints.commands.ValidateCommand;
import com.lunarpatriots.waypoints.exceptions.DataFileException;
import com.lunarpatriots.waypoints.listener.ActivateWaypointListener;
import com.lunarpatriots.waypoints.listener.SelectWaypointListener;
import com.lunarpatriots.waypoints.listener.UseWaypointListener;
import com.lunarpatriots.waypoints.util.DataFileUtil;
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
            final WaypointRepository repository = loadDb();

            registerCommands(repository);
            registerEvents(repository);
        } catch (final Exception ex) {
            LogUtil.error("An error occurred while loading plugin.");
            ex.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
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
            final String configVersion = config.getString("version", "");
            final String pluginVersion = this.getDescription().getVersion();

            if (!pluginVersion.equals(configVersion)) {
                LogUtil.warn("Config file is outdated!");
            }
        } catch (final Exception ex) {
            LogUtil.error("Failed to load config file!");
            throw ex;
        }
    }

    @Deprecated
    public void loadDataFile() throws DataFileException {
        LogUtil.info("Loading data file...");
        DataFileUtil.loadFromFile(this);
    }

    public WaypointRepository loadDb() throws DatabaseException {
        LogUtil.info("Loading database...");

        final WaypointRepository repository = new WaypointRepository(this);
        try {
            repository.initTable();
            return repository;
        } catch (final DatabaseException ex) {
            LogUtil.error("Failed to initialize table.");
            throw ex;
        }
    }

    private void registerEvents(final WaypointRepository repository) {
        LogUtil.info("Registering events...");
        final PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new ActivateWaypointListener(repository), this);
        pluginManager.registerEvents(new UseWaypointListener(this, repository), this);
        pluginManager.registerEvents(new SelectWaypointListener(repository), this);
    }

    private void registerCommands(final WaypointRepository repository) {
        LogUtil.info("Registering commands...");
        this.getCommand("validate").setExecutor(new ValidateCommand(repository));
        this.getCommand("clean").setExecutor(new CleanCommand(repository));
        this.getCommand("list").setExecutor(new ListCommand(repository));
        this.getCommand("import").setExecutor(new ImportCommand(this, repository));
    }
}
