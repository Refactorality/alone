package com.palehorsestudios.alone.util;

import com.palehorsestudios.alone.player.Player;

public class ScoreCalculator {
  private static ScoreCalculator calculator;

  private ScoreCalculator(){};

  public static ScoreCalculator getInstance(){
    if(calculator == null){
      calculator = new ScoreCalculator();
    }
    return calculator;
  }

  public int calculateScore(Player player, int day){

    // initial score set, based on if player is dead
    int playerScore = (player.isDead()) ? 0 : 10000;
    playerScore = playerScore / day;
    playerScore += player.getWeight();
    playerScore += player.getHydration();
    playerScore += player.getMorale();
    playerScore += player.getShelter().getWaterTank();
    playerScore += player.getShelter().getFirewood();
    playerScore += player.getShelter().getIntegrity();


    return playerScore;
  }




}
