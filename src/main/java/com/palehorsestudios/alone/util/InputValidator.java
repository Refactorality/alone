package com.palehorsestudios.alone.util;

import com.palehorsestudios.alone.gui.GameApp;
import com.palehorsestudios.alone.player.Player;

import static com.palehorsestudios.alone.Main.parseChoice;

public class InputValidator {

    /**
     * check user input and displays error message if wrong input
     * @param currentInput is what the user is typing
     * @param player  is the current player
     * @param instance  is the current game
     */
    public static void checkInput(String currentInput, Player player, GameApp instance){
        if (currentInput.length()>0 && parseChoice(currentInput, player) != null){
            instance.getGameController().getLabelPlayerInput().setText("Player Input:");
            instance.notifyInput();
        }else {
            instance.getGameController().getLabelPlayerInput().setText("Player Input: Hey! Wrong input! Please try again!");
        }
    }
}
