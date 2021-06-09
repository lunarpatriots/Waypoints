package com.lunarpatriots.waypoints.listener;

import com.lunarpatriots.waypoints.Waypoints;
import com.lunarpatriots.waypoints.constants.Constants;
import com.lunarpatriots.waypoints.model.Waypoint;
import com.lunarpatriots.waypoints.repository.WaypointRepository;
import com.lunarpatriots.waypoints.util.ConfigUtil;
import com.lunarpatriots.waypoints.util.GuiUtil;
import com.lunarpatriots.waypoints.util.LogUtil;
import com.lunarpatriots.waypoints.util.ValidatorUtil;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/08/2021
 */
public class PlayerInteraction implements Listener {

    private final WaypointRepository repository;
    private final String waypointSignPrefix;
    private final Waypoints plugin;

    public PlayerInteraction(final Waypoints plugin) {
        this.repository = new WaypointRepository(plugin);
        this.plugin = plugin;
        this.waypointSignPrefix = String.format(
            Constants.WAYPOINT_FORMAT, ConfigUtil.getString(plugin, "waypoint-prefix"));
    }

    @EventHandler
    public void waypointInteraction(final PlayerInteractEvent event) {
        final Block targetBlock = event.getClickedBlock();
        final Player player = event.getPlayer();

        if (ValidatorUtil.validateTriggeredWaypoint(event, targetBlock)) {
            final Sign sign = (Sign) targetBlock.getState();

            if (ValidatorUtil.validateWaypointPrefix(sign, waypointSignPrefix)) {
                try {
                    final List<Waypoint> waypoints = repository.getWaypoints();
                    final String waypointName = sign.getLine(1);

                    ValidatorUtil.validateInteractedWaypoint(
                        repository,
                        waypoints,
                        waypointName,
                        sign.getX(),
                        sign.getY(),
                        sign.getZ());

                    waypoints.removeIf(waypoint -> waypointName.equals(waypoint.getName()));

                    openSelectionMenu(waypoints, player);
                } catch (final Exception ex) {
                    LogUtil.error(ex.getMessage());
                }
            }
        }
    }

    private void openSelectionMenu(final List<Waypoint> waypoints, final Player player) {
        if (waypoints.size() > 0) {
            final Inventory waypointSelector = GuiUtil.initWaypointSelector(plugin, waypoints, player);
            player.openInventory(waypointSelector);
        } else {
            player.sendMessage(ChatColor.RED + "No other waypoints registered!");
        }
    }
}
