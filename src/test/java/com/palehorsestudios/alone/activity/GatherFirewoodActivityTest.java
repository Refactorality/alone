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
import static org.junit.Assert.assertTrue;

public class GatherFirewoodActivityTest {
  static final double MED_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT = 179.7;
  static final double MED_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT = 179.4;
  static final double MED_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT = 178.7;

  Logger logger = Logger.getLogger(GatherFirewoodActivityTest.class.getName());
  Activity getItemFromShelter;
  Activity gatherFirewood;
  Player player;

  @Before
  public void setUp() {
    gatherFirewood = GatherFirewoodActivity.getInstance();
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
  public void testGatherFirewoodWithoutItems() {
    int previousHydration = player.getHydration();
    double previousFirewood = player.getShelter().getFirewood();
    String gatherFirewoodResult = gatherFirewood.act(new Choice("gather", player));
    double firewoodChange = player.getShelter().getFirewood() - previousFirewood;
    boolean validFirewoodChange = false;
    double[] validFirewoodChangeValues = new double[]{1.0, 3.0, 5.0};
    for(double validFirewoodChangeValue : validFirewoodChangeValues) {
      if(firewoodChange == validFirewoodChangeValue) {
        validFirewoodChange = true;
        break;
      }
    }
    assertTrue(validFirewoodChange);
    if (firewoodChange == 1.0 ) {
      assertEquals(MED_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.LOW), player.getHydration());
      assertEquals(6.0, player.getMorale(), 0.01);
    }
    else if (firewoodChange == 3.0) {
      assertEquals(MED_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
      assertEquals(7.0, player.getMorale(), 0.01);
    }
    else if (firewoodChange == 5.0) {
      assertEquals(MED_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.HIGH), player.getHydration());
      assertEquals(8.0, player.getMorale(), 0.01);
    }
    assertEquals("Good Job! You just gathered " + firewoodChange
        + " bundles of firewood.", gatherFirewoodResult);
  }

  @Test
  public void testGatherFirewoodWithItems() {
    getItemFromShelter.act(new Choice("axe", player, (Item.AXE)));
    int previousHydration = player.getHydration();
    double previousFirewood = player.getShelter().getFirewood();
    String gatherFirewoodResult = gatherFirewood.act(new Choice("gather", player));
    double firewoodChange = player.getShelter().getFirewood() - previousFirewood;
    boolean validFirewoodChange = false;
    double[] validFirewoodChangeValues = new double[]{1.1, 3.3, 5.5};
    for(double validFirewoodChangeValue : validFirewoodChangeValues) {
      if(firewoodChange == validFirewoodChangeValue) {
        validFirewoodChange = true;
        break;
      }
    }
    assertTrue(validFirewoodChange);
    if (firewoodChange == 1.1 ) {
      assertEquals(MED_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.LOW), player.getHydration());
      assertEquals(6.0, player.getMorale(), 0.01);
    }
    else if (firewoodChange == 3.3) {
      assertEquals(MED_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
      assertEquals(7.0, player.getMorale(), 0.01);
    }
    else if (firewoodChange == 5.5) {
      assertEquals(MED_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.HIGH), player.getHydration());
      assertEquals(8.0, player.getMorale(), 0.01);
    }
    assertEquals("Good Job! You just gathered " + firewoodChange
        + " bundles of firewood.", gatherFirewoodResult);
  }
}