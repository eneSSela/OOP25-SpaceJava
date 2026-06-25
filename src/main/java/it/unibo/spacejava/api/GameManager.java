package it.unibo.spacejava.api;

/**
 * Questa è l'interfaccia che definisce il controller del gioco, per unire tutti i componenti. Come ad esempio il menu, 
 * la gestione del suono, la gestione delle onde di nemici e così via.
 */
public interface GameManager {

    /**
     * Avvia il gioco.
     */
    void startGame();

    /**
     * Ferma l'applicazione e chiude il gioco.
     */
    void stopGame();

}
