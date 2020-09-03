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
      put("eat moose", "eat");
      put("eat fish", "eat");
      put("eat squirrel", "eat");
      put("eat rabbit", "eat");
      put("eat porcupine", "eat");
      put("eat bug", "eat");
      put("eat bugs", "eat");
      put("eat mushroom", "eat");
      put("eat mushrooms", "eat");
      put("eat berry", "eat");
      put("eat berries", "eat");
      put("hunt", "hunt");
      put("hunt moose", "hunt");
      put("hunt squirrel", "hunt");
      put("hunt rabbit", "hunt");
      put("hunt porcupine", "hunt");
      put("hunt bug", "forage");
      put("hunt mushrooms", "forage");
      put("hunt berry", "hunt");
      put("hunt bugs", "forage");
      put("hunt mushroom", "forage");
      put("hunt berries", "hunt");
      put("get moose", "hunt");
      put("get squirrel", "hunt");
      put("get rabbit", "hunt");
      put("get porcupine", "hunt");
      put("get bug", "forage");
      put("get mushrooms", "forage");
      put("get berry", "hunt");
      put("get bugs", "forage");
      put("get mushroom", "forage");
      put("get berries", "hunt");
      put("kill moose", "hunt");
      put("kill squirrel", "hunt");
      put("kill rabbit", "hunt");
      put("kill porcupine", "hunt");
      put("kill bug", "forage");
      put("kill mushrooms", "forage");
      put("kill berry", "hunt");
      put("kill bugs", "forage");
      put("kill mushroom", "forage");
      put("kill berries", "hunt");
      put("trap squirrel", "trap");
      put("trap moose", "trap");
      put("trap rabbit", "trap");
      put("trap porcupine", "trap");
      put("trap", "trap");
      put("trapping", "trap");
      put("go trap", "trap");
      put("go trapping", "trap");
      put("fish", "fish");
      put("fishing", "fish");
      put("go fish", "fish");
      put("go fishing", "fish");
      put("get fishing line", "get");
      put("get fishing hooks", "get");
      put("get fishing hook", "get");
      put("get fishing lure", "get");
      put("get fishing lures", "get");
      put("get knife", "get");
      put("get flint and steel", "get");
      put("get bow", "get");
      put("get arrow", "get");
      put("get arrows", "get");
      put("get family photo", "get");
      put("get photo", "get");
      put("get photograph", "get");
      put("get rope", "get");
      put("get parachute chord", "get");
      put("get cordage", "get");
      put("get flare", "get");
      put("get flares", "get");
      put("get extra boots", "get");
      put("get extra boot", "get");
      put("get boots", "get");
      put("get boot", "get");
      put("get extra pants", "get");
      put("get pants", "get");
      put("get sleeping gear", "get");
      put("get sleeping bag", "get");
      put("get cold weather gear", "get");
      put("get cold gear", "get");
      put("get tarp", "get");
      put("get matches", "get");
      put("get match", "get");
      put("get first aid", "get");
      put("get first aid kit", "get");
      put("get flashlight", "get");
      put("get light", "get");
      put("get batteries", "get");
      put("get battery", "get");
      put("get wire", "get");
      put("get 18 gauge wire", "get");
      put("get wires", "get");
      put("get 18 gauge wires", "get");
      put("get snare", "get");
      put("get waterproof jacket", "get");
      put("get jacket", "get");
      put("get pot", "get");
      put("get cooking pot", "get");
      put("get axe", "get");
      put("get hatchet", "get");
      put("get iodine tablets", "get");
      put("get iodine", "get");
      put("get tablets", "get");
      put("get pistol", "get");
      put("get gun", "get");
      put("get ammunition", "get");
      put("get ammo", "get");
      put("get rounds", "get");
      put("get shovel", "get");
      put("get harmonica", "get");
      put("get lighter", "get");
      put("get survival manual", "get");
      put("get manual", "get");
      put("get journal and pen", "get");
      put("get journal", "get");
      put("put fishing line", "put");
      put("put fishing hooks", "put");
      put("put fishing hook", "put");
      put("put fishing lure", "put");
      put("put fishing lures", "put");
      put("put knife", "put");
      put("put flint and steel", "put");
      put("put bow", "put");
      put("put arrow", "put");
      put("put arrows", "put");
      put("put family photo", "put");
      put("put photo", "put");
      put("put photograph", "put");
      put("put rope", "put");
      put("put parachute chord", "put");
      put("put cordage", "put");
      put("put flare", "put");
      put("put flares", "put");
      put("put extra boots", "put");
      put("put extra boot", "put");
      put("put boots", "put");
      put("put boot", "put");
      put("put extra pants", "put");
      put("put pants", "put");
      put("put sleeping gear", "put");
      put("put sleeping bag", "put");
      put("put cold weather gear", "put");
      put("put cold gear", "put");
      put("put tarp", "put");
      put("put matches", "put");
      put("put match", "put");
      put("put first aid", "put");
      put("put first aid kit", "put");
      put("put flashlight", "put");
      put("put light", "put");
      put("put batteries", "put");
      put("put battery", "put");
      put("put wire", "put");
      put("put 18 gauge wire", "put");
      put("put wires", "put");
      put("put 18 gauge wires", "put");
      put("put snare", "put");
      put("put waterproof jacket", "put");
      put("put jacket", "put");
      put("put pot", "put");
      put("put cooking pot", "put");
      put("put axe", "put");
      put("put hatchet", "put");
      put("put iodine tablets", "put");
      put("put iodine", "put");
      put("put tablets", "put");
      put("put pistol", "put");
      put("put gun", "put");
      put("put ammunition", "put");
      put("put ammo", "put");
      put("put rounds", "put");
      put("put shovel", "put");
      put("put harmonica", "put");
      put("put lighter", "put");
      put("put survival manual", "put");
      put("put manual", "put");
      put("put journal and pen", "put");
      put("put journal", "put");
    }};
    Map<String, Food> choiceFoodMap = new HashMap<>(){{
      put("eat moose", Food.MOOSE);
      put("eat fish", Food.FISH);
      put("eat squirrel", Food.SQUIRREL);
      put("eat rabbit", Food.RABBIT);
      put("eat porcupine", Food.PORCUPINE);
      put("eat bug", Food.BUG);
      put("eat bugs", Food.BUG);
      put("eat mushroom", Food.MUSHROOM);
      put("eat mushrooms", Food.MUSHROOM);
      put("eat berry", Food.BERRIES);
      put("eat berries", Food.BERRIES);
      put("hunt moose", Food.MOOSE);
      put("hunt squirrel", Food.SQUIRREL);
      put("hunt rabbit", Food.RABBIT);
      put("hunt porcupine", Food.PORCUPINE);
      put("hunt bug", Food.BUG);
      put("hunt mushrooms", Food.MUSHROOM);
      put("hunt berry", Food.BERRIES);
      put("hunt bugs", Food.BUG);
      put("hunt mushroom", Food.MUSHROOM);
      put("hunt berries", Food.BERRIES);
      put("get moose", Food.MOOSE);
      put("get squirrel", Food.SQUIRREL);
      put("get rabbit", Food.RABBIT);
      put("get porcupine", Food.PORCUPINE);
      put("get bug", Food.BUG);
      put("get mushrooms", Food.MUSHROOM);
      put("get berry", Food.BERRIES);
      put("get bugs", Food.BUG);
      put("get mushroom", Food.MUSHROOM);
      put("get berries", Food.BERRIES);
      put("kill moose", Food.MOOSE);
      put("kill squirrel", Food.SQUIRREL);
      put("kill rabbit", Food.RABBIT);
      put("kill porcupine", Food.PORCUPINE);
      put("kill bug", Food.BUG);
      put("kill mushrooms", Food.MUSHROOM);
      put("kill berry", Food.BERRIES);
      put("kill bugs", Food.BUG);
      put("kill mushroom", Food.MUSHROOM);
      put("kill berries", Food.BERRIES);
      put("trap squirrel", Food.SQUIRREL);
      put("trap moose", Food.MOOSE);
      put("trap rabbit", Food.RABBIT);
      put("trap porcupine", Food.PORCUPINE);
    }};
    Map<String, Item> choiceItemMap = new HashMap<>(){{
      put("axe", Item.AXE);
    }};

    // uses input to build a choice by looking up keywords in a choice map
    // split up input into array of words
    String keyword;
    Food food;
    Item item;
    if(Optional.ofNullable(choiceKeywordMap.get(input.toLowerCase())).isPresent()) {
      keyword = Optional.ofNullable(choiceKeywordMap.get(input.toLowerCase())).get();
      if(Optional.ofNullable(choiceFoodMap.get(input.toLowerCase())).isPresent()) {
        food = Optional.ofNullable(choiceFoodMap.get(input.toLowerCase())).get();
        choice = new Choice(keyword, player, food);
      }
      else if(Optional.ofNullable(choiceItemMap.get(input.toLowerCase())).isPresent()) {
        item = Optional.ofNullable(choiceItemMap.get(input.toLowerCase())).get();
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
