package Additions;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {
    private String path;
    private Clip clip;
    public SoundPlayer(String path) {

        try{
            File musicPath = new File(path);
            if(musicPath.exists()){
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(musicPath));
            }else{
                System.out.println("Nie znaleziono takiego pliku");
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void changeTrack(String path){
        File musicPath = new File(path);
        try {
            clip.stop();
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(musicPath));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void playOnce(){
        clip.start();
    }

    public void playContinoulsly(){
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }


}
