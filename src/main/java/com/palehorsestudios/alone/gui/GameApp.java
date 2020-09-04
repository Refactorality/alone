package com.palehorsestudios.alone.gui;

import com.palehorsestudios.alone.Main;
import com.palehorsestudios.alone.player.Player;
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

public class GameApp extends Application {
  private GameController gameController;
  private IntroController introController;
  private String currentInput;

  private static GameApp instance;

  public GameApp() {
    instance = this;
  }

  public static GameApp getInstance() {
    return instance;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    // Load root layout from fxml file.
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
    Player player = new Player(Main.getInitialItems());
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
    while (!Main.isPlayerDead(player) && !Main.isPlayerRescued(day)) {
      int finalDay = day;
      /*
      Platform.runLater(
        new Runnable() {
          @Override
          public void run() {
            gameController.getDateAndTime().setText("Day " + finalDay + " Morning");
          }
        });

      Main.iterate(player);
      Platform.runLater(
        new Runnable() {
          @Override
          public void run() {
            gameController.getDateAndTime().setText("Day " + finalDay + " Afternoon");
          }
        });
      Main.iterate(player);
      day++;

       */
      /*
      String dayHalf = "Afternoon";
      String input = StartView.getInstance().getInput();
      Choice choice = parseChoice(input, player);
      Activity activity = parseActivityChoice(choice);
      if(activity == null) {
        // display help
      }
      else if(activity == EatActivity.getInstance() || activity == DrinkWaterActivity.getInstance()) {
        activity.act(choice);
        activity.act(choice);
        Platform.runLater(
            new Runnable() {
              @Override
              public void run() {
                controller.getDateAndTime().setText("Day " + finalDay + " " + nextHalfDay(dayHalf));
                getNarrative(new File("resources/iterationChoices.txt"));
              }
            });
      }
      else {
        activity.act(choice);
        Platform.runLater(
            new Runnable() {
              @Override
              public void run() {
                controller.getDateAndTime().setText("Day " + finalDay + " " + nextHalfDay(dayHalf));
                getNarrative(new File("resources/iterationChoices.txt"));
              }
            });
        if(dayHalf.equals("Afternoon")) {
          day++;
        }
      }

       */
    }
  }

  private String nextHalfDay(String currentHalf) {
    if(currentHalf.equals("Morning")) {
      currentHalf = "Afternoon";
    } else {
      currentHalf = "Morning";
    }
    return currentHalf;
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
    try(BufferedReader br = new BufferedReader(new FileReader(file))) {
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
    try(BufferedReader br = new BufferedReader(new FileReader(file))) {
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
