package com.palehorsestudios.alone.util;

import com.palehorsestudios.alone.gui.GameController;
import javafx.scene.image.Image;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class Images {
    public static void showImg(GameController gameController, String imgPath){
        Image image = new Image(imgPath);
        gameController.getImgView().setImage(image);
        gameController.getImgView().setFitWidth(480);
        gameController.getImgView().setPreserveRatio(true);
    }

    public static void randomMorningImg(GameController gameController){
        String randomImgPath = getRandomImg("resources/Image/Morning");
        Image image = new Image(randomImgPath);
        gameController.getImgView().setImage(image);
        gameController.getImgView().setFitWidth(480);
        gameController.getImgView().setPreserveRatio(true);
    }

    public static void randomAfternoonImg(GameController gameController){
        String randomImgPath = getRandomImg("resources/Image/Afternoon");
        Image image = new Image(randomImgPath);
        gameController.getImgView().setImage(image);
        gameController.getImgView().setFitWidth(480);
        gameController.getImgView().setPreserveRatio(true);
    }

    private static String getRandomImg(String folderPath){
        File dir = new File(folderPath);
        File[] files = dir.listFiles();
        Random rand = new Random();
        String randomImg = files[rand.nextInt(files.length)].toPath().toString();
        return randomImg;
    }
}
