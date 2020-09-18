package com.palehorsestudios.alone.gui;

import com.palehorsestudios.alone.*;
import com.palehorsestudios.alone.activity.Activity;
import com.palehorsestudios.alone.activity.ActivityLevel;
import com.palehorsestudios.alone.activity.BuildFireActivity;
import com.palehorsestudios.alone.activity.DrinkWaterActivity;
import com.palehorsestudios.alone.activity.EatActivity;
import com.palehorsestudios.alone.activity.GetItemActivity;
import com.palehorsestudios.alone.activity.PutItemActivity;
import com.palehorsestudios.alone.Choice;
import com.palehorsestudios.alone.Food;
import com.palehorsestudios.alone.HelperMethods;
import com.palehorsestudios.alone.Item;
import com.palehorsestudios.alone.activity.*;
import com.palehorsestudios.alone.dayencounter.BearEncounterDay;
import com.palehorsestudios.alone.dayencounter.DayEncounter;
import com.palehorsestudios.alone.nightencounter.BearEncounterNight;
import com.palehorsestudios.alone.nightencounter.RainStorm;
import com.palehorsestudios.alone.nightencounter.RescueHelicopterNight;
import com.palehorsestudios.alone.player.Player;
import com.palehorsestudios.alone.player.SuccessRate;
import com.palehorsestudios.alone.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Map;
import java.util.Set;

import static com.palehorsestudios.alone.util.AchievementTracker.showAchievementTracker;
import static com.palehorsestudios.alone.util.LeaderBoard.*;

import static com.palehorsestudios.alone.Main.parseActivityChoice;
import static com.palehorsestudios.alone.Main.parseChoice;
import static com.palehorsestudios.alone.util.DynamoDBoperations.dbConn;
import static javafx.util.Duration.seconds;

public class GameApp extends Application {
    private static final int COUNT_DOWN = 30;
    private static GameApp instance;
    private static Set<Item> initItems;
    // inner input signal Class
    private final InputSignal inputSignal = new InputSignal();
    private ItemSelectionController itemSelectionController;
    private GameController gameController;
    private IntroController introController;
    private String currentInput;
    private Player player;
    private Thread thread;
    private Sound introSound;
    private Sound encounterSound = new Sound("resources/Sound.Encounters/BearEncounter.wav", 3200);
    private Thread encounterSoundThread;

    public GameApp() {
        instance = this;
    }

    public static GameApp getInstance() {
        return instance;
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
        if (player.getWeight() < 180.0 * 0.8) {
            result = result + " But you die of losing too much weight! /n/n Game Over!";
        }
        int hydrationCost = ActivityLevel.MEDIUM.getHydrationCost(successRate);
        player.setHydration(player.getHydration() - hydrationCost);
        if (player.getHydration() < 0) {
            result = result + " But you die of thirst! /n/n Game Over!";
        }
        player.getShelter().setFire(false);
        return result;
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
        introController.getIntro().setScrollTop(0);
        DynamoDBoperations.updateGuiTopTenFromDynamoDB(introController, dbConn());


        // config event listener for select items scene
        EventHandler<ActionEvent> startGameHandler =
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        FXMLLoader itemSelectLoader = new FXMLLoader();
                        try {
                            itemSelectionController = new ItemSelectionController();
                            itemSelectLoader.setController(itemSelectionController);
                            itemSelectLoader.setLocation(GameApp.class.getResource("selectItems.fxml"));
                            VBox itemSelectLayout = itemSelectLoader.load();
                            // Show the scene containing the root layout.
                            Scene selectItemsScene = new Scene(itemSelectLayout);
                            Stage selectItemsStage = new Stage();
                            selectItemsStage.setScene(selectItemsScene);
                            // hide intro scene
                            introScene.getWindow().hide();
                            // show game scene
                            selectItemsStage.show();
                            // start sound
                            introSound = new Sound("resources/Sound/Intro/Alone.wav", 30000);
                            thread = new Thread(introSound);
                            thread.start();

                            // start the count down timer
                            final Integer[] timeSeconds = {COUNT_DOWN};
                            Timeline timeline = new Timeline();
                            timeline.setCycleCount(Timeline.INDEFINITE);
                            timeline
                                    .getKeyFrames()
                                    .add(
                                            new KeyFrame(
                                                    seconds(1),
                                                    new EventHandler() {
                                                        @Override
                                                        public void handle(Event event) {
                                                            timeSeconds[0]--;
                                                            // update timerLabel
                                                            itemSelectionController
                                                                    .getCountdown()
                                                                    .setText(timeSeconds[0].toString());
                                                            if (timeSeconds[0] <= 0) {
                                                                itemSelectionController.getPaneSelected().setDisable(true);
                                                                timeline.stop();
                                                            }
                                                        }
                                                    }));
                            timeline.playFromStart();
                            itemSelectionController.selectItems();
                            DynamoDBoperations.updateGuiTopTenFromDynamoDB(itemSelectionController, dbConn());

                            // config next button listener
                            EventHandler<ActionEvent> nextHandler =
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
                                                // get initial items from the checkboxes
                                                initItems = itemSelectionController.selectItems();
                                                // hide item selection scene
                                                selectItemsScene.getWindow().hide();
                                                System.out.println(initItems.size());
                                                // show game scene
                                                gameStage.show();
                                                // start the background game thread
                                                runGameThread();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };

                            itemSelectionController.getNext().setOnAction(nextHandler);
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
            currentInput = gameController.getPlayerInput().getText().trim();
            InputValidator.checkInput(currentInput,player,instance);
            gameController.getPlayerInput().clear();
            gameController.getPlayerInput().requestFocus();
          }
        };

    EventHandler<KeyEvent> enterPressedHandler =
        keyEvent -> {
          if (keyEvent.getCode() == KeyCode.ENTER) {
            currentInput = gameController.getPlayerInput().getText().trim();
            InputValidator.checkInput(currentInput,player,instance);
            gameController.getPlayerInput().clear();
            gameController.getPlayerInput().requestFocus();
          }
        };

    gameController.getPlayerInput().setOnKeyPressed(enterPressedHandler);

    gameController.getEnterButton().setOnAction(eventHandler);
    Thread gameLoop =
        new Thread(
            new Runnable() {
              @Override
              public void run() {
                executeGameLoop();
              }
            });

    // don't let thread prevent JVM shutdown
    gameLoop.setDaemon(true);
    gameLoop.start();
  }

  // game thread logic, so we should also wrap the UI access calls
  private void executeGameLoop() {
    player = new Player(initItems);
    // flag for encounter results
    boolean encounterDeath = false;
    boolean encounterRescue = false;
    // encounter results
    String encounterResults = "Killed by the encounter";
    // must run in ui thread
    Platform.runLater(
            new Runnable() {
              @Override
              public void run() {
                getNarrative(new File("resources/parserHelp.txt"));
                getNarrative(new File("resources/scene1.txt"));
                introSound.doTerminateSound();
              }
            });
    final int[] day = {1};
    final String[] dayHalf = {"Morning"};
    gameController.getDateAndTime().setText("Day " + day[0] + " " + dayHalf[0]);
      DynamoDBoperations.updateGuiTopTenFromDynamoDB(gameController, dbConn());
      while (!player.isDead() && !player.isRescued() && !player.isRescued(day[0])) {
      // update the UI fields
      introSound.doTerminateSound();
      updateUI();
      String input = getInput();
      Choice choice = parseChoice(input, player);
      Activity activity = parseActivityChoice(choice);
      //log activity in the stat tracker
      StatTracker.logActivity(activity);
      if (activity == null) {
        getNarrative(new File("resources/parserHelp.txt"));
      } else if (activity == EatActivity.getInstance()
          || activity == DrinkWaterActivity.getInstance()
          || activity == GetItemActivity.getInstance()
          || activity == PutItemActivity.getInstance()
          || activity == BuildFireActivity.getInstance()
          || activity == MakeItemActivity.getInstance()) {
        String activityResult = activity.act(choice);
        gameController
                .getDailyLog()
                .appendText("Day " + day[0] + " " + dayHalf[0] + ": " + activityResult + "\n");
      } else {
        final int[] seed = {(int) Math.floor(Math.random() * 10)};
        String activityResult;
        // always perform activity typed out after encounter
        activityResult = activity.act(choice);
        gameController
                .getDailyLog()
                .appendText("Day " + day[0] + " " + dayHalf[0] + ": " + activityResult + "\n");

        // run encounter if occurs
        if (seed[0] > 2) {
          //refactored activityResult to include GameAssets encounters
          int randomDayEncounterIndex = (int) Math.floor(Math.random() * GameAssets.getEncounters().values().size());
          DayEncounter randomEncounter = (DayEncounter) GameAssets.getEncounters().values().toArray()[randomDayEncounterIndex];
//          DayEncounter randomEncounter = GameAssets.getEncounters().get("Sunny Day");
          //log encounter in the stat tracker
          StatTracker.logEncounter(randomEncounter);
          activityResult = randomEncounter.encounter(player);
          if (player.isDead()) {
            encounterDeath = true;
          } else if (player.isRescued()) {
            encounterRescue = true;
          }
          encounterResults = activityResult;
          //play encounter sound
          encounterSound.setSoundPath(randomEncounter.getSoundPath());
          encounterSoundThread = new Thread(encounterSound);
          encounterSoundThread.start();
          //show result if encounter occurs
          gameController
                  .getDailyLog()
                  .appendText("Day " + day[0] + " " + dayHalf[0] + ": " + activityResult + "\n");

        }

        if (dayHalf[0].equals("Morning")) {
          dayHalf[0] = "Afternoon";
        } else {
          if (!player.isDead() && !player.isRescued(day[0])) {
            seed[0] = (int) Math.floor(Math.random() * 10);
            String nightResult;
            if (seed[0] > 1) {
              // changed to type day encounter to log in stat tracker
              DayEncounter[] nightEncounters =
                      new DayEncounter[]{
                              RainStorm.getInstance(),
                              BearEncounterNight.getInstance(),
                              RescueHelicopterNight.getInstance()};
              int randomNightEncounterIndex =
                      (int) Math.floor(Math.random() * nightEncounters.length);
              DayEncounter nightEncounter = nightEncounters[randomNightEncounterIndex];
              // log encounter in stat tracker
              StatTracker.logEncounter(nightEncounter);
              nightResult = nightEncounter.encounter(player);
              if (player.isDead()) {
                encounterDeath = true;
              } else if (player.isRescued()) {
                encounterRescue = true;
              }
              encounterResults = nightResult;
              encounterSound.setSoundPath(nightEncounter.getSoundPath());
              encounterSoundThread = new Thread(encounterSound);
              encounterSoundThread.start();
            } else {
              nightResult = overnightStatusUpdate(player);
            }
            gameController
                    .getDailyLog()
                    .appendText("Day " + day[0] + " Night: " + nightResult + "\n");
            dayHalf[0] = "Morning";
            day[0]++;

          }
        }
        gameController.getDateAndTime().setText("Day " + day[0] + " " + dayHalf[0]);
      }
    }
//    gameController.getPlayerInput().setVisible(false);
//    gameController.getEnterButton().setVisible(false);
    updateUI();


    //this is when game ends and display changes
    //make score calculator, calculate score, setScore of player object
    final int score = ScoreCalculator.getInstance().calculateScore(player, day[0]);
    player.setScore(score);

    getGameController().getGameOver().setVisible(true);
    getGameController().getGameOver().setStyle("-fx-text-alignment: center");
    if (player.isDead()) {
      if (encounterDeath) {
        getGameController().getGameOver().appendText("GAME OVER\n" + encounterResults);
      } else {
        if (player.getWeight() < 180.0 * 0.8) {
          getGameController()
                  .getGameOver()
                  .appendText("GAME OVER\nYour malnutrition caused you to die of hypothermia. :-(");
        } else if (player.getMorale() <= 0) {
          getGameController()
                  .getGameOver()
                  .appendText("GAME OVER\nYour morale is too low. You died of despair.");
        } else {
          getGameController().getGameOver().appendText("GAME OVER\nYou died of thirst.");
        }
      }
    } else {
      if (encounterRescue) {
        getGameController().getGameOver().appendText("YOU SURVIVED!\n" + encounterResults);
      } else {
        getGameController()
                .getGameOver()
                .appendText(
                        "YOU SURVIVED!\nA search and rescue party has found you at last. No more eating bugs for you (unless you're into that sort of thing).");
      }
    }

    //append score to gameController gameover text
    getGameController().getGameOver().appendText("\n\nYour score: " + String.valueOf(score));
    //append activity tracker log to game over text
    getGameController().getGameOver().appendText("\n\n" + StatTracker.displayActivitiesLog());
    //append encounter tracker log to game over text
    getGameController().getGameOver().appendText("\n" + StatTracker.displayEncountersLog());
      //append accomplishments tracker log to game_over text
      getGameController().getGameOver().appendText("\n" + showAchievementTracker());
    if (player.isDead() || player.isRescued() || player.isRescued(day[0])) {
      saveUserScore(score);
    }
  }

    public void updateUI() {
        // update player status
        Platform.runLater(
                new Runnable() {
                    @Override
                    public void run() {
                        if (!gameController.getWeight().getText().isEmpty()
                                && !gameController.getWeight().getText().isBlank()
                                && !gameController.getHydration().getText().isEmpty()
                                && !gameController.getHydration().getText().isBlank()
                                && !gameController.getMorale().getText().isEmpty()
                                && !gameController.getMorale().getText().isBlank()
                                && !gameController.getIntegrity().getText().isEmpty()
                                && !gameController.getIntegrity().getText().isBlank()
                                && !gameController.getFirewood().getText().isEmpty()
                                && !gameController.getFirewood().getText().isBlank()
                                && !gameController.getWater().getText().isEmpty()
                                && !gameController.getWater().getText().isBlank()) {
                            try {
                                double currentWeight = Double.parseDouble(gameController.getWeight().getText());
                                if (currentWeight < player.getWeight()) {
                                    gameController.getWeight().setStyle("-fx-text-inner-color: green;");
                                } else if (currentWeight > player.getWeight()) {
                                    gameController.getWeight().setStyle("-fx-text-inner-color: red;");
                                } else {
                                    gameController.getWeight().setStyle("-fx-text-inner-color: black;");
                                }
                                int currentHydration = Integer.parseInt(gameController.getHydration().getText());
                                if (currentHydration < player.getHydration()) {
                                    gameController.getHydration().setStyle("-fx-text-inner-color: green;");
                                } else if (currentHydration > player.getHydration()) {
                                    gameController.getHydration().setStyle("-fx-text-inner-color: red;");
                                } else {
                                    gameController.getHydration().setStyle("-fx-text-inner-color: black;");
                                }
                                int currentMorale = Integer.parseInt(gameController.getMorale().getText());
                                if (currentMorale < player.getMorale()) {
                                    gameController.getMorale().setStyle("-fx-text-inner-color: green;");
                                } else if (currentMorale > player.getMorale()) {
                                    gameController.getMorale().setStyle("-fx-text-inner-color: red;");
                                } else {
                                    gameController.getMorale().setStyle("-fx-text-inner-color: black;");
                                }
                                double currentIntegrity =
                                        Double.parseDouble(gameController.getIntegrity().getText());
                                if (currentIntegrity < player.getShelter().getIntegrity()) {
                                    gameController.getIntegrity().setStyle("-fx-text-inner-color: green;");
                                } else if (currentIntegrity > player.getShelter().getIntegrity()) {
                                    gameController.getIntegrity().setStyle("-fx-text-inner-color: red;");
                                } else {
                                    gameController.getIntegrity().setStyle("-fx-text-inner-color: black;");
                                }
                                double currentFirewood = Double.parseDouble(gameController.getFirewood().getText());
                                if (currentFirewood < player.getShelter().getFirewood()) {
                                    gameController.getFirewood().setStyle("-fx-text-inner-color: green;");
                                } else if (currentFirewood > player.getShelter().getFirewood()) {
                                    gameController.getFirewood().setStyle("-fx-text-inner-color: red;");
                                } else {
                                    gameController.getFirewood().setStyle("-fx-text-inner-color: black;");
                                }
                                int currentWater = Integer.parseInt(gameController.getWater().getText());
                                if (currentWater < player.getShelter().getWaterTank()) {
                                    gameController.getWater().setStyle("-fx-text-inner-color: green;");
                                } else if (currentWater > player.getShelter().getWaterTank()) {
                                    gameController.getWater().setStyle("-fx-text-inner-color: red;");
                                } else {
                                    gameController.getWater().setStyle("-fx-text-inner-color: black;");
                                }
                            } catch (Exception e) {
                            }
                        }
                        gameController.getWeight().setText(String.valueOf(HelperMethods.round(player.getWeight(), 1)));
                        gameController.getHydration().setText(String.valueOf(player.getHydration()));
                        gameController.getMorale().setText(String.valueOf(player.getMorale()));
                        gameController
                                .getIntegrity()
                                .setText(String.valueOf((player.getShelter().getIntegrity())));
                        gameController
                                .getFirewood()
                                .setText(String.valueOf((player.getShelter().getFirewood())));
                        gameController.getWater().setText(String.valueOf((player.getShelter().getWaterTank())));
                    }
                });

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
                                gameController.getCarriedItems().getItems().add(item.toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

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
                                        .add(HelperMethods.getLargestFoodUnit(entry.getValue()) + " " + entry.getKey());
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

    //inner input signal Class

    public static class InputSignal{}

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

    public void saveUserScore(int score) {
        Platform.runLater(
                new Runnable() {
                    @Override
                    public void run() {
                        gameController.getLabelPlayerInput().setText("Player Input: Please enter your name to save score");
                        EventHandler<ActionEvent> eventHandler =
                                new EventHandler<ActionEvent>() {

                                    public void handle(ActionEvent event) {
                                        currentInput = gameController.getPlayerInput().getText().trim();
                                        if (currentInput.isEmpty()) {
                                            LeaderBoard.makeUpdateLeader("Anonymous", score, gameController, dbConn());
                                        } else {
                                            LeaderBoard.makeUpdateLeader(LeaderBoard.capitalize(currentInput), score, gameController, dbConn());
                                        }
                                        gameController.getLabelPlayerInput().setText("Thank you very much for playing!");
                                        gameController.getPlayerInput().setVisible(false);
                                        gameController.getEnterButton().setVisible(false);
                                    }
                                };

                        EventHandler<KeyEvent> enterPressedHandler =
                                keyEvent -> {
                                    if (keyEvent.getCode() == KeyCode.ENTER) {
                                        currentInput = gameController.getPlayerInput().getText().trim();
                                        if (currentInput.isEmpty()) {
                                            LeaderBoard.makeUpdateLeader("Anonymous", score, gameController, dbConn());
                                        } else {
                                            LeaderBoard.makeUpdateLeader(LeaderBoard.capitalize(currentInput), score, gameController, dbConn());
                                        }
                                        gameController.getLabelPlayerInput().setText("Thank you very much for playing!");
                                        gameController.getPlayerInput().setVisible(false);
                                        gameController.getEnterButton().setVisible(false);
                                    }
                                };

                        gameController.getPlayerInput().setOnKeyPressed(enterPressedHandler);

                        gameController.getEnterButton().setOnAction(eventHandler);
                    }
                });
    }

    // Makes sure to terminate all threads when closing game window.
    @Override
    public void stop() throws Exception {
        System.exit(0);
    }

}
