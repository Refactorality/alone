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
import com.palehorsestudios.alone.gui.IntroController;
import com.palehorsestudios.alone.gui.ItemSelectionController;

import java.util.HashMap;
import java.util.Map;

import static com.palehorsestudios.alone.util.LeaderBoard.sortByValueTopTen;

public class DynamoDBoperations {

   /*
    To set these variables on Linux, macOS, or Unix, use export :
    export AWS_ACCESS_KEY_ID=your_access_key_id
    export AWS_SECRET_ACCESS_KEY=your_secret_access_key
    To set these variables on Windows, use set :
    set AWS_ACCESS_KEY_ID=your_access_key_id
    set AWS_SECRET_ACCESS_KEY=your_secret_access_key
    You can use the AWS Profile environment variable to change the profile loaded by the SDK.
    For example, on Linux, macOS, or Unix you would run the following command to change the profile to
    myProfile.
    export AWS_PROFILE="myProfile"
    On Windows you would use the following.
    set AWS_PROFILE="myProfile"
    */

    public static AmazonDynamoDB dbConn() {
        //System.out.println(System.getenv("AWS_SECRET_ACCESS_KEY"));
        BasicAWSCredentials credentials = new BasicAWSCredentials("AKIAYMDIU6O4224SK6XX", "RF9AOYvzcw9MqByP/GhotYQrAz53yyuzZ8nofR1P");

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion("us-east-1")
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
       // AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
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
                gameController.getMenuTopTen().getItems().get(countMenuItems). setText(entry.getKey() + " " + entry.getValue().toString());
                countMenuItems++;
            }
        }
        dbConn.shutdown();
    }

    public static void updateGuiTopTenFromDynamoDB(IntroController gameController, AmazonDynamoDB dbConn) {
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
                gameController.getMenuTopTen().getItems().get(countMenuItems). setText(entry.getKey() + " " + entry.getValue().toString());
                countMenuItems++;
            }
        }
        dbConn.shutdown();
    }

    public static void updateGuiTopTenFromDynamoDB(ItemSelectionController gameController, AmazonDynamoDB dbConn) {
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
                gameController.getMenuTopTen().getItems().get(countMenuItems). setText(entry.getKey() + " " + entry.getValue().toString());
                countMenuItems++;
            }
        }
        dbConn.shutdown();
    }


}

