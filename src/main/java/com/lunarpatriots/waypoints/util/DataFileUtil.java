package com.lunarpatriots.waypoints.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lunarpatriots.waypoints.MainApp;
import com.lunarpatriots.waypoints.api.model.Waypoint;
import com.lunarpatriots.waypoints.exceptions.DataFileException;

import com.lunarpatriots.waypoints.model.WaypointsList;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/08/2021
 */
@Getter
@Setter
public class DataFileUtil {

    public static List<Waypoint> data = new ArrayList<>();

    private DataFileUtil() {
    }

    @Deprecated
    public static void loadFromFile(final MainApp plugin) throws DataFileException {
        final File dataFile = getDataFile(plugin);
        final Gson gson = new Gson();

        try (final Reader fileReader = new FileReader(dataFile)) {
            final WaypointsList list = gson.fromJson(fileReader, WaypointsList.class);
            data = list.getData();
        } catch(final IOException ex) {
            LogUtil.error("Failed to read data file");
            throw new DataFileException(ex.getMessage(), ex);
        }
    }

    public static List<Waypoint> loadFromFile(final MainApp plugin, final String filename)
        throws DataFileException {

        final File dataFile = getDataFile(plugin, filename);
        final Gson gson = new Gson();

        try (final Reader fileReader = new FileReader(dataFile)) {
            final WaypointsList list = gson.fromJson(fileReader, WaypointsList.class);
            return list.getData();
        } catch (final IOException ex) {
            throw new DataFileException(ex.getMessage(), ex);
        }
    }

    @Deprecated
    public static void saveToFile(final MainApp plugin) throws DataFileException {
        final File dataFile = getDataFile(plugin);
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try(final Writer writer = new FileWriter(dataFile)) {
            final WaypointsList list = new WaypointsList();
            list.setData(data);
            gson.toJson(list, writer);
        } catch (final IOException ex) {
            LogUtil.error(ex.getMessage());
            throw new DataFileException("Failed to write to data file!");
        }
    }

    @Deprecated
    private static File getDataFile(final MainApp plugin) throws DataFileException {
        final File baseDirectory = plugin.getDataFolder();

        if (!baseDirectory.exists()) {
            throw new DataFileException("Base directory not found!");
        }

        final File dataDirectory = new File(baseDirectory, "data");

        if (!dataDirectory.exists()) {
            LogUtil.info("Data directory not found! Creating data directory...");
            dataDirectory.mkdir();
        }

        final File dataFile = new File(dataDirectory, "waypoints.json");

        if (!dataFile.exists()) {
            try {
                LogUtil.info("Data file not found! Creating data file...");
                dataFile.createNewFile();
                saveToFile(plugin);
            } catch (final IOException ex) {
                LogUtil.error("Unable to create data file!");
                throw new DataFileException(ex.getMessage(), ex);
            }
        }

        return dataFile;
    }

    public static File getDataFile(final MainApp plugin, final String filename) throws DataFileException {
        final File baseDirectory = plugin.getDataFolder();

        if (!baseDirectory.exists()) {
            throw new DataFileException("Base directory not found!");
        }

        final File dataDirectory = new File(baseDirectory, "data");

        if (!dataDirectory.exists()) {
            LogUtil.info("Data directory not found! Creating data directory...");
            dataDirectory.mkdir();
        }

        final File dataFile = new File(dataDirectory, filename);

        if (!dataFile.exists()) {
            try {
                LogUtil.info("Data file not found! Creating data file...");
                dataFile.createNewFile();
            } catch (final IOException ex) {
                LogUtil.error("Unable to create data file!");
                throw new DataFileException(ex.getMessage(), ex);
            }
        }

        return dataFile;
    }
}
