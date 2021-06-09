package com.lunarpatriots.waypoints.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Waypoint {
    private String uuid;
    private String name;
    private String world;
    private int x;
    private int y;
    private int z;
    private int cost;

    public Waypoint(final String name, final String world, final int x, final int y, final int z) {
        this.name = name;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Waypoint(final String uuid, final String name, final String world, final int x, final int y, final int z) {
        this.uuid = uuid;
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

    public Location getLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z);
    }
}
