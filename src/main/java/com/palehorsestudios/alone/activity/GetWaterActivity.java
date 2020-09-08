package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.player.SuccessRate;

public class GetWaterActivity extends Activity{
  private static GetWaterActivity activityReference;

  private GetWaterActivity(){}

  public static Activity getInstance() {
    if(activityReference == null) {
      activityReference = new GetWaterActivity();
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
        Activity.getActivityBoostFactor(new Item[] {Item.IODINE_TABLETS, Item.POT, Item.EXTRA_BOOTS}, choice.getPlayer());
    int addedWater;
    int finalAddedWater;
    if (successRate == SuccessRate.LOW) {
      addedWater = 3 + ((int) Math.ceil(boostFactor * 10));
      choice.getPlayer().updateMorale(1);
    }
    else if (successRate == SuccessRate.MEDIUM) {
      addedWater = 4 + ((int) Math.ceil(boostFactor * 10));
      choice.getPlayer().updateMorale(1);
    }
    else {
      addedWater = 5 + ((int) Math.ceil(boostFactor * 10));
      choice.getPlayer().updateMorale(2);
    }
    finalAddedWater = choice.getPlayer().getShelter().updateWater(addedWater);
    return "You added " + finalAddedWater + " in the water tank.";
  }
}

