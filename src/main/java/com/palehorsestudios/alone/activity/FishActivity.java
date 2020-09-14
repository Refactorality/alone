package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.GameAssets;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.player.SuccessRate;

public class FishActivity extends Activity {
  private static FishActivity activityReference;

  private FishActivity(){}

  public static Activity getInstance() {
    if(activityReference == null) {
      activityReference = new FishActivity();
    }
    return activityReference;
  }

  @Override
  public String act(Choice choice) {
    String result;
    SuccessRate successRate = Activity.generateSuccessRate();
    double caloriesBurned = ActivityLevel.MEDIUM.getCaloriesBurned(successRate);
    choice.getPlayer().updateWeight(-caloriesBurned);
    int hydrationCost = ActivityLevel.MEDIUM.getHydrationCost(successRate);
    choice.getPlayer().setHydration(choice.getPlayer().getHydration() - hydrationCost);
    // get boost factor based on items the player is carrying
    double boostFactor =
        Activity.getActivityBoostFactor(
            new Item[] {
                GameAssets.gameItems.get("SURVIVAL_MANUAL"), GameAssets.gameItems.get("FISHING_HOOKS"), GameAssets.gameItems.get("FISHING_LINE"), GameAssets.gameItems.get("FISHING_LURES")
            }, choice.getPlayer());
    // gear, maybe we should eliminate low success rate possibility.
    if (successRate == SuccessRate.LOW) {
      choice.getPlayer().updateMorale(-2);
      result = "I guess that's why they don't call it catching. You didn't catch any fish.";
    } else if (successRate == SuccessRate.MEDIUM) {
      choice.getPlayer().getShelter()
          .addFoodToCache(GameAssets.gameFoods.get("FISH"), GameAssets.gameFoods.get("FISH").getGrams() + GameAssets.gameFoods.get("FISH").getGrams() * boostFactor);
      choice.getPlayer().updateMorale(2);
      result = "It looks like you'll be eating fresh fish tonight! You caught one lake trout.";
    } else {
      choice.getPlayer().getShelter()
          .addFoodToCache(
              GameAssets.gameFoods.get("FISH"), GameAssets.gameFoods.get("FISH").getGrams() * 3 + GameAssets.gameFoods.get("FISH").getGrams() * 3 * boostFactor);
      choice.getPlayer().updateMorale(3);
      result = "I hope there's room in your food cache. You caught three white fish!";
    }
    return result;
  }
}
