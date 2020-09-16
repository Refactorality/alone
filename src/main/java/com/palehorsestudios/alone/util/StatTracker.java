package com.palehorsestudios.alone.util;

import com.palehorsestudios.alone.activity.Activity;
import com.palehorsestudios.alone.dayencounter.DayEncounter;

import java.util.HashMap;
import java.util.Map;

public class StatTracker {

  // activity: what you did, when you did it

  private static final HashMap<Activity, Integer> activityLog = new HashMap<>();
  private static final HashMap<String, Integer> encounterLog = new HashMap<>();

  // enter encounter in log map
  public static void logEncounter(DayEncounter encounter){
    //     check if activity key exists, if so increment by one. else, add pair with value of 1
    //keys are strings not class types, classes may differ but encounter name is the same.
    //ex: BearEncounter vs BearEncounterNight due to refactoring
    if(encounterLog.containsKey(encounter.getName())){
      encounterLog.put(encounter.getName(), encounterLog.get(encounter.getName())+1);
    }
    else{
      encounterLog.put(encounter.getName(), 1);
    }
  }

  // enter activity in log map
  public static void logActivity(Activity activity){
//     check if activity key exists, if so increment by one. else, add pair with value of 1
    if(activityLog.containsKey(activity)){
      activityLog.put(activity, activityLog.get(activity)+1);
    }
    else{
      activityLog.put(activity, 1);
    }
  }

  // string to display activities for end screen
  public static String displayActivitiesLog(){
    StringBuilder sb = new StringBuilder("Game Stats: \n");
    for(Map.Entry<Activity, Integer> activity: activityLog.entrySet()){
      if(activity.getKey().getActivityName() != null){
        sb.append(activity.getKey().getActivityName()).append(": ").append(activity.getValue()).append("\n");
      }
    }
    return sb.toString();
  }

  // string to display encounters for end screen
  public static String displayEncountersLog(){
    StringBuilder sb = new StringBuilder("Game Events: \n");
    for(Map.Entry<String, Integer> encounter: encounterLog.entrySet()){
      if(encounter.getKey() != null){
        sb.append(encounter.getKey()).append(": ").append(encounter.getValue()).append("\n");
      }
    }
    return sb.toString();
  }
}


