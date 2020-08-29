package com.palehorsestudios.alone.player;

import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.Result;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PlayerTest {

  Player player;

  @Before
  public void setUp() {
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
                Item.WATERPROOF_JACKET,
                Item.COLD_WEATHER_GEAR));
    player = new Player(items);
    player.getShelter().addFoodToCache(Food.FISH, 1000);
  }

  @Test
  public void testEatPlayerWeight() {
    player.eat(Food.FISH);
    assertEquals(180.99, player.getWeight(), 0.01);
  }

  @Test
  public void testEatFoodCache() {
    player.eat(Food.FISH);
    assertEquals(660.0, player.getShelter().getFoodCache().get(Food.FISH), 0.001);
  }

  @Test
  public void testGoFishing() {
    Result fishingResult = player.goFishing();
    if (fishingResult.getFoodCount() == 0) {
      assertEquals(
          "I guess that's why they don't call it catching. You didn't catch any fish.",
          fishingResult.getMessage());
      assertEquals(3, player.getMorale());
      assertEquals(Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.FISH));
      assertEquals(179.74, player.getWeight(), 0.005);
    } else if (fishingResult.getFoodCount() == Food.FISH.getGrams()) {
      assertEquals(
          "It looks like you'll be eating fresh fish tonight! You caught one lake trout",
          fishingResult.getMessage());
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + Food.FISH.getGrams()).get(),
          player.getShelter().getFoodCache().get(Food.FISH));
      assertEquals(179.47, player.getWeight(), 0.005);
    } else {
      assertEquals(
          "I hope there's room in your food cache. You caught three white fish!",
          fishingResult.getMessage());
      assertEquals(8, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + (Food.FISH.getGrams() * 3)).get(),
          player.getShelter().getFoodCache().get(Food.FISH));
      assertEquals(178.95, player.getWeight(), 0.005);
    }
  }

  @Test
  public void goHunting() {}

  @Test
  public void goTrapping() {}

  @Test
  public void goForaging() {}

  @Test
  public void improveShelter() {}

  @Test
  public void gatherFirewood() {
    System.out.println(player.gatherFirewood().getMessage());
  }

  @Test
  public void getWater() {}

  @Test
  public void boostMorale() {}

  @Test
  public void rest() {}

  @Test
  public void testToString() {}

  @Test
  public void testAddItem() {}

  @Test
  public void testPutItemInShelterHappy() {
    assertEquals(
        "One harmonica moved to your shelter.",
        player.putItemInShelter(Item.HARMONICA).getMessage());
    assertEquals(Optional.of(1).get(), player.getShelter().getEquipment().get(Item.HARMONICA));
    assertFalse(player.getItems().contains(Item.HARMONICA));
  }

  @Test
  public void testPutItemInShelterFail() {
    assertEquals(
        "You do not have a(n) family photo on you.",
        player.putItemInShelter(Item.FAMILY_PHOTO).getMessage());
  }

  @Test
  public void testGetItemFromShelterHappy() {
    player.putItemInShelter(Item.HARMONICA);
    player.putItemInShelter(Item.FISHING_LINE);
    assertEquals(
        "1 harmonica removed from your shelter. You have 0 remaining.",
        player.getItemFromShelter(Item.HARMONICA).getMessage());
    assertEquals(Optional.of(0).get(), player.getShelter().getEquipment().get(Item.HARMONICA));
  }

  @Test
  public void testGetItemFromShelterFail() {
    assertEquals(
        "You do not have a(n) harmonica in your shelter.",
        player.getItemFromShelter(Item.HARMONICA).getMessage());
  }
}
