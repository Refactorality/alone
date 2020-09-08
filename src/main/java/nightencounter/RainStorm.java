package nightencounter;

import com.palehorsestudios.alone.player.Player;

public class RainStorm extends NightEncounter {
  private static NightEncounter encounter;

  private RainStorm(){}

  public static NightEncounter getInstance() {
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
