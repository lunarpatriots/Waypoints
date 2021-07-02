package com.lunarpatriots.waypoints.listener;

import com.lunarpatriots.waypoints.api.exceptions.DatabaseException;
import com.lunarpatriots.waypoints.api.repository.WaypointRepository;
import com.lunarpatriots.waypoints.constants.Constants;
import com.lunarpatriots.waypoints.model.dto.WaypointDto;
import com.lunarpatriots.waypoints.util.LogUtil;
import com.lunarpatriots.waypoints.util.MessageUtil;
import com.lunarpatriots.waypoints.util.ValidatorUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
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
public final class SelectWaypointListener implements Listener {

    private final WaypointRepository repository;

    public SelectWaypointListener(final WaypointRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void onWaypointSelect(final InventoryClickEvent event) {
        final InventoryView openedInventory = event.getView();

        final String guiName = ChatColor.DARK_GREEN + Constants.GUI_TITLE;
        final String eventGuiName = openedInventory.getTitle();

        if (guiName.equalsIgnoreCase(eventGuiName)) {
            event.setCancelled(true);
            final ItemStack selectedWaypoint = event.getCurrentItem();

            if (Optional.ofNullable(selectedWaypoint).isPresent()
                    && Material.FILLED_MAP == selectedWaypoint.getType()) {

                final ItemMeta waypointInfo = selectedWaypoint.getItemMeta();
                final Player player = (Player) event.getWhoClicked();

                assert Optional.ofNullable(waypointInfo).isPresent();

                final WaypointDto dto = new WaypointDto(player.getWorld().getName(), waypointInfo);

                player.closeInventory();
                teleportPlayer(player, dto);
            }
        }
    }

    private void teleportPlayer(final Player player, final WaypointDto waypointDto) {
        final Location targetLocation = waypointDto.getLocation();
        final Block targetBlock = targetLocation.getBlock();
        final int cost = waypointDto.getCost();

        if (ValidatorUtil.isValidWaypointBlock(targetBlock)) {
            final int currentLevel = player.getLevel();
            if (player.getLevel() >= waypointDto.getCost()) {
                MessageUtil.success(player, String.format("Fast travelling to %s...", waypointDto.getName()));

                player.setLevel(currentLevel - cost);

                final Location sourceLocation = player.getLocation();
                final int particleCount = 100;
                player.spawnParticle(Particle.PORTAL, sourceLocation.add(0, 1, 0), particleCount);
                player.playSound(sourceLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                player.playSound(targetLocation, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                player.teleport(targetLocation);
            } else {
                MessageUtil.fail(player, "You do not have enough exp to fast travel to that location!");
            }
        } else {
            MessageUtil.fail(player, "Fast travel point not found! Removing from list...");

            try {
                repository.deleteWaypoint(waypointDto.getUuid());
            } catch (final DatabaseException ex) {
                LogUtil.error(ex.getMessage());
            }
        }
    }
}
