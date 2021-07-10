package com.lunarpatriots.waypoints;

import com.lunarpatriots.waypoints.api.constants.SqlConstants;
import com.lunarpatriots.waypoints.api.exceptions.DatabaseException;
import com.lunarpatriots.waypoints.api.repository.WaypointRepository;
import com.lunarpatriots.waypoints.api.repository.impl.WaypointRepositoryImpl;
import com.lunarpatriots.waypoints.commands.CleanCommand;
import com.lunarpatriots.waypoints.commands.ImportCommand;
import com.lunarpatriots.waypoints.commands.ValidateCommand;
import com.lunarpatriots.waypoints.commands.WaypointsCommand;
import com.lunarpatriots.waypoints.listener.ActivateWaypointListener;
import com.lunarpatriots.waypoints.listener.DestroyWaypointListener;
import com.lunarpatriots.waypoints.listener.SelectWaypointListener;
import com.lunarpatriots.waypoints.listener.UseWaypointListener;
import com.lunarpatriots.waypoints.util.ConfigUtil;
import com.lunarpatriots.waypoints.util.LogUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/08/2021
 */
public final class MainApp extends JavaPlugin {

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

        try {
            final String configVersion = ConfigUtil.getString(this, "version", "");
            final String pluginVersion = this.getDescription().getVersion();

            if (!pluginVersion.equals(configVersion)) {
                LogUtil.warn("Config file is outdated!");
            }
        } catch (final Exception ex) {
            LogUtil.error("Failed to load config file!");
            throw ex;
        }
    }

    public WaypointRepository loadDb() throws DatabaseException {
        LogUtil.info("Loading database...");

        final WaypointRepository repository = new WaypointRepositoryImpl(this);
        try {
            repository.initTable(SqlConstants.CREATE_WAYPOINTS_TABLE_QUERY);

            if (!ConfigUtil.getBoolean(this, "global-waypoints")) {
                repository.initTable(SqlConstants.CREATE_USERS_TABLE_QUERY);
            }

            return repository;
        } catch (final DatabaseException ex) {
            LogUtil.error("Failed to initialize table.");
            throw ex;
        }
    }

    private void registerEvents(final WaypointRepository repository) {
        LogUtil.info("Registering events...");
        final PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new ActivateWaypointListener(this, repository), this);
        pluginManager.registerEvents(new UseWaypointListener(this, repository), this);
        pluginManager.registerEvents(new SelectWaypointListener(repository), this);
        pluginManager.registerEvents(new DestroyWaypointListener(repository),  this);
    }

    private void registerCommands(final WaypointRepository repository) {
        LogUtil.info("Registering commands...");

        initCommand(this.getCommand("validate"), new ValidateCommand(repository));
        initCommand(this.getCommand("clean"), new CleanCommand(repository));
        initCommand(this.getCommand("import"), new ImportCommand(this, repository));
        initCommand(this.getCommand("waypoints"), new WaypointsCommand(repository));
    }

    private void initCommand(final PluginCommand command, final CommandExecutor executor) {
        command.setExecutor(executor);
        command.setTabCompleter((TabCompleter) executor);
    }
}
