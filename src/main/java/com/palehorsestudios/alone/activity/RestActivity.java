package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.player.SuccessRate;
import java.util.Random;

public class RestActivity extends Activity{
  private static RestActivity activityReference;

  private RestActivity(){}

  public static Activity getInstance() {
    if(activityReference == null) {
      activityReference = new RestActivity();
    }
    return activityReference;
  }

  @Override
  public String act(Choice choice) {
    // randomly generate the hours for rest
    Random rand = new Random();
    int hours = rand.nextInt(8);
    // calories burning rate
    SuccessRate burnRate;
    if (hours < 4) {
      burnRate = SuccessRate.LOW;
      choice.getPlayer().updateMorale(1);
    } else {
      burnRate = SuccessRate.MEDIUM;
      choice.getPlayer().updateMorale(2);
    }
    double caloriesBurned = ActivityLevel.LOW.getCaloriesBurned(burnRate);
    choice.getPlayer().updateWeight(-caloriesBurned);
    int hydrationCost = ActivityLevel.LOW.getHydrationCost(burnRate);
    choice.getPlayer().setHydration(choice.getPlayer().getHydration() - hydrationCost);
    return "You rested for " + hours + " hours and are ready for the next adventure!";
  }
}
