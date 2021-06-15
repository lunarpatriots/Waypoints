package com.lunarpatriots.waypoints.commands;

import com.lunarpatriots.waypoints.api.model.Waypoint;
import com.lunarpatriots.waypoints.api.repository.WaypointRepository;
import com.lunarpatriots.waypoints.util.LogUtil;
import com.lunarpatriots.waypoints.util.MessageUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created By: theLANister Date created: 06/13/2021 Date Modified: 6/14/2021
 */

public class ListCommand implements TabExecutor {
    enum Region {
        OVERWORLD("overworld", ""), NETHER("nether", "_nether"), END("end", "_the_end");

        String keyword;
        String value;

        Region(final String keyword, final String value) {
            this.keyword = keyword;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getKeyword() {
            return keyword;
        }

        public static String getRegion(final String keys) {
            String regionValue = "";
            for (Region regions : Region.values()) {
                if (regions.keyword.equals(keys)) {
                    regionValue = regions.getValue();
                }
            }

            return regionValue;
        }

        public static String getRegionMessage(final String keys) {
            String regionMessage = "";
            for (Region regions : Region.values()) {
                if (regions.keyword.equals(keys)) {
                    if (regions.keyword.equals("overworld")) {
                        regionMessage = ChatColor.AQUA + "Overworld " + ChatColor.GREEN + "Waypoints: \n";
                    } else if (regions.keyword.equals("nether")) {
                        regionMessage = ChatColor.RED + "Nether " + ChatColor.GREEN + "Waypoints: \n";
                    } else if (regions.keyword.equals("end")) {
                        regionMessage = ChatColor.LIGHT_PURPLE + "End " + ChatColor.GREEN + "Waypoints: \n";
                    }else {
                        regionMessage = "Invalid Region!";
                    }
                } 
            }

            return regionMessage;
        }
    };

    private final WaypointRepository repository;

    private ListCommand(final WaypointRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s,
            final String[] strings) {
        final Player player = (Player) commandSender;

        String msg = "";
        String waypoints = "";
        List<Waypoint> waypointRegion = new ArrayList<Waypoint>();

        try {
            if (player.getWorld().getEnvironment().equals(World.Environment.NORMAL)){
                waypointRegion = repository.getWaypoints(player.getWorld().getName() + Region.getRegion(strings[0]));
            } else if (player.getWorld().getEnvironment().equals(World.Environment.NETHER)){
                waypointRegion = repository.getWaypoints(player.getWorld().getName().replace("_nether", Region.getRegion(strings[0])));
            } else if (player.getWorld().getEnvironment().equals(World.Environment.THE_END)){
                waypointRegion = repository.getWaypoints(player.getWorld().getName().replace("_the_end", Region.getRegion(strings[0])));
            }

            waypoints = waypointRegion.stream().map(Waypoint::getName).collect(Collectors.joining("\n- "));
            if(waypoints.length() != 0){
                msg =  Region.getRegionMessage(strings[0]) + "- ";
                msg += String.format("%s", waypoints);
                MessageUtil.success(player, msg);
            } else {
                msg = "There are no valid waypoints on this region! ";
                MessageUtil.fail(player, msg);
            }
        } catch (final Exception ex) {
            LogUtil.error(ex.getMessage());
        }


        return true;
    }

    @Override
    public List<String> onTabComplete(final CommandSender commandSender, final Command command, final String s,
            final String[] strings) {

        return null;
    }
}
