package com.od.output;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundPlayer {

    public static void playMusic(String musicLocation) {
        try {
            File musicPath = new File(musicLocation);
            if (musicPath.exists()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("File not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playSound(String soundLocation) {
        try {
            File musicPath = new File(soundLocation);
            if (musicPath.exists()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } else {
                System.out.println("File not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
