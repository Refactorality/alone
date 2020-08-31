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
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PlayerTest {

  static final double LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT = 179.87;
  static final double LOW_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT = 179.74;
  static final double LOW_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT = 179.47;
  static final double MED_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT = 179.74;
  static final double MED_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT = 179.47;
  static final double MED_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT = 178.95;
  static final double HIGH_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT = 178.77;
  static final double HIGH_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT = 177.55;
  static final double HIGH_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT = 175.45;

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
                Item.WATERPROOF_JACKET,
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
    // TODO: Can't test until getWater is implemented
  }

  @Test
  public void testDrinkWaterFail() {
    assertEquals(
        "There isn't a drop left in your water tank. You should go fetch some water soon before you die of thirst!",
        player.drinkWater().getMessage());
    assertEquals(0, player.getShelter().getWaterTank());
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
  public void testGoFishingNoItems() {
    Result fishingResult = player.goFishing();
    if (fishingResult.getFoodCount() == 0) {
      assertEquals(
          "I guess that's why they don't call it catching. You didn't catch any fish.",
          fishingResult.getMessage());
      assertEquals(3, player.getMorale());
      assertEquals(
          Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.FISH), 0.001);
      assertEquals(MED_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    } else if (fishingResult.getFoodCount() == Food.FISH.getGrams()) {
      assertEquals(
          "It looks like you'll be eating fresh fish tonight! You caught one lake trout.",
          fishingResult.getMessage());
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + Food.FISH.getGrams()).get(),
          player.getShelter().getFoodCache().get(Food.FISH),
          0.001);
      assertEquals(MED_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    } else {
      assertEquals(
          "I hope there's room in your food cache. You caught three white fish!",
          fishingResult.getMessage());
      assertEquals(8, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + (Food.FISH.getGrams() * 3)).get(),
          player.getShelter().getFoodCache().get(Food.FISH),
          0.001);
      assertEquals(MED_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    }
  }

  @Test
  public void testGoFishingWithItems() {
    player.getItemFromShelter(Item.FISHING_LINE);
    player.getItemFromShelter(Item.FISHING_HOOKS);
    Result fishingResult = player.goFishing();
    if (fishingResult.getFoodCount() == 0) {
      assertEquals(
          "I guess that's why they don't call it catching. You didn't catch any fish.",
          fishingResult.getMessage());
      assertEquals(3, player.getMorale());
      assertEquals(
          Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.FISH), 0.001);
      assertEquals(MED_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    } else if (fishingResult.getFoodCount() == Food.FISH.getGrams() + Food.FISH.getGrams() * 0.2) {
      assertEquals(
          "It looks like you'll be eating fresh fish tonight! You caught one lake trout.",
          fishingResult.getMessage());
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + Food.FISH.getGrams() + Food.FISH.getGrams() * 0.2).get(),
          player.getShelter().getFoodCache().get(Food.FISH),
          0.001);
      assertEquals(MED_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    } else {
      assertEquals(
          "I hope there's room in your food cache. You caught three white fish!",
          fishingResult.getMessage());
      assertEquals(8, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + (Food.FISH.getGrams() * 3 + Food.FISH.getGrams() * 3 * 0.2)).get(),
          player.getShelter().getFoodCache().get(Food.FISH),
          0.001);
      assertEquals(MED_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    }
  }

  @Test
  public void testGoHuntingNoItems() {
    Result huntingResult = player.goHunting();
    if (huntingResult.getFoodCount() == 0) {
      assertEquals(
          "I guess that's why they don't call it killing. You couldn't get a shot on an animal.",
          huntingResult.getMessage());
      assertEquals(3, player.getMorale());
      assertEquals(
          Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.PORCUPINE));
      assertEquals(Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.MOOSE));
      assertEquals(HIGH_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    } else if (huntingResult.getFoodCount() == Food.PORCUPINE.getGrams()) {
      assertEquals(
          "Watch out for those quills! You killed a nice fat porcupine that should keep you fed for a while.",
          huntingResult.getMessage());
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + Food.PORCUPINE.getGrams()).get(),
          player.getShelter().getFoodCache().get(Food.PORCUPINE));
      assertEquals(HIGH_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    } else {
      assertEquals(
          "Moose down! It took five trips, but you were able to process the meat and transport it back to your shelter before a predator got to it first.",
          huntingResult.getMessage());
      assertEquals(9, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + Food.MOOSE.getGrams()).get(),
          player.getShelter().getFoodCache().get(Food.MOOSE));
      assertEquals(HIGH_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    }
  }

  @Test
  public void testGoHuntingWithItems() {
    player.getItemFromShelter(Item.SURVIVAL_MANUAL);
    player.getItemFromShelter(Item.BOW);
    player.getItemFromShelter(Item.ARROWS);
    Result huntingResult = player.goHunting();
    if (huntingResult.getFoodCount() == 0) {
      assertEquals(
          "I guess that's why they don't call it killing. You couldn't get a shot on an animal.",
          huntingResult.getMessage());
      assertEquals(3, player.getMorale());
      assertEquals(
          Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.PORCUPINE));
      assertEquals(Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.MOOSE));
      assertEquals(HIGH_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    } else if (huntingResult.getFoodCount()
        == Food.PORCUPINE.getGrams() + Food.PORCUPINE.getGrams() * 0.3) {
      assertEquals(
          "Watch out for those quills! You killed a nice fat porcupine that should keep you fed for a while.",
          huntingResult.getMessage());
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + Food.PORCUPINE.getGrams() + Food.PORCUPINE.getGrams() * 0.3).get(),
          player.getShelter().getFoodCache().get(Food.PORCUPINE),
          0.001);
      assertEquals(HIGH_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    } else {
      assertEquals(
          "Moose down! It took five trips, but you were able to process the meat and transport it back to your shelter before a predator got to it first.",
          huntingResult.getMessage());
      assertEquals(9, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + Food.MOOSE.getGrams() + Food.MOOSE.getGrams() * 0.3).get(),
          player.getShelter().getFoodCache().get(Food.MOOSE),
          0.001);
      assertEquals(HIGH_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    }
  }

  @Test
  public void testGoTrappingNoItems() {
    Result trappingResult = player.goTrapping();
    if (trappingResult.getFoodCount() == 0) {
      assertEquals(
          "Those varmints are smarter than they look. Your traps were empty.",
          trappingResult.getMessage());
      assertEquals(3, player.getMorale());
      assertEquals(
          Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.SQUIRREL));
      assertEquals(Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.RABBIT));
      assertEquals(MED_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    } else if (trappingResult.getFoodCount() == (Food.SQUIRREL.getGrams() * 2)) {
      assertEquals(
          "Your patience has paid off. There were two squirrels in your traps!",
          trappingResult.getMessage());
      assertEquals(6, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + (Food.SQUIRREL.getGrams() * 2)).get(),
          player.getShelter().getFoodCache().get(Food.SQUIRREL));
      assertEquals(MED_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    } else {
      assertEquals(
          "You'll have plenty of lucky rabbit feet now. Your snared three rabbits!",
          trappingResult.getMessage());
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + (Food.RABBIT.getGrams() * 3)).get(),
          player.getShelter().getFoodCache().get(Food.RABBIT));
      assertEquals(MED_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    }
  }

  @Test
  public void testGoTrappingWithItems() {
    player.getItemFromShelter(Item.WIRE);
    Result trappingResult = player.goTrapping();
    if (trappingResult.getFoodCount() == 0) {
      assertEquals(
          "Those varmints are smarter than they look. Your traps were empty.",
          trappingResult.getMessage());
      assertEquals(3, player.getMorale());
      assertEquals(
          Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.SQUIRREL));
      assertEquals(Optional.of(1000.0).get(), player.getShelter().getFoodCache().get(Food.RABBIT));
      assertEquals(MED_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    } else if (trappingResult.getFoodCount()
        == (Food.SQUIRREL.getGrams() * 2 + Food.SQUIRREL.getGrams() * 2 * 0.1)) {
      assertEquals(
          "Your patience has paid off. There were two squirrels in your traps!",
          trappingResult.getMessage());
      assertEquals(6, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + (Food.SQUIRREL.getGrams() * 2 + Food.SQUIRREL.getGrams() * 2 * 0.1))
              .get(),
          player.getShelter().getFoodCache().get(Food.SQUIRREL),
          0.001);
      assertEquals(MED_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    } else {
      assertEquals(
          "You'll have plenty of lucky rabbit feet now. Your snared three rabbits!",
          trappingResult.getMessage());
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(1000.0 + (Food.RABBIT.getGrams() * 3 + Food.RABBIT.getGrams() * 3 * 0.1))
              .get(),
          player.getShelter().getFoodCache().get(Food.RABBIT),
          0.001);
      assertEquals(MED_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    }
  }

  @Test
  public void goForagingNoItems() {
    Result foragingResult = player.goForaging();
    if (foragingResult.getFoodCount() == Food.BERRIES.getGrams() * 2) {
      assertEquals(
          "Lucky for you, berries are ripe this time of year. You picked as many as you could carry.",
          foragingResult.getMessage());
      assertEquals(6, player.getMorale());
      assertEquals(
          Optional.of(Food.BERRIES.getGrams() * 2).get(),
          player.getShelter().getFoodCache().get(Food.BERRIES));
      assertEquals(LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    } else if (foragingResult.getFoodCount() == (Food.MUSHROOM.getGrams() * 4)) {
      assertEquals(
          "Delicious fungus! You found a log covered in edible mushrooms.",
          foragingResult.getMessage());
      assertEquals(6, player.getMorale());
      assertEquals(
          Optional.of(Food.MUSHROOM.getGrams() * 4).get(),
          player.getShelter().getFoodCache().get(Food.MUSHROOM),
          0.001);
      assertEquals(LOW_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    } else {
      assertEquals(
          "You never thought you would say this, but you are thrilled to have found a large group "
              + "of leaf beetles under a decayed log. These critters are packed full of protein!",
          foragingResult.getMessage());
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(Food.BUG.getGrams() * 3).get(),
          player.getShelter().getFoodCache().get(Food.BUG),
          0.001);
      assertEquals(LOW_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    }
  }

  @Test
  public void goForagingWithItems() {
    player.getItemFromShelter(Item.POT);
    player.getItemFromShelter(Item.EXTRA_BOOTS);
    Result foragingResult = player.goForaging();
    if (foragingResult.getFoodCount()
        == Food.BERRIES.getGrams() * 2 + Food.BERRIES.getGrams() * 2 * 0.2) {
      assertEquals(
          "Lucky for you, berries are ripe this time of year. You picked as many as you could carry.",
          foragingResult.getMessage());
      assertEquals(6, player.getMorale());
      assertEquals(
          Optional.of(Food.BERRIES.getGrams() * 2 + Food.BERRIES.getGrams() * 2 * 0.2).get(),
          player.getShelter().getFoodCache().get(Food.BERRIES),
          0.001);
      assertEquals(LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    } else if (foragingResult.getFoodCount()
        == (Food.MUSHROOM.getGrams() * 4 + Food.MUSHROOM.getGrams() * 4 * 0.2)) {
      assertEquals(
          "Delicious fungus! You found a log covered in edible mushrooms.",
          foragingResult.getMessage());
      assertEquals(6, player.getMorale());
      assertEquals(
          Optional.of(Food.MUSHROOM.getGrams() * 4 + Food.MUSHROOM.getGrams() * 4 * 0.2).get(),
          player.getShelter().getFoodCache().get(Food.MUSHROOM),
          0.001);
      assertEquals(LOW_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    } else {
      assertEquals(
          "You never thought you would say this, but you are thrilled to have found a large group "
              + "of leaf beetles under a decayed log. These critters are packed full of protein!",
          foragingResult.getMessage());
      assertEquals(7, player.getMorale());
      assertEquals(
          Optional.of(Food.BUG.getGrams() * 3 + Food.BUG.getGrams() * 3 * 0.2).get(),
          player.getShelter().getFoodCache().get(Food.BUG),
          0.001);
      assertEquals(LOW_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    }
  }

  @Test
  public void improveShelterNoItems() {
    double previousIntegrity = player.getShelter().getIntegrity();
    logger.info("previous integrity no items: " + previousIntegrity);
    Result shelterImprovementResult = player.improveShelter();
    double shelterIntegrityChange = player.getShelter().getIntegrity() - previousIntegrity;
    if (shelterIntegrityChange < 2) {
      assertEquals(
          "You can sleep a little better at night. You were able to better "
              + "insulate the walls of your shelter.",
          shelterImprovementResult.getMessage());
      assertEquals(4.0, player.getShelter().getIntegrity(), 0.001);
      assertEquals(6, player.getMorale());
      assertEquals(HIGH_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    } else if (shelterIntegrityChange < 3) {
      assertEquals(
          "It's always nice to be able to get out of the rain and snow. "
              + "Your roof is in better shape now.",
          shelterImprovementResult.getMessage());
      assertEquals(5.0, player.getShelter().getIntegrity(), 0.001);
      assertEquals(6, player.getMorale());
      assertEquals(HIGH_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    } else {
      assertEquals(
          "It was a lot of work, but your improved fireplace will keep "
              + "you much warmer tonight",
          shelterImprovementResult.getMessage());
      assertEquals(6.0, player.getShelter().getIntegrity(), 0.001);
      assertEquals(7, player.getMorale());
      assertEquals(175.45, player.getWeight(), 0.005);
      assertEquals(HIGH_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    }
  }

  @Test
  public void improveShelterWithItems() {
    player.getItemFromShelter(Item.AXE);
    double previousIntegrity = player.getShelter().getIntegrity();
    logger.info("previous integrity no items: " + previousIntegrity);
    Result shelterImprovementResult = player.improveShelter();
    double shelterIntegrityChange = player.getShelter().getIntegrity() - previousIntegrity;
    if (shelterIntegrityChange < 2) {
      assertEquals(
          "You can sleep a little better at night. You were able to better "
              + "insulate the walls of your shelter.",
          shelterImprovementResult.getMessage());
      assertEquals(4.1, player.getShelter().getIntegrity(), 0.001);
      assertEquals(6, player.getMorale());
      assertEquals(HIGH_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    } else if (shelterIntegrityChange < 3) {
      assertEquals(
          "It's always nice to be able to get out of the rain and snow. "
              + "Your roof is in better shape now.",
          shelterImprovementResult.getMessage());
      assertEquals(5.2, player.getShelter().getIntegrity(), 0.001);
      assertEquals(7, player.getMorale());
      assertEquals(HIGH_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    } else {
      assertEquals(
          "It was a lot of work, but your improved fireplace will keep "
              + "you much warmer tonight",
          shelterImprovementResult.getMessage());
      assertEquals(6.3, player.getShelter().getIntegrity(), 0.001);
      assertEquals(7, player.getMorale());
      assertEquals(175.45, player.getWeight(), 0.005);
      assertEquals(HIGH_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
    }
  }

  @Test
  public void gatherFirewood() {
    Result gatherFirewoodResult = player.gatherFirewood();
    double firewoodAmount = gatherFirewoodResult.getFirewood();
    System.out.println(firewoodAmount);
    double caloriesBurned = gatherFirewoodResult.getCalories();
    int updatedMorale = gatherFirewoodResult.getMorale();
    if (firewoodAmount == 1.2 ) {
      assertEquals(-75.0, caloriesBurned, 0.01);
      assertEquals(MED_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(player.getMorale(), updatedMorale, 0.01);
    }
    else if (firewoodAmount == 3.6) {
      assertEquals(-150.0, caloriesBurned, 0.01);
      assertEquals(MED_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(player.getMorale(), updatedMorale, 0.01);
    }
    else if (firewoodAmount == 6.0) {
      assertEquals(-300.0, caloriesBurned, 0.01);
      assertEquals(MED_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(player.getMorale(), updatedMorale, 0.01);
    }
    assertEquals("Good Job! You just gathered " + firewoodAmount + " bundles of firewood.", gatherFirewoodResult.getMessage());
  }

  @Test
  public void getWater() {
    Result getWaterResult = player.getWater();
    int addedWater = getWaterResult.getWater();
    double burnedCalories = getWaterResult.getCalories();
    if (addedWater == 1) {
      assertEquals(player.getMorale(), getWaterResult.getMorale());
      assertEquals(LOW_ACTIVITY_LOW_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(-37.5, getWaterResult.getCalories(), 0.01);
    }
    else if (addedWater == 2) {
      assertEquals(player.getMorale(), getWaterResult.getMorale());
      assertEquals(LOW_ACTIVITY_MED_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(-75.0, getWaterResult.getCalories(), 0.01);
    }
    else if (addedWater == 3) {
      assertEquals(player.getMorale(), getWaterResult.getMorale());
      assertEquals(LOW_ACTIVITY_HIGH_SUCCESS_PLAYER_WEIGHT, player.getWeight(), 0.005);
      assertEquals(-150.0, getWaterResult.getCalories(), 0.01);
    }
    assertEquals("You added " + addedWater + " in the water tank.", getWaterResult.getMessage());
  }

  @Test
  public void boostMorale() {
    Result boostMoraleResult = player.boostMorale();
    int boostedMoraleFromResult = boostMoraleResult.getMorale();
    int currentMorale = player.getMorale();
    int boostedMorale = currentMorale - boostedMoraleFromResult;
    if (boostedMorale == -1) {
      assertEquals("It is cold and sad here. I know you are lonely, do you want to take some rest?", boostMoraleResult.getMessage());
    }
    else if (boostedMorale == 2) {
      assertEquals("You found a harmonica, and you played with it for an hour, your morale is high now!", boostMoraleResult.getMessage());
      assertEquals(37.5, boostMoraleResult.getCalories(), 0.01);
    }
    else if (boostedMorale == 3) {
      assertEquals("You found your family photo and it reminds you all the good memories with your family! Your" +
          " morale is high now!", boostMoraleResult.getMessage());
      assertEquals(37.5, boostMoraleResult.getCalories(), 0.01);
    }
    else if (boostedMorale == 1 ) {
      assertEquals("You found a Journal and a pen, you decide to capture current experience in the journal. " +
          "Your morale is high now!", boostMoraleResult.getMessage());
      assertEquals(37.5, boostMoraleResult.getCalories(), 0.01);
    }
  }

  @Test
  public void rest() {
    Result restResult = player.rest();
    assertEquals("You have rested for some hours and are ready for the next day!", restResult.getMessage());
  }

  @Test
  public void testToString() {}

  @Test
  public void testAddItem() {}

  @Test
  public void testPutItemInShelterHappy() {
    player.getItemFromShelter(Item.HARMONICA);
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
    player.getItemFromShelter(Item.HARMONICA);
    assertEquals(
        "You do not have a(n) harmonica in your shelter.",
        player.getItemFromShelter(Item.HARMONICA).getMessage());
  }
}
