package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.player.SuccessRate;

public class BuildFireActivity extends Activity{
  private static BuildFireActivity activityReference;

  private BuildFireActivity(){}

  public static Activity getInstance() {
    if(activityReference == null) {
      activityReference = new BuildFireActivity();
    }
    return activityReference;
  }

  @Override
  public String act(Choice choice) {
    String result;
    if(choice.getPlayer().getShelter().getFirewood() <= 0) {
      result = "You don't have any firewood.";
    } else {
      double boostFactor =
          choice.getPlayer().getActivityBoostFactor(new Item[] {Item.SURVIVAL_MANUAL, Item.LIGHTER, Item.MATCHES, Item.FLINT_AND_STEEL});
      SuccessRate successRate;
      if(boostFactor == 0.0) {
        successRate = SuccessRate.LOW;
        choice.getPlayer().updateWeight(-ActivityLevel.LOW.getCaloriesBurned(SuccessRate.HIGH));
        choice.getPlayer().setHydration(choice.getPlayer().getHydration() - ActivityLevel.LOW.getHydrationCost(SuccessRate.HIGH));
      } else if(boostFactor == 0.1) {
        successRate = SuccessRate.MEDIUM;
        choice.getPlayer().updateWeight(-ActivityLevel.LOW.getCaloriesBurned(SuccessRate.MEDIUM));
        choice.getPlayer().setHydration(choice.getPlayer().getHydration() - ActivityLevel.LOW.getHydrationCost(SuccessRate.MEDIUM));
      } else if(boostFactor == 0.2) {
        successRate = SuccessRate.MEDIUM;
        choice.getPlayer().updateWeight(-ActivityLevel.LOW.getCaloriesBurned(SuccessRate.MEDIUM));
        choice.getPlayer().setHydration(choice.getPlayer().getHydration() - ActivityLevel.LOW.getHydrationCost(SuccessRate.MEDIUM));
      } else {
        successRate = SuccessRate.HIGH;
        choice.getPlayer().updateWeight(-ActivityLevel.LOW.getCaloriesBurned(SuccessRate.LOW));
        choice.getPlayer().setHydration(choice.getPlayer().getHydration() - ActivityLevel.LOW.getHydrationCost(SuccessRate.LOW));
      }
      int attemptOutcome;
      if (successRate == SuccessRate.LOW) {
        attemptOutcome = (int) Math.floor(Math.random() * 4);
      } else if (successRate == SuccessRate.MEDIUM) {
        attemptOutcome = (int) Math.floor(Math.random() * 2);
      } else {
        attemptOutcome = 1;
      }
      if(attemptOutcome == 1) {
        choice.getPlayer().getShelter().setFire(true);
        choice.getPlayer().updateMorale(2);
        choice.getPlayer().getShelter().updateFirewood(-1);
        result = "It's amazing how much more bearable surviving is with a warm fire.";
      } else {
        choice.getPlayer().getShelter().setFire(false);
        choice.getPlayer().updateMorale(-2);
        result = "That is depressing. You can't seem to get the fire started.";
      }
    }
    return result;
  }
}
