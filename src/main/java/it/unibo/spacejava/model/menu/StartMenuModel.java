package it.unibo.spacejava.model.menu;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class StartMenuModel {

    private final List<String> options = List.of("Gioca", "Seleziona Skin", "Esci");
    private int selectedIndex = 0;
    private boolean blinkOn = true;

    private final List<Runnable> listeners = new CopyOnWriteArrayList<>();

    public List<String> getOptions() {
        return options;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void selectNext() {
        selectedIndex = (selectedIndex + 1) % options.size();
        notifyListeners();
    }

    public void selectPrevious() {
        selectedIndex = (selectedIndex - 1 + options.size()) % options.size();
        notifyListeners();
    }

    public boolean isBlinkOn() {
        return blinkOn;
    }

    public void setBlinkOn(boolean blinkOn) {
        if (this.blinkOn != blinkOn) {
            this.blinkOn = blinkOn;
            notifyListeners();
        }
    }

    public void addChangeListener(Runnable listener) {
        listeners.add(Objects.requireNonNull(listener));
    }

    public void removeChangeListener(Runnable listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }

    public String getSelectedOption() {
        return options.get(selectedIndex);
    }
}
