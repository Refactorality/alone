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

public class TrapActivityTest {

  static final double MED_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT = 179.7;
  static final double MED_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT = 179.4;
  static final double MED_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT = 178.7;

  Logger logger = Logger.getLogger(TrapActivityTest.class.getName());
  Activity getItemFromShelter;
  Activity goTrapping;
  Player player;

  @Before
  public void setUp() {
    goTrapping = TrapActivity.getInstance();
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
  public void testGoTrappingNoItems() {
    int previousHydration = player.getHydration();
    String trappingResult = goTrapping.act(new Choice("trap", player));
    String[] possibleResults = new String[]{"Those varmints are smarter than they look. Your traps were empty.",
        "Your patience has paid off. There were two squirrels in your traps!",
        "You'll have plenty of lucky rabbit feet now. Your snared three rabbits!"};
    boolean validResult = false;
    for(String possibleResult : possibleResults) {
      if (trappingResult.equals(possibleResult)) {
        validResult = true;
        break;
      }
    }
    assertTrue(validResult);
    if (trappingResult.equals("Those varmints are smarter than they look. Your traps were empty.")) {
      assertEquals(3, player.getMorale());
      assertEquals(
          Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(GameAssets.gameFoods.get("SQUIRREL")));
      assertEquals(Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(GameAssets.gameFoods.get("RABBIT")));
      assertEquals(MED_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.LOW), player.getHydration());
    } else if (trappingResult.equals("Your patience has paid off. There were two squirrels in your traps!")) {
      assertEquals(6, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + (GameAssets.gameFoods.get("SQUIRREL").getGrams() * 2)).get(),
          player.getShelter().getFoodCache().get(GameAssets.gameFoods.get("SQUIRREL")));
      assertEquals(MED_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
    } else {
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + (GameAssets.gameFoods.get("RABBIT").getGrams() * 3)).get(),
          player.getShelter().getFoodCache().get(GameAssets.gameFoods.get("RABBIT")));
      assertEquals(MED_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.HIGH), player.getHydration());
    }
  }

  @Test
  public void testGoTrappingWithItems() {
    getItemFromShelter.act(new Choice("wire", player, (GameAssets.gameItems.get("WIRE)));
    int previousHydration = player.getHydration();
    String trappingResult = goTrapping.act(new Choice("trap", player));
    String[] possibleResults = new String[]{"Those varmints are smarter than they look. Your traps were empty.",
        "Your patience has paid off. There were two squirrels in your traps!",
        "You'll have plenty of lucky rabbit feet now. Your snared three rabbits!"};
    boolean validResult = false;
    for(String possibleResult : possibleResults) {
      if (trappingResult.equals(possibleResult)) {
        validResult = true;
        break;
      }
    }
    assertTrue(validResult);
    if (trappingResult.equals("Those varmints are smarter than they look. Your traps were empty.")) {
      assertEquals(3, player.getMorale());
      assertEquals(
          Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(GameAssets.gameFoods.get("SQUIRREL")));
      assertEquals(Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(GameAssets.gameFoods.get("RABBIT")));
      assertEquals(MED_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.LOW), player.getHydration());
    } else if (trappingResult.equals("Your patience has paid off. There were two squirrels in your traps!")) {
      assertEquals(6, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + (GameAssets.gameFoods.get("SQUIRREL").getGrams() * 2 + GameAssets.gameFoods.get("SQUIRREL").getGrams() * 2 * 0.1))
              .get(),
          player.getShelter().getFoodCache().get(GameAssets.gameFoods.get("SQUIRREL")),
          0.001);
      assertEquals(MED_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
    } else {
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + (GameAssets.gameFoods.get("RABBIT").getGrams() * 3 + GameAssets.gameFoods.get("RABBIT").getGrams() * 3 * 0.1))
              .get(),
          player.getShelter().getFoodCache().get(GameAssets.gameFoods.get("RABBIT")),
          0.001);
      assertEquals(MED_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.HIGH), player.getHydration());
    }
  }
}