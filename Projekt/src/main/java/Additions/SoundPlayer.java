package Additions;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class SoundPlayer {
    private InputStream stream;
    private Clip clip;
    public SoundPlayer(InputStream stream) {

        try{
            this.stream = stream;
            InputStream bufferedIn = new BufferedInputStream(stream);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void changeTrack(InputStream stream){
        try {
            clip.stop();
            this.stream = stream;
            InputStream bufferedIn = new BufferedInputStream(stream);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

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
