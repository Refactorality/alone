package com.palehorsestudios.alone.dayencounter;

import com.palehorsestudios.alone.player.Player;
import com.palehorsestudios.alone.util.Sound;

public abstract class DayEncounter {
  private String name;
  private String soundPath;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
    this.soundPath = "resources/Sound.Encounters/" + name.replaceAll("\\s+", "") + ".wav";
  }

  public String getSoundPath() {
    return soundPath;
  }




  public abstract String encounter(Player player);
}
