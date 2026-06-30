package it.unibo.spacejava.model;

import it.unibo.spacejava.api.Score;

/**
 * Implementazione del punteggio del giocatore.
 */
public final class ScoreImpl implements Score {
    private int totalPoints;
    private int currentRunScore;
    private int highScore;

    /**
     * Costruttore che inizializza il punteggio a zero.
     */
    public ScoreImpl() {
        this.totalPoints = SaveManager.loadTotalScore();
        this.highScore = SaveManager.loadHighScore();
        this.currentRunScore = 0;
    }

    /**
     * Aggiunge punti al totale.
     *
     * @param points i punti da aggiungere
     */
    @Override
    public void addPoints(final int points) {
        if (points > 0) {
            this.currentRunScore += points;
            this.totalPoints += points;
            SaveManager.saveScores(this.totalPoints, this.highScore);
        }
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
            SaveManager.saveScores(this.totalPoints, this.highScore);
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

    @Override
    public int getCurrentRunScore() {
        return this.currentRunScore;
    }

    @Override
    public int getHighScore() {
        return this.highScore;
    }

    @Override
    public void resetCurrentRun() {
        if (this.currentRunScore > this.highScore) {
            this.highScore = this.currentRunScore;
            SaveManager.saveScores(this.totalPoints, this.highScore);
        } else {
            SaveManager.saveScores(this.totalPoints, this.highScore);
        }
        this.currentRunScore = 0;
    }
}
