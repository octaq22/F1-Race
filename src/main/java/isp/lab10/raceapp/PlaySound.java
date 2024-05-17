package isp.lab10.raceapp;

import java.io.File;
import javax.sound.sampled.*;

public class PlaySound{
    private Clip clip;
    private Clip clipfinal;
    private Clip clip3;

    void playSound() {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(".\\crofty.wav")));
            clip.start();

            // Adăugăm un listener pentru a detecta sfârșitul redării sunetului 1
            clip.addLineListener(new LineListener() {
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        // După ce sunetul 1 se termină, începem redarea sunetului 2
                        playSecondSound();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metodă pentru redarea sunetului al doilea
    void playSecondSound() {
        try {
            clipfinal = AudioSystem.getClip();
            clipfinal.open(AudioSystem.getAudioInputStream(new File(".\\shanghai-formula-1-grand-prix.wav")));
            clipfinal.start();

            // Adăugăm un listener pentru a detecta sfârșitul redării sunetului 2
            clipfinal.addLineListener(new LineListener() {
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        // După ce sunetul 2 se termină, începem redarea sunetului 3
                        playThirdSound();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metodă pentru redarea sunetului al treilea
    void playThirdSound() {
        try {
            clip3 = AudioSystem.getClip();
            clip3.open(AudioSystem.getAudioInputStream(new File(".\\f1podium.wav")));
            clip3.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void stopSound(){
        if(clip != null)
            clip.stop();
        if(clipfinal != null)
            clipfinal.stop();
        if(clip3 != null)
            clip3.stop();
    }

    public static void main(String[] args) throws InterruptedException {
        PlaySound ps = new PlaySound();
        ps.playSound();
    }
}
