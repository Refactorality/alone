package com.palehorsestudios.alone.gui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;

public class FxmlController {
  @FXML
  private TextArea curActivity;
  @FXML
  private TextArea playerInput;
  @FXML
  private TextArea playerStat;
  @FXML
  private TextArea dailyLog;

  private static Scene scene;

  public FxmlController() {};

  public void setScene(Scene scene) {this.scene = scene;}


}
