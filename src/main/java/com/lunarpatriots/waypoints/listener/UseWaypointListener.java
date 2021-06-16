package com.lunarpatriots.waypoints.listener;

import com.lunarpatriots.waypoints.MainApp;
import com.lunarpatriots.waypoints.api.model.Waypoint;
import com.lunarpatriots.waypoints.api.repository.WaypointRepository;
import com.lunarpatriots.waypoints.util.GuiUtil;
import com.lunarpatriots.waypoints.util.LogUtil;
import com.lunarpatriots.waypoints.util.MessageUtil;
import com.lunarpatriots.waypoints.util.ValidatorUtil;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.Optional;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/08/2021
 */
public final class UseWaypointListener implements Listener {

    private final MainApp plugin;
    private final WaypointRepository repository;

    public UseWaypointListener(final MainApp plugin, final WaypointRepository repository) {
        this.plugin = plugin;
        this.repository = repository;
    }

    @EventHandler
    public void useWaypoint(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Block targetBlock = event.getClickedBlock();

        if (Action.RIGHT_CLICK_BLOCK == event.getAction()
            && ValidatorUtil.isValidWaypointBlock(targetBlock)) {

            final Sign sign = (Sign) targetBlock.getState();
            final Waypoint interactedWaypoint = new Waypoint(player.getWorld().getName(), sign);

            try {
                final List<Waypoint> waypoints = repository.getWaypoints(interactedWaypoint.getWorld());

                if (isActivated(waypoints, interactedWaypoint)) {
                    waypoints.removeIf(waypoint -> interactedWaypoint.getName().equals(waypoint.getName()));
                    openSelectionMenu(waypoints, player);
                } else {
                    MessageUtil
                        .fail(player, "Waypoint is not yet activated! Left-click with a compass to activate.");
                }
            } catch (final Exception ex) {
                LogUtil.error(ex.getMessage());
            }
        }
    }

    private void openSelectionMenu(final List<Waypoint> waypoints, final Player player) {
        if (waypoints.size() > 0) {
            final Inventory waypointSelector = GuiUtil.initWaypointSelector(plugin, waypoints, player);
            player.openInventory(waypointSelector);
        } else {
            MessageUtil.fail(player, "No other waypoints found in region!");
        }
    }

    private boolean isActivated(final List<Waypoint> waypoints, final Waypoint interactedWaypoint) {
        final Optional<Waypoint> existingWaypoint = waypoints.stream()
            .filter(waypoint -> interactedWaypoint.getName().equals(waypoint.getName())
                && interactedWaypoint.getLocation().equals(waypoint.getLocation()))
            .findFirst();

        return existingWaypoint.isPresent();
    }
}
