package com.palehorsestudios.alone.dayencounter;

import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.player.Player;

public class RescueHelicopterDay extends DayEncounter {

  private static DayEncounter encounter;

  private RescueHelicopterDay(){}

  public static DayEncounter getInstance() {
    if (encounter == null) {
      encounter = new RescueHelicopterDay();
    }
    return encounter;
  }

  @Override
  public String encounter(Player player) {

    if (player.getItems().contains(Item.FLARE)) {
      player.setHydration(20);
      player.updateMorale(20);
      player.setRescued(true);
      return "You hear a helicopter approaching your position rapidly from the north."
          + " You ignite your flare and wave it wildly over your head."
          + " Incredibly, they spot your flare and land on a nearby beach."
          + " They greet you with a warm blanket and tell you to hop in."
          + " You are saved, but you will never forget this incredible experience.";
    } else {
      player.updateMorale(-5);
      return "You hear a helicopter approaching your position rapidly from the north."
          + " You wave your arms and scream your lungs out, but it is no use."
          + " They continue flying south and pass your position half a mile to west."
          + " Perhaps if you had a flare they would have seen it.";
    }
  }
}

