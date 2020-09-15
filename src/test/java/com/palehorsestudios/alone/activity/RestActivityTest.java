package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.player.Player;
import com.palehorsestudios.alone.player.SuccessRate;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class RestActivityTest {

  static final double LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT = 179.8;
  static final double LOW_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT = 179.7;

  Logger logger = Logger.getLogger(RestActivityTest.class.getName());
  Activity rest;
  Player player;

  @Before
  public void setUp() {
    rest = RestActivity.getInstance();
    Set<Item> items =
        new HashSet<>(
            Arrays.asList(
                GameAssets.gameItems.get("AXE"),
                GameAssets.gameItems.get("KNIFE"),
                GameAssets.gameItems.get("FISHING_LINE"),
                GameAssets.gameItems.get("FISHING_HOOKS"),
                GameAssets.gameItems.get("WIRE"),
                GameAssets.gameItems.get("HARMONICA"),
                GameAssets.gameItems.get("FLINT_AND_STEEL"),
                GameAssets.gameItems.get("POT"),
                GameAssets.gameItems.get("FIRST_AID_KIT"),
                GameAssets.gameItems.get("COLD_WEATHER_GEAR));
    player = new Player(items);
    player.getShelter().addFoodToCache(GameAssets.gameFoods.get("FISH"), 1000);
    player.getShelter().addFoodToCache(GameAssets.gameFoods.get("SQUIRREL"), 1000);
    player.getShelter().addFoodToCache(GameAssets.gameFoods.get("RABBIT"), 1000);
    player.getShelter().addFoodToCache(GameAssets.gameFoods.get("PORCUPINE"), 1000);
    player.getShelter().addFoodToCache(GameAssets.gameFoods.get("MOOSE"), 1000);
  }

  @Test
  public void testRest() {
    int previousHydration = player.getHydration();
    String restResult = rest.act(new Choice("rest", player));
    String[] restResultWords = restResult.split(" ");
    int hours = Integer.parseInt(restResultWords[3]);
    assertEquals("You rested for " + hours + " hours and are ready for the next adventure!", restResult);
    if(hours < 4) {
      assertEquals(LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.LOW), player.getHydration());
      assertEquals(6.0, player.getMorale(), 0.001);
    } else {
      assertEquals(LOW_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
      assertEquals(7.0, player.getMorale(), 0.001);
    }
  }
}