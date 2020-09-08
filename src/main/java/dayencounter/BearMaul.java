package dayencounter;

import com.palehorsestudios.alone.player.Player;

public class BearMaul extends DayEncounter {
  private static DayEncounter encounter;

  private BearMaul(){}

  public static DayEncounter getInstance() {
    if(encounter == null) {
      encounter = new BearMaul();
    }
    return encounter;
  }

  @Override
  public String encounter(Player player) {
    player.setHydration(player.getHydration() - 5);
    player.updateMorale(-5);
    return "You were mauled by a bear!";
  }
}
