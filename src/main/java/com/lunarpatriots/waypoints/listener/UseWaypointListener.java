package com.lunarpatriots.waypoints.listener;

import com.lunarpatriots.waypoints.MainApp;
import com.lunarpatriots.waypoints.model.Waypoint;
import com.lunarpatriots.waypoints.repository.WaypointRepository;
import com.lunarpatriots.waypoints.util.GuiUtil;
import com.lunarpatriots.waypoints.util.LogUtil;
import com.lunarpatriots.waypoints.util.MessageUtil;
import com.lunarpatriots.waypoints.util.ValidatorUtil;
import org.bukkit.ChatColor;
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
public class UseWaypointListener implements Listener {

    private final WaypointRepository repository;
    private final MainApp plugin;

    public UseWaypointListener(final MainApp plugin) {
        this.repository = new WaypointRepository(plugin);
        this.plugin = plugin;
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
                        .error(player, "Waypoint is not yet activated! Actvivate it by left-clicking with a compass.");
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
            player.sendMessage(ChatColor.RED + "No other waypoints found in region!");
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
