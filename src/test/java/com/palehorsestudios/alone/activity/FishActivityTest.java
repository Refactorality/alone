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

public class FishActivityTest {

  static final double MED_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT = 179.7;
  static final double MED_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT = 179.4;
  static final double MED_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT = 178.7;

  Logger logger = Logger.getLogger(FishActivityTest.class.getName());
  Activity getItemFromShelter;
  Activity goFishing;
  Player player;

  @Before
  public void setUp() {
    goFishing = FishActivity.getInstance();
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
  public void testGoFishingNoItems() {
    int previousHydration = player.getHydration();
    String fishingResult = goFishing.act(new Choice("go fishing", player));
    String[] possibleResults = new String[]{"I guess that's why they don't call it catching. You didn't catch any fish.",
        "It looks like you'll be eating fresh fish tonight! You caught one lake trout.",
        "I hope there's room in your food cache. You caught three white fish!"};
    boolean validResult = false;
    for(String possibleResult : possibleResults) {
      if (fishingResult.equals(possibleResult)) {
        validResult = true;
        break;
      }
    }
    assertTrue(validResult);
    if (fishingResult.equals("I guess that's why they don't call it catching. You didn't catch any fish.")) {
      assertEquals(3, player.getMorale());
      assertEquals(
          Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.FISH), 0.001);
      assertEquals(MED_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.LOW), player.getHydration());
    } else if (fishingResult.equals("It looks like you'll be eating fresh fish tonight! You caught one lake trout.")) {
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + Food.FISH.getGrams()).get(),
          player.getShelter().getFoodCache().get(Food.FISH),
          0.001);
      assertEquals(MED_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
    } else {
      assertEquals(8, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + (Food.FISH.getGrams() * 3)).get(),
          player.getShelter().getFoodCache().get(Food.FISH),
          0.001);
      assertEquals(MED_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.HIGH), player.getHydration());
    }
  }

  @Test
  public void testGoFishingWithItems() {
    getItemFromShelter.act(new Choice("fishing line", player, (GameAssets.gameItems.get("FISHING_LINE)));
    getItemFromShelter.act(new Choice("fishing hooks", player, (GameAssets.gameItems.get("FISHING_HOOKS)));
    getItemFromShelter.act(new Choice("fishing lures", player, (GameAssets.gameItems.get("FISHING_LURES)));
    int previousHydration = player.getHydration();
    String fishingResult = goFishing.act(new Choice("go fishing", player));
    String[] possibleResults = new String[]{"I guess that's why they don't call it catching. You didn't catch any fish.",
        "It looks like you'll be eating fresh fish tonight! You caught one lake trout.",
        "I hope there's room in your food cache. You caught three white fish!"};
    boolean validResult = false;
    for(String possibleResult : possibleResults) {
      if (fishingResult.equals(possibleResult)) {
        validResult = true;
        break;
      }
    }
    assertTrue(validResult);
    if (fishingResult.equals("I guess that's why they don't call it catching. You didn't catch any fish.")) {
      assertEquals(3, player.getMorale());
      assertEquals(
          Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.FISH), 0.001);
      assertEquals(MED_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.LOW), player.getHydration());
    } else if (fishingResult.equals("It looks like you'll be eating fresh fish tonight! You caught one lake trout.")) {
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + Food.FISH.getGrams() + Food.FISH.getGrams() * 0.2).get(),
          player.getShelter().getFoodCache().get(Food.FISH),
          0.001);
      assertEquals(MED_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
    } else {
      assertEquals(8, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + (Food.FISH.getGrams() * 3 + Food.FISH.getGrams() * 3 * 0.2)).get(),
          player.getShelter().getFoodCache().get(Food.FISH),
          0.001);
      assertEquals(MED_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.HIGH), player.getHydration());
    }
  }
}