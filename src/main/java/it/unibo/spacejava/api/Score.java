package it.unibo.spacejava.api;

/**
 * Rappresenta il punteggio del giocatore.
 */
public interface Score {
    /**
     * Aggiunge punti al punteggio totale.
     *
     * @param points i punti da aggiungere
     */
    void addPoints(int points);

    /**
     * Consuma i punti dal punteggio totale.
     *
     * @param points punti da sottrarre al totale
     * @return un booleano che corrisponde all'esito dell'operazione
     */
    boolean consumePoints(int points);

    /**
     * Restituisce il punteggio totale.
     *
     * @return il punteggio totale
     */
    int getTotal();
}
