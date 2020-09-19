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

    /**
     * Show achievements at the end of the game
     * @return returns a string
     */
    public static String showAchievementTracker(Player player){
        HashMap<String, Achievement> achievements = readAchievementsXML();
        HashMap<Activity, Integer> activityLog = getActivityLog();

        validateAchievements(achievements, activityLog);
        addPointsAchievements(player, achievements);

        List<Achievement> trueAchievements = achievements.values().stream()
                        .filter(a -> a.isAchieved())
                            .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder("Your Achievement/s:\n");
        if(!trueAchievements.isEmpty()){
            trueAchievements.forEach(e -> sb.append(e.getVisibleName()).append("\n"));
        }else {
            sb.append("No achievements");
        }
        return sb.toString();
    }

    /**
     * Compare activity successes to see if is an achievement
     * @param achievements hachmap containng achievement objects generate from the xml file
     * @param activityLog Hashmap of player's activities
     */
    private static void validateAchievements(HashMap<String, Achievement> achievements, HashMap<Activity, Integer> activityLog){
        if (!achievements.isEmpty() && !activityLog.isEmpty()){

            for(Map.Entry<String, Achievement> achievement : achievements.entrySet()){
                String name = achievement.getValue().getName();
                for (Map.Entry<Activity, Integer> activity : activityLog.entrySet())
                    if(activity.getKey().getActivityName() != null){
                        if (activity.getKey().getActivityName().equalsIgnoreCase(name)){
                            if(getSuccessfulFoodActivity(activity.getKey().getActivityName().toLowerCase()) > achievement.getValue().getMinimum()){
                                achievement.getValue().setAchieved(true);
                            }
                    }
                }
            }
        }
    }

    public static String showAchievementsInLine(Player player){
        HashMap<String, Achievement> achievements = readAchievementsXML();
        HashMap<Activity, Integer> activityLog = getActivityLog();

        validateAchievements(achievements, activityLog);
        addPointsAchievements(player, achievements);

        List<Achievement> trueAchievements = achievements.values().stream()
                .filter(a -> a.isAchieved())
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder("");
        if(!trueAchievements.isEmpty()){
            trueAchievements.forEach(e -> sb.append(e.getVisibleName()).append(". "));
        }
        return sb.toString();
    }

    public static String showAchievementsAtEndOfDay(Player player, int[]day){
        String achievements = showAchievementsInLine(player);
        if(achievements == null || achievements.isBlank()){
            return "";
        }else {
            return "Day " + day[0] + " Night: Congrats on your achievement/s: " + achievements + "\n";
        }
    }

    public static void addPointsAchievements(Player player, HashMap<String, Achievement> achievements){
        int playerScore = player.getScore();
        if (!achievements.isEmpty()){
            for(Map.Entry<String, Achievement> achievement : achievements.entrySet()) {
                if(achievement.getValue().getName() != null && isNumber(achievement.getValue().getName())){
                    String name = achievement.getValue().getName();
                    if(playerScore >= Integer.parseInt(name)){
                        achievement.getValue().setAchieved(true);
                    }
                }
            }}
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

}
