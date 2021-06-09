package com.lunarpatriots.waypoints.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.security.Timestamp;

@Getter
@Setter
@ToString
public class Waypoint {
    private String uuid;
    private String name;
    private int x;
    private int y;
    private int z;
    private Timestamp createdDate;
}
