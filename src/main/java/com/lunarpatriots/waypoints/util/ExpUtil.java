package com.lunarpatriots.waypoints.util;

import org.bukkit.entity.Player;

/**
 * Created By: lunarpatriots@gmail.com
 * Date created: 06/09/2021
 */
public final class ExpUtil {

    private ExpUtil() {
    }

    // Calculate amount of EXP needed to level up
    private static int getExpToLevelUp(final int level) {
        final int expToLvlUp;

        if (level <= 15) {
            expToLvlUp = 2 * level + 7;
        } else if (level <=  30) {
            expToLvlUp = 5 * level - 38;
        } else {
            expToLvlUp = 9 * level - 158;
        }

        return expToLvlUp;
    }

    // Calculate total experience up to a level
    private static int getExpAtLevel(final int level) {
        final int currentExp;

        if (level <= 16) {
            currentExp = (int) (Math.pow(level, 2) + 6 * level);
        } else if (level <= 31) {
            currentExp = (int) (2.5 * Math.pow(level, 2) - 40.5 * level + 360.0);
        } else {
            currentExp = (int) (4.5 * Math.pow(level, 2) - 162.5 * level + 2220.0);
        }

        return currentExp;
    }

    // Calculate player's current EXP amount
    public static int getPlayerExp(final Player player) {
        final int level = player.getLevel();
        int exp = 0;

        // Get the amount of XP in past levels
        exp += getExpAtLevel(level);

        // Get amount of XP towards next level
        exp += Math.round(getExpToLevelUp(level) * player.getExp());

        return exp;
    }

    public static void changePlayerExp(final Player player, final int exp) {
        if (exp != 0) {
            // Get player's current exp
            final int currentExp = getPlayerExp(player);

            // Reset player's current exp to 0
            player.setExp(0);
            player.setLevel(0);

            // Give the player their exp back, with the difference
            final int newExp = currentExp + exp;
            player.giveExp(newExp);
        }
    }
}
