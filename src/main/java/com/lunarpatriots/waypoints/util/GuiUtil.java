package com.lunarpatriots.waypoints.util;

import com.lunarpatriots.waypoints.Waypoints;
import com.lunarpatriots.waypoints.model.Waypoint;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/09/2021
 */
public class GuiUtil {

    private GuiUtil() {
    }

    public static Inventory initWaypointSelector(final Waypoints plugin,
                                                 final List<Waypoint> waypoints,
                                                 final Player player) {
        LogUtil.info("Initializing waypoint selection gui");
        final int size = waypoints.size();

        final int inventorySize;

        if (size % 9 == 0) {
            inventorySize = size;
        } else {
            inventorySize = 9 * (size / 9 + 1);
        }

        final Inventory selectionGui = Bukkit.createInventory(
            player,
            inventorySize,
            ChatColor.DARK_GREEN + ConfigUtil.getString(plugin, "selector-title"));
        waypoints.forEach(waypoint -> {
            final ItemStack selection = createWaypointSelection(waypoint, player, plugin);
            selectionGui.addItem(selection);
        });

        return selectionGui;
    }

    private static ItemStack createWaypointSelection(final Waypoint waypoint,
                                                     final Player player,
                                                     final Waypoints plugin) {
        final ItemStack selection = new ItemStack(Material.WARPED_SIGN);
        final Double costPerBlock = ConfigUtil.getDouble(plugin, "exp-per-block");
        final int minDistance = ConfigUtil.getInt(plugin, "min-distance");

        final ItemMeta itemDetails = selection.getItemMeta();
        itemDetails.setDisplayName(ChatColor.GREEN + waypoint.getName());
        final Location location = new Location(player.getWorld(), waypoint.getX(), waypoint.getY(), waypoint.getZ());

        final List<String> meta = new ArrayList<>();
        meta.add(ChatColor.GRAY + "uuid: " + waypoint.getUuid());
        meta.add(ChatColor.GRAY + "x: " + waypoint.getX());
        meta.add(ChatColor.GRAY + "y: " + waypoint.getY());
        meta.add(ChatColor.GRAY + "z: " + waypoint.getZ());
        meta.add(ChatColor.GREEN + "cost: "
            + ValidatorUtil.computeFastTravelPenalty(player, costPerBlock, minDistance, location) + " exp");
        itemDetails.setLore(meta);
        selection.setItemMeta(itemDetails);

        return selection;
    }
}
