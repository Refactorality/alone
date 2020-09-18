package com.palehorsestudios.alone.util;

import com.palehorsestudios.alone.Achievement;
import com.palehorsestudios.alone.activity.Activity;
import com.palehorsestudios.alone.util.reader.AchievementReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.palehorsestudios.alone.util.StatTracker.getActivityLog;
import static com.palehorsestudios.alone.util.StatTracker.getSuccessfulFoodActivity;
import static com.palehorsestudios.alone.util.reader.AchievementReader.readAchievementsXML;

public class AchievementTracker {

    public static String showAchievementTracker(){
        HashMap<String, Achievement> achievements = readAchievementsXML();
        HashMap<Activity, Integer> activityLog = getActivityLog();

        validateAchievements(achievements, activityLog);

        List<Achievement> trueAchievements = achievements.values().stream()
                        .filter(a -> a.isAchieved() == true)
                            .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder("Your Achievement/s:\n");
        if(!trueAchievements.isEmpty()){
            trueAchievements.forEach(e -> sb.append(e.getVisibleName()).append("\n"));
        }else {
            sb.append("No achievements");
        }
        return sb.toString();
    }

    private static void validateAchievements(HashMap<String, Achievement> achievements, HashMap<Activity, Integer> activityLog){
        if (!achievements.isEmpty() && !activityLog.isEmpty()){

            for(Map.Entry<String, Achievement> achievement : achievements.entrySet()){
                String name = achievement.getValue().getName();
                for (Map.Entry<Activity, Integer> activity : activityLog.entrySet())
                if (activity.getKey().getActivityName().equalsIgnoreCase(name)){
                        if(getSuccessfulFoodActivity(activity.getKey().getActivityName().toLowerCase()) > achievement.getValue().getMinimum()){
                            achievement.getValue().setAchieved(true);
                        }
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(readAchievementsXML());
        System.out.println(getActivityLog());
    }
}
