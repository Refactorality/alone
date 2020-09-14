package com.palehorsestudios.alone.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.player.Player;
import com.palehorsestudios.alone.player.SuccessRate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;

public class HuntActivityTest {

  static final double HIGH_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT = 178.5;
  static final double HIGH_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT = 177.1;
  static final double HIGH_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT = 174.5;

  Logger logger = Logger.getLogger(HuntActivityTest.class.getName());
  Activity getItemFromShelter;
  Activity goHunting;
  Player player;

  @Before
  public void setUp() {
    goHunting = HuntActivity.getInstance();
    getItemFromShelter = GetItemActivity.getInstance();
    Set<Item> items =
        new HashSet<>(
            Arrays.asList(
                GameAssets.gameItems.get("AXE,
                GameAssets.gameItems.get("KNIFE,
                GameAssets.gameItems.get("FISHING_LINE,
                GameAssets.gameItems.get("FISHING_HOOKS,
                GameAssets.gameItems.get("WIRE,
                GameAssets.gameItems.get("HARMONICA,
                GameAssets.gameItems.get("FLINT_AND_STEEL,
                GameAssets.gameItems.get("POT,
                GameAssets.gameItems.get("FIRST_AID_KIT,
                GameAssets.gameItems.get("COLD_WEATHER_GEAR));
    player = new Player(items);
    player.getShelter().addFoodToCache(Food.FISH, 1000);
    player.getShelter().addFoodToCache(Food.SQUIRREL, 1000);
    player.getShelter().addFoodToCache(Food.RABBIT, 1000);
    player.getShelter().addFoodToCache(Food.PORCUPINE, 1000);
    player.getShelter().addFoodToCache(Food.MOOSE, 1000);
  }

  @Test
  public void testGoHuntingNoItems() {
    int previousHydration = player.getHydration();
    String huntingResult = goHunting.act(new Choice("go hunting", player));
    String[] possibleResults = new String[]{"I guess that's why they don't call it killing. You couldn't get a shot on an animal.",
        "Watch out for those quills! You killed a nice fat porcupine that should keep you fed for a while.",
        "Moose down! It took five trips, but you were able to process the meat and transport it back to your shelter before a predator got to it first."};
    boolean validResult = false;
    for(String possibleResult : possibleResults) {
      if (huntingResult.equals(possibleResult)) {
        validResult = true;
        break;
      }
    }
    assertTrue(validResult);
    if (huntingResult.equals("I guess that's why they don't call it killing. You couldn't get a shot on an animal.")) {
      assertEquals(3, player.getMorale());
      assertEquals(
          Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.PORCUPINE));
      assertEquals(Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.MOOSE));
      assertEquals(HIGH_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.LOW), player.getHydration());
    } else if (huntingResult.equals("Watch out for those quills! You killed a nice fat porcupine that should keep you fed for a while.")) {
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + Food.PORCUPINE.getGrams()).get(),
          player.getShelter().getFoodCache().get(Food.PORCUPINE));
      assertEquals(HIGH_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
    } else {
      assertEquals(9, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + Food.MOOSE.getGrams()).get(),
          player.getShelter().getFoodCache().get(Food.MOOSE));
      assertEquals(HIGH_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.HIGH), player.getHydration());
    }
  }

  @Test
  public void testGoHuntingWithItems() {
    getItemFromShelter.act(new Choice("knife", player, (GameAssets.gameItems.get("KNIFE)));
    getItemFromShelter.act(new Choice("bow", player, (GameAssets.gameItems.get("BOW)));
    getItemFromShelter.act(new Choice("arrows", player, (GameAssets.gameItems.get("ARROWS)));
    int previousHydration = player.getHydration();
    String huntingResult = goHunting.act(new Choice("hunting", player));
    String[] possibleResults = new String[]{"I guess that's why they don't call it killing. You couldn't get a shot on an animal.",
        "Watch out for those quills! You killed a nice fat porcupine that should keep you fed for a while.",
        "Moose down! It took five trips, but you were able to process the meat and transport it back to your shelter before a predator got to it first."};
    boolean validResult = false;
    for(String possibleResult : possibleResults) {
      if (huntingResult.equals(possibleResult)) {
        validResult = true;
        break;
      }
    }
    assertTrue(validResult);
    if (huntingResult.equals("I guess that's why they don't call it killing. You couldn't get a shot on an animal.")) {
      assertEquals(3, player.getMorale());
      assertEquals(
          Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.PORCUPINE));
      assertEquals(Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.MOOSE));
      assertEquals(HIGH_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.LOW), player.getHydration());
    } else if (huntingResult.equals("Watch out for those quills! You killed a nice fat porcupine that should keep you fed for a while.")) {
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + Food.PORCUPINE.getGrams() + Food.PORCUPINE.getGrams() * 0.1).get(),
          player.getShelter().getFoodCache().get(Food.PORCUPINE),
          0.001);
      assertEquals(HIGH_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
    } else {
      assertEquals(9, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + Food.MOOSE.getGrams() + Food.MOOSE.getGrams() * 0.1).get(),
          player.getShelter().getFoodCache().get(Food.MOOSE),
          0.001);
      assertEquals(HIGH_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.HIGH), player.getHydration());
    }
  }

}