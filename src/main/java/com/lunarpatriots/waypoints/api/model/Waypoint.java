package com.lunarpatriots.waypoints.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/14/2021
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Waypoint {

    private String uuid;
    private String name;
    private String world;
    private int xCoordinate;
    private int yCoordinate;
    private int zCoordinate;

    public Waypoint(final String name,
                    final String world,
                    final int xCoordinate,
                    final int yCoordinate,
                    final int zCoordinate) {
        this.name = name;
        this.world = world;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.zCoordinate = zCoordinate;
    }

    public Waypoint(final String world, final Sign sign) {
        this.world = world;
        this.name = sign.getLine(1);
        this.xCoordinate = sign.getX();
        this.yCoordinate = sign.getY();
        this.zCoordinate = sign.getZ();
    }

    public Waypoint(final String world, final ItemMeta itemMeta) {
        final List<String> lore = Objects.requireNonNull(itemMeta.getLore());

        this.world = world;
        this.name = itemMeta.getDisplayName();
        this.uuid = lore.get(0).split(" ")[1];
        this.xCoordinate = Integer.parseInt(lore.get(1).split(" ")[1]);
        this.yCoordinate = Integer.parseInt(lore.get(2).split(" ")[1]);
        this.zCoordinate = Integer.parseInt(lore.get(3).split(" ")[1]);
    }

    public final Location getLocation() {
        return new Location(Bukkit.getWorld(world), xCoordinate, yCoordinate, zCoordinate);
    }
}
