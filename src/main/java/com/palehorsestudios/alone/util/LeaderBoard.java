package com.palehorsestudios.alone.util;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.palehorsestudios.alone.gui.GameController;
import com.palehorsestudios.alone.gui.IntroController;

import java.util.*;
import java.util.stream.Collectors;

import static com.palehorsestudios.alone.util.DynamoDBoperations.addNewItemToLeaderBoard;

public class LeaderBoard {
    private static final Map<String, Integer> scoresMap = new HashMap<>();
    private static Scanner scanner;

    /**
     * Adds name with score to dynamoDB, updates if already present, refreshes visual menu with top ten
     * leader board
     *
     * @param playerName
     * @param playerScore
     */
    public static void makeUpdateLeader(String playerName, int playerScore, GameController gameController, AmazonDynamoDB dbConn) {
        try {
            addNewItemToLeaderBoard(playerName, playerScore, dbConn);
            DynamoDBoperations.updateGuiTopTenFromDynamoDB(gameController, dbConn);

        } catch (Exception e) {
            System.out.println("Error updating leader board");
            e.printStackTrace();
        }
    }

    /**
     * method to sort map by values
     *
     * @param map
     * @return
     */
    public static Map<String, Integer> sortByValueTopTen(Map<String, Integer> map) {
        Map<String, Integer> topTen =
                map.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(10)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return topTen;
    }

    /**
     * Method to capitalize first letter of every word in a sentence
     *
     * @param string
     * @return
     */
    public static String capitalize(String string) {
        StringBuffer res = new StringBuffer();

        String[] strArr = string.toLowerCase().split(" ");
        for (String str : strArr) {
            char[] stringArray = str.trim().toCharArray();
            stringArray[0] = Character.toUpperCase(stringArray[0]);
            str = new String(stringArray);

            res.append(str).append(" ");
        }
        return res.toString().trim();
    }
}
