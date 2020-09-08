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

  static final double LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT = 179.9;
  static final double LOW_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT = 179.7;
  static final double LOW_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT = 179.5;

  Logger logger = Logger.getLogger(DrinkWaterActivityTest.class.getName());
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
                Item.AXE,
                Item.KNIFE,
                Item.FISHING_LINE,
                Item.FISHING_HOOKS,
                Item.WIRE,
                Item.HARMONICA,
                Item.FLINT_AND_STEEL,
                Item.POT,
                Item.FIRST_AID_KIT,
                Item.COLD_WEATHER_GEAR));
    player = new Player(items);
    player.getShelter().addFoodToCache(Food.FISH, 1000);
    player.getShelter().addFoodToCache(Food.SQUIRREL, 1000);
    player.getShelter().addFoodToCache(Food.RABBIT, 1000);
    player.getShelter().addFoodToCache(Food.PORCUPINE, 1000);
    player.getShelter().addFoodToCache(Food.MOOSE, 1000);
  }

  @Test
  public void testGetWaterWithoutItems() {
    int previousWater = player.getShelter().getWaterTank();
    int previousHydration = player.getHydration();
    String getWaterResult = getWater.act(new Choice("get water", player));
    int waterChange = player.getShelter().getWaterTank() - previousWater;
    boolean validWaterChange = false;
    for(int i = 1; i < 4; i++) {
      if(waterChange == i) {
        validWaterChange = true;
        break;
      }
    }
    assertTrue(validWaterChange);
    if (waterChange == 1) {
      assertEquals(6, player.getMorale());
      assertEquals(LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.LOW), player.getHydration());
    }
    else if (waterChange == 2) {
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
    getItemFromShelter.act(new Choice("pot", player, (Item.POT)));
    int previousWater = player.getShelter().getWaterTank();
    int previousHydration = player.getHydration();
    String getWaterResult = getWater.act(new Choice("get water", player));
    int waterChange = player.getShelter().getWaterTank() - previousWater;
    boolean validWaterChange = false;
    for(int i = 2; i < 5; i++) {
      if(waterChange == i) {
        validWaterChange = true;
        break;
      }
    }
    assertTrue(validWaterChange);
    if (waterChange == 2) {
      assertEquals(6, player.getMorale());
      assertEquals(LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.LOW), player.getHydration());
    }
    else if (waterChange == 3) {
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