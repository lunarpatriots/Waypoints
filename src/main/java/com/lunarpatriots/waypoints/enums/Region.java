package com.lunarpatriots.waypoints.enums;

import com.lunarpatriots.waypoints.exceptions.RegionException;
import org.bukkit.World.Environment;
import net.md_5.bungee.api.ChatColor;

import java.util.Arrays;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/15/2021
 */
public enum Region {
    OVERWORLD("overworld", Environment.NORMAL, "", ChatColor.AQUA + "Overworld"),
    NETHER("nether", Environment.NETHER, "_nether", ChatColor.RED  + "Nether"),
    END("end", Environment.THE_END, "_the_end", ChatColor.LIGHT_PURPLE + "End");

    private final String keyword;
    private final Environment environment;
    private final String suffix;
    private final String displayValue;

    Region(final String keyword, final Environment environment, final String suffix, final String displayValue) {
        this.keyword = keyword;
        this.environment = environment;
        this.suffix = suffix;
        this.displayValue = displayValue;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public static Region getRegion(final String keyword) throws RegionException {
       return Arrays.stream(Region.values())
           .filter(region -> region.keyword.equals(keyword))
           .findFirst()
           .orElseThrow(() -> new RegionException("Invalid region."));
    }

    public static Region getRegion(final Environment environment) throws RegionException {
        return Arrays.stream(Region.values())
            .filter(region -> region.environment.equals(environment))
            .findFirst()
            .orElseThrow(() -> new RegionException("Invalid region."));
    }
}
