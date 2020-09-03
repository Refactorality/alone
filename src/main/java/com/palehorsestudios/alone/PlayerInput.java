package com.palehorsestudios.alone;

import java.util.Scanner;
import javax.swing.text.html.parser.Parser;

public final class PlayerInput { // singleton class to manage all player inputs
  private static PlayerInput refToPlayerInput = null;
  private Scanner scanner;

  public PlayerInput() {
    // Constructor that insists no object can be created from outside of the PlayerInput class
    // Implements Singleton design concept
    scanner = new Scanner(System.in);
  }

  public static PlayerInput getInstance() {
    // Static mod means this method can be called without the existence of an input object
    // If no input object exists one will be created
    // If one already exists, it will be reused
    if (refToPlayerInput == null) refToPlayerInput = new PlayerInput();
    return refToPlayerInput;
  }

  /**
   * @param playerAction is used to describe the best method to use to obtain the most logical
   *     player action given the narrative.
   * @return string of user input
   */
  String getString(String playerAction) {
    scanner.useDelimiter("\r\n"); // ensures capture of all input up to the enter keystroke
    String pInput = scanner.nextLine();
    return pInput;
    // preceding use of useDelimiter() changed state of Scanner object. reset() reestablishes
    // original state

  }
}