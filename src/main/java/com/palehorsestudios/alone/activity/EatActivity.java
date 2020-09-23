package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;

public class EatActivity extends Activity {
  private static final int SERVING_SIZE = 340;
  private static EatActivity activityReference;

  private EatActivity(){}

  public static Activity getInstance() {
    if(activityReference == null) {
      activityReference = new EatActivity();
    }
    activityReference.setActivityName("Ate");
    return activityReference;
  }

  @Override
  public String act(Choice choice) {
    String result;
    double removalResult = choice.getPlayer().getShelter().removeFoodFromCache(choice.getFood(), SERVING_SIZE);
    if (removalResult > 0.0) {
      // update player weight
      // give a boost if they have a fire
      if(choice.getPlayer().getShelter().hasFire()) {
        choice.getPlayer().updateWeight(choice.getFood().getCaloriesPerGram() * removalResult * 1.1);
        result = "You had a nice warm meal of "  + choice.getFood() + " cooked over your fire.";
      } else {
        choice.getPlayer().updateWeight(choice.getFood().getCaloriesPerGram() * removalResult * 0.9);
        result = choice.getFood() + " doesn't taste very good uncooked. You should consider lighting a fire.";
      }
    } else {
      result = "You don't have any " + choice.getFood() + ".";
    }
    return result;
  }
}
