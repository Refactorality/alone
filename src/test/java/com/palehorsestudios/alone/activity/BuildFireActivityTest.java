package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BuildFireActivityTest {

  Logger logger = Logger.getLogger(DrinkWaterActivityTest.class.getName());
  Player player;

  @Before
  public void setUp() throws Exception {
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
    GetItemActivity.getInstance().act(new Choice("get", player, Item.FLINT_AND_STEEL));
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