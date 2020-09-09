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
  public void testEatHappyWithoutFire() {
    assertEquals("fish doesn't taste very good uncooked. You should consider lighting a fire.",
        eat.act(new Choice("eat", player, Food.FISH)));
    assertEquals(180.9, player.getWeight(), 0.01);
    assertEquals(660.0, player.getShelter().getFoodCache().get(Food.FISH), 0.001);
  }

  @Test
  public void testEatHappyWithFire() {
    while(player.getShelter().getFirewood() <= 0) {
      getFirewood.act(new Choice("gather firewood", player));
    }
    getItemFromShelter.act(new Choice("get flint and steel", player, (Item.FLINT_AND_STEEL)));
    while(!player.getShelter().hasFire()) {
      buildFire.act(new Choice("build fire", player));
    }
    assertEquals("You had a nice warm meal of fish cooked over your fire.", eat.act(new Choice("eat fish", player, (Food.FISH))));
    assertEquals(660.0, player.getShelter().getFoodCache().get(Food.FISH), 0.001);
  }

  @Test
  public void testEatFail() {
    assertEquals("You don't have any bug.", eat.act(new Choice("eat bug", player, (Food.BUG))));
  }
}