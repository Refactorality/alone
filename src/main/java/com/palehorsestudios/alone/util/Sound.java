package com.palehorsestudios.alone.util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 *Class to play and stop sounds in a different thread anywhere.
 */
public class Sound implements Runnable{
    private String soundPath;
    private long milisPlayTime;
    private Clip mySound;

    //added for encounters
    public Sound(){};

    public Sound(String soundPath, long milisPlayTime){
        setSoundPath(soundPath);
        setMilisPlayTime(milisPlayTime);
    }

    public String getSoundPath() {
        return soundPath;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }

    public long getMilisPlayTime() {
        return milisPlayTime;
    }

    public void setMilisPlayTime(long milisPlayTime) {
        this.milisPlayTime = milisPlayTime;
    }

    public void playSound(){
        File myLocalFile = new File(getSoundPath());

        try{
            AudioInputStream myLocalWav = AudioSystem.getAudioInputStream(myLocalFile);
            mySound = AudioSystem.getClip();
            mySound.open(myLocalWav);
            mySound.start();
            Thread.sleep(getMilisPlayTime());
            mySound.stop();
        }catch (Exception e){
            System.err.println("Error with Sound");
            e.printStackTrace();
        }
    }

    public void doTerminateSound(){
        mySound.stop();
    }

    /**
     * Plays sound when method called.
     */
    @Override
    public void run() {
        playSound();
    }

}