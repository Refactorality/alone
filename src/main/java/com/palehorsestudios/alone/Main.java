package com.palehorsestudios.alone;

import com.palehorsestudios.alone.gui.FxmlController;
import com.palehorsestudios.alone.gui.StartView;
import com.palehorsestudios.alone.player.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.application.Application;

public class Main {
private static FxmlController controller;
  public static void main(String[] args) {
    Application.launch(StartView.class, args);
  }

  public static void iterate(Player player) {
    controller = StartView.getInstance().getController();
    controller.getPlayerStat().appendText(player.toString());
    boolean validChoice = false;
    String choice = "";
    while (!validChoice) {
      StartView.getInstance().appendToCurActivity("Enter a number from 1 to 11: ");
      choice = StartView.getInstance().getInput();
      if (choice.equals("1")
          || choice.equals("2")
          || choice.equals("3")
          || choice.equals("4")
          || choice.equals("5")
          || choice.equals("6")
          || choice.equals("7")
          || choice.equals("8")
          || choice.equals("9")
          || choice.equals("10")
          || choice.equals("11")) {
        validChoice = true;
      }
    }
    if (choice.equals("1")) {
      if (player.getShelter().getFoodCache().isEmpty()) {
        StartView.getInstance().appendToCurActivity("\nYou don't have any food to eat.");
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
        controller.getDailyLog().appendText("\n" + player.eat(foodToEat) + "\n");
      }
    } else if (choice.equals("2")) {
      controller.getDailyLog().appendText("\n" + player.drinkWater() + "\n");
    } else if (choice.equals("3")) {
      controller.getDailyLog().appendText("\n" + player.goFishing() + "\n");
    } else if (choice.equals("4")) {
      controller.getDailyLog().appendText("\n" + player.goHunting() + "\n");
    } else if (choice.equals("5")) {
      controller.getDailyLog().appendText("\n" + player.goTrapping() + "\n");
    } else if (choice.equals("6")) {
      controller.getDailyLog().appendText("\n" + player.goForaging() + "\n");
    } else if (choice.equals("7")) {
      controller.getDailyLog().appendText("\n" + player.improveShelter() + "\n");
    } else if (choice.equals("8")) {
      controller.getDailyLog().appendText("\n" + player.gatherFirewood() + "\n");
    } else if (choice.equals("9")) {
      controller.getDailyLog().appendText("\n" + player.getWater() + "\n");
    } else if (choice.equals("10")) {
      controller.getDailyLog().appendText("\n" + player.boostMorale() + "\n");
    } else {
      controller.getDailyLog().appendText("\n" + player.rest() + "\n");
    }
  }

  public static boolean isPlayerDead(Player player) {
    boolean gameOver = false;
    if (player.getWeight() < 180.0 * 0.6) {
      StartView.getInstance().appendToCurActivity("GAME OVER\n Your starved to death :-(");
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
                "YOU WIN!\nA search and rescue party has found you at last. No more eating bugs for you (unless you're into that sort of thing).");
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
