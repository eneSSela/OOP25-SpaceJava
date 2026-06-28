package it.unibo.spacejava.controller.menu;

import java.awt.event.KeyEvent;
import java.util.Objects;

import javax.swing.Timer;

import it.unibo.spacejava.KeyHandler;
import it.unibo.spacejava.model.menu.StartMenuModel;
import it.unibo.spacejava.model.sound.SoundManagerImpl;
import it.unibo.spacejava.api.Command;

/**
 * Controller per il menu di start. Gestisce l'input da tastiera e aggiorna il model di conseguenza.
 * Inoltre, gestisce il timer per il lampeggiamento dell'opzione selezionata ,
 * riproduce i suoni quando l'utente seleziona un'opzione.
 * Infine, fornisce dei callback per le azioni da eseguire quando l'utente seleziona "Gioca", "Seleziona Skin" o "Esci".
 */
public class StartMenuController extends KeyHandler {

    private static final String SELECTION_SOUND_PATH = "/audio/selection.wav";
    private static final String ENTER_SOUND_PATH = "/audio/enter.wav";
    private static final int BLINK_INTERVAL = 500; // Intervallo di lampeggiamento in millisecondi

    private final StartMenuModel model;
    private final Command onPlay;
    private final Command onSkinSelection;
    private final Command onExit;
    private final Timer blinkTimer;

    /**
     * Costrusice il controller del memu di start, inzializadno model soundmanger, 
     * e i coallback per le varie ozpioni che vengono scelte dall'utente.
     * 
     * @param model il model del menu di start, che contiene le opzioni e lo stato del lampeggiamento
     * @param onPlay il callback da eseguire quando l'utente seleziona "Gioca"
     * @param onSkinSelection il callback da eseguire quando l'utente seleziona "Seleziona Skin"
     * @param onExit il callback da eseguire quando l'utente seleziona "Esci"
     */
    public StartMenuController(
            final StartMenuModel model, 
            final Command onPlay, 
            final Command onSkinSelection, 
            final Command onExit) {
        this.model = Objects.requireNonNull(model, "Non può essere nullo");
        this.onPlay = Objects.requireNonNull(onPlay);
        this.onSkinSelection = Objects.requireNonNull(onSkinSelection);
        this.onExit = Objects.requireNonNull(onExit);

        blinkTimer = new Timer(BLINK_INTERVAL, e -> model.setBlinkOn(!model.isBlinkOn()));
        blinkTimer.start();
    }

    /**
     * Gestisce la pressione di un tasto.
     * 
     * @param e l'evento di pressione del tasto
     */
    @Override
    public void keyPressed(final KeyEvent e) {
        super.keyPressed(e);

        if (super.isUpPressed()) {
            model.selectPrevious();
            SoundManagerImpl.getInstance().playSound(SELECTION_SOUND_PATH);
        } else if (super.isDownPressed()) {
            model.selectNext();
            SoundManagerImpl.getInstance().playSound(SELECTION_SOUND_PATH);
        } else if (super.isEnterPressed()) {
            SoundManagerImpl.getInstance().playSound(ENTER_SOUND_PATH);
            this.stop();
            if (model.getSelectedIndex() == 0) {
                onPlay.execute();
            } else if (model.getSelectedIndex() == 1) {
                onSkinSelection.execute();
            } else {
                onExit.execute();
            }
        }
    }

    /**
     * Ferma il timer del lampeggiamento.
     */
    public void stop() {
        blinkTimer.stop();
    }

    /**
     * Avvia o riavvia il timer del lampeggiamento.
     */
    public void start() {
        if (!blinkTimer.isRunning()) {
            blinkTimer.start();
        }
    }
}
