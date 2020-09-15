package com.palehorsestudios.alone.nightencounter;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.activity.Activity;
import com.palehorsestudios.alone.activity.GetItemActivity;
import com.palehorsestudios.alone.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BearEncounterNightTest {

  Logger logger = Logger.getLogger(BearEncounterNightTest.class.getName());
  NightEncounter bearEncounterNight;
  Activity getItemFromShelter;
  Player player;

  @Before
  public void setUp() {
    bearEncounterNight = BearEncounterNight.getInstance();
    getItemFromShelter = GetItemActivity.getInstance();
  }

  @Test
  public void testBearEncounterNightKnifeAndSurvivalManualPlayerLives() {
    Set<Item> items =
        new HashSet<>(
            Arrays.asList(GameAssets.gameItems.get("KNIFE"), GameAssets.gameItems.get("SURVIVAL_MANUAL));
    player = new Player(items);
    player.getShelter().addEquipment(GameAssets.gameItems.get("KNIFE"), 1);
    player.getShelter().addEquipment(GameAssets.gameItems.get("SURVIVAL_MANUAL, 1);
    String encounterResult = bearEncounterNight.encounter(player);
    String expectedString =
        "You wake in the middle of the night... something is nearby. \n"
            + "You hear a coarse, weighty breathiness, the kind only a bear might make. Instinctively, "
            + "you reach for your knife. With it in hand, you slowly lift your head to glimpse at "
            + "the disturbance. \n"
            + "A massive grizzly sniffs about your camp, and he is making his way towards you and "
            + "your food cache. You jump up and make yourself as large as you can, screaming and "
            + "acting insane. The bear roars but hesitates. It's an intense showdown of the bear "
            + "charging and acting as if he's going to attack, only to stop and seemingly rethink if "
            + "he wants to tangle with you. You swipe at the air with your knife, and continue your "
            + "antics, somehow able to hold your nerve in the face of certain death. "
            + "Eventually, the bear backs down, and disappears into the darkness. \n"
            + "Reading that survival manual has paid off, although you have a sinking feeling that "
            + "the bear may be back.";
    boolean validResult = false;
    if (encounterResult.equals(expectedString)) {
      validResult = true;
    }
    assertTrue(validResult);
    assertEquals(4, player.getMorale(), .001);
  }

  @Test
  public void testBearEncounterNightNoItemsPlayerDies() {
    Set<Item> items =
        new HashSet<>(
            Arrays.asList());
    player = new Player(items);
    this.player.updateMorale(-1);
    String encounterResult = bearEncounterNight.encounter(player);
    Map foodCacheExpected = player.getShelter().getFoodCache();
    String expectedString =
        "You wake in the middle of the night... something is nearby. \n"
            + "You hear a coarse, weighty breathiness, the kind only a bear might make. Instinctively, "
            + "you slowly lift your head to glimpse at the disturbance. \n "
            + "A massive grizzly sniffs about your camp, and he is making his way towards you and "
            + "your food cache. \n"
            + "You've heard stories about people surviving bear attacks by playing dead. "
            + "Without much in the way of being able to defend yourself, you decide the best thing "
            + "for you to do is to feign your death in hopes that the grizzly will ignore you in "
            + "favor of the food you have stored in your cache. \n"
            + "Amazingly, the bear remains throughout the night, eating its fill, though it never "
            + "bothers you. Exhausted, shaken, and hungry, you watch the sun rise feeling lucky that "
            + "you survived. Unfortunately, as you were already somewhat malnourished, you are"
            + "gripped with hypothermia from your starvation and lack of calories from bodily "
            + "sources. You have died.";
    boolean validResultString = false;
    boolean validResultMap = false;
    if (encounterResult.equals(expectedString)) {
      validResultString = true;
    }
    if (foodCacheExpected.equals(player.getShelter().getFoodCache())) {
      validResultMap = true;
    }
    assertTrue(validResultMap);
    assertTrue(validResultString);
    assertTrue(player.isDead());
    assertEquals(0, player.getMorale(), .001);
  }

  @Test
  public void testBearEncounterNightNoItemsPlayerLives() {
    Set<Item> items =
        new HashSet<>(
            Arrays.asList());
    player = new Player(items);
    String encounterResult = bearEncounterNight.encounter(player);
    Map foodCacheExpected = player.getShelter().getFoodCache();
    String expectedString =
        "You wake in the middle of the night... something is nearby. \n"
            + "You hear a coarse, weighty breathiness, the kind only a bear might make. Instinctively, "
            + "you slowly lift your head to glimpse at the disturbance. \n "
            + "A massive grizzly sniffs about your camp, and he is making his way towards you and "
            + "your food cache. \n"
            + "You've heard stories about people surviving bear attacks by playing dead. "
            + "Without much in the way of being able to defend yourself, you decide the best thing "
            + "for you to do is to feign your death in hopes that the grizzly will ignore you in "
            + "favor of the food you have stored in your cache. \n"
            + "Amazingly, the bear remains throughout the night, eating its fill, though it never "
            + "bothers you. Exhausted, shaken, and hungry, you watch the sun rise feeling lucky that "
            + "you survived. Unfortunately, it seems you'll have to start over on your rations!";
    boolean validResultString = false;
    boolean validResultMap = false;
    if (encounterResult.equals(expectedString)) {
      validResultString = true;
    }
    if (foodCacheExpected.equals(player.getShelter().getFoodCache())) {
      validResultMap = true;
    }
    assertTrue(validResultMap);
    assertTrue(validResultString);
    assertEquals(1, player.getMorale(), .001);
  }

  @Test
  public void testBearEncounterNightWithPistolAndCartridgesItems() {
    Set<Item> items =
        new HashSet<>(
            Arrays.asList(GameAssets.gameItems.get("PISTOL_CARTRIDGES, GameAssets.gameItems.get("PISTOL));
    player = new Player(items);
    getItemFromShelter.act(new Choice("pistol", player, (GameAssets.gameItems.get("PISTOL)));
    getItemFromShelter.act(new Choice("ammo", player, (GameAssets.gameItems.get("PISTOL_CARTRIDGES)));
    String encounterResult = bearEncounterNight.encounter(player);
    String expectedString =
        "You wake in the middle of the night... something is nearby. \n"
            + "You hear a coarse, weighty breathiness, the kind only a bear might make. Instinctively, "
            + "you reach for your pistol. With it in hand, you open the cylinder to make sure it's loaded "
            + "and slowly lift your head to glimpse at the disturbance. \n"
            + "A massive grizzly sniffs about your camp, and he is making his way towards you and your "
            + "food cache. You slowly level your pistol center mass at the bear, and fire three shots "
            + "in quick succession. The bear attempts to bite at whatever is stinging him, but your "
            + "aim is true and the bear slumps to the ground. You set about harvesting the bear before "
            + "the meat goes to waste.";
    boolean validResult = false;
    if (encounterResult.equals(expectedString)) {
      validResult = true;
    }
    assertTrue(validResult);
    assertEquals(4, player.getHydration(), .001);
    assertEquals(4, player.getMorale(), .001);
    assertEquals(178.2, player.getWeight(), .001);
  }

  @Test
  public void testBearEncounterNightWithKnifeAndSurvivalManualItemsPlayerSurvives() {
    player.getShelter().addEquipment(GameAssets.gameItems.get("KNIFE"), 1);
    player.getShelter().addEquipment(GameAssets.gameItems.get("SURVIVAL_MANUAL, 1);
    getItemFromShelter.act(new Choice("knife", player, (GameAssets.gameItems.get("KNIFE)));
    String encounterResult = bearEncounterNight.encounter(player);
    String expectedString =
        "You wake in the middle of the night... something is nearby. \n"
            + "You hear a coarse, weighty breathiness, the kind only a bear might make. Instinctively, "
            + "you reach for your knife. With it in hand, you slowly lift your head to glimpse at "
            + "the disturbance. \n"
            + "A massive grizzly sniffs about your camp, and he is making his way towards you and "
            + "your food cache. You jump up and make yourself as large as you can, screaming and "
            + "acting insane. The bear roars but hesitates. It's an intense showdown of the bear "
            + "charging and acting as if he's going to attack, only to stop and seemingly rethink if "
            + "he wants to tangle with you. You swipe at the air with your knife, and continue your "
            + "antics, somehow able to hold your nerve in the face of certain death. "
            + "Eventually, the bear backs down, and disappears into the darkness. \n"
            + "Reading that survival manual has paid off, although you have a sinking feeling that "
            + "the bear may be back.";
    boolean validResult = false;
    if (encounterResult.equals(expectedString)) {
      validResult = true;
    }
    assertTrue(validResult);
    assertEquals(4, player.getMorale(), .001);
  }
}