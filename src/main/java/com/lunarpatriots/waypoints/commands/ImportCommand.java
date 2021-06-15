package com.lunarpatriots.waypoints.commands;

import com.lunarpatriots.waypoints.MainApp;
import com.lunarpatriots.waypoints.api.exceptions.DatabaseException;
import com.lunarpatriots.waypoints.api.model.Waypoint;
import com.lunarpatriots.waypoints.api.repository.WaypointRepository;
import com.lunarpatriots.waypoints.exceptions.DataFileException;
import com.lunarpatriots.waypoints.util.DataFileUtil;
import com.lunarpatriots.waypoints.util.LogUtil;
import com.lunarpatriots.waypoints.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/15/2021
 */
public class ImportCommand implements TabExecutor {

    private final MainApp plugin;
    private final WaypointRepository repository;

    public ImportCommand(final MainApp plugin, final WaypointRepository repository) {
        this.plugin = plugin;
        this.repository = repository;
    }

    @Override
    public boolean onCommand(final CommandSender commandSender,
                             final Command command,
                             final String s,
                             final String[] strings) {
        final Player player = (Player) commandSender;

        if (strings.length == 0) {
            MessageUtil.fail(player, "Incorrect usage!");
        } else {
            final String filename = strings[0];

            try {
                final List<Waypoint> waypoints = DataFileUtil.loadFromFile(plugin, filename);
                final int newRecords = importRecords(waypoints);

                if (newRecords > 0) {
                    final int totalRecords = waypoints.size();
                    if (newRecords == totalRecords) {
                        MessageUtil.success(player, newRecords + " waypoints imported!");
                    } else {
                        final int diff = totalRecords - newRecords;

                        MessageUtil.fail(player, String.format("%s/%s imported!", diff, totalRecords));
                    }
                } else {
                    MessageUtil.fail(player, "No waypoints imported!");
                }
            } catch (final DataFileException ex) {
                MessageUtil.fail(player, "Failed to load json file!");
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(final CommandSender commandSender,
                                      final Command command,
                                      final String s,
                                      final String[] strings) {
        return null;
    }

    private int importRecords(final List<Waypoint> waypoints) {
        final AtomicInteger count = new AtomicInteger(0);
        waypoints.forEach(waypoint -> {
            try {
                final int rows = repository.saveWaypoint(waypoint);
                count.getAndAdd(rows);
            } catch (final DatabaseException ex) {
                LogUtil.error(ex.getMessage());
            }
        });

        return count.get();
    }
}
