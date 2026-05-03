package it.unibo.spacejava.model.sound;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import it.unibo.spacejava.model.sound.api.SoundManager;

public class SoundManagerImpl implements SoundManager{

    //variabile per tenere in memoria la traccia audio che viene riprodotta in sottofondo
    private Clip backgroundMusicClip;


    @Override
    public void playSound(String soundName) {
        
        if (soundName == null  || soundName.trim().isEmpty()) {
            throw new IllegalArgumentException("Il precorso del file non può essere null o vuoto");
        }

        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(soundName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            System.err.println("Errore durante la riproduzione del suono: " + soundName);
            e.printStackTrace();
        }
    }

    @Override
    public void playBackgroundMusic(String musicName) {
       if (musicName == null  || musicName.trim().isEmpty()) {
            throw new IllegalArgumentException("Il precorso del file non può essere null o vuoto");
        }
        this.stopBackgroundMusic(); // Ferma la musica di sottofondo precedente, se presente

        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(musicName ));
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioIn);
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.err.println("Errore durante la riproduzione del suono: " + musicName);
            e.printStackTrace();
        }
    }

    @Override
    public void stopBackgroundMusic() {
        if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop();
            backgroundMusicClip.close();
        }
    }
}
    

