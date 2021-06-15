package com.lunarpatriots.waypoints.model.dto;

import com.lunarpatriots.waypoints.api.model.Waypoint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/12/2021
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class WaypointDto extends Waypoint {

    private int cost;

    public WaypointDto(final String world, final ItemMeta waypointInfo) {
        super(world, waypointInfo);
        this.cost = Integer.parseInt(waypointInfo.getLore().get(4).split(" ")[1]);
    }
}
