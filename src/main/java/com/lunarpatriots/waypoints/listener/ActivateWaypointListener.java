package com.lunarpatriots.waypoints.listener;

import com.lunarpatriots.waypoints.model.Waypoint;
import com.lunarpatriots.waypoints.repository.WaypointRepository;
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
public class ActivateWaypointListener implements Listener {

    private WaypointRepository repository;

    public ActivateWaypointListener() {
        this.repository = new WaypointRepository();
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

    private Optional<Waypoint> retrieveDuplicate(final List<Waypoint> waypoints, final String name) {
        return waypoints.stream()
            .filter(waypoint -> waypoint.getName().equals(name))
            .findFirst();
    }

    private void saveWaypoint(final Waypoint newWaypoint, final Player player) {
        final List<Waypoint> waypoints = repository.getWaypoints(newWaypoint.getWorld());
        final Optional<Waypoint> duplicate = retrieveDuplicate(waypoints, newWaypoint.getName());

        if (duplicate.isPresent()) {
            final Waypoint duplicateWaypoint = duplicate.get();

            if (ValidatorUtil.isValidWaypointBlock(duplicateWaypoint.getLocation().getBlock())) {

                if (duplicateWaypoint.getLocation().equals(newWaypoint.getLocation())) {
                    MessageUtil.fail(player, "Waypoint already activated!");
                } else {
                    MessageUtil.fail(player, "Duplicate waypoint name!");
                }
            } else {
                newWaypoint.setUuid(duplicateWaypoint.getUuid());
                repository.updateWaypoint(newWaypoint);
            }
        } else {
            newWaypoint.setUuid(UUID.randomUUID().toString());
            repository.saveWaypoint(newWaypoint);
            MessageUtil.success(player, "New waypoint activated!");
        }
    }
}
