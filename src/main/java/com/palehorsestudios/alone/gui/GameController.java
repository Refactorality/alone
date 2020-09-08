package com.palehorsestudios.alone.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class GameController {
  @FXML private TextArea curActivity;
  @FXML private TextArea playerInput;
  @FXML private TextArea dailyLog;
  @FXML private TextField dateAndTime;
  @FXML private Button enterBtn;
  @FXML private TextField weight;
  @FXML private TextField hydration;
  @FXML private TextField morale;
  @FXML private ListView<String> carriedItems;
  @FXML private TextField integrity;
  @FXML private TextField firewood;
  @FXML private TextField water;
  @FXML private ListView<String> foodCache;
  @FXML private ListView<String> equipment;

  public TextField getWeight() {
    return weight;
  }

  public TextField getHydration() {
    return hydration;
  }

  public TextField getMorale() {
    return morale;
  }

  public ListView<String> getCarriedItems() { return carriedItems; }

  public TextField getIntegrity() {
    return integrity;
  }

  public TextField getFirewood() {
    return firewood;
  }

  public TextField getWater() {
    return water;
  }

  public ListView<String> getFoodCache() {
    return foodCache;
  }

  public ListView<String> getEquipment() {
    return equipment;
  }

  public GameController() {}

  public TextArea getCurActivity() {
    return curActivity;
  }

  public TextArea getPlayerInput() {
    return playerInput;
  }

  public TextArea getDailyLog() {
    return dailyLog;
  }

  public TextField getDateAndTime() {
    return dateAndTime;
  }

  public Button getEnterButton() {
    return enterBtn;
  }

}
