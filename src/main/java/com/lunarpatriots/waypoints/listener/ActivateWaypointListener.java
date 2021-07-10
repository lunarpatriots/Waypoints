package com.lunarpatriots.waypoints.listener;

import com.lunarpatriots.waypoints.MainApp;
import com.lunarpatriots.waypoints.api.exceptions.DatabaseException;
import com.lunarpatriots.waypoints.api.model.Waypoint;
import com.lunarpatriots.waypoints.api.repository.WaypointRepository;
import com.lunarpatriots.waypoints.util.LogUtil;
import com.lunarpatriots.waypoints.util.MessageUtil;
import com.lunarpatriots.waypoints.util.ValidatorUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/09/2021
 */
public final class ActivateWaypointListener implements Listener {

    private final WaypointRepository repository;
    private final MainApp plugin;

    public ActivateWaypointListener(final MainApp plugin, final WaypointRepository repository) {
        this.repository = repository;
        this.plugin = plugin;
    }

    @EventHandler
    public void activateWaypoint(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Block targetBlock = event.getClickedBlock();
        final ItemStack itemUsed = player.getInventory().getItemInMainHand();

        if (Action.LEFT_CLICK_BLOCK == event.getAction()
                && ValidatorUtil.isValidWaypointBlock(targetBlock)
                && Material.COMPASS.equals(itemUsed.getType())) {

            final Sign sign = (Sign) targetBlock.getState();
            final Waypoint interactedWaypoint = new Waypoint(player.getWorld().getName(), sign);

            try {
                saveWaypoint(interactedWaypoint, player);
            } catch (final Exception ex) {
                LogUtil.error(ex.getMessage());
            }
        }
    }

    private void saveWaypoint(final Waypoint newWaypoint, final Player player) {
        try {
            final List<Waypoint> waypoints = repository.filterWaypoints(newWaypoint.getWorld());
            final Optional<Waypoint> duplicate = ValidatorUtil.getDuplicate(waypoints, newWaypoint.getName());
            final String playerUuid = player.getUniqueId().toString();

            if (duplicate.isPresent()) {
                handleDuplicate(player, duplicate.get(), newWaypoint);
            } else {
                final String waypointUuid = UUID.randomUUID().toString();
                newWaypoint.setUuid(waypointUuid);
                repository.saveWaypoint(newWaypoint);
                repository.saveReference(UUID.randomUUID().toString(), playerUuid, waypointUuid);
                MessageUtil.success(player, "New waypoint activated!");
            }
        } catch (final DatabaseException ex) {
            LogUtil.error(ex.getMessage());
        }
    }

    private void handleDuplicate(final Player player, final Waypoint duplicateWaypoint, final Waypoint newWaypoint)
            throws DatabaseException {

        final List<Waypoint> playerWaypoints = ValidatorUtil.isGlobalEnabled(plugin)
            ? null
            : repository.filterWaypointsPerPlayer(player.getUniqueId().toString(),
            player.getWorld().getName());
        final Block duplicateBlock = duplicateWaypoint.getLocation().getBlock();

        final String waypointUuid = duplicateWaypoint.getUuid();
        final String playerUuid = player.getUniqueId().toString();

        if (ValidatorUtil.isValidWaypointBlock(duplicateBlock)) {
            if (ValidatorUtil.isActivatedForPlayer(plugin, playerWaypoints, duplicateWaypoint.getName())) {
                final String msg = duplicateWaypoint.getLocation().equals(newWaypoint.getLocation())
                    ? "Waypoint already activated!"
                    : String.format("A waypoint with the name %s already exists!"
                        + " Please set a different waypoint name.",
                    newWaypoint.getName());

                MessageUtil.fail(player, msg);
            } else {
                repository.saveReference(UUID.randomUUID().toString(), playerUuid, duplicateWaypoint.getUuid());
                MessageUtil.success(player, "New waypoint activated!");
            }
        } else {
            newWaypoint.setUuid(waypointUuid);
            repository.updateWaypoint(newWaypoint);

            if (!ValidatorUtil.isActivatedForPlayer(plugin, playerWaypoints, duplicateWaypoint.getName())) {
                repository.saveReference(UUID.randomUUID().toString(),
                    playerUuid,
                    duplicateWaypoint.getUuid());
            }

            MessageUtil.success(player, "New waypoint activated!");
        }
    }
}
