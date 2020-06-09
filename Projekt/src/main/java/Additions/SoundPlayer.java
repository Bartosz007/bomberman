package Additions;

import javax.sound.sampled.*;
import java.io.File;

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
        try{
            clip.start();

        }catch(Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void playContinoulsly(){
        try {
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setLoud(float value){
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float val = 86.0f*value/100.0f;
        val = val - 80;
        gainControl.setValue(val);
    }

    public void stop(){
        try{
            clip.stop();
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
