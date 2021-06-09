package com.lunarpatriots.waypoints.actions;

import com.lunarpatriots.waypoints.MainApp;
import com.lunarpatriots.waypoints.model.Waypoint;
import com.lunarpatriots.waypoints.repository.WaypointRepository;
import com.lunarpatriots.waypoints.util.ConfigUtil;
import org.bukkit.entity.Player;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/08/2021
 */
public class Travel {

    private final MainApp plugin;
    private final WaypointRepository repository;
    private final int expPerBlock;

    public Travel(final MainApp plugin) {
        this.plugin = plugin;
        repository = new WaypointRepository(plugin);
        this.expPerBlock = ConfigUtil.getInt(plugin, "exp-per-block");
    }

    private void attemptFastTravel(final Player player, final Waypoint waypoint) {

    }
}
