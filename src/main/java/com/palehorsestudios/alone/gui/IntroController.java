package com.palehorsestudios.alone.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;

public class IntroController{

  @FXML private Button startGame;
  @FXML private TextArea intro;
  @FXML private Menu MenuTopTen;

  public IntroController() {}

  public Button getStartGame() {
    return startGame;
  }

  public TextArea getIntro() {
    return intro;
  }

  public Menu getMenuTopTen() {
    return MenuTopTen;
  }

  public void setMenuTopTen(Menu menuTopTen) {
    MenuTopTen = menuTopTen;
  }
}
