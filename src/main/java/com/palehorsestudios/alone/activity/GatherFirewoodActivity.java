package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.player.SuccessRate;

import static com.palehorsestudios.alone.HelperMethods.round;

public class GatherFirewoodActivity extends Activity{
  private static final double FIREWOOD_BUNDLE = 1;
  private static GatherFirewoodActivity activityReference;

  private GatherFirewoodActivity(){}

  public static Activity getInstance() {
    if(activityReference == null) {
      activityReference = new GatherFirewoodActivity();
    }
    return activityReference;
  }

  @Override
  public String act(Choice choice) {
      SuccessRate successRate = generateSuccessRate();
      double caloriesBurned = ActivityLevel.MEDIUM.getCaloriesBurned(successRate);
      choice.getPlayer().updateWeight(-caloriesBurned);
      int hydrationCost = ActivityLevel.MEDIUM.getHydrationCost(successRate);
      choice.getPlayer().setHydration(choice.getPlayer().getHydration() - hydrationCost);
      double firewoodAmount = 0.0;
      double boostFactor =
          Activity.getActivityBoostFactor(new Item[] {Item.PARACHUTE_CHORD, Item.AXE, Item.HATCHET}, choice.getPlayer());
      if (successRate == SuccessRate.LOW) {
        firewoodAmount = FIREWOOD_BUNDLE * 1.0 * (1.0 + boostFactor);
      } else if (successRate == SuccessRate.MEDIUM) {
        firewoodAmount = FIREWOOD_BUNDLE * 3.0 * (1.0 + boostFactor);
      } else if (successRate == SuccessRate.HIGH) {
        firewoodAmount = FIREWOOD_BUNDLE * 5.0 * (1.0 + boostFactor);
      }
      firewoodAmount = round(firewoodAmount, 1);
    choice.getPlayer().updateMorale((int) Math.ceil(firewoodAmount / 2.0));
    choice.getPlayer().getShelter().updateFirewood(firewoodAmount);
      return "Good Job! You just gathered " + firewoodAmount + " bundles of firewood.";
    }
}
