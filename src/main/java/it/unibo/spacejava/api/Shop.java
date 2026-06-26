package it.unibo.spacejava.api;

/**
 * Contratto per l'acquisto di una skin.
 */
@FunctionalInterface
public interface Shop {
    /**
     * Tenta di comprare la skin attualmente selezionata.
     *
     * @param score il punteggio del giocatore
     * @return true se l'acquisto va a buon fine, false altrimenti
     */
    boolean buySelectedSkin(Score score);
}
