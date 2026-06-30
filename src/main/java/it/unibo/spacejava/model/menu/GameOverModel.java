package it.unibo.spacejava.model.menu;

import java.util.List;
import java.util.Objects;

import it.unibo.spacejava.api.MenuObserver;

/**
 * Model class for managing the state of the GameOver menu screen.
 */
public final class GameOverModel {

    private final List<String> options = List.of("Gioca di nuovo", "Torna al menù");
    private int selectedIndex;
    private boolean blinkOn;
    private MenuObserver observer;
    private int finalScore;

    /**
     * Constructs a new model for the GameOver men, initializing the default state.
     */
    public GameOverModel() {
        this.selectedIndex = 0;
        this.blinkOn = true;
    }

    /**
     * Gets the list of available options in the GameOver menu.
     * 
     * @return an unmodifiable list of option strings
     */
    public List<String> getOptions() {
        return this.options;
    }

    /**
     * Gets the index of the currently selected option.
     * 
     * @return the currently selected index
     */
    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    /**
     * Moves the selection to the next option, wrapping around if necessary.
     */
    public void selectNext() {
        this.selectedIndex = (this.selectedIndex + 1) % this.options.size();
        this.notifyListener();
    }

    /**
     * Moves the selection to the previous option.
     */
    public void selectPrevious() {
        this.selectedIndex = (this.selectedIndex - 1 + this.options.size()) % this.options.size();
        this.notifyListener();
    }

    /**
     * Checks if the blinking cursor effect is currently active.
     * 
     * @return true if the blink is on, false otherwise
     */
    public boolean isBlinkOn() {
        return this.blinkOn;
    }

    /**
     * Sets the blinking state for the cursor and notifies the observer if it changes.
     * 
     * @param blinkOn true to turn the blink on, false to turn it off
     */
    public void setBlinkOn(final boolean blinkOn) {
        if (this.blinkOn != blinkOn) {
            this.blinkOn = blinkOn;
            this.notifyListener();
        }
    }

    /**
     * Sets the player's final score to be displayed on the GameOver screen.
     * 
     * @param score the final score to set
     */
    public void setFinalScore(final int score) {
        this.finalScore = score;
    }

    /**
     * Gets the player's final score.
     * 
     * @return the final score
     */
    public int getFinalScore() {
        return this.finalScore;
    }

    /**
     * Gets the text of the currently selected option.
     * 
     * @return the selected option string
     */
    public String getSelectedOption() {
        return this.options.get(this.selectedIndex);
    }

    /**
     * Sets the observer that will be notified of the changes in the model's state.
     * 
     * @param obs the observer to register
     */
    public void setObserver(final MenuObserver obs) {
        this.observer = Objects.requireNonNull(obs);
    }

    /**
     * Notifies the registered observer that the model's state has changed.
     */
    private void notifyListener() {
        if (this.observer != null) {
            observer.updateMenuState();
        }
    }
}
