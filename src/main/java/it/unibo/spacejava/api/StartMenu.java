package it.unibo.spacejava.api;

import java.util.List;

/**
 * StartMenu interfaccia che rappresenta il menu iniziale del gioco, con metodi per ottenere le opzioni del menu, 
 * selezionare l'opzione successiva o precedente e ottenere l'indice dell'opzione attualmente selezionata.
 */
public interface StartMenu {

    /**
     * Restituisce la lista delle opzioni del menu.
     * 
     * @return la lista delle opzioni del menu
     */
    List<String> getOptions();

    /**
     * Restituisce l'indice dell'opzione attualmente selezionata.
     */
    void selectNext();

    /**
     * Seleziona l'opzione precedente.
     */
    void selectPrevious();

    /**
     * Restituisce l'indice dell'opzione attualmente selezionata.
     * 
     * @return l'indice dell'opzione attualmente selezionata
     */
    int getSelectedIndex();
}
