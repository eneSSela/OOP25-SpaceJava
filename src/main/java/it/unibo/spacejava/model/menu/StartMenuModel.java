package it.unibo.spacejava.model.menu;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class StartMenuModel {

    private final List<String> options = List.of("Gioca", "Seleziona Skin", "Esci");
    private int selectedIndex = 0;
    private boolean blinkOn = true;

    private final List<Runnable> listeners = new CopyOnWriteArrayList<>();

    /**
     * Restituisce la lista delle opzioni del menu.
     * @return la lista delle opzioni del menu
     */
    public List<String> getOptions() {
        return options;
    }

    /**
     * Restituisce l'indice dell'opzione attualmente selezionata.
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
     * @return true se il lampeggiamento è attivo, false altrimenti
     */
    public boolean isBlinkOn() {
        return blinkOn;
    }

    /**
     * Imposta lo stato del lampeggiamento.
     * @param blinkOn true per attivare il lampeggiamento, false per disattivarlo
     */
    public void setBlinkOn(boolean blinkOn) {
        if (this.blinkOn != blinkOn) {
            this.blinkOn = blinkOn;
            notifyListeners();
        }
    }
    
    /**
     * Aggiunge un listener che verra invaita una notifica ogni volta che il model subisce una modifica.
     * @param listener
     */
    public void addChangeListener(Runnable listener) {
        listeners.add(Objects.requireNonNull(listener));
    }

    /**
     * Il contrario del precedente, rimuove un listenre dalla lista dei listener che vongono notificati ad ogni modifica del model.
     * @param listener
     */
    public void removeChangeListener(Runnable listener) {
        listeners.remove(listener);
    }

    /**
     * Metodo che cilca tutti i listener presenti nella lista, per poterli notificare che il model a subito un cambiamento.
     */
    private void notifyListeners() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }

    /**
     * Restituisce l'opzione attualmente selezionata.
     * @return l'opzione attualmente selezionata sotto forma di striga per poterla visualizzare nella view
     */
    public String getSelectedOption() {
        return options.get(selectedIndex);
    }
}
