package com.palehorsestudios.alone;

import com.palehorsestudios.alone.activity.*;
import com.palehorsestudios.alone.gui.GameApp;
import com.palehorsestudios.alone.player.Player;
import javafx.application.Application;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Main {
  private static GameAssets game;

  public static void main(String[] args) {
    game = new GameAssets();
    game.loadAssets();
    Application.launch(GameApp.class, args);
  }

  public static Choice parseChoice(String input, Player player) {
    Choice choice = null;

    // uses input to build a choice by looking up keywords in a choice map
    // split up input into array of words
    String keyword;
    Food food;
    Item item;
    if (Optional.ofNullable(GameAssets.choiceKeywordMap.get(input.toLowerCase())).isPresent()) {
      keyword = Optional.ofNullable(GameAssets.choiceKeywordMap.get(input.toLowerCase())).get();
      if (keyword.equals("eat")) {
        if (Optional.ofNullable(GameAssets.choiceFoodMap.get(input.toLowerCase())).isPresent()) {
          food = Optional.ofNullable(GameAssets.choiceFoodMap.get(input.toLowerCase())).get();
          choice = new Choice(keyword, player, food);
        } else {
          choice = new Choice(keyword, player);
        }
      } else if (keyword.equals("get") || keyword.equals("put")) {
        if (Optional.ofNullable(GameAssets.choiceItemMap.get(input.toLowerCase())).isPresent()) {
          item = Optional.ofNullable(GameAssets.choiceItemMap.get(input.toLowerCase())).get();
          choice = new Choice(keyword, player, item);
        } else {
          choice = null;
        }
      } else if (keyword.equals("make") || keyword.equals("cook")) {
        int numResources = 0;

        if (Optional.ofNullable(GameAssets.choiceFoodMap.get(input.toLowerCase())).isPresent()) {
          for (Item resource : GameAssets.gameFoods.get(GameAssets.choiceFoodMap.get(input.toLowerCase()).getName()).getResourcesRequired()){
            if (player.getShelter().getFoodCache().containsKey(resource)) {
              numResources++;
            }
          }
          if (numResources == GameAssets.gameFoods.get(GameAssets.choiceFoodMap.get(input.toLowerCase()).getName()).getResourcesRequired().size()) {
            food = Optional.ofNullable(GameAssets.choiceFoodMap.get(input.toLowerCase())).get();
          } else {
            food = null;
          }
          choice = new Choice(keyword, player, food);
        }
        else if (Optional.ofNullable(GameAssets.choiceItemMap.get(input.toLowerCase())).isPresent()) {
          for (Item resource : GameAssets.gameItems.get(GameAssets.choiceItemMap.get(input.toLowerCase()).getName()).getResourcesRequired()) {
            if (player.getShelter().getEquipment().containsKey(resource)) {
              numResources++;
            }
          }
          if (numResources == GameAssets.gameItems.get(GameAssets.choiceItemMap.get(input.toLowerCase()).getName()).getResourcesRequired().size()) {
            item = Optional.ofNullable(GameAssets.choiceItemMap.get(input.toLowerCase())).get();
          } else {
            item = null;
          }
          choice = new Choice(keyword, player, item);
        }
      } else {
        choice = new Choice(keyword, player);
      }
    } else {
      choice = null;
    }
    return choice;
  }

  public static Activity parseActivityChoice(Choice choice) {
    Activity activity;
    if (choice == null) {
      // display help menu
      activity = null;
    } else {
      if (choice.getKeyword().equals("get")) {
        activity = GetItemActivity.getInstance();
      } else if (choice.getKeyword().equals("put")) {
        activity = PutItemActivity.getInstance();
      } else if (choice.getKeyword().equals("eat")) {
        activity = EatActivity.getInstance();
      } else if (choice.getKeyword().equals("drink")) {
        activity = DrinkWaterActivity.getInstance();
      } else if (choice.getKeyword().equals("fish")) {
        activity = FishActivity.getInstance();
      } else if (choice.getKeyword().equals("hunt")) {
        activity = HuntActivity.getInstance();
      } else if (choice.getKeyword().equals("trap")) {
        activity = TrapActivity.getInstance();
      } else if (choice.getKeyword().equals("forage")) {
        activity = ForageActivity.getInstance();
      } else if (choice.getKeyword().equals("improve")) {
        activity = ImproveShelterActivity.getInstance();
      } else if (choice.getKeyword().equals("gather")) {
        activity = GatherFirewoodActivity.getInstance();
      } else if (choice.getKeyword().equals("fire")) {
        activity = BuildFireActivity.getInstance();
      } else if (choice.getKeyword().equals("water")) {
        activity = GetWaterActivity.getInstance();
      } else if (choice.getKeyword().equals("morale")) {
        activity = BoostMoraleActivity.getInstance();
      } else if (choice.getKeyword().equals("make")) {
        activity = MakeItemActivity.getInstance();
      } else if (choice.getKeyword().equals("collect")) {
        activity = GatherResourceActivity.getInstance();
      } else {
        activity = RestActivity.getInstance();
      }
    }
    return activity;
  }

}
