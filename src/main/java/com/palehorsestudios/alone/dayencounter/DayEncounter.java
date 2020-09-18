package com.palehorsestudios.alone.dayencounter;

import com.palehorsestudios.alone.player.Player;
import com.palehorsestudios.alone.util.Sound;

public abstract class DayEncounter {
  private String name;
  private final Sound encounterSound = new Sound("resources/Sound.Encounters/bearsound.wav", 3000);


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSoundPath(String path){
    this.encounterSound.setSoundPath(path);
  }

  public void playSound(){
    if(this.encounterSound.getSoundPath() != null){
      encounterSound.playSound();
    }
  }

  public abstract String encounter(Player player);
}
