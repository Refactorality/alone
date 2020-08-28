package com.palehorsestudios.alone;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
  public static void getNarrative(File file) throws FileNotFoundException {
    Scanner sc = new Scanner(new File(String.valueOf(file)));
    while(sc.hasNextLine()) {
      String line = sc.nextLine();
      System.out.println(line);
    }
  }
  public static void main(String[] args) throws IOException {
    // Main method that runs the game
    getNarrative(new File("resources/intronarrative.txt")); //initiates intro narrative
  }
}
