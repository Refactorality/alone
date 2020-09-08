package dayencounter;

import static com.palehorsestudios.alone.Item.KNIFE;
import static com.palehorsestudios.alone.Item.PISTOL;
import static com.palehorsestudios.alone.Item.PISTOL_CARTRIDGES;
import static com.palehorsestudios.alone.Item.SURVIVAL_MANUAL;

import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.player.Player;

public class BearEncounterDay extends DayEncounter {
  private static DayEncounter encounter;

  private BearEncounterDay(){}

  public static DayEncounter getInstance() {
    if (encounter == null) {
      encounter = new BearEncounterDay();
    }
    return encounter;
  }

  @Override
  public String encounter(Player player) {

    if (player.getItems().contains(PISTOL_CARTRIDGES) && player.getItems().contains(PISTOL)) {
//        || player.getItems().contains(BOW) && player.getItems().contains(ARROWS))
//      TODO: add else if for bow and arrow
      player.setHydration(player.getHydration() - 1); //adjust according to ActivityLevel?
      player.updateWeight(player.getWeight() - 500); //adjust according to ActivityLevel?
      player.updateMorale(player.getMorale() + 3);
      player.getShelter().addFoodToCache(Food.BEAR, Food.BEAR.getGrams());
      return "While in the northern territories, you're just another link in the food chain. Every time "
          + "you leave to venture out into the wilderness, whatever you might be doing, "
          + "this fact is never far from your thoughts. \n"
          + "There's always the possibility of a run in with a Grizzly, and during this such outing, "
          + "you are charged by a large male asserting his claim on the territory you're in. "
          + "Luckily, you're packing. You draw your pistol and fire once, though the bear continues his "
          + "advance. It is unclear as to whether or not you actually hit it! The second shot rings out, "
          + "and the bear slows, though only a little as he continues his mad rush. "
          + "The bear is upon you, now, and you're close enough to see that your third shot clearly "
          + "impacts the bear in the chest, and he tumbles limply to a stop at your feet. "
          + "Your heart thunders in your ears and your skin is pocked with gooseflesh. "
          + "You stare in shock at the enormous bear before you, and several minutes pass before "
          + "you can move. \n"
          + "You set about the task of harvesting the bear, thankful you had your pistol on you!";
    } else if (player.getShelter().getEquipment().containsKey(SURVIVAL_MANUAL) && player.getItems().contains(KNIFE)) {
//    || player.getItems().contains(HATCHET)
//    || player.getItems().contains(AXE)); {
//      TODO: add else if for hatchet and axe
      player.updateMorale(player.getMorale() - 5);
      player.setHydration(player.getHydration() - 3);
      player.setWeight(player.getWeight() - 700);
        if (player.isDead()) {
          return "Mother Nature is an unmerciful matron. \n"
            + "Although you were able to fend off the bear, you have died from your wounds. Game over."; }
        else {
          return "While in the northern territories, you're just another link in the food chain. Every time "
              + "you leave to venture out into the wilderness, whatever you might be doing, "
              + "this fact is never far from your thoughts. \n"
              + "There's always the possibility of a run in with a Grizzly, and during this such outing, "
              + "you are charged by a large male asserting his claim on the territory you're in. "
              + "Without much in the way to defend yourself besides a knife and your knowledge garnered"
              + "from the survival manual you have dutifully read in your downtime, you know what to do "
              + "in the event of a bear attack. The large grizzly descends upon you, but you successfully "
              + "defend with an equal amount of ferocity! \n"
              + "Though the bear has been wounded, he leaves you lying in a heap, battered and bloody."
              + "You had better get some rest before you succumb to your wounds!";
        }
    } else {
      player.updateMorale(-10);
      return "While in the northern territories, you're just another link in the food chain. Every time "
          + "you leave to venture out into the wilderness, whatever you might be doing, "
          + "this fact is never far from your thoughts. \n"
          + "There's always the possibility of a run in with a Grizzly, and during this such outing, "
          + "you are charged by a large male asserting his claim on the territory you're in. "
          + "Without much in the way to defend yourself, and no knowledge of what to do in the"
          + "event of a bear attack, the large grizzly descends upon you."
          + "You have died! Game over.";
    }
  }
}

