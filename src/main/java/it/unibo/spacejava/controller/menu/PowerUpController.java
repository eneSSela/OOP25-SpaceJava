package it.unibo.spacejava.controller.menu;

import java.awt.event.KeyEvent;
import java.util.Objects;

import it.unibo.spacejava.KeyHandler;
import it.unibo.spacejava.api.Command;
import it.unibo.spacejava.model.menu.PowerUpSelectionModel;

/**
 * Controller for the power-up selection screen.
 * Handles input to navigate between orizzontal banners.
 */
public final class PowerUpController extends KeyHandler {

    private final PowerUpSelectionModel model;
    private final Command onConfirm;

    /**
     * Power up controller builder.
     * 
     * @param model the model that manages the state of the power-ups
     * @param onConfirm callback to execute when the user confirms a choice
     */
    public PowerUpController(final PowerUpSelectionModel model, final Command onConfirm) {
        this.model = Objects.requireNonNull(model, "Il model NON può essere null");
        this.onConfirm = Objects.requireNonNull(onConfirm);
    }

    /**
     * Manages key presses to navigate the menu and confirm.
     * 
     * @param e event associated with key press
     */
    @Override
    public void keyPressed(final KeyEvent e) {
        super.keyPressed(e);

        if (super.isUpPressed()) {
            model.selectPrevious();
        } else if (super.isDownPressed()) {
            model.selectNext();
        } else if (super.isEnterPressed()) {
            onConfirm.execute();
        }
    }
}
