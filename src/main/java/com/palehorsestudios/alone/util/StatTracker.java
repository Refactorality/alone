package com.palehorsestudios.alone.util;

import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.activity.Activity;
import com.palehorsestudios.alone.dayencounter.DayEncounter;
import com.palehorsestudios.alone.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.palehorsestudios.alone.util.AchievementTracker.showAchievementTracker;

public class StatTracker {

  // TODO: add day of activity and outcomes?

  private static final HashMap<Activity, Integer> activityLog = new HashMap<>();
  private static final HashMap<String, Integer> encounterLog = new HashMap<>();
  // key = activity -> key = Food item -> key = success + key = amount
  //{"activity" : {"food item name" : {"success" : count, "quantity" : grams} } }
  private static final HashMap<String, HashMap<String, HashMap<String, Double>>> foodLog = new HashMap<>(){
    {
      //place array list value with string keys in foodLog hashmap
      put("forage", new HashMap<>());
      put("hunted", new HashMap<>());
      put("fished", new HashMap<>());
      put("trapped", new HashMap<>());
    }

  };

  // enter encounter in log map
  public static void logEncounter(DayEncounter encounter){
    //check if activity key exists, if so increment by one. else, add pair with value of 1
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

  // enter food item to log amp
  public static void logFood(Food food, Double quantity){
    String activity = null;
    if(food.getVisibleName().equalsIgnoreCase("berries") ||
            food.getVisibleName().equalsIgnoreCase("mushroom")||
            food.getVisibleName().equalsIgnoreCase("bug")
    ){
      activity = "forage";
    }

    else if(food.getVisibleName().equalsIgnoreCase("moose") ||
            food.getVisibleName().equalsIgnoreCase("porcupine")
//            hunting you do not get bear, change this?
//            || food.getVisibleName().equalsIgnoreCase("bear")
    ){
      activity = "hunted";
    }
    else if (food.getVisibleName().equalsIgnoreCase("rabbit") ||
            food.getVisibleName().equalsIgnoreCase("squirrel")
    ){
      activity = "trapped";
    }
    else{activity = "fished";}

    if(foodLog.get(activity).get(food.getVisibleName()) == null){
      foodLog.get(activity).put(food.getVisibleName(), new HashMap<>(){
        {
          put("success", 1.0);
          put("quantity", quantity);
        }
      });
    }
    else{
      foodLog.get(activity).get(food.getVisibleName()).put("success", foodLog.get(activity).get(food.getVisibleName()).get("success") + 1.0);
      foodLog.get(activity).get(food.getVisibleName()).put("quantity", foodLog.get(activity).get(food.getVisibleName()).get("quantity") + quantity);
    }

  }

  // string to display activities for end screen
  public static String displayActivitiesLog(Player player){
    StringBuilder sb = new StringBuilder("Game Stats: \n");
    for(Map.Entry<Activity, Integer> activity: activityLog.entrySet()){
      if(activity.getKey().getActivityName() != null){
        sb.append(activity.getKey().getActivityName()).append(": ").append(activity.getValue());
        // if activity is a food activity, add number of successes to display
        if(activity.getKey().getActivityName().equalsIgnoreCase("fished")||
        activity.getKey().getActivityName().equalsIgnoreCase("hunted")||
        activity.getKey().getActivityName().equalsIgnoreCase("trapped")){
          sb.append(" | ").append("Successes: ").append(
                  getSuccessfulFoodActivity(activity.getKey().getActivityName().toLowerCase()));
        }
      sb.append("\n");
      }
    }
    showAchievementTracker(player);
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

  public static int getSuccessfulFoodActivity(String activity){
    int successfulHunts = 0;
    // check if activity has been performed
    if(foodLog.get(activity) != null){
      // get the designated activity map
      for(Map.Entry<String, HashMap<String, Double>> food : foodLog.get(activity).entrySet()){
        // for each food item in the activity map, increase successfulHunts by the success value from the food map
        for(Map.Entry<String, Double> data: food.getValue().entrySet()){
          if("success".equalsIgnoreCase(data.getKey())){
            successfulHunts += data.getValue();
          }
        }
      }
    }

    return  successfulHunts;
  }

  public static void displayFoodLog(){
    for(Map.Entry<String, HashMap<String, HashMap<String, Double>>> activity : foodLog.entrySet()){
      System.out.println(activity);
//      for(Map.Entry<String, HashMap<String, Double>> food : activity.getValue().entrySet()){
//        System.out.println(food);
//        for(Map.Entry<String, Double> data : food.getValue().entrySet()){
//          System.out.println(data.getKey());
//          System.out.println(data.getValue());
//        }
//      }
    }
  }

  public static HashMap<Activity, Integer> getActivityLog() {
    return activityLog;
  }
}


