package com.palehorsestudios.alone;

import com.google.common.collect.ImmutableSet;
import com.palehorsestudios.alone.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

  Player player;

  @Before
  public void setUp() {
    Set<Item> items = new HashSet<>(Arrays.asList(
            Item.AXE,
            Item.KNIFE,
            Item.FISHING_LINE,
            Item.FISHING_HOOKS,
            Item.WIRE,
            Item.HARMONICA,
            Item.FLINT_AND_STEEL,
            Item.POT,
            Item.WATERPROOF_JACKET,
            Item.COLD_WEATHER_GEAR
    ));
    player = new Player(items);
    player.getShelter().addFood(Food.FISH, 1000);
  }

  @Test
  public void testEatPlayerWeight() {
    player.eat(Food.FISH);
    assertEquals(180.66, player.getWeight(), 0.01);
  }

  @Test
  public void testEatFoodCache() {
    player.eat(Food.FISH);
    assertEquals(773.0, player.getShelter().getFoodCache().get(Food.FISH), 0.001);
  }

  @Test
  public void goFishing() {}

  @Test
  public void goHunting() {}

  @Test
  public void goTrapping() {}

  @Test
  public void goForaging() {}

  @Test
  public void improveShelter() {}

  @Test
  public void gatherFirewood() {}

  @Test
  public void getWater() {}

  @Test
  public void boostMorale() {}

  @Test
  public void rest() {}

  @Test
  public void testToString() {}

  @Test
  public void testAddItem() {

  }

  @Test
  public void testRemoveItem() {
    player.removeItem(Item.FAMILY_PHOTO);
  }
}