package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.player.SuccessRate;

public class TrapActivity extends Activity {
  private static TrapActivity activityReference;

  private TrapActivity(){}

  public static Activity getInstance() {
    if (activityReference == null) {
      activityReference = new TrapActivity();
    }
    return activityReference;
  }

  @Override
  public String act(Choice choice) {
    String result;
    SuccessRate successRate = generateSuccessRate();
    double caloriesBurned = ActivityLevel.MEDIUM.getCaloriesBurned(successRate);
    choice.getPlayer().updateWeight(-caloriesBurned);
    int hydrationCost = ActivityLevel.MEDIUM.getHydrationCost(successRate);
    choice.getPlayer().setHydration(choice.getPlayer().getHydration() - hydrationCost);
    double boostFactor =
        Activity.getActivityBoostFactor(
            new Item[] {Item.SURVIVAL_MANUAL, Item.WIRE, Item.KNIFE}, choice.getPlayer());
    // gear, maybe we should eliminate low success rate possibility.
    if (successRate == SuccessRate.LOW) {
      choice.getPlayer().updateMorale(-2);
      result = "Those varmints are smarter than they look. Your traps were empty.";
    } else if (successRate == SuccessRate.MEDIUM) {
      choice
          .getPlayer()
          .getShelter()
          .addFoodToCache(
              Food.SQUIRREL,
              Food.SQUIRREL.getGrams() * 2 + Food.SQUIRREL.getGrams() * 2 * boostFactor);
      choice.getPlayer().updateMorale(1);
      result = "Your patience has paid off. There were two squirrels in your traps!";
    } else {
      choice
          .getPlayer()
          .getShelter()
          .addFoodToCache(
              Food.RABBIT, Food.RABBIT.getGrams() * 3 + Food.RABBIT.getGrams() * 3 * boostFactor);
      choice.getPlayer().updateMorale(2);
      result = "You'll have plenty of lucky rabbit feet now. Your snared three rabbits!";
      }
      return result;
    }
  }