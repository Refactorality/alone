package com.palehorsestudios.alone.util;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.palehorsestudios.alone.gui.GameController;

import java.util.HashMap;
import java.util.Map;

import static com.palehorsestudios.alone.util.LeaderBoard.sortByValueTopTen;

public class DynamoDBoperations {

    public static AmazonDynamoDB dbConn() {
        BasicAWSCredentials credentials = new BasicAWSCredentials("", "");
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion("us-east-1")
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
        return client;
    }


    public static void addNewItemToLeaderBoard(String playerName, int playerScore, AmazonDynamoDB dbConn) throws Exception {

        DynamoDB dynamoDB = new DynamoDB(dbConn);

        Table table = dynamoDB.getTable("LeaderBoard");

        String name = playerName;
        int score = playerScore;

        try {
            System.out.println("Adding a new item...");
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey("Name", name).with("Score", score));
            System.out.println("PutItem succeeded:\n");

        } catch (Exception e) {
            System.err.println("Unable to add item: " + name + " " + score);
            System.err.println(e.getMessage());
        }
    }

    public static void updateGuiTopTenFromDynamoDB(GameController gameController, AmazonDynamoDB dbConn) {
        Map<String, Integer> oldScoresMap = new HashMap<>();
        int countMenuItems = 0;

        ScanRequest scanRequest = new ScanRequest()
                .withTableName("LeaderBoard");

        ScanResult result = dbConn.scan(scanRequest);
        for (Map<String, AttributeValue> item : result.getItems()) {
            oldScoresMap.put(item.get("Name").getS(), Integer.parseInt(item.get("Score").getN()));
        }

        oldScoresMap = sortByValueTopTen(oldScoresMap);

        for (Map.Entry<String, Integer> entry : oldScoresMap.entrySet()) {
            if (countMenuItems < gameController.getMenuTopTen().getItems().size()) {
                gameController.getMenuTopTen().getItems().get(countMenuItems).setText(entry.getKey() + " " + entry.getValue().toString());
                countMenuItems++;
            }
        }
        dbConn.shutdown();
    }
}

