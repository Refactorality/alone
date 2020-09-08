package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.player.Player;
import com.palehorsestudios.alone.player.SuccessRate;

public abstract class Activity {
  public abstract String act(Choice choice);

  /**
   * Helper method for determining if Result of player activity gets amplified.
   *
   * @param boosterItems Items that could boost activity Result if Player possesses them.
   * @return Factor by which Player activity Result gets boosted.
   */
  public static double getActivityBoostFactor(Item[] boosterItems, Player player) {
    double boostValue = 0.0;
    for (Item item : boosterItems) {
      if (player.getItems().contains(item)) {
        boostValue += 0.1;
      }
    }
    return boostValue;
  }

  /**
   * Helper method for generating a random SuccessRate.
   * @return Random SuccessRate.
   */
  public static SuccessRate generateSuccessRate() {
    int seed = ((int) Math.floor(Math.random() * 3));
    if (seed == 0) {
      return SuccessRate.LOW;
    } else if (seed == 1) {
      return SuccessRate.MEDIUM;
    } else {
      return SuccessRate.HIGH;
    }
  }
}
