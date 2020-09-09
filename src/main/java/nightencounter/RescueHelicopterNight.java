package nightencounter;

import com.palehorsestudios.alone.player.Player;

public class RescueHelicopterNight extends NightEncounter {

  private static NightEncounter encounter;

  private RescueHelicopterNight(){}

  public static NightEncounter getInstance() {
    if (encounter == null) {
      encounter = new RescueHelicopterNight();
    }
    return encounter;
  }

  @Override
  public String encounter(Player player) {

    if (player.getShelter().hasFire()) {
      player.setHydration(20);
      player.updateMorale(20);
      player.setRescued(true);
      return "You wake from a deep slumber to the sound of an approaching helicopter."
          + " Amazingly, they were ferrying people to a nearby island when they spotted your fire."
          + " They land on the beach, and greet you with a warm blanket and tell you to hop in."
          + " You are saved, but you will never forget this incredible experience.";
    } else {
      player.updateMorale(-5);
      return "You wake from a deep slumber to the sound of an approaching helicopter."
          + " You wave your arms and scream your lungs out, but it is no use."
          + " They fly right over your camp. Perhaps if you had a fire they would have seen it.";
    }
  }
}

