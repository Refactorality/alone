package com.palehorsestudios.alone.gui;

import com.palehorsestudios.alone.Main;
import com.palehorsestudios.alone.player.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class StartView extends Application {
  private static StartView instance;

  public StartView() {
    instance = this;
  }

  public static StartView getInstance() {
    return instance;
  }

  private Stage primaryStage;
  private BorderPane rootLayout;
  private FxmlController controller;
  private FXMLLoader loader;
  private static Scene scene;
  private String currentInput;

  public class InputSignal {}
  private InputSignal inputSignal = new InputSignal();

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

  @Override
  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    this.primaryStage.setTitle("Alone");
    initRootLayout();
    EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        currentInput = controller.getPlayerInput().getText();
        notifyInput();
      }
    };
    controller.getEnterButton().setOnAction(eventHandler);
    runGameThread();
  }

  /**
   * Initializes the root layout.
   */
  public void initRootLayout() {
    try {
      // Load root layout from fxml file.
      loader = new FXMLLoader();
      controller = new FxmlController();
      loader.setController(controller);
      loader.setLocation(StartView.class.getResource("rootLayout.fxml"));
      rootLayout = loader.load();
      getNarrative(new File("resources/intronarrative.txt"));
      getNarrative(new File("resources/itemselection.txt"));

      // Show the scene containing the root layout.
      scene = new Scene(rootLayout);
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void runGameThread() {
    Thread gameLoop = new Thread(new Runnable() {
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
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        getNarrative(new File("resources/scene1.txt"));
      }
    });
    // TODO: need to allow for two iterations per day
    int day = 1;
    while (!Main.isPlayerDead(player) && !Main.isPlayerRescued(day)) {
      int finalDay = day;
      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          controller.getDateAndTime().setText("Day" + finalDay + " morning");
        }
      });
      appendText("\nDAY " + day);
      Main.iterate(player);
      day++;
    }
  }

  // call from game logic thread to get the input
  public String getInput() {
    waitInput();
    return currentInput;
  }

  // getter to get controller
  public FxmlController getController() {
    return controller;
  }
  // getter to get the scene
  public static Scene getScene() {
    return scene;
  }

  public void getNarrative(File file) {
    try  {
      controller.getCurActivity().clear();
      String line;
      BufferedReader br = new BufferedReader(new FileReader(file));
      while ((line = br.readLine()) != null) {
        controller.getCurActivity().appendText(line + "\n");
      }
      br.close();
    } catch (IOException e) {
      System.out.println(
          "Whoops! We seemed to have misplaced the next segment of the story. We're working on it!");
    }
  }

  // call from game logic thread to update the UI
  public void appendText(String txt) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          controller.getCurActivity().appendText(txt + "\n");
        } catch (Exception e) {
          System.out.println(controller);
          e.printStackTrace();
        }
      }
    });
  }

  public void initStatus() {
    controller.getDateAndTime().setText("Day 1 morning");
    controller.getWeather().setText("Cloudy, temperature 40F");
  }
}
