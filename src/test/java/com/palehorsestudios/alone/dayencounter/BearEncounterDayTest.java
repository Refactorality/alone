package com.palehorsestudios.alone.dayencounter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.activity.Activity;
import com.palehorsestudios.alone.activity.GetItemActivity;
import com.palehorsestudios.alone.player.Player;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;

public class BearEncounterDayTest {
  Logger logger = Logger.getLogger(BearEncounterDayTest.class.getName());
  DayEncounter bearEncounterDay;
  Activity getItemFromShelter;
  Player player;

  @Before
  public void setUp() {
    bearEncounterDay = BearEncounterDay.getInstance();
    getItemFromShelter = GetItemActivity.getInstance();
    Set<Item> items =
        new HashSet<>(
            Arrays.asList(GameAssets.gameItems.get("KNIFE, GameAssets.gameItems.get("PISTOL, GameAssets.gameItems.get("PISTOL_CARTRIDGES));
    player = new Player(items);
    player.getShelter().addFoodToCache(Food.BEAR, 1000);
  }

  @Test
  public void testBearEncounterDayNoItems() {
    int previousMorale = player.getMorale();
    String encounterResult = bearEncounterDay.encounter(player);
    String expectedString =
        "While in the northern territories, you're just another link in the food chain. Every time "
            + "you leave to venture out into the wilderness, whatever you might be doing, "
            + "this fact is never far from your thoughts. \n"
            + "There's always the possibility of a run in with a Grizzly, and during this such outing, "
            + "you are charged by a large male asserting his claim on the territory you're in. "
            + "Without much in the way to defend yourself, and no knowledge of what to do in the "
            + "event of a bear attack, the large grizzly descends upon you. "
            + "You have died! Game over.";
    boolean validResult = false;
    if (encounterResult.equals(expectedString)) {
      validResult = true;
    }
    assertTrue(validResult);
    assertTrue(player.isDead());
    assertEquals(previousMorale - 5, player.getMorale(), .001);
  }

  @Test
  public void testBearEncounterDayWithPistolAndCartridgesItems() {
    getItemFromShelter.act(new Choice("pistol", player, (GameAssets.gameItems.get("PISTOL)));
    getItemFromShelter.act(new Choice("ammo", player, (GameAssets.gameItems.get("PISTOL_CARTRIDGES)));
    int previousMorale = player.getMorale();
    double previousWeight = player.getWeight();
    int previousHydration = player.getHydration();
    String encounterResult = bearEncounterDay.encounter(player);
    String expectedString =
        "While in the northern territories, you're just another link in the food chain. Every time "
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
    boolean validResult = false;
    if (encounterResult.equals(expectedString)) {
      validResult = true;
    }
    assertTrue(validResult);
    assertEquals(previousHydration -1, player.getHydration(), .001);
    assertEquals(previousMorale + 3, player.getMorale(), .001);
    assertEquals(previousWeight - 1.8, player.getWeight(), .001);
    assertEquals(Optional.of(1000.0 + Food.BEAR.getGrams()).get(), player.getShelter().getFoodCache().get(Food.BEAR), .001);
  }

  @Test
  public void testBearEncounterDayWithKnifeAndSurvivalManualItemsPlayerSurvives() {
    player.getShelter().addEquipment(GameAssets.gameItems.get("SURVIVAL_MANUAL, 1);
    getItemFromShelter.act(new Choice("knife", player, (GameAssets.gameItems.get("KNIFE)));
    int previousMorale = player.getMorale();
    double previousWeight = player.getWeight();
    int previousHydration = player.getHydration();
    String encounterResult = bearEncounterDay.encounter(player);
    String expectedString =
        "While in the northern territories, you're just another link in the food chain. Every time "
            + "you leave to venture out into the wilderness, whatever you might be doing, "
            + "this fact is never far from your thoughts. \n"
            + "There's always the possibility of a run in with a Grizzly, and during this such outing, "
            + "you are charged by a large male asserting his claim on the territory you're in. "
            + "Without much in the way to defend yourself besides a knife and your knowledge garnered "
            + "from the survival manual you have dutifully read in your downtime, you know what to do "
            + "in the event of a bear attack. The large grizzly descends upon you, but you successfully "
            + "defend with an equal amount of ferocity! \n"
            + "Though the bear has been wounded, he leaves you lying in a heap, battered and bloody. "
            + "You have lost a lot of blood. You had better get some rest before you succumb to "
            + "your wounds!";
    boolean validResult = false;
    if (encounterResult.equals(expectedString)) {
      validResult = true;
    }
    assertTrue(validResult);
    assertEquals(previousHydration - 3, player.getHydration(), .001);
    assertEquals(previousMorale - 4, player.getMorale(), .001);
    assertEquals(previousWeight - 2.5, player.getWeight(), .001);
  }
  @Test
  public void testBearEncounterDayWithKnifeAndSurvivalManualItemsPlayerDies() {
    player.getShelter().addEquipment(GameAssets.gameItems.get("SURVIVAL_MANUAL, 1);
    getItemFromShelter.act(new Choice("knife", player, (GameAssets.gameItems.get("KNIFE)));
    this.player.updateMorale(-1);
    this.player.updateWeight(-700);
    this.player.updateHydration(-2);
    String encounterResult = bearEncounterDay.encounter(player);
    String expectedString =
        "While in the northern territories, you're just another link in the food chain. Every time "
            + "you leave to venture out into the wilderness, whatever you might be doing, "
            + "this fact is never far from your thoughts. \n"
            + "There's always the possibility of a run in with a Grizzly, and during this such outing, "
            + "you are charged by a large male asserting his claim on the territory you're in. "
            + "Without much in the way to defend yourself besides a knife and your knowledge garnered "
            + "from the survival manual you have dutifully read in your downtime, you know what to do "
            + "in the event of a bear attack. The large grizzly descends upon you, but you successfully "
            + "defend with an equal amount of ferocity! \n"
            + "Though the bear has been wounded, he leaves you lying in a heap, battered and bloody. "
            + "You have lost a lot of blood. Mother Nature is an unmerciful matron. \n"
            + "Although you were able to fend off the bear, you have died from your wounds. Game over.";
    boolean validResult = false;
    if (encounterResult.equals(expectedString)) {
      validResult = true;
    }
    assertTrue(validResult);
    assertEquals(0, player.getHydration(), .001);
    assertEquals(0, player.getMorale(), .001);
    assertEquals(175, player.getWeight(), .001);
    assertTrue(player.isDead());
  }
}