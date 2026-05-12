package it.unibo.spacejava.model.sound.api;

/**
 * Questa interfaccia definisce il contratto per la gestione e la riproduzione 
 * degli effetti sonori e della musica all'interno del gioco.
 */
public interface SoundManager {

    /**
     * Riproduce un effetto sonoro specificato dal nome. Il nome deve corrispondere a una risorsa sonora disponibile.
     * 
     * @param soundName il percorso del file audio all'interno delle risorse
     * @throws IllegalArgumentException se il nome del suono non è valido o se la risorsa non è trovata
     */
    void playSound(String soundName);

    /**
     * Riproduce la musica di sottofondo specificata dal nome. Il nome deve corrispondere a una risorsa musicale disponibile.
     * 
     * @param musicName il percorso del file audio all'interno delle risorse
     * @throws IllegalArgumentException se il nome della musica non è valido o se la risorsa non è trovata
     */
    void playBackgroundMusic(String musicName);

    /**
     * Ferma la musica di sottofondo.
     */
    void stopBackgroundMusic();
}
