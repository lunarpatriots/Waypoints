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
        final int firstLvlStep = 15;
        final int secondLvlStep = 30;

        final int expToLvlUp;
        if (level <= firstLvlStep) {
            final int secondLvlMultiplier = 2;
            final int firstLvlOffset = 7;
            expToLvlUp = secondLvlMultiplier * level + firstLvlOffset;
        } else if (level <=  secondLvlStep) {
            final int secondLvlMultiplier = 5;
            final int secondLvlOffset = 38;
            expToLvlUp = secondLvlMultiplier * level - secondLvlOffset;
        } else {

            final int thirdLvlMultiplier = 9;
            final int thirdLvlOffset = 158;
            expToLvlUp = thirdLvlMultiplier * level - thirdLvlOffset;
        }

        return expToLvlUp;
    }

    // Calculate total experience up to a level
    private static int getExpAtLevel(final int level) {
        final int firstLvlStep = 16;
        final int secondLvlStep = 31;

        final int currentExp;
        if (level <= firstLvlStep) {
            final int firstLvlOffset = 6 * level;
            currentExp = (int) (Math.pow(level, 2) + firstLvlOffset);
        } else if (level <= secondLvlStep) {
            final double secondLvlMultiplier = 2.5;
            final double secondLvlOffset = 40.5 * level + 360.0;
            currentExp = (int) (secondLvlMultiplier * Math.pow(level, 2) - secondLvlOffset);
        } else {
            final double thirdLvlMultiplier = 4.5;
            final double thirdLvlOffset = 162.5 * level + 2220.0;
            currentExp = (int) (thirdLvlMultiplier * Math.pow(level, 2) - thirdLvlOffset);
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
