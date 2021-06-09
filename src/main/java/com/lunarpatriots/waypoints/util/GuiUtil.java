package com.lunarpatriots.waypoints.util;

import com.lunarpatriots.waypoints.Waypoints;
import com.lunarpatriots.waypoints.model.Waypoint;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By: tristan.hamili@novare.com.hk
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

        final Inventory selectionGui = Bukkit
            .createInventory(player, inventorySize, ChatColor.DARK_GREEN + ConfigUtil.getString(plugin, "selector-title"));
        waypoints.forEach(waypoint -> {
            final ItemStack selection = createWaypointSelection(waypoint);
            selectionGui.addItem(selection);
        });

        return selectionGui;
    }

    private static ItemStack createWaypointSelection(final Waypoint waypoint) {
        final ItemStack selection = new ItemStack(Material.WARPED_SIGN);

        final ItemMeta itemDetails = selection.getItemMeta();
        itemDetails.setDisplayName(ChatColor.GREEN + waypoint.getName());

        final List<String> meta = new ArrayList<>();
        meta.add(ChatColor.GRAY + "uuid: " + waypoint.getUuid());
        meta.add(ChatColor.GRAY + "x: " + waypoint.getX());
        meta.add(ChatColor.GRAY + "y: " + waypoint.getY());
        meta.add(ChatColor.GRAY + "z: " + waypoint.getZ());
        itemDetails.setLore(meta);
        selection.setItemMeta(itemDetails);

        LogUtil.info("Adding " + waypoint.toString());

        return selection;
    }
}
