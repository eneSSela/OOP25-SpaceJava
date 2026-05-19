package it.unibo.spacejava.model.menu;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import it.unibo.spacejava.api.StartMenuObserver;

/**
 * Classe che rappresenta  il model per la shcermata del menu iniziale,
 * contiene la logica per la gestione delle ozpioni del menu,e per il lampeggio dell'ozpione slezionata, 
 * imeplementata tramite una lista di listener che notifica la view delle eventale odifica. 
 */
public class StartMenuModel {

    private final List<String> options = List.of("Gioca", "Seleziona Skin", "Esci");
    private int selectedIndex;
    private boolean blinkOn;
    private final List<StartMenuObserver> listeners = new CopyOnWriteArrayList<>();

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
    public List<String> getOptions() {
        return options;
    }

    /**
     * Restituisce l'indice dell'opzione attualmente selezionata.
     * 
     * @return l'indice dell'opzione attualmente selezionata
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }

    /**
     * Seleziona l'opzione successiva.
     */
    public void selectNext() {
        selectedIndex = (selectedIndex + 1) % options.size();
        notifyListeners();
    }

    /**
     * Seleziona l'opzione precedente.
     */
    public void selectPrevious() {
        selectedIndex = (selectedIndex - 1 + options.size()) % options.size();
        notifyListeners();
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
            this.notifyListeners();
        }
    }

    /**
     * Aggiunge un observer che invia una notifica ogni volta che il model subisce una modifica.
     * 
     * @param observer il listener da aggiungere alla lista
     */
    public void addObserver(final StartMenuObserver observer) {
        listeners.add(Objects.requireNonNull(observer));
    }

    /**
     * Il contrario del precedente, rimuove un observer dalla lista degli observer,
     * che vengono notificati ad ogni modifica del model.
     * 
     * @param observer observer da rimuovere dalla lista
     */
    public void removeObserver(final StartMenuObserver observer) {
        listeners.remove(observer);
    }

    /**
     * Metodo che cilca tutti i listener presenti nella lista, per poterli notificare che il model a subito un cambiamento.
     */
    private void notifyListeners() {
        for (final StartMenuObserver observer : listeners) {
            observer.updateMenuState();
        }
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
