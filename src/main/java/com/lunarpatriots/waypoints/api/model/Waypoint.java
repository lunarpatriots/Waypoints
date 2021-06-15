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
    private int x;
    private int y;
    private int z;

    public Waypoint(final String name, final String world, final int x, final int y, final int z) {
        this.name = name;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Waypoint(final String world, final Sign sign) {
        this.world = world;
        this.name = sign.getLine(1);
        this.x = sign.getX();
        this.y = sign.getY();
        this.z = sign.getZ();
    }

    public Waypoint(final String world, final ItemMeta itemMeta) {
        final List<String> lore = itemMeta.getLore();

        this.world = world;
        this.name = itemMeta.getDisplayName();
        this.uuid = (lore.get(0).split(" ")[1]);
        this.x = Integer.parseInt(lore.get(1).split(" ")[1]);
        this.y = Integer.parseInt(lore.get(2).split(" ")[1]);
        this.z = Integer.parseInt(lore.get(3).split(" ")[1]);
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z);
    }
}
