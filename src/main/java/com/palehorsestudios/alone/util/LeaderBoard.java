package com.palehorsestudios.alone.util;

import com.palehorsestudios.alone.gui.GameController;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class LeaderBoard {
    private static Scanner scanner;
    private static Map<String, Integer> scoresMap = new HashMap<>();


    /**
     * Adds name with score to file, updates if already present
     *
     * @param playerName
     * @param playerScore
     */
    public static void makeUpdateLeader(String playerName, int playerScore, GameController gameController) {
        playerName = capitalize(playerName);
        String newLeaderFile = "resources/Scores/newLeaderFile.txt";
        String oldLeaderFile = "resources/Scores/LeaderBoard.txt";
        String name = "";
        String score = "";

        File oldLeaderBoard = new File(oldLeaderFile);
        File newLeaderBoard = new File(newLeaderFile);

        try {
            FileWriter fw = new FileWriter(newLeaderFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            scanner = new Scanner(new File(oldLeaderFile));
            scanner.useDelimiter("[,\n]");
            scanner.nextLine(); // skips first line because is the title
            while (scanner.hasNext()) {
                name = capitalize(scanner.next().strip());
                score = scanner.next().strip();
                if (name.equals(playerName)) {
                    scoresMap.put(playerName, playerScore);
                } else {
                    scoresMap.put(name, Integer.parseInt(score));
                }
            }

            if (!scoresMap.containsKey(playerName)) {
                scoresMap.put(playerName, playerScore);
            }

            scoresMap = sortByValueTopTen(scoresMap);

            pw.println("Top Ten Leader Board");
            for (Map.Entry<String, Integer> entry : scoresMap.entrySet()) {
                pw.println(entry.getKey() + ", " + entry.getValue());
            }

            scanner.close();
            pw.flush();
            pw.close();
            oldLeaderBoard.delete();
            File temp = new File(oldLeaderFile);
            newLeaderBoard.renameTo(temp);
            LeaderBoard.updateGuiTopTen(gameController, scoresMap);
        } catch (IOException e) {
            System.out.println("Error with LeaderBoard.java writing to file");
            e.printStackTrace();
        }
    }

    // method to sort map by values
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

    /**
     * Updates Meny with top ten
     *
     * @param gameController
     */
    public static void updateGuiTopTen(GameController gameController, Map<String,Integer> mapWithScores) {
        int item = 0;
        for (Map.Entry<String, Integer> entry : mapWithScores.entrySet()) {
            if(item < gameController.getMenuTopTen().getItems().size() ){
                gameController.getMenuTopTen().getItems().get(item).setText(String.valueOf(entry.getKey() + " " + entry.getValue()));
                item++;
            }
        }

    }

    public static Map<String, Integer> readOldScoresMap() {
        Map<String, Integer> oldScoresMap = new HashMap<>();
        String name;
        String score;
        String oldLeaderFile = "resources/Scores/LeaderBoard.txt";
        File oldLeaderBoard = new File(oldLeaderFile);
        try {
            scanner = new Scanner(new File(oldLeaderFile));
            scanner.useDelimiter("[,\n]");
            scanner.nextLine(); // skips first line because is the title
            while (scanner.hasNext()) {
                name = capitalize(scanner.next().strip());
                score = scanner.next().strip();
                oldScoresMap.put(name, Integer.parseInt(score));

            }
        } catch (IOException e) {
            System.out.println("Error with LeaderBoard.java writing to file");
            e.printStackTrace();
        }
        scanner.close();
        oldScoresMap =sortByValueTopTen(oldScoresMap);
        return oldScoresMap;
    }
}
