package com.lunarpatriots.waypoints.model;

import com.lunarpatriots.waypoints.api.model.Waypoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/12/2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaypointsList {
    private List<Waypoint> data;
}
