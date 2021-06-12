package com.lunarpatriots.waypoints.listener;

import com.lunarpatriots.waypoints.MainApp;
import com.lunarpatriots.waypoints.model.dto.WaypointDto;
import com.lunarpatriots.waypoints.repository.WaypointRepository;
import com.lunarpatriots.waypoints.util.ConfigUtil;
import com.lunarpatriots.waypoints.util.ExpUtil;
import com.lunarpatriots.waypoints.util.MessageUtil;
import com.lunarpatriots.waypoints.util.ValidatorUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/09/2021
 */
public class SelectWaypointListener implements Listener {

    private final MainApp plugin;

    public SelectWaypointListener(final MainApp plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWaypointSelect(final InventoryClickEvent event) {
        final InventoryView openedInventory = event.getView();

        final String guiName = ChatColor.DARK_GREEN + ConfigUtil.getString(plugin, "selector-title");
        final String eventGuiName = openedInventory.getTitle();

        if (guiName.equalsIgnoreCase(eventGuiName)) {
            event.setCancelled(true);
            final ItemStack selectedWaypoint = event.getCurrentItem();

            if (Optional.ofNullable(selectedWaypoint).isPresent()
                    && Tag.SIGNS.isTagged(selectedWaypoint.getType())) {

                final ItemMeta waypointInfo = selectedWaypoint.getItemMeta();
                final Player player = (Player) event.getWhoClicked();
                final WaypointDto waypoint = new WaypointDto(player.getWorld().getName(), waypointInfo);

                player.closeInventory();
                teleportPlayer(player, waypoint);
            }
        }
    }

    private void teleportPlayer(final Player player, final WaypointDto waypoint) {
        final Location targetLocation = waypoint.getLocation();
        final Block targetBlock = targetLocation.getBlock();

        if (ValidatorUtil.isValidWaypointBlock(targetBlock)) {
            if (ExpUtil.getPlayerExp(player) >= waypoint.getCost()) {
                MessageUtil.success(player, String.format("Fast travelling to %s...", waypoint.getName()));

                if (waypoint.getCost() > 0) {
                    ExpUtil.changePlayerExp(player, -waypoint.getCost());
                }

                player.teleport(targetLocation);
            } else {
                MessageUtil.error(player, "You do not have enough exp to fast travel to that location!");
            }
        } else {
            MessageUtil.error(player, "Fast travel point not found! Removing from list...");
            final WaypointRepository repository = new WaypointRepository();

            repository.deleteWaypoint(waypoint);
        }
    }
}
