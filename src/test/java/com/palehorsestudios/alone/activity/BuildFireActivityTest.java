package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.GameAssets;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class BuildFireActivityTest {

  Logger logger = Logger.getLogger(DrinkWaterActivityTest.class.getName());
  Player player;

  @Before
  public void setUp() throws Exception {
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
                GameAssets.gameItems.get("COLD_WEATHER_GEAR")));
    player = new Player(items);
    player.getShelter().addFoodToCache(Food.FISH, 1000);
    player.getShelter().addFoodToCache(Food.SQUIRREL, 1000);
    player.getShelter().addFoodToCache(Food.RABBIT, 1000);
    player.getShelter().addFoodToCache(Food.PORCUPINE, 1000);
    player.getShelter().addFoodToCache(Food.MOOSE, 1000);
  }

  @Test
  public void testBuildFireNoWood() {
    assertEquals("You don't have any firewood.", BuildFireActivity.getInstance().act(new Choice("fire", player)));
  }

  @Test
  public void testBuildFireWithWoodNoItem() {
    List<String> results = new LinkedList<>();
    for(int i = 0; i < 1000; i++) {
      while(player.getShelter().getFirewood() == 0) {
        GatherFirewoodActivity.getInstance().act(new Choice("gather", player));
      }
      results.add((BuildFireActivity.getInstance().act(new Choice("fire", player))));
    }
    assertTrue(results.contains("It's amazing how much more bearable surviving is with a warm fire."));
    assertTrue(results.contains("That is depressing. You can't seem to get the fire started."));
    int fireCount = 0;
    for(String result : results) {
      if(result.equals("It's amazing how much more bearable surviving is with a warm fire.")) {
        fireCount++;
      }
    }
    assertTrue(fireCount > 250);
  }

  @Test
  public void testBuildFireWithWoodAndItems() {
    GetItemActivity.getInstance().act(new Choice("get", player, GameAssets.gameItems.get("FLINT_AND_STEEL")));
    List<String> results = new LinkedList<>();
    for(int i = 0; i < 1000; i++) {
      while(player.getShelter().getFirewood() == 0) {
        GatherFirewoodActivity.getInstance().act(new Choice("gather", player));
      }
      results.add((BuildFireActivity.getInstance().act(new Choice("fire", player))));
    }
    assertTrue(results.contains("It's amazing how much more bearable surviving is with a warm fire."));
    assertTrue(results.contains("That is depressing. You can't seem to get the fire started."));
    int fireCount = 0;
    for(String result : results) {
      if(result.equals("It's amazing how much more bearable surviving is with a warm fire.")) {
        fireCount++;
      }
    }
    assertTrue(fireCount > 450);
  }
}