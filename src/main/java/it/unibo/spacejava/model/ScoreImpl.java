package it.unibo.spacejava.model;

import it.unibo.spacejava.api.Score;

/**
 * Implementazione del punteggio del giocatore.
 */
public final class ScoreImpl implements Score {
    private int totalPoints;

    /**
     * Costruttore che inizializza il punteggio a zero.
     */
    public ScoreImpl() {
        this.totalPoints = 0;
    }

    /**
     * Aggiunge punti al totale.
     *
     * @param points i punti da aggiungere
     */
    @Override
    public void addPoints(final int points) {
        this.totalPoints += points;
    }

    /**
     * Consuma punti dal totale se disponibili.
     *
     * @param points i punti da consumare
     * @return true se i punti sono stati consumati, false altrimenti
     */
    @Override
    public boolean consumePoints(final int points) {
        if (this.totalPoints >= points) {
            this.totalPoints -= points;
            return true;
        }
        return false;
    }

    /**
     * Restituisce il totale attuale dei punti.
     *
     * @return il totale attuale
     */
    @Override
    public int getTotal() {
        return this.totalPoints;
    }
}
