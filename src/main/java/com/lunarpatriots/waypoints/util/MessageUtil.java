package com.lunarpatriots.waypoints.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/09/2021
 */
public class MessageUtil {

    private MessageUtil() {
    }

    public static void success(final Player player, final String message) {
        player.sendMessage(ChatColor.GREEN + message);
    }

    public static void error(final Player player, final String message) {
        player.sendMessage(ChatColor.RED + message);
    }
}
