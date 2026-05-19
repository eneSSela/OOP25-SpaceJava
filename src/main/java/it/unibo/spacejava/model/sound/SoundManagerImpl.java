package it.unibo.spacejava.model.sound;

import java.io.IOException;
import java.util.Objects;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import it.unibo.spacejava.model.sound.api.SoundManager;

/**
 * Implementazione delle interfaccia SoundManager.
 * Questa classe gestisce la ripsoduzione degli effetti sonori e,
 * della musica di sottofondo utilizzando la librearia Java sound API.
 */
public final class SoundManagerImpl implements SoundManager {

    //Unica istanza del SoundManger, per avere un unico gesore dei seuoni in tutto il gioco
    private static final SoundManagerImpl INSTANCE = new SoundManagerImpl();

    //variabile per tenere in memoria la traccia audio che viene riprodotta in sottofondo
    private Clip backgroundMusicClip;

    /**
     * Restistuisce l'unica instanza disponibile del SoundManager,
     * questa scleta implementa il patter singleton, per garantire che ci sia un unico gestore dei suoni in tutto il gioco,
     * evitando cosi problemi di sincronizzazione o conflitti nella riproduzione dei suoni.
     * 
     * @return l'implementazione globale del SoundManager
     */
    public static SoundManagerImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void playSound(final String soundName) {

        if (Objects.isNull(soundName) || soundName.isEmpty()) {
            throw new IllegalArgumentException("Il precorso del file non può essere null o vuoto");
        }

        try {
            final AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(soundName));
            final Clip clip = AudioSystem.getClip();
            //Qusto listener serve per risolvere il memory leak
            clip.addLineListener(e -> {
                if (e.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
            clip.open(audioIn);
            clip.start();
        } catch (final UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Errore durante la riproduzione del suono: " + soundName); //NOPMD
            e.printStackTrace(); //NOPMD
        }
    }

    @Override
    public void playBackgroundMusic(final String musicName) {
       if (Objects.isNull(musicName) || musicName.isEmpty()) {
            throw new IllegalArgumentException("Il precorso del file non può essere null o vuoto");
        }
        this.stopBackgroundMusic(); // Ferma la musica di sottofondo precedente, se presente

        try {
            final AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(musicName));
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioIn);
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (final UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Errore durante la riproduzione del suono: " + musicName); //NOPMD
            e.printStackTrace(); //NOPMD
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
