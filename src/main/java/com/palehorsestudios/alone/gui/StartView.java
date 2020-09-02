package com.palehorsestudios.alone.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StartView extends Application {

  private Stage primaryStage;
  private BorderPane rootLayout;
  private FxmlController controller;
  private FXMLLoader loader;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    this.primaryStage.setTitle("Star Search App");


    initRootLayout();
    // Load File
    // controller.loadFile(this.primaryStage);

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

      // Show the scene containing the root layout.
      Scene scene = new Scene(rootLayout);
      controller.setScene(scene);
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
