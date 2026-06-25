package it.unibo.spacejava.api;

public interface Shop {
    /**
     * Tenta di comprare la skin attualmente selezionata.
     * 
     * @return true se l'acquisto va a buon fine, false altrimenti.
     */
    boolean buySelectedSkin();
}
