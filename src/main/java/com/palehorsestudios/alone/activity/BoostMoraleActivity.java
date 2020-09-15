package com.palehorsestudios.alone.activity;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.player.SuccessRate;

import java.util.*;

public class BoostMoraleActivity extends Activity{
  private static BoostMoraleActivity activityReference;

  private BoostMoraleActivity(){}

  public static Activity getInstance() {
    if(activityReference == null) {
      activityReference = new BoostMoraleActivity();
    }
    return activityReference;
  }

  @Override
  public String act(Choice choice) {
    String result;
    double caloriesBurned = ActivityLevel.LOW.getCaloriesBurned(SuccessRate.LOW);
    choice.getPlayer().updateWeight(-caloriesBurned);
    int hydrationCost = ActivityLevel.LOW.getHydrationCost(SuccessRate.LOW);
    choice.getPlayer().setHydration(choice.getPlayer().getHydration() - hydrationCost);
    List<Item> moraleBoostItemsOwn = new ArrayList<>();
    Set<Item> moraleBoostItems = new HashSet<>(Arrays.asList(Item.FAMILY_PHOTO, Item.HARMONICA, Item.JOURNAL));
    for (Item i : moraleBoostItems ) {
      if (choice.getPlayer().getItems().contains(i)) {
        moraleBoostItemsOwn.add(i);
      }
    }
    if (moraleBoostItemsOwn.isEmpty()) {
      choice.getPlayer().updateMorale(-1);
      result = "It is cold and sad here. You wish you had something to lift your spirits. Do you want to take some rest?";
    } else {
      // randomly pick a item from the moralBoostItemsOwn
      Random rand = new Random();
      int randomIndex = rand.nextInt(moraleBoostItemsOwn.size());
      if (moraleBoostItemsOwn.get(randomIndex) == Item.FAMILY_PHOTO ) {
        choice.getPlayer().updateMorale(3);
        result = "You found your family photo, and it reminds you all the good memories with your family! Your" +
            " morale is high now!";
      }
      else if (moraleBoostItemsOwn.get(randomIndex) == Item.HARMONICA ) {
        choice.getPlayer().updateMorale(2);
        result = "You played your harmonica for an hour. Your morale is high now!";
      }
      else {
        choice.getPlayer().updateMorale(1);
        result = "You decide to capture your current experience in your journal. " +
            "You are feeling much better.";
      }
    }
    return result;
  }
}
