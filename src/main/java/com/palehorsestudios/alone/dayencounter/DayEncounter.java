package com.palehorsestudios.alone.dayencounter;

import com.palehorsestudios.alone.player.Player;

public abstract class DayEncounter {
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public abstract String encounter(Player player);
}
