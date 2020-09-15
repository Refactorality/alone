package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class EatActivityTest {

    Logger logger = Logger.getLogger(EatActivityTest.class.getName());
    Activity eat;
    Activity getFirewood;
    Activity getItemFromShelter;
    Activity buildFire;
    Player player;

    @Before
    public void setUp() {
      eat = EatActivity.getInstance();
      getFirewood = GatherFirewoodActivity.getInstance();
      getItemFromShelter = GetItemActivity.getInstance();
      buildFire = BuildFireActivity.getInstance();
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
  public void testEatHappyWithoutFire() {
    assertEquals("fish doesn't taste very good uncooked. You should consider lighting a fire.",
        eat.act(new Choice("eat", player, GameAssets.gameFoods.get("FISH"))));
    assertEquals(180.9, player.getWeight(), 0.01);
    assertEquals(660.0, player.getShelter().getFoodCache().get(GameAssets.gameFoods.get("FISH")), 0.001);
  }

  @Test
  public void testEatHappyWithFire() {
    while(player.getShelter().getFirewood() <= 0) {
      getFirewood.act(new Choice("gather firewood", player));
    }
    getItemFromShelter.act(new Choice("get flint and steel", player, (GameAssets.gameItems.get("FLINT_AND_STEEL)));
    while(!player.getShelter().hasFire()) {
      buildFire.act(new Choice("build fire", player));
    }
    assertEquals("You had a nice warm meal of fish cooked over your fire.", eat.act(new Choice("eat fish", player, (GameAssets.gameFoods.get("FISH")))));
    assertEquals(660.0, player.getShelter().getFoodCache().get(GameAssets.gameFoods.get("FISH")), 0.001);
  }

  @Test
  public void testEatFail() {
    assertEquals("You don't have any bug.", eat.act(new Choice("eat bug", player, (GameAssets.gameFoods.get("BUG")))));
  }
}