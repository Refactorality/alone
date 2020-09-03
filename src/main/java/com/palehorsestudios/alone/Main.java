package com.palehorsestudios.alone;

import com.palehorsestudios.alone.activity.Activity;
import com.palehorsestudios.alone.activity.BoostMoraleActivity;
import com.palehorsestudios.alone.activity.BuildFireActivity;
import com.palehorsestudios.alone.activity.DrinkWaterActivity;
import com.palehorsestudios.alone.activity.EatActivity;
import com.palehorsestudios.alone.activity.FishActivity;
import com.palehorsestudios.alone.activity.ForageActivity;
import com.palehorsestudios.alone.activity.GatherFirewoodActivity;
import com.palehorsestudios.alone.activity.GetItemActivity;
import com.palehorsestudios.alone.activity.GetWaterActivity;
import com.palehorsestudios.alone.activity.HuntActivity;
import com.palehorsestudios.alone.activity.ImproveShelterActivity;
import com.palehorsestudios.alone.activity.PutItemActivity;
import com.palehorsestudios.alone.activity.RestActivity;
import com.palehorsestudios.alone.activity.TrapActivity;
import com.palehorsestudios.alone.gui.FxmlController;
import com.palehorsestudios.alone.gui.StartView;
import com.palehorsestudios.alone.player.Player;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javafx.application.Application;

public class Main {
  private static FxmlController controller;

  public static void main(String[] args) {
    Application.launch(StartView.class, args);
  }

  public static void iterate(Player player) {
    controller = StartView.getInstance().getController();
    controller.getPlayerStat().clear();
    controller.getPlayerStat().appendText(player.toString());



    String input = StartView.getInstance().getInput();
    Choice choice = parseChoice(input, player);
    parseActivityChoice(choice).act(choice);


//      if (choice.toLowerCase().contains("eat")
//      || (choice.toLowerCase().contains("food"))
//      || (choice.toLowerCase().contains("rabbit"))
//      || (choice.toLowerCase().contains("squirrel"))
//      || (choice.toLowerCase().contains("moose"))
//      || (choice.toLowerCase().contains("berries"))
//      || (choice.toLowerCase().contains("mushrooms"))
//      || (choice.toLowerCase().contains("bugs"))
//      || (choice.toLowerCase().contains("beatles"))
//      || (choice.toLowerCase().contains("fish"))
//      || (choice.toLowerCase().contains("porcupine"))) {
//      if (player.getShelter().getFoodCache().isEmpty()) {
//        StartView.getInstance().appendToCurActivity("\nYou don't have any food to eat.");
//      } else {
//        Food foodToEat = null;
//        int foodIdx = (int) Math.floor(Math.random() * player.getShelter().getFoodCache().size());
//        int i = 0;
//        for (Food currentFood : player.getShelter().getFoodCache().keySet()) {
//          if (i == foodIdx) {
//            foodToEat = currentFood;
//            break;
//          }
//          i++;
//        }
//        controller.getDailyLog().appendText(player.eat(foodToEat) + "\n");
//      }
//    } else if (choice.toLowerCase().contentEquals("drink water")){
//        controller.getDailyLog().appendText(player.drinkWater() + "\n");
//    } else if (choice.toLowerCase().contains("fishing")
//    || (choice.toLowerCase().contains("fish"))) {
//        controller.getDailyLog().appendText(player.goFishing() + "\n");
//    } else if (choice.toLowerCase().contains("hunting")
//    || (choice.toLowerCase().contains("hunt"))) {
//        controller.getDailyLog().appendText(player.goHunting() + "\n");
//    } else if (choice.toLowerCase().contains("trapping")
//    || (choice.toLowerCase().contains("trap"))) {
//        controller.getDailyLog().appendText(player.goTrapping() + "\n");
//    } else if (choice.toLowerCase().contains("foraging")
//    || (choice.toLowerCase().contains("forage"))) {
//        controller.getDailyLog().appendText(player.goForaging() + "\n");
//    } else if (choice.toLowerCase().contains("shelter")
//    || (choice.toLowerCase().contains("build"))
//    || (choice.toLowerCase().contains("improve"))
//    || (choice.toLowerCase().contains("work"))) {
//        controller.getDailyLog().appendText(player.improveShelter() + "\n");
//    } else if (choice.toLowerCase().contains("firewood")
//    || (choice.toLowerCase().contains("fire wood"))) {
//        controller.getDailyLog().appendText(player.gatherFirewood() + "\n");
//    } else if (choice.toLowerCase().contentEquals("get water")
//    || (choice.toLowerCase().contentEquals("find water"))) {
//        controller.getDailyLog().appendText(player.getWater() + "\n");
//    } else if (choice.toLowerCase().contains("morale")) {
//        controller.getDailyLog().appendText(player.boostMorale() + "\n");
//    } else if (choice.toLowerCase().contains("sleep")
//    || (choice.toLowerCase().contains("rest"))) {
//        controller.getDailyLog().appendText(player.rest() + "\n");
//    } else if (choice.toLowerCase().contains("help")) {
//        StartView.getInstance().getNarrative(new File("resources/parserHelp.txt"));
//    } else if (choice.toLowerCase().contains("fire")
//      || (choice.toLowerCase().contains("make"))
//      || (choice.toLowerCase().contains("light"))
//      || (choice.toLowerCase().contains("build"))){
//        controller.getDailyLog().appendText(player.buildFire() + "\n");
//    } else if (choice.toLowerCase().contains("put")
//      || (choice.toLowerCase().contains("arrow"))){
//        controller.getDailyLog().appendText(player.putItemInShelter(Item.ARROWS) + "\n");
//    } else { System.out.println("What's that? Sorry, I don't understand '" + choice + "'."); }
  }

  public static Choice parseChoice(String input, Player player) {
    Choice choice;
    Map<String, String> choiceKeywordMap = new HashMap<>(){{
      put("eat", "eat");
      put("hunt", "hunt");
      put("hunting", "hunt");
      put("fish", "fish");
      put("fishing", "fish");
    }};
    Map<String, Food> choiceFoodMap = new HashMap<>(){{
      put("moose", Food.MOOSE);
    }};
    Map<String, Item> choiceItemMap = new HashMap<>(){{
      put("axe", Item.AXE);
    }};

    // uses input to build a choice by looking up keywords in a choice map
    // split up input into array of words
    String[] inputWords = input.split(" ");
    String keyword;
    Food food;
    Item item;
    if(Optional.ofNullable(choiceKeywordMap.get(inputWords[0].toLowerCase())).isPresent()) {
      keyword = Optional.ofNullable(choiceKeywordMap.get(inputWords[0].toLowerCase())).get();
      if(Optional.ofNullable(choiceFoodMap.get(inputWords[1].toLowerCase())).isPresent()) {
        food = Optional.ofNullable(choiceFoodMap.get(inputWords[1].toLowerCase())).get();
        choice = new Choice(keyword, player, food);
      }
      else if(Optional.ofNullable(choiceItemMap.get(inputWords[1].toLowerCase())).isPresent()) {
        item = Optional.ofNullable(choiceItemMap.get(inputWords[1].toLowerCase())).get();
        choice = new Choice(keyword, player, item);
      }
      else {
        choice = new Choice(keyword, player);
      }
    } else {
      choice = null;
    }
    return choice;
  }

  public static Activity parseActivityChoice(Choice choice) {
    Activity activity;
    if(choice == null) {
      // display help menu
      activity = null;
    } else {
      if(choice.getKeyword().equals("get")) {
        activity = GetItemActivity.getInstance();
      }
      else if(choice.getKeyword().equals("put")) {
        activity = PutItemActivity.getInstance();
      }
      else if(choice.getKeyword().equals("eat")) {
        activity = EatActivity.getInstance();
      }
      else if(choice.getKeyword().equals("drink")) {
        activity = DrinkWaterActivity.getInstance();
      }
      else if(choice.getKeyword().equals("fish")) {
        activity = FishActivity.getInstance();
      }
      else if(choice.getKeyword().equals("hunt")) {
        activity = HuntActivity.getInstance();
      }
      else if(choice.getKeyword().equals("trap")) {
        activity = TrapActivity.getInstance();
      }
      else if(choice.getKeyword().equals("forage")) {
        activity = ForageActivity.getInstance();
      }
      else if(choice.getKeyword().equals("improve")) {
        activity = ImproveShelterActivity.getInstance();
      }
      else if(choice.getKeyword().equals("gather")) {
        activity = GatherFirewoodActivity.getInstance();
      }
      else if(choice.getKeyword().equals("fire")) {
        activity = BuildFireActivity.getInstance();
      }
      else if(choice.getKeyword().equals("water")) {
        activity = GetWaterActivity.getInstance();
      }
      else if(choice.getKeyword().equals("morale")) {
        activity = BoostMoraleActivity.getInstance();
      }
      else {
        activity = RestActivity.getInstance();
      }
    }
    return activity;
  }

  public static boolean isPlayerDead(Player player) {
    boolean gameOver = false;
    if (player.getWeight() < 180.0 * 0.6) {
      StartView.getInstance().appendToCurActivity("GAME OVER\n You starved to death :-(");
      gameOver = true;
    } else if (player.getMorale() <= 0) {
      StartView.getInstance()
          .appendToCurActivity("GAME OVER\n Your morale is too low. You died of despair.");
      gameOver = true;
    } else if (player.getHydration() <= 0) {
      StartView.getInstance().appendToCurActivity("GAME OVER\n You died of thirst.");
    }
    return gameOver;
  }

  public static boolean isPlayerRescued(int days) {
    boolean playerIsRescued = false;
    if (days > 15) {
      playerIsRescued = ((int) Math.floor(Math.random() * 2)) != 0;
      if (playerIsRescued) {
        StartView.getInstance()
            .appendToCurActivity(
                "YOU SURVIVED!\nA search and rescue party has found you at last. No more eating bugs for you (unless you're into that sort of thing).");
      }
    }
    return playerIsRescued;
  }



  public static Set<Item> getInitialItems() {
    // lookup map for grabbing possible items
    final Map<Integer, Item> itemMap =
        new HashMap<Integer, Item>() {
          {
            put(1, Item.FISHING_LINE);
            put(2, Item.FISHING_HOOKS);
            put(3, Item.FISHING_LURES);
            put(4, Item.KNIFE);
            put(5, Item.FLINT_AND_STEEL);
            put(6, Item.BOW);
            put(7, Item.ARROWS);
            put(8, Item.FAMILY_PHOTO);
            put(9, Item.PARACHUTE_CHORD);
            put(10, Item.FLARE);
            put(11, Item.EXTRA_BOOTS);
            put(12, Item.EXTRA_PANTS);
            put(13, Item.SLEEPING_GEAR);
            put(14, Item.COLD_WEATHER_GEAR);
            put(15, Item.TARP);
            put(16, Item.MATCHES);
            put(17, Item.FIRST_AID_KIT);
            put(18, Item.FLASHLIGHT);
            put(19, Item.BATTERIES);
            put(20, Item.WIRE);
            put(21, Item.POT);
            put(22, Item.AXE);
            put(23, Item.HATCHET);
            put(24, Item.IODINE_TABLETS);
            put(25, Item.PISTOL);
            put(26, Item.PISTOL_CARTRIDGES);
            put(27, Item.SHOVEL);
            put(28, Item.HARMONICA);
            put(29, Item.LIGHTER);
            put(30, Item.SURVIVAL_MANUAL);
            put(31, Item.JOURNAL);
          }
        };

    // ask player for 10 items from list
    Set<Item> items = new HashSet<>();
    for (int i = 0; i < 10; i++) {
      String item = "";
      boolean validInput = false;
      while (!validInput) {
        StartView.getInstance().appendToCurActivity("Enter an item number between 1 and 31: ");
        item = StartView.getInstance().getInput();
        if (item.length() == 1) {
          char char0 = item.charAt(0);
          if (char0 == '1'
              || char0 == '2'
              || char0 == '3'
              || char0 == '4'
              || char0 == '5'
              || char0 == '6'
              || char0 == '7'
              || char0 == '8'
              || char0 == '9') {
            validInput = true;
          }
        } else if (item.length() == 2) {
          char char0 = item.charAt(0);
          char char1 = item.charAt(1);
          if ((char0 == '1' || char0 == '2')
              && (char1 == '0'
                  || char1 == '1'
                  || char1 == '2'
                  || char1 == '3'
                  || char1 == '4'
                  || char1 == '5'
                  || char1 == '6'
                  || char1 == '7'
                  || char1 == '8'
                  || char1 == '9')) {
            validInput = true;
          } else if (char0 == '3' && (char1 == '0' || char1 == '1')) {
            validInput = true;
          }
        }
        if (validInput && items.contains(itemMap.get(Integer.parseInt(item)))) {
          validInput = false;
          StartView.getInstance()
              .appendToCurActivity("You already have a " + itemMap.get(Integer.parseInt(item)));
        }
      }
      Item itemAdded = itemMap.get(Integer.parseInt(item));
      items.add(itemAdded);
      StartView.getInstance()
          .appendToCurActivity(
              "You put the " + itemAdded + " in your bag. You have " + (9 - i) + " remaining.");
    }
    return items;
  }
}
