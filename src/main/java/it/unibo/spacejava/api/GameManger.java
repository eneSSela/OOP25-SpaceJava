package it.unibo.spacejava.api;

/**
 * Questa è l'interfaccia che definisce il controller del gioco, per unire tutti i componenti. Come ad esempio il menu, 
 * la gestione del suono, la gestione delle onde di nemici e così via.
 */
public interface GameManger {

    /**
     * Avvia il gioco.
     */
    void startGame();

    /**
     * Aggiunge puti al punteggio.
     * 
     * @param points punti da aggiungere
     */
    void addScore(int points);

    /**
     * Restituisce il punteggio.
     * 
     * @return punteggio corrente
     */
    int getScore();

    /**
     * Sottrae punti dal punteggio.
     * 
     * @param points punti da sottrarre
     */
    void decreaseScore(int points);

    /**
     * Resetta il punteggio.
     */
    void resetScore();

}
