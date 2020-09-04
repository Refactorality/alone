package com.palehorsestudios.alone;

import com.palehorsestudios.alone.gui.GameController;
import com.palehorsestudios.alone.gui.GameApp;
import com.palehorsestudios.alone.player.Player;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.application.Application;

public class Main {

  public static void main(String[] args) {
    Application.launch(GameApp.class, args);
  }

  public static void iterate(Player player) {
    GameController controller = GameApp.getInstance().getGameController();
    controller.getWeight().setText(String.valueOf(player.getWeight()));
    controller.getHydration().setText(String.valueOf(player.getHydration()));
    controller.getMorale().setText(String.valueOf(player.getMorale()));
    String choice = "";
      choice = GameApp.getInstance().getInput();
      if (choice.toLowerCase().contains("eat")
      || (choice.toLowerCase().contains("food"))
      || (choice.toLowerCase().contains("rabbit"))
      || (choice.toLowerCase().contains("squirrel"))
      || (choice.toLowerCase().contains("moose"))
      || (choice.toLowerCase().contains("berries"))
      || (choice.toLowerCase().contains("mushrooms"))
      || (choice.toLowerCase().contains("bugs"))
      || (choice.toLowerCase().contains("beatles"))
      || (choice.toLowerCase().contains("fish"))
      || (choice.toLowerCase().contains("porcupine"))) {
      if (player.getShelter().getFoodCache().isEmpty()) {
        GameApp.getInstance().appendToCurActivity("\nYou don't have any food to eat.");
      } else {
        Food foodToEat = null;
        int foodIdx = (int) Math.floor(Math.random() * player.getShelter().getFoodCache().size());
        int i = 0;
        for (Food currentFood : player.getShelter().getFoodCache().keySet()) {
          if (i == foodIdx) {
            foodToEat = currentFood;
            break;
          }
          i++;
        }
        controller.getDailyLog().appendText(player.eat(foodToEat) + "\n");
      }
    } else if (choice.toLowerCase().contentEquals("drink water")){
        controller.getDailyLog().appendText(player.drinkWater() + "\n");
    } else if (choice.toLowerCase().contains("fishing")
    || (choice.toLowerCase().contains("fish"))) {
        controller.getDailyLog().appendText(player.goFishing() + "\n");
    } else if (choice.toLowerCase().contains("hunting")
    || (choice.toLowerCase().contains("hunt"))) {
        controller.getDailyLog().appendText(player.goHunting() + "\n");
    } else if (choice.toLowerCase().contains("trapping")
    || (choice.toLowerCase().contains("trap"))) {
        controller.getDailyLog().appendText(player.goTrapping() + "\n");
    } else if (choice.toLowerCase().contains("foraging")
    || (choice.toLowerCase().contains("forage"))) {
        controller.getDailyLog().appendText(player.goForaging() + "\n");
    } else if (choice.toLowerCase().contains("shelter")
    || (choice.toLowerCase().contains("build"))
    || (choice.toLowerCase().contains("improve"))
    || (choice.toLowerCase().contains("work"))) {
        controller.getDailyLog().appendText(player.improveShelter() + "\n");
    } else if (choice.toLowerCase().contains("firewood")
    || (choice.toLowerCase().contains("fire wood"))) {
        controller.getDailyLog().appendText(player.gatherFirewood() + "\n");
    } else if (choice.toLowerCase().contentEquals("get water")
    || (choice.toLowerCase().contentEquals("find water"))) {
        controller.getDailyLog().appendText(player.getWater() + "\n");
    } else if (choice.toLowerCase().contains("morale")) {
        controller.getDailyLog().appendText(player.boostMorale() + "\n");
    } else if (choice.toLowerCase().contains("sleep")
    || (choice.toLowerCase().contains("rest"))) {
        controller.getDailyLog().appendText(player.rest() + "\n");
    } else if (choice.toLowerCase().contains("help")) {
        GameApp.getInstance().getNarrative(new File("resources/parserHelp.txt"));
    } else if (choice.toLowerCase().contains("fire")
      || (choice.toLowerCase().contains("make"))
      || (choice.toLowerCase().contains("light"))
      || (choice.toLowerCase().contains("build"))){
        controller.getDailyLog().appendText(player.buildFire() + "\n");
    } else if (choice.toLowerCase().contains("put")
      || (choice.toLowerCase().contains("arrow"))){
        controller.getDailyLog().appendText(player.putItemInShelter(Item.ARROWS) + "\n");
    } else { System.out.println("What's that? Sorry, I don't understand '" + choice + "'."); }
  }

  public static boolean isPlayerDead(Player player) {
    boolean gameOver = false;
    if (player.getWeight() < 180.0 * 0.6) {
      GameApp.getInstance().appendToCurActivity("GAME OVER\n You starved to death :-(");
      gameOver = true;
    } else if (player.getMorale() <= 0) {
      GameApp.getInstance()
          .appendToCurActivity("GAME OVER\n Your morale is too low. You died of despair.");
      gameOver = true;
    } else if (player.getHydration() <= 0) {
      GameApp.getInstance().appendToCurActivity("GAME OVER\n You died of thirst.");
    }
    return gameOver;
  }

  public static boolean isPlayerRescued(int days) {
    boolean playerIsRescued = false;
    if (days > 15) {
      playerIsRescued = ((int) Math.floor(Math.random() * 2)) != 0;
      if (playerIsRescued) {
        GameApp.getInstance()
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
        GameApp.getInstance().appendToCurActivity("Enter an item number between 1 and 31: ");
        item = GameApp.getInstance().getInput();
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
          GameApp.getInstance()
              .appendToCurActivity("You already have a " + itemMap.get(Integer.parseInt(item)));
        }
      }
      Item itemAdded = itemMap.get(Integer.parseInt(item));
      items.add(itemAdded);
      GameApp.getInstance()
          .appendToCurActivity(
              "You put the " + itemAdded + " in your bag. You have " + (9 - i) + " remaining.");
    }
    return items;
  }
}
