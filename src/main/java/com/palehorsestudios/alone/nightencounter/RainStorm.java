package com.palehorsestudios.alone.nightencounter;

import com.palehorsestudios.alone.dayencounter.DayEncounter;
import com.palehorsestudios.alone.player.Player;

public class RainStorm extends DayEncounter {
  private static DayEncounter encounter;

  private RainStorm(){}

  public static DayEncounter getInstance() {
    if(encounter == null) {
      encounter = new RainStorm();
    }
    return encounter;
  }

  @Override
  public String encounter(Player player) {
    return "It rained last night.";
  }
}
