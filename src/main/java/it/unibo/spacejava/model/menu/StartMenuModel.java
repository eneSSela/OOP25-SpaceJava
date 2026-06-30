package it.unibo.spacejava.model.menu;

import java.util.List;
import java.util.Objects;

import it.unibo.spacejava.api.MenuObserver;
import it.unibo.spacejava.api.StartMenu;

/**
 * Classe che rappresenta  il model per la shcermata del menu iniziale,
 * contiene la logica per la gestione delle ozpioni del menu,e per il lampeggio dell'ozpione slezionata, 
 * imeplementata tramite una lista di listener che notifica la view delle eventale odifica. 
 */
public class StartMenuModel implements StartMenu {

    private final List<String> options = List.of("Gioca", "Seleziona Skin", "Esci");
    private int selectedIndex;
    private boolean blinkOn;
    private MenuObserver observer;

    /**
     * Costruttore della classe StartMenuModel,
     * che inizializza l'indice dell'opzione selezionata a 0 e il lampeggiamento a true.
     */
    public StartMenuModel() {
        this.selectedIndex = 0;
        this.blinkOn = true;
    }

    /**
     * Restituisce la lista delle opzioni del menu.
     * 
     * @return la lista delle opzioni del menu
     */
    @Override
    public List<String> getOptions() {
        return options;
    }

    /**
     * Restituisce l'indice dell'opzione attualmente selezionata.
     * 
     * @return l'indice dell'opzione attualmente selezionata
     */
    @Override
    public int getSelectedIndex() {
        return selectedIndex;
    }

    /**
     * Seleziona l'opzione successiva.
     */
    @Override
    public void selectNext() {
        selectedIndex = (selectedIndex + 1) % options.size();
        notifyListener();
    }

    /**
     * Seleziona l'opzione precedente.
     */
    @Override
    public void selectPrevious() {
        selectedIndex = (selectedIndex - 1 + options.size()) % options.size();
        notifyListener();
    }

    /**
     * Restituisce lo stato del lampeggiamento.
     * 
     * @return true se il lampeggiamento è attivo, false altrimenti
     */
    public boolean isBlinkOn() {
        return blinkOn;
    }

    /**
     * Imposta lo stato del lampeggiamento.
     * 
     * @param blinkOn true per attivare il lampeggiamento, false per disattivarlo
     */
    public void setBlinkOn(final boolean blinkOn) {
        if (this.blinkOn != blinkOn) {
            this.blinkOn = blinkOn;
            this.notifyListener();
        }
    }

    /**
     * Metodo per poter settare l'observer.
     * 
     * @param obs il listener da aggiungere alla lista
     */
    public void setObserver(final MenuObserver obs) {
        observer = Objects.requireNonNull(obs);
    }

    /**
     * Metodo usato per poter notificare al observer che il model a subito un cambiamento.
     */
    private void notifyListener() {
        observer.updateMenuState();
    }

    /**
     * Restituisce l'opzione attualmente selezionata.
     * 
     * @return l'opzione attualmente selezionata sotto forma di striga per poterla visualizzare nella view
     */
    public String getSelectedOption() {
        return options.get(selectedIndex);
    }
}
