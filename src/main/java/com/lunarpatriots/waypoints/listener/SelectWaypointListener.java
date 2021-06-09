package com.lunarpatriots.waypoints.listener;

import com.lunarpatriots.waypoints.MainApp;
import com.lunarpatriots.waypoints.model.Waypoint;
import com.lunarpatriots.waypoints.repository.WaypointRepository;
import com.lunarpatriots.waypoints.util.ConfigUtil;
import com.lunarpatriots.waypoints.util.ExpUtil;
import com.lunarpatriots.waypoints.util.LogUtil;
import org.bukkit.Bukkit;
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

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

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
            final boolean validateSelection = Optional.ofNullable(selectedWaypoint).isPresent()
                && Tag.SIGNS.isTagged(selectedWaypoint.getType());

            if (validateSelection) {
                final ItemMeta waypointInfo = selectedWaypoint.getItemMeta();
                final Waypoint waypoint = retrieveWaypointMeta(waypointInfo);
                final Player player = (Player) event.getWhoClicked();
                player.closeInventory();

                attemptFastTravel(player, waypoint);
            }
        }
    }

    private Waypoint retrieveWaypointMeta(final ItemMeta info) {
        final Waypoint waypoint = new Waypoint();
        waypoint.setName(info.getDisplayName());

        final List<String> lore = info.getLore();
        waypoint.setUuid(lore.get(0).split(" ")[1]);
        waypoint.setX(Integer.parseInt(lore.get(1).split(" ")[1]));
        waypoint.setY(Integer.parseInt(lore.get(2).split(" ")[1]));
        waypoint.setZ(Integer.parseInt(lore.get(3).split(" ")[1]));
        waypoint.setCost(Integer.parseInt(lore.get(4).split(" ")[1]));

        return waypoint;
    }

    private void attemptFastTravel(final Player player, final Waypoint waypoint) {
        LogUtil.info("Checking if selected waypoint is available...");
        final Location targetLocation = new Location(
            player.getWorld(),
            waypoint.getX(),
            waypoint.getY(),
            waypoint.getZ());

        final Block targetBlock = targetLocation.getBlock();

        if (!targetBlock.isEmpty() && Tag.SIGNS.isTagged(targetBlock.getType())) {
            if (ExpUtil.getPlayerExp(player) >= waypoint.getCost()) {
                player.sendMessage(ChatColor.DARK_GREEN + "Fast travelling to " + waypoint.getName());

                if (waypoint.getCost() > 0) {
                    ExpUtil.changePlayerExp(player, -waypoint.getCost());
                }

                player.teleport(targetLocation);
            } else {
                player.sendMessage(ChatColor.RED + "You do not have enough exp to fast travel to that location!");
            }
        } else {
            final WaypointRepository repository = new WaypointRepository(plugin);
            player.sendMessage(ChatColor.RED + "Fast travel point not found! Removing from list...");

            try {
                repository.deleteData(waypoint.getUuid());
            } catch (final Exception ex) {
                Bukkit.getLogger().log(Level.SEVERE, ex.getMessage());
            }
        }
    }
}
