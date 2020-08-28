package com.palehorsestudios.alone;

import com.palehorsestudios.alone.player.Player;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.Scanner;

public class Main {
  public static void getNarrative(File file) {
    try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(file)))) {
      stream.forEach(System.out::println);
    } catch (IOException e) {
      System.out.println("Whoops! We seemed to have misplaced the next segment of the story. We're working on it!");
    }
//    try {
//    Scanner sc = new Scanner(new File(String.valueOf(file)));
//    while(sc.hasNextLine()) {
//      String line = sc.next();
//      System.out.println(line);
//    }
//    sc.close();
//      }
//    catch(FileNotFoundException e) {
//      System.out.println("Whoops! We seemed to have misplaced the next segment of the story. We're working on it!");
//    }
  }
  public static void main(String[] args){
    // Main method that runs the game
    PlayerInput playerInput = new PlayerInput();
    getNarrative(new File("resources/intronarrative.txt")); //initiates intro narrative
    PlayerInput.getInstance().getString("Please provide an input ");
    System.out.println(playerInput);
//    getNarrative(new File("resources/itemselection.txt")); //prints items that can be selected
//    getNarrative(new File("resources/scene1.txt"));
  }
}
