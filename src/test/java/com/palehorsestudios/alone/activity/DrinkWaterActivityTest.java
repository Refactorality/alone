package com.palehorsestudios.alone.activity;

import static org.junit.Assert.assertEquals;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.player.Player;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;

public class DrinkWaterActivityTest {
  Logger logger = Logger.getLogger(DrinkWaterActivityTest.class.getName());
  Activity drinkWater;
  Activity getWater;
  Player player;

  @Before
  public void setUp() {
    drinkWater = DrinkWaterActivity.getInstance();
    getWater = GetWaterActivity.getInstance();
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
  public void testDrinkWaterHappyOneWater() {
    player.getShelter().updateWater(-100);
    player.getShelter().updateWater(1);
    int previousHydration = player.getHydration();
    int previousWaterTank = player.getShelter().getWaterTank();
    assertEquals("That's better. Your hydration is now at "
        + (previousHydration + 1)
        + ", and you have "
        + (previousWaterTank - 1)
        + " water(s) remaining.", drinkWater.act(new Choice("drink", player)));
  }

  @Test
  public void testDrinkWaterHappyTwoWaters() {
    player.getShelter().updateWater(-100);
    player.getShelter().updateWater(2);
    int previousHydration = player.getHydration();
    int previousWaterTank = player.getShelter().getWaterTank();
    assertEquals("That's better. Your hydration is now at "
        + (previousHydration + 2)
        + ", and you have "
        + (previousWaterTank - 2)
        + " water(s) remaining.", drinkWater.act(new Choice("drink", player)));
  }

  @Test
  public void testDrinkWaterHappyThreeWaters() {
    while(player.getShelter().getWaterTank() < 3) {
      GetWaterActivity.getInstance().act(new Choice("water", player));
    }
    int previousHydration = player.getHydration();
    int previousWaterTank = player.getShelter().getWaterTank();
    assertEquals("That's better. Your hydration is now at "
        + (previousHydration + 3)
        + ", and you have "
        + (previousWaterTank - 3)
        + " water(s) remaining.", drinkWater.act(new Choice("drink", player)));
  }

  @Test
  public void testDrinkWaterFail() {
    assertEquals(
        "There isn't a drop left in your water tank. You should go fetch some water soon before you die of thirst!",
        DrinkWaterActivity.getInstance().act(new Choice("drink", player)));
    assertEquals(0, player.getShelter().getWaterTank());
  }
}