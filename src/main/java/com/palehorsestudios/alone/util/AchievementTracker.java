package com.palehorsestudios.alone.util;

import com.palehorsestudios.alone.Achievement;
import com.palehorsestudios.alone.activity.Activity;
import com.palehorsestudios.alone.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.palehorsestudios.alone.util.StatTracker.getActivityLog;
import static com.palehorsestudios.alone.util.StatTracker.getSuccessfulFoodActivity;
import static com.palehorsestudios.alone.util.reader.AchievementReader.readAchievementsXML;

public class AchievementTracker {
    private static HashMap<String, Achievement> achievements = readAchievementsXML();

    /**
     * Show achievements at the end of the game
     *
     * @return returns a string
     */
    public static String showAchievementTracker(Player player) {

        HashMap<Activity, Integer> activityLog = getActivityLog();

        validateAchievements(activityLog);
        addPointsAchievements(player);

        List<Achievement> trueAchievements = achievements.values().stream()
                .filter(a -> a.isAchieved())
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder("Your Achievement/s:\n");
        if (!trueAchievements.isEmpty()) {
            trueAchievements.forEach(e -> sb.append(e.getVisibleName()).append("\n"));
        } else {
            sb.append("No achievements");
        }
        return sb.toString();
    }

    /**
     * Compare activity successes to see if is an achievement
     *
     * @param activityLog Hashmap of player's activities
     */
    private static void validateAchievements(HashMap<Activity, Integer> activityLog) {
        if (!achievements.isEmpty() && !activityLog.isEmpty()) {
            for (Map.Entry<String, Achievement> achievement : achievements.entrySet()) {
                String name = achievement.getValue().getName();
                for (Map.Entry<Activity, Integer> activity : activityLog.entrySet())
                    if (activity.getKey().getActivityName() != null) {
                        if (activity.getKey().getActivityName().equalsIgnoreCase(name)) {
                            if (getSuccessfulFoodActivity(activity.getKey().getActivityName().toLowerCase()) > achievement.getValue().getMinimum()) {
                                achievement.getValue().setAchieved(true);
                            }
                        }
                    }
            }
        }
    }

    public static String showAchievementsInLine(Player player) {
        HashMap<Activity, Integer> activityLog = getActivityLog();

        validateAchievements(activityLog);
        addPointsAchievements(player);

        List<Achievement> trueAchievements = achievements.values().stream()
                .filter(a -> a.isAchieved())
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder("");
        if (!trueAchievements.isEmpty()) {
            trueAchievements.forEach(e -> sb.append(e.getVisibleName()).append(". "));
        }
        return sb.toString();
    }

    public static String showAchievementsAtEndOfDay(Player player, int[] day) {
        String achievements = showAchievementsInLine(player);
        if (achievements == null || achievements.isBlank()) {
            return "";
        } else {
            return "Day " + day[0] + " Night: Congrats on your achievement/s: " + achievements + "\n";
        }
    }

    public static void addPointsAchievements(Player player) {
        int playerScore = player.getScore();
        if (!achievements.isEmpty()) {
            for (Map.Entry<String, Achievement> achievement : achievements.entrySet()) {
                if (achievement.getValue().getName() != null && isNumber(achievement.getValue().getName())) {
                    String name = achievement.getValue().getName();
                    if (playerScore >= Integer.parseInt(name)) {
                        achievement.getValue().setAchieved(true);
                    }
                }
            }
        }
    }

    public static boolean isNumber(String number) {
        if (number == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(number);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void discoverCrafting() {
        if (!achievements.isEmpty()) {
            for (Map.Entry<String, Achievement> achievement : achievements.entrySet()) {
                if (achievement.getValue().getName().equalsIgnoreCase("discover_crafting")) {
                    if(achievement.getValue().getMinimum() == 2){
                        achievement.getValue().setAchieved(true);
                    }
                }
            }
        }
    }

    public static String showDiscoverCrafting(int day, String dayHalf) {
        boolean print = false;
        if (!achievements.isEmpty()) {
            for (Map.Entry<String, Achievement> achievement : achievements.entrySet()) {
                if (achievement.getValue().getName().equalsIgnoreCase("discover_crafting")) {
                    if(achievement.getValue().getMinimum() == 2 && achievement.getValue().isAchieved()){
                        achievement.getValue().setMinimum(3);
                        print = true;
                    }
                }
            }
        }
        if (print == false) {
            return "";
        } else {
            return "Day " + day + " " + dayHalf + ": you " + "discover crafting" + "\n";
        }
    }
}
