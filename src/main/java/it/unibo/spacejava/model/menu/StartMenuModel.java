package it.unibo.spacejava.model.menu;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Classe che rappresenta  il model per la shcermata del menu iniziale,
 * contiene la logica per la gestione delle ozpioni del menu,e per il lampeggio dell'ozpione slezionata, 
 * imeplementata tramite una lista di listener che notifica la view delle eventale odifica. 
 */
public class StartMenuModel {

    private final List<String> options = List.of("Gioca", "Seleziona Skin", "Esci");
    private int selectedIndex;
    private boolean blinkOn;
    private final List<Runnable> listeners = new CopyOnWriteArrayList<>();

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
     * Aggiunge un listener che verra invaita una notifica ogni volta che il model subisce una modifica.
     * 
     * @param listener il listener da aggiungere alla lista
     */
    public void addChangeListener(final Runnable listener) {
        listeners.add(Objects.requireNonNull(listener));
    }

    /**
     * Il contrario del precedente, rimuove un listenre dalla lista dei listener,
     * che vongono notificati ad ogni modifica del model.
     * 
     * @param listener listenre da rimuovere dalla lista
     */
    public void removeChangeListener(final Runnable listener) {
        listeners.remove(listener);
    }

    /**
     * Metodo che cilca tutti i listener presenti nella lista, per poterli notificare che il model a subito un cambiamento.
     */
    private void notifyListeners() {
        for (final Runnable listener : listeners) {
            listener.run();
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
