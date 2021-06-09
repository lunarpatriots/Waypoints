package com.lunarpatriots.waypoints.actions;

import com.lunarpatriots.waypoints.Waypoints;
import com.lunarpatriots.waypoints.model.Waypoint;
import com.lunarpatriots.waypoints.repository.WaypointRepository;
import com.lunarpatriots.waypoints.util.ConfigUtil;
import org.bukkit.entity.Player;

/**
 * Created By: tristan.hamili@novare.com.hk
 * Date created: 06/08/2021
 */
public class Travel {

    private final Waypoints plugin;
    private final WaypointRepository repository;
    private final int expPerBlock;

    public Travel(final Waypoints plugin) {
        this.plugin = plugin;
        repository = new WaypointRepository(plugin);
        this.expPerBlock = ConfigUtil.getInt(plugin, "exp-per-block");
    }

    private void attemptFastTravel(final Player player, final Waypoint waypoint) {

    }
}
