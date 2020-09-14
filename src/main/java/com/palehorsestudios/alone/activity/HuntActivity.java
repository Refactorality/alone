package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.GameAssets;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.player.SuccessRate;

public class HuntActivity extends Activity{
  private static HuntActivity activityReference;

    private HuntActivity(){}

    public static Activity getInstance() {
      if(activityReference == null) {
        activityReference = new HuntActivity();
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
      // get boost factor based on items the player is carrying
      double boostFactor =
          Activity.getActivityBoostFactor(
              new Item[] {
                  GameAssets.gameItems.get("SURVIVAL_MANUAL"),
                  GameAssets.gameItems.get("ARROWS"),
                  GameAssets.gameItems.get("BOW"),
                  GameAssets.gameItems.get("PISTOL"),
                  GameAssets.gameItems.get("PISTOL_CARTRIDGES"),
                  GameAssets.gameItems.get("KNIFE")
              },
              choice.getPlayer());
      // gear, maybe we should eliminate low success rate possibility.
      if (successRate == SuccessRate.LOW) {
        choice.getPlayer().updateMorale(-2);
        result = "I guess that's why they don't call it killing. You couldn't get a shot on an animal.";
      } else if (successRate == SuccessRate.MEDIUM) {
        choice.getPlayer().getShelter()
            .addFoodToCache(
                Food.PORCUPINE, Food.PORCUPINE.getGrams() + Food.PORCUPINE.getGrams() * boostFactor);
        choice.getPlayer().updateMorale(2);
        result = "Watch out for those quills! You killed a nice fat porcupine that should keep you fed for a while.";
      } else {
        choice.getPlayer().getShelter()
            .addFoodToCache(Food.MOOSE, Food.MOOSE.getGrams() + Food.MOOSE.getGrams() * boostFactor);
        choice.getPlayer().updateMorale(4);
        result = "Moose down! It took five trips, but you were able to process the meat and transport it back to " +
            "your shelter before a predator got to it first.";
      }
      return result;
    }
}
