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
import java.util.Set;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;

public class GetWaterActivityTest {

  static final double LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT = 179.8;
  static final double LOW_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT = 179.7;
  static final double LOW_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT = 179.4;

  Logger logger = Logger.getLogger(GetWaterActivityTest.class.getName());
  Activity getItemFromShelter;
  Activity getWater;
  Player player;

  @Before
  public void setUp() {
    getWater = GetWaterActivity.getInstance();
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
  public void testGetWaterWithoutItems() {
    int previousWater = player.getShelter().getWaterTank();
    int previousHydration = player.getHydration();
    String getWaterResult = getWater.act(new Choice("get water", player));
    int waterChange = player.getShelter().getWaterTank() - previousWater;
    boolean validWaterChange = false;
    for(int i = 4; i < 7; i++) {
      if(waterChange == i) {
        validWaterChange = true;
        break;
      }
    }
    assertTrue(validWaterChange);
    if (waterChange == 4) {
      assertEquals(6, player.getMorale());
      assertEquals(LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.LOW), player.getHydration());
    }
    else if (waterChange == 5) {
      assertEquals(6, player.getMorale());
      assertEquals(LOW_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
    }
    else {
      assertEquals(7, player.getMorale());
      assertEquals(LOW_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.HIGH), player.getHydration());
    }
    assertEquals("You added " + waterChange + " in the water tank.", getWaterResult);
  }

  @Test
  public void testGetWaterWithItems() {
    getItemFromShelter.act(new Choice("pot", player, (GameAssets.gameItems.get("POT)));
    int previousWater = player.getShelter().getWaterTank();
    int previousHydration = player.getHydration();
    String getWaterResult = getWater.act(new Choice("get water", player));
    int waterChange = player.getShelter().getWaterTank() - previousWater;
    boolean validWaterChange = false;
    for(int i = 5; i < 8; i++) {
      if(waterChange == i) {
        validWaterChange = true;
        break;
      }
    }
    assertTrue(validWaterChange);
    if (waterChange == 5) {
      assertEquals(6, player.getMorale());
      assertEquals(LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.LOW), player.getHydration());
    }
    else if (waterChange == 6) {
      assertEquals(6, player.getMorale());
      assertEquals(LOW_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
    }
    else {
      assertEquals(7, player.getMorale());
      assertEquals(LOW_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.HIGH), player.getHydration());
    }
    assertEquals("You added " + waterChange + " in the water tank.", getWaterResult);
  }
}