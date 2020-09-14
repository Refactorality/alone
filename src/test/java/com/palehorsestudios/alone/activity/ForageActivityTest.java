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

public class ForageActivityTest {

  static final double LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT = 179.8;
  static final double LOW_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT = 179.7;
  static final double LOW_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT = 179.4;

  Logger logger = Logger.getLogger(ForageActivityTest.class.getName());
  Activity getItemFromShelter;
  Activity goForaging;
  Player player;

  @Before
  public void setUp() {
    goForaging = ForageActivity.getInstance();
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
    player.getShelter().addFoodToCache(GameAssets.gameFoods.get("FISH"), 1000);
    player.getShelter().addFoodToCache(GameAssets.gameFoods.get("SQUIRREL"), 1000);
    player.getShelter().addFoodToCache(GameAssets.gameFoods.get("RABBIT"), 1000);
    player.getShelter().addFoodToCache(GameAssets.gameFoods.get("PORCUPINE"), 1000);
    player.getShelter().addFoodToCache(GameAssets.gameFoods.get("MOOSE"), 1000);
  }

  @Test
  public void testGoForagingNoItems() {
    int previousHydration = player.getHydration();
    String foragingResult = goForaging.act(new Choice("forage", player));
    String[] possibleResults = new String[]{"Lucky for you, berries are ripe this time of year. You picked as many as you could carry.",
        "Delicious fungus! You found a log covered in edible mushrooms.",
        "You never thought you would say this, but you are thrilled to have found a large group "
            + "of leaf beetles under a decayed log. These critters are packed full of protein!"};
    boolean validResult = false;
    for(String possibleResult : possibleResults) {
      if (foragingResult.equals(possibleResult)) {
        validResult = true;
        break;
      }
    }
    assertTrue(validResult);
    if (foragingResult.equals("Lucky for you, berries are ripe this time of year. You picked as many as you could carry.")) {
      assertEquals(6, player.getMorale());
      assertEquals(
          Optional.of(GameAssets.gameFoods.get("BERRIES").getGrams() * 2).get(),
          player.getShelter().getFoodCache().get(GameAssets.gameFoods.get("BERRIES")));
      assertEquals(LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.LOW), player.getHydration());
    } else if (foragingResult.equals("Delicious fungus! You found a log covered in edible mushrooms.")) {
      assertEquals(6, player.getMorale());
      assertEquals(
          Optional.of(GameAssets.gameFoods.get("MUSHROOM").getGrams() * 4).get(),
          player.getShelter().getFoodCache().get(GameAssets.gameFoods.get("MUSHROOM")),
          0.001);
      assertEquals(LOW_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
    } else {
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(GameAssets.gameFoods.get("BUG").getGrams() * 3).get(),
          player.getShelter().getFoodCache().get(GameAssets.gameFoods.get("BUG")),
          0.001);
      assertEquals(LOW_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.HIGH), player.getHydration());
    }
  }

  @Test
  public void testGoForagingWithItems() {
    getItemFromShelter.act(new Choice("pot", player, (GameAssets.gameItems.get("POT)));
    getItemFromShelter.act(new Choice("pot", player, (GameAssets.gameItems.get("EXTRA_BOOTS)));
    int previousHydration = player.getHydration();
    String foragingResult = goForaging.act(new Choice("forage", player));
    String[] possibleResults = new String[]{"Lucky for you, berries are ripe this time of year. You picked as many as you could carry.",
        "Delicious fungus! You found a log covered in edible mushrooms.",
        "You never thought you would say this, but you are thrilled to have found a large group "
            + "of leaf beetles under a decayed log. These critters are packed full of protein!"};
    boolean validResult = false;
    for(String possibleResult : possibleResults) {
      if (foragingResult.equals(possibleResult)) {
        validResult = true;
        break;
      }
    }
    assertTrue(validResult);
    if (foragingResult.equals("Lucky for you, berries are ripe this time of year. You picked as many as you could carry.")) {
      assertEquals(6, player.getMorale());
      assertEquals(
          Optional.of(GameAssets.gameFoods.get("BERRIES").getGrams() * 2 + GameAssets.gameFoods.get("BERRIES").getGrams() * 2 * 0.1).get(),
          player.getShelter().getFoodCache().get(GameAssets.gameFoods.get("BERRIES")),
          0.001);
      assertEquals(LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.LOW), player.getHydration());
    } else if (foragingResult.equals("Delicious fungus! You found a log covered in edible mushrooms.")) {
      assertEquals(6, player.getMorale());
      assertEquals(
          Optional.of(GameAssets.gameFoods.get("MUSHROOM").getGrams() * 4 + GameAssets.gameFoods.get("MUSHROOM").getGrams() * 4 * 0.1).get(),
          player.getShelter().getFoodCache().get(GameAssets.gameFoods.get("MUSHROOM")),
          0.001);
      assertEquals(LOW_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
    } else {
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(GameAssets.gameFoods.get("BUG").getGrams() * 3 + GameAssets.gameFoods.get("BUG").getGrams() * 3 * 0.1).get(),
          player.getShelter().getFoodCache().get(GameAssets.gameFoods.get("BUG")),
          0.001);
      assertEquals(LOW_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.HIGH), player.getHydration());
    }
  }
}