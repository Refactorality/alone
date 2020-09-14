package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.GameAssets;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.player.SuccessRate;

public class ForageActivity extends Activity{
  private static ForageActivity activityReference;

  private ForageActivity(){}

  public static Activity getInstance() {
    if(activityReference == null) {
      activityReference = new ForageActivity();
    }
    return activityReference;
  }

  @Override
  public String act(Choice choice) {
    String result;
    SuccessRate successRate = generateSuccessRate();
    double caloriesBurned = ActivityLevel.LOW.getCaloriesBurned(successRate);
    choice.getPlayer().updateWeight(-caloriesBurned);
    int hydrationCost = ActivityLevel.LOW.getHydrationCost(successRate);
    choice.getPlayer().setHydration(choice.getPlayer().getHydration() - hydrationCost);
    double boostFactor =
        Activity.getActivityBoostFactor(
            new Item[] {GameAssets.gameItems.get("SURVIVAL_MANUAL"), GameAssets.gameItems.get("EXTRA_BOOTS"), GameAssets.gameItems.get("KNIFE"), GameAssets.gameItems.get("POT")}, choice.getPlayer());
    // gear, maybe we should eliminate low success rate possibility.
    if (successRate == SuccessRate.LOW) {
      choice.getPlayer().getShelter()
          .addFoodToCache(
              Food.BERRIES,
              Food.BERRIES.getGrams() * 2 + Food.BERRIES.getGrams() * 2 * boostFactor);
      choice.getPlayer().updateMorale(1);
      result = "Lucky for you, berries are ripe this time of year. You picked as many as you could carry.";
    } else if (successRate == SuccessRate.MEDIUM) {
      choice.getPlayer().getShelter()
          .addFoodToCache(
              Food.MUSHROOM,
              Food.MUSHROOM.getGrams() * 4 + Food.MUSHROOM.getGrams() * 4 * boostFactor);
      choice.getPlayer().updateMorale(1);
      result = "Delicious fungus! You found a log covered in edible mushrooms.";
    } else {
      choice.getPlayer().getShelter()
          .addFoodToCache(
              Food.BUG, Food.BUG.getGrams() * 3 + Food.BUG.getGrams() * 3 * boostFactor);
      choice.getPlayer().updateMorale(2);
      result = "You never thought you would say this, but you are thrilled to have found a large group "
          + "of leaf beetles under a decayed log. These critters are packed full of protein!";
    }
    return result;
  }
}
