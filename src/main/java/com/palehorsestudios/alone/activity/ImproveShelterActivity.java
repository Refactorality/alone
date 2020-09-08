package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.player.SuccessRate;

public class ImproveShelterActivity extends Activity{
  private static ImproveShelterActivity activityReference;

  private ImproveShelterActivity(){}

  public static Activity getInstance() {
    if(activityReference == null) {
      activityReference = new ImproveShelterActivity();
    }
    return activityReference;
  }

  @Override
  public String act(Choice choice) {
    String result;
    SuccessRate successRate = generateSuccessRate();
    double caloriesBurned = ActivityLevel.HIGH.getCaloriesBurned(successRate);
    choice.getPlayer().updateWeight(-caloriesBurned);
    int hydrationCost = ActivityLevel.HIGH.getHydrationCost(successRate);
    choice.getPlayer().setHydration(choice.getPlayer().getHydration() - hydrationCost);
    double boostFactor = Activity.getActivityBoostFactor(new Item[]{Item.KNIFE, Item.PARACHUTE_CHORD, Item.AXE, Item.HATCHET, Item.SHOVEL, Item.SURVIVAL_MANUAL}, choice.getPlayer());
    double improvementAmount;
    if (successRate == SuccessRate.LOW) {
      improvementAmount = 1 + 1 * boostFactor;
      result = "Slowly but surely, you continue to improve on some semblance of a shelter.";
    } else if (successRate == SuccessRate.MEDIUM) {
      improvementAmount = 2 + 2 * boostFactor;
      result = "You have a new idea on a way to improve your shelter. You're confident that it will be more comfortable now.";
    } else {
      result = "Your shelter is coming along nicely, with several improvements you were able to implement.";
      improvementAmount = 3 + 3 * boostFactor;
    }
    choice.getPlayer().getShelter().setIntegrity(choice.getPlayer().getShelter().getIntegrity() + improvementAmount);
    choice.getPlayer().updateMorale((int) Math.ceil(improvementAmount / 2));
    return result;
  }
}
