package com.palehorsestudios.alone.player;

import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.Item;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

  static final double LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT = 179.9;
  static final double LOW_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT = 179.7;
  static final double LOW_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT = 179.5;
  static final double MED_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT = 179.7;
  static final double MED_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT = 179.5;
  static final double MED_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT = 178.9;
  static final double HIGH_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT = 178.8;
  static final double HIGH_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT = 177.5;
  static final double HIGH_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT = 175.4;

  Player player;
  Logger logger = Logger.getLogger(PlayerTest.class.getName());

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
  public void testDrinkWaterHappy() {
    while(player.getShelter().getWaterTank() == 0) {
      player.getWater();
    }
    int previousHydration = player.getHydration();
    int previousWaterTank = player.getShelter().getWaterTank();
    assertEquals("That's better. Your hydration is now at "
        + (previousHydration + 1)
        + ", and you have "
        + (previousWaterTank - 1)
        + " water(s) remaining.", player.drinkWater());
  }

  @Test
  public void testDrinkWaterFail() {
    assertEquals(
        "There isn't a drop left in your water tank. You should go fetch some water soon before you die of thirst!",
        player.drinkWater());
    assertEquals(0, player.getShelter().getWaterTank());
  }

  @Test
  public void testEatHappyWithoutFire() {
    assertEquals("fish doesn't taste very good uncooked. You should consider lighting a fire.", player.eat(Food.FISH));
    assertEquals(180.9, player.getWeight(), 0.01);
    assertEquals(660.0, player.getShelter().getFoodCache().get(Food.FISH), 0.001);
  }

  @Test
  public void testEatHappyWithFire() {
    while(player.getShelter().getFirewood() <= 0) {
      player.gatherFirewood();
    }
    player.getItemFromShelter(Item.FLINT_AND_STEEL);
    while(!player.getShelter().hasFire()) {
      player.buildFire();
    }
    assertEquals("You had a nice warm meal of fish cooked over your fire.", player.eat(Food.FISH));
    assertEquals(660.0, player.getShelter().getFoodCache().get(Food.FISH), 0.001);
  }

  @Test
  public void testEatFail() {
    assertEquals("You don't have any bug.", player.eat(Food.BUG));
  }

  @Test
  public void testGoFishingNoItems() {
    int previousHydration = player.getHydration();
    String fishingResult = player.goFishing();
    String[] possibleResults = new String[]{"I guess that's why they don't call it catching. You didn't catch any fish.",
        "It looks like you'll be eating fresh fish tonight! You caught one lake trout.",
        "I hope there's room in your food cache. You caught three white fish!"};
    boolean validResult = false;
    for(String possibleResult : possibleResults) {
      if (fishingResult.equals(possibleResult)) {
        validResult = true;
        break;
      }
    }
    assertTrue(validResult);
    if (fishingResult.equals("I guess that's why they don't call it catching. You didn't catch any fish.")) {
      assertEquals(3, player.getMorale());
      assertEquals(
          Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.FISH), 0.001);
      assertEquals(MED_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.LOW), player.getHydration());
    } else if (fishingResult.equals("It looks like you'll be eating fresh fish tonight! You caught one lake trout.")) {
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + Food.FISH.getGrams()).get(),
          player.getShelter().getFoodCache().get(Food.FISH),
          0.001);
      assertEquals(MED_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
    } else {
      assertEquals(8, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + (Food.FISH.getGrams() * 3)).get(),
          player.getShelter().getFoodCache().get(Food.FISH),
          0.001);
      assertEquals(MED_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.HIGH), player.getHydration());
    }
  }

  @Test
  public void testGoFishingWithItems() {
    player.getItemFromShelter(Item.FISHING_LINE);
    player.getItemFromShelter(Item.FISHING_HOOKS);
    int previousHydration = player.getHydration();
    String fishingResult = player.goFishing();
    String[] possibleResults = new String[]{"I guess that's why they don't call it catching. You didn't catch any fish.",
        "It looks like you'll be eating fresh fish tonight! You caught one lake trout.",
        "I hope there's room in your food cache. You caught three white fish!"};
    boolean validResult = false;
    for(String possibleResult : possibleResults) {
      if (fishingResult.equals(possibleResult)) {
        validResult = true;
        break;
      }
    }
    assertTrue(validResult);
    if (fishingResult.equals("I guess that's why they don't call it catching. You didn't catch any fish.")) {
      assertEquals(3, player.getMorale());
      assertEquals(
          Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.FISH), 0.001);
      assertEquals(MED_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.LOW), player.getHydration());
    } else if (fishingResult.equals("It looks like you'll be eating fresh fish tonight! You caught one lake trout.")) {
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + Food.FISH.getGrams() + Food.FISH.getGrams() * 0.2).get(),
          player.getShelter().getFoodCache().get(Food.FISH),
          0.001);
      assertEquals(MED_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
    } else {
      assertEquals(8, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + (Food.FISH.getGrams() * 3 + Food.FISH.getGrams() * 3 * 0.2)).get(),
          player.getShelter().getFoodCache().get(Food.FISH),
          0.001);
      assertEquals(MED_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.HIGH), player.getHydration());
    }
  }

  @Test
  public void testGoHuntingNoItems() {
    int previousHydration = player.getHydration();
    String huntingResult = player.goHunting();
    String[] possibleResults = new String[]{"I guess that's why they don't call it killing. You couldn't get a shot on an animal.",
        "Watch out for those quills! You killed a nice fat porcupine that should keep you fed for a while.",
        "Moose down! It took five trips, but you were able to process the meat and transport it back to your shelter before a predator got to it first."};
    boolean validResult = false;
    for(String possibleResult : possibleResults) {
      if (huntingResult.equals(possibleResult)) {
        validResult = true;
        break;
      }
    }
    assertTrue(validResult);
    if (huntingResult.equals("I guess that's why they don't call it killing. You couldn't get a shot on an animal.")) {
      assertEquals(3, player.getMorale());
      assertEquals(
          Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.PORCUPINE));
      assertEquals(Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.MOOSE));
      assertEquals(HIGH_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.LOW), player.getHydration());
    } else if (huntingResult.equals("Watch out for those quills! You killed a nice fat porcupine that should keep you fed for a while.")) {
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + Food.PORCUPINE.getGrams()).get(),
          player.getShelter().getFoodCache().get(Food.PORCUPINE));
      assertEquals(HIGH_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
    } else {
      assertEquals(9, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + Food.MOOSE.getGrams()).get(),
          player.getShelter().getFoodCache().get(Food.MOOSE));
      assertEquals(HIGH_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.HIGH), player.getHydration());
    }
  }

  @Test
  public void testGoHuntingWithItems() {
    player.getItemFromShelter(Item.KNIFE);
    player.getItemFromShelter(Item.BOW);
    player.getItemFromShelter(Item.ARROWS);
    int previousHydration = player.getHydration();
    String huntingResult = player.goHunting();
    String[] possibleResults = new String[]{"I guess that's why they don't call it killing. You couldn't get a shot on an animal.",
        "Watch out for those quills! You killed a nice fat porcupine that should keep you fed for a while.",
        "Moose down! It took five trips, but you were able to process the meat and transport it back to your shelter before a predator got to it first."};
    boolean validResult = false;
    for(String possibleResult : possibleResults) {
      if (huntingResult.equals(possibleResult)) {
        validResult = true;
        break;
      }
    }
    assertTrue(validResult);
    if (huntingResult.equals("I guess that's why they don't call it killing. You couldn't get a shot on an animal.")) {
      assertEquals(3, player.getMorale());
      assertEquals(
          Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.PORCUPINE));
      assertEquals(Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.MOOSE));
      assertEquals(HIGH_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.LOW), player.getHydration());
    } else if (huntingResult.equals("Watch out for those quills! You killed a nice fat porcupine that should keep you fed for a while.")) {
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + Food.PORCUPINE.getGrams() + Food.PORCUPINE.getGrams() * 0.1).get(),
          player.getShelter().getFoodCache().get(Food.PORCUPINE),
          0.001);
      assertEquals(HIGH_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
    } else {
      assertEquals(9, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + Food.MOOSE.getGrams() + Food.MOOSE.getGrams() * 0.1).get(),
          player.getShelter().getFoodCache().get(Food.MOOSE),
          0.001);
      assertEquals(HIGH_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.HIGH), player.getHydration());
    }
  }

  @Test
  public void testGoTrappingNoItems() {
    int previousHydration = player.getHydration();
    String trappingResult = player.goTrapping();
    String[] possibleResults = new String[]{"Those varmints are smarter than they look. Your traps were empty.",
        "Your patience has paid off. There were two squirrels in your traps!",
        "You'll have plenty of lucky rabbit feet now. Your snared three rabbits!"};
    boolean validResult = false;
    for(String possibleResult : possibleResults) {
      if (trappingResult.equals(possibleResult)) {
        validResult = true;
        break;
      }
    }
    assertTrue(validResult);
    if (trappingResult.equals("Those varmints are smarter than they look. Your traps were empty.")) {
      assertEquals(3, player.getMorale());
      assertEquals(
          Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.SQUIRREL));
      assertEquals(Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.RABBIT));
      assertEquals(MED_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.LOW), player.getHydration());
    } else if (trappingResult.equals("Your patience has paid off. There were two squirrels in your traps!")) {
      assertEquals(6, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + (Food.SQUIRREL.getGrams() * 2)).get(),
          player.getShelter().getFoodCache().get(Food.SQUIRREL));
      assertEquals(MED_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
    } else {
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + (Food.RABBIT.getGrams() * 3)).get(),
          player.getShelter().getFoodCache().get(Food.RABBIT));
      assertEquals(MED_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.HIGH), player.getHydration());
    }
  }

  @Test
  public void testGoTrappingWithItems() {
    player.getItemFromShelter(Item.WIRE);
    int previousHydration = player.getHydration();
    String trappingResult = player.goTrapping();
    String[] possibleResults = new String[]{"Those varmints are smarter than they look. Your traps were empty.",
        "Your patience has paid off. There were two squirrels in your traps!",
        "You'll have plenty of lucky rabbit feet now. Your snared three rabbits!"};
    boolean validResult = false;
    for(String possibleResult : possibleResults) {
      if (trappingResult.equals(possibleResult)) {
        validResult = true;
        break;
      }
    }
    assertTrue(validResult);
    if (trappingResult.equals("Those varmints are smarter than they look. Your traps were empty.")) {
      assertEquals(3, player.getMorale());
      assertEquals(
          Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.SQUIRREL));
      assertEquals(Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.RABBIT));
      assertEquals(MED_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.LOW), player.getHydration());
    } else if (trappingResult.equals("Your patience has paid off. There were two squirrels in your traps!")) {
      assertEquals(6, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + (Food.SQUIRREL.getGrams() * 2 + Food.SQUIRREL.getGrams() * 2 * 0.1))
              .get(),
          player.getShelter().getFoodCache().get(Food.SQUIRREL),
          0.001);
      assertEquals(MED_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
    } else {
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + (Food.RABBIT.getGrams() * 3 + Food.RABBIT.getGrams() * 3 * 0.1))
              .get(),
          player.getShelter().getFoodCache().get(Food.RABBIT),
          0.001);
      assertEquals(MED_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.HIGH), player.getHydration());
    }
  }

  @Test
  public void testGoForagingNoItems() {
    int previousHydration = player.getHydration();
    String foragingResult = player.goForaging();
    String[] possibleResults = new String[]{"Lucky for you, berries are ripe this time of year. You picked as many as you could carry.",
        "Delicious fungus! You found a log covered in edible mushrooms.",
        "You never thought you would say this, but you are thrilled to have found a large group "
            + "of leaf beetles under a decayed log. These critters are packed full of protein!"};
    boolean validResult = false;
    for(String possibleResult : possibleResults) {
      if (foragingResult.equals(possibleResult)) {
        validResult = true;
        break;
      }
    }
    assertTrue(validResult);
    if (foragingResult.equals("Lucky for you, berries are ripe this time of year. You picked as many as you could carry.")) {
      assertEquals(6, player.getMorale());
      assertEquals(
          Optional.of(Food.BERRIES.getGrams() * 2).get(),
          player.getShelter().getFoodCache().get(Food.BERRIES));
      assertEquals(LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.LOW), player.getHydration());
    } else if (foragingResult.equals("Delicious fungus! You found a log covered in edible mushrooms.")) {
      assertEquals(6, player.getMorale());
      assertEquals(
          Optional.of(Food.MUSHROOM.getGrams() * 4).get(),
          player.getShelter().getFoodCache().get(Food.MUSHROOM),
          0.001);
      assertEquals(LOW_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
    } else {
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(Food.BUG.getGrams() * 3).get(),
          player.getShelter().getFoodCache().get(Food.BUG),
          0.001);
      assertEquals(LOW_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.HIGH), player.getHydration());
    }
  }

  @Test
  public void testGoForagingWithItems() {
    player.getItemFromShelter(Item.POT);
    player.getItemFromShelter(Item.EXTRA_BOOTS);
    int previousHydration = player.getHydration();
    String foragingResult = player.goForaging();
    String[] possibleResults = new String[]{"Lucky for you, berries are ripe this time of year. You picked as many as you could carry.",
        "Delicious fungus! You found a log covered in edible mushrooms.",
        "You never thought you would say this, but you are thrilled to have found a large group "
            + "of leaf beetles under a decayed log. These critters are packed full of protein!"};
    boolean validResult = false;
    for(String possibleResult : possibleResults) {
      if (foragingResult.equals(possibleResult)) {
        validResult = true;
        break;
      }
    }
    assertTrue(validResult);
    if (foragingResult.equals("Lucky for you, berries are ripe this time of year. You picked as many as you could carry.")) {
      assertEquals(6, player.getMorale());
      assertEquals(
          Optional.of(Food.BERRIES.getGrams() * 2 + Food.BERRIES.getGrams() * 2 * 0.1).get(),
          player.getShelter().getFoodCache().get(Food.BERRIES),
          0.001);
      assertEquals(LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.LOW), player.getHydration());
    } else if (foragingResult.equals("Delicious fungus! You found a log covered in edible mushrooms.")) {
      assertEquals(6, player.getMorale());
      assertEquals(
          Optional.of(Food.MUSHROOM.getGrams() * 4 + Food.MUSHROOM.getGrams() * 4 * 0.1).get(),
          player.getShelter().getFoodCache().get(Food.MUSHROOM),
          0.001);
      assertEquals(LOW_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
    } else {
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(Food.BUG.getGrams() * 3 + Food.BUG.getGrams() * 3 * 0.1).get(),
          player.getShelter().getFoodCache().get(Food.BUG),
          0.001);
      assertEquals(LOW_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.HIGH), player.getHydration());
    }
  }

  @Test
  public void testImproveShelterNoItems() {
    double previousIntegrity = player.getShelter().getIntegrity();
    int previousHydration = player.getHydration();
    String shelterImprovementResult = player.improveShelter();
    String[] possibleResults = new String[]{"You can sleep a little better at night. You were able to better "
        + "insulate the walls of your shelter.",
        "It's always nice to be able to get out of the rain and snow. "
            + "Your roof is in better shape now.",
        "It was a lot of work, but your improved fireplace will keep "
            + "you much warmer tonight"};
    boolean validResult = false;
    for(String possibleResult : possibleResults) {
      if (shelterImprovementResult.equals(possibleResult)) {
        validResult = true;
        break;
      }
    }
    assertTrue(validResult);
    double shelterIntegrityChange = player.getShelter().getIntegrity() - previousIntegrity;
    if (shelterIntegrityChange < 2) {
      assertEquals(4.0, player.getShelter().getIntegrity(), 0.001);
      assertEquals(6, player.getMorale());
      assertEquals(HIGH_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.LOW), player.getHydration());
    } else if (shelterIntegrityChange < 3) {
      assertEquals(5.0, player.getShelter().getIntegrity(), 0.001);
      assertEquals(6, player.getMorale());
      assertEquals(HIGH_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
    } else {
      assertEquals(6.0, player.getShelter().getIntegrity(), 0.001);
      assertEquals(7, player.getMorale());
      assertEquals(HIGH_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.HIGH), player.getHydration());
    }
  }

  @Test
  public void testImproveShelterWithItems() {
    player.getItemFromShelter(Item.AXE);
    double previousIntegrity = player.getShelter().getIntegrity();
    int previousHydration = player.getHydration();
    String shelterImprovementResult = player.improveShelter();
    String[] possibleResults = new String[]{"You can sleep a little better at night. You were able to better "
        + "insulate the walls of your shelter.",
        "It's always nice to be able to get out of the rain and snow. "
            + "Your roof is in better shape now.",
        "It was a lot of work, but your improved fireplace will keep "
            + "you much warmer tonight"};
    boolean validResult = false;
    for(String possibleResult : possibleResults) {
      if (shelterImprovementResult.equals(possibleResult)) {
        validResult = true;
        break;
      }
    }
    assertTrue(validResult);
    double shelterIntegrityChange = player.getShelter().getIntegrity() - previousIntegrity;
    if (shelterIntegrityChange < 2) {
      assertEquals(4.1, player.getShelter().getIntegrity(), 0.001);
      assertEquals(6, player.getMorale());
      assertEquals(HIGH_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.LOW), player.getHydration());
    } else if (shelterIntegrityChange < 3) {
      assertEquals(5.2, player.getShelter().getIntegrity(), 0.001);
      assertEquals(7, player.getMorale());
      assertEquals(HIGH_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.MEDIUM), player.getHydration());
    } else {
      assertEquals(6.3, player.getShelter().getIntegrity(), 0.001);
      assertEquals(7, player.getMorale());
      assertEquals(HIGH_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(previousHydration - ActivityLevel.HIGH.getHydrationCost(SuccessRate.HIGH), player.getHydration());
    }
  }

  @Test
  public void testGatherFirewoodWithoutItems() {
    int previousHydration = player.getHydration();
    double previousFirewood = player.getShelter().getFirewood();
    String gatherFirewoodResult = player.gatherFirewood();
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
    player.getItemFromShelter(Item.AXE);
    int previousHydration = player.getHydration();
    double previousFirewood = player.getShelter().getFirewood();
    String gatherFirewoodResult = player.gatherFirewood();
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

  @Test
  public void testGetWaterWithoutItems() {
    int previousWater = player.getShelter().getWaterTank();
    int previousHydration = player.getHydration();
    String getWaterResult = player.getWater();
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
    player.getItemFromShelter(Item.POT);
    int previousWater = player.getShelter().getWaterTank();
    int previousHydration = player.getHydration();
    String getWaterResult = player.getWater();
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

  @Test
  public void testBoostMoraleWithoutItems() {
    int previousMorale = player.getMorale();
    int previousHydration = player.getHydration();
    String boostMoraleResult = player.boostMorale();
    int boostedMorale = player.getMorale() - previousMorale;
    assertEquals(-1, boostedMorale);
    assertEquals(LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.LOW), player.getHydration());
    assertEquals("It is cold and sad here. You wish you had something to lift your spirits. " +
          "Do you want to take some rest?", boostMoraleResult);
  }

  @Test
  public void testBoostMoraleWitItems() {
    player.getItemFromShelter(Item.HARMONICA);
    int previousMorale = player.getMorale();
    int previousHydration = player.getHydration();
    String boostMoraleResult = player.boostMorale();
    int boostedMorale = player.getMorale() - previousMorale;
    int[] moralePossibilities = new int[]{1, 2, 3};
    boolean validMoralePossibility = false;
    for(int moralePossibility : moralePossibilities) {
      if(boostedMorale == moralePossibility) {
        validMoralePossibility = true;
        break;
      }
    }
    assertTrue(validMoralePossibility);
    assertEquals(LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    assertEquals(previousHydration - ActivityLevel.LOW.getHydrationCost(SuccessRate.LOW), player.getHydration());
    if (boostedMorale == 2) {
      assertEquals("You played your harmonica for an hour. Your morale is high now!",
          boostMoraleResult);
    }
    else if (boostedMorale == 3) {
      assertEquals("You found your family photo, and it reminds you all the good memories with your family! Your" +
          " morale is high now!", boostMoraleResult);
    }
    else {
      assertEquals("You decide to capture your current experience in your journal. " +
          "You are feeling much better.", boostMoraleResult);
    }
  }

  @Test
  public void testRest() {
    int previousHydration = player.getHydration();
    String restResult = player.rest();
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

  @Test
  public void testAddItem() {}

  @Test
  public void testPutItemInShelterHappy() {
    player.getItemFromShelter(Item.HARMONICA);
    assertEquals(
        "One harmonica moved to your shelter.",
        player.putItemInShelter(Item.HARMONICA));
    assertEquals(Optional.of(1).get(), player.getShelter().getEquipment().get(Item.HARMONICA));
    assertFalse(player.getItems().contains(Item.HARMONICA));
  }

  @Test
  public void testPutItemInShelterFail() {
    assertEquals(
        "You do not have a(n) family photo on you.",
        player.putItemInShelter(Item.FAMILY_PHOTO));
  }

  @Test
  public void testGetItemFromShelterHappy() {
    player.putItemInShelter(Item.HARMONICA);
    player.putItemInShelter(Item.FISHING_LINE);
    assertEquals(
        "You retrieved 1 harmonica from your shelter.",
        player.getItemFromShelter(Item.HARMONICA));
    assertEquals(Optional.of(0).get(), player.getShelter().getEquipment().get(Item.HARMONICA));
  }

  @Test
  public void testGetItemFromShelterFail() {
    player.getItemFromShelter(Item.HARMONICA);
    assertEquals(
        "You do not have a(n) harmonica in your shelter.",
        player.getItemFromShelter(Item.HARMONICA));
  }

  @Test
  public void testOvernightStatusUpdateHigh() {
    assertEquals("It was a long cold night. I have to light a fire tonight!", player.overnightStatusUpdate());
    assertEquals(MED_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.001);
    assertEquals(2, player.getMorale());
    assertEquals(2, player.getHydration());
  }

  @Test
  public void testOvernightStatusUpdateMedium() {
    while(player.getShelter().getFirewood() <= 0) {
      player.gatherFirewood();
    }
    player.getItemFromShelter(Item.FLINT_AND_STEEL);
    while(!player.getShelter().hasFire()) {
      player.buildFire();
    }
    while(player.getShelter().getWaterTank() < 5) {
      player.getWater();
    }
    while(player.getHydration() < 5) {
      player.drinkWater();
    }
    while(player.getMorale() > 8) {
      player.boostMorale();
    }
    double previousWeight = player.getWeight();
    int previousHydration = player.getHydration();
    int previousMorale = player.getMorale();
    assertEquals("It was sure nice to have a fire last night, but this shelter doesn't provide much protection from the elements.", player.overnightStatusUpdate());
    double weightChange = player.getWeight() - previousWeight;
    int hydrationChange = player.getHydration() - previousHydration;
    int moraleChange = player.getMorale() - previousMorale;
    assertEquals(-(ActivityLevel.MEDIUM.getCaloriesBurned(SuccessRate.MEDIUM) / 285.7), weightChange, 0.05);
    assertEquals(-ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.MEDIUM), hydrationChange);
    assertEquals(1, moraleChange);
  }

  @Test
  public void testOvernightStatusUpdateLow() {
    while(player.getShelter().getFirewood() <= 0) {
      player.gatherFirewood();
    }
    player.getItemFromShelter(Item.FLINT_AND_STEEL);
    while(!player.getShelter().hasFire()) {
      player.buildFire();
    }
    while(player.getShelter().getIntegrity() < 7) {
      player.improveShelter();
    }
    while(player.getShelter().getWaterTank() < 5) {
      player.getWater();
    }
    while(player.getHydration() < 5) {
      player.drinkWater();
    }
    while(player.getMorale() > 7) {
      player.boostMorale();
    }
    double previousWeight = player.getWeight();
    int previousHydration = player.getHydration();
    int previousMorale = player.getMorale();
    assertEquals("Last night was great! I feel refreshed and ready to take on whatever comes my way today.", player.overnightStatusUpdate());
    double weightChange = player.getWeight() - previousWeight;
    int hydrationChange = player.getHydration() - previousHydration;
    int moraleChange = player.getMorale() - previousMorale;
    assertEquals(-(ActivityLevel.MEDIUM.getCaloriesBurned(SuccessRate.LOW) / 285.7), weightChange, 0.05);
    assertEquals(-ActivityLevel.MEDIUM.getHydrationCost(SuccessRate.LOW), hydrationChange);
    assertEquals(2, moraleChange);
  }
}
