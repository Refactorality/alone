package com.palehorsestudios.alone.gui;

import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.Main;
import com.palehorsestudios.alone.activity.Activity;
import com.palehorsestudios.alone.activity.ActivityLevel;
import com.palehorsestudios.alone.activity.DrinkWaterActivity;
import com.palehorsestudios.alone.activity.EatActivity;
import com.palehorsestudios.alone.activity.GetItemActivity;
import com.palehorsestudios.alone.activity.PutItemActivity;
import com.palehorsestudios.alone.player.Player;
import com.palehorsestudios.alone.player.SuccessRate;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import static com.palehorsestudios.alone.Main.parseActivityChoice;
import static com.palehorsestudios.alone.Main.parseChoice;

public class GameApp extends Application {
  private GameController gameController;
  private IntroController introController;
  private String currentInput;
  private Player player;
  private static GameApp instance;

  public GameApp() {
    instance = this;
  }

  public static GameApp getInstance() {
    return instance;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    // Load intro layout from fxml file.
    FXMLLoader introLoader = new FXMLLoader();
    introController = new IntroController();
    introLoader.setController(introController);
    introLoader.setLocation(GameApp.class.getResource("intro.fxml"));
    VBox introLayout = introLoader.load();
    getIntro(new File("resources/intronarrative.txt"));
    // Show the scene containing the intro layout.
    Scene introScene = new Scene(introLayout);
    primaryStage.setScene(introScene);
    primaryStage.show();

    // config event listener
    EventHandler<ActionEvent> startGameHandler =
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            FXMLLoader gameViewLoader = new FXMLLoader();
            try {
              gameController = new GameController();
              gameViewLoader.setController(gameController);
              gameViewLoader.setLocation(GameApp.class.getResource("game.fxml"));
              VBox gameLayout = gameViewLoader.load();
              // Show the scene containing the root layout.
              Scene gameScene = new Scene(gameLayout);
              Stage gameStage = new Stage();
              gameStage.setScene(gameScene);
              getNarrative(new File("resources/itemselection.txt"));
              // hide intro scene
              introScene.getWindow().hide();
              // show game scene
              gameStage.show();
              // start the background game thread
              runGameThread();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        };
    introController.getStartGame().setOnAction(startGameHandler);
  }

  private void runGameThread() {
    EventHandler<ActionEvent> eventHandler =
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            currentInput = gameController.getPlayerInput().getText();
            gameController.getPlayerInput().clear();
            notifyInput();
          }
        };

    gameController.getEnterButton().setOnAction(eventHandler);
    Thread gameLoop =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                try {
                  // TODO: maybe not a delay start, instead using a start button.
                  Thread.sleep(3000);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
                executeGameLoop();
              }
            });

    // don't let thread prevent JVM shutdown
    gameLoop.setDaemon(true);
    gameLoop.start();
  }

  // game thread logic, so we should also wrap the UI access calls
  private void executeGameLoop() {
    player = new Player(Main.getInitialItems());
    // must run in ui thread
    Platform.runLater(
        new Runnable() {
          @Override
          public void run() {
            getNarrative(new File("resources/parserHelp.txt"));
          }
        });
    // TODO: need to allow for two iterations per day
    int day = 1;
    String dayHalf = "Morning";
    gameController.getDateAndTime().setText("Day " + day + " " + dayHalf);
    while (!player.isDead() && !player.isRescued(day)) {
      int dayForThread = day;
      String dayHalfForThread = dayHalf;
      // update the UI fields
      updateUI();
      String input = GameApp.getInstance().getInput();
      Choice choice = parseChoice(input, player);
      Activity activity = parseActivityChoice(choice);

      if (activity == null) {
        Platform.runLater(
            new Runnable() {
              @Override
              public void run() {
                getNarrative(new File("resources/parserHelp.txt"));
              }
            });
      } else if (activity == EatActivity.getInstance()
          || activity == DrinkWaterActivity.getInstance()
          || activity == GetItemActivity.getInstance()
          || activity == PutItemActivity.getInstance()) {
        String activityResult = activity.act(choice);
        Platform.runLater(
            new Runnable() {
              @Override
              public void run() {
                gameController
                    .getDailyLog()
                    .appendText("Day " + dayForThread + " " + dayHalfForThread + ": " + activityResult + "\n");
              }
            });
      } else {
        String activityResult = activity.act(choice);
        Platform.runLater(
            new Runnable() {
              @Override
              public void run() {
                gameController
                    .getDailyLog()
                    .appendText(
                        "Day "
                            + dayForThread
                            + " "
                            + dayHalfForThread
                            + ": "
                            + activityResult
                            + "\n");
                gameController.getDateAndTime().setText("Day " + dayForThread + " " + nextHalfDay(dayHalfForThread));
              }
            });
        if (dayHalfForThread.equals("Morning")) {
          day++;
          dayHalf = dayHalfForThread;
        }
      }
    }
    gameController.getPlayerInput().setVisible(false);
    gameController.getEnterButton().setVisible(false);
    if(player.isDead()) {
      if (player.getWeight() < 180.0 * 0.6) {
        appendToCurActivity("GAME OVER\n You starved to death :-(");
      } else if (player.getMorale() <= 0) {
        appendToCurActivity("GAME OVER\n Your morale is too low. You died of despair.");
      } else {
        appendToCurActivity("GAME OVER\n You died of thirst.");
      }
    }
    else {
      appendToCurActivity(
              "YOU SURVIVED!\nA search and rescue party has found you at last. No more eating bugs for you (unless you're into that sort of thing).");
    }
  }

  private static String nextHalfDay(String currentHalf) {
    if(currentHalf.equals("Morning")) {
      currentHalf = "Afternoon";
    } else {
      currentHalf = "Morning";
    }
    return currentHalf;
  }

  public static String overnightStatusUpdate(Player player) {
    String result;
    SuccessRate successRate;
    double overnightPreparedness = player.getShelter().getIntegrity();
    if (player.getShelter().hasFire()) {
      overnightPreparedness += 10;
    }
    if (overnightPreparedness < 10) {
      successRate = SuccessRate.HIGH;
      result = "It was a long cold night. I have to light a fire tonight!";
      player.updateMorale(-3);
    } else if (overnightPreparedness < 17) {
      successRate = SuccessRate.MEDIUM;
      result =
          "It was sure nice to have a fire last night, but this shelter doesn't provide much protection from the elements.";
      player.updateMorale(1);
    } else {
      successRate = SuccessRate.LOW;
      result =
          "Last night was great! I feel refreshed and ready to take on whatever comes my way today.";
      player.updateMorale(2);
    }
    double caloriesBurned = ActivityLevel.MEDIUM.getCaloriesBurned(successRate);
    player.updateWeight(-caloriesBurned);
    int hydrationCost = ActivityLevel.MEDIUM.getHydrationCost(successRate);
    player.setHydration(player.getHydration() - hydrationCost);
    return result;
  }

  // update UI status

  public void updateUI() {
    // update player status
    gameController.getWeight().setText(String.valueOf(player.getWeight()));
    gameController.getHydration().setText(String.valueOf(player.getHydration()));
    gameController.getMorale().setText(String.valueOf(player.getMorale()));
    // clear item in the list view
    Platform.runLater(
        new Runnable() {
          @Override
          public void run() {
            try {
              gameController.getCarriedItems().getItems().clear();
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
    // add new carried items to items list view
    Platform.runLater(
        new Runnable() {
          @Override
          public void run() {
            try {
              for (Item item : player.getItems()) {
                gameController.getCarriedItems().getItems().add(item);
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });

    // update shelter status
    gameController.getIntegrity().setText(String.valueOf((player.getShelter().getIntegrity())));
    gameController.getFirewood().setText(String.valueOf((player.getShelter().getFirewood())));
    gameController.getWater().setText(String.valueOf((player.getShelter().getWaterTank())));
    ;
    // clear food cache list view
    Platform.runLater(
        new Runnable() {
          @Override
          public void run() {
            try {
              gameController.getFoodCache().getItems().clear();
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
    // add new food cache to food list view
    Platform.runLater(
        new Runnable() {
          @Override
          public void run() {
            try {
              for (Map.Entry<Food, Double> entry : player.getShelter().getFoodCache().entrySet()) {
                gameController
                    .getFoodCache()
                    .getItems()
                    .add(entry.getValue() + " " + entry.getKey());
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
    // clear equipment list view
    Platform.runLater(
        new Runnable() {
          @Override
          public void run() {
            try {
              gameController.getEquipment().getItems().clear();
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
    // add new equipment to equipment list view
    Platform.runLater(
        new Runnable() {
          @Override
          public void run() {
            try {
              for (Map.Entry<Item, Integer> entry : player.getShelter().getEquipment().entrySet()) {
                gameController
                    .getEquipment()
                    .getItems()
                    .add(entry.getValue() + " " + entry.getKey());
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
  }

  // inner input signal Class
  private InputSignal inputSignal = new InputSignal();

  public class InputSignal {}

  public void waitInput() {
    synchronized (inputSignal) {
      try {
        inputSignal.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void notifyInput() {
    synchronized (inputSignal) {
      inputSignal.notify();
    }
  }

  // call from game logic thread to get the input
  public String getInput() {
    waitInput();
    return currentInput;
  }

  public void getNarrative(File file) {
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        gameController.getCurActivity().appendText(line + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println(
          "Whoops! We seemed to have misplaced the next segment of the story. We're working on it!");
    }
  }

  public void getIntro(File file) {
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        introController.getIntro().appendText(line + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println(
          "Whoops! We seemed to have misplaced the next segment of the story. We're working on it!");
    }
  }

  // call from game logic thread to update the UI for current activity
  public void appendToCurActivity(String txt) {
    Platform.runLater(
        new Runnable() {
          @Override
          public void run() {
            try {
              gameController.getCurActivity().appendText(txt + "\n");
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
  }

  public GameController getGameController() {
    return gameController;
  }
}
