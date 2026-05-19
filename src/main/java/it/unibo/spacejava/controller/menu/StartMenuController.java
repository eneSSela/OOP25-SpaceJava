package it.unibo.spacejava.controller.menu;

import java.awt.event.KeyEvent;
import javax.swing.Timer;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.spacejava.KeyHandler;
import it.unibo.spacejava.model.menu.StartMenuModel;
import it.unibo.spacejava.model.sound.api.SoundManager;

/**
 * Controller per il menu di start. Gestisce l'input da tastiera e aggiorna il model di conseguenza.
 * Inoltre, gestisce il timer per il lampeggiamento dell'opzione selezionata ,
 * riproduce i suoni quando l'utente seleziona un'opzione.
 * Infine, fornisce dei callback per le azioni da eseguire quando l'utente seleziona "Gioca", "Seleziona Skin" o "Esci".
 */
@SuppressFBWarnings(
    value = "EI_EXPOSE_REP", 
    justification = "Nel game loop è necessario condividere i riferimenti originali per le performance" 
)
public class StartMenuController extends KeyHandler {

    private static final String SELECTION_SOUND_PATH = "/audio/selection.wav";
    private static final String ENTER_SOUND_PATH = "/audio/enter.wav";
    private static final int BLINK_INTERVAL = 500; // Intervallo di lampeggiamento in millisecondi

    private final StartMenuModel model;
    private final SoundManager soundManager;
    private final Runnable onPlay;
    private final Runnable onSkinSelection;
    private final Runnable onExit;
    private final Timer blinkTimer;

    /**
     * Costrusice il controller del memu di start, inzializadno model soundmanger, 
     * e i coallback per le varie ozpioni che vengono scelte dall'utente.
     * 
     * @param model il model del menu di start, che contiene le opzioni e lo stato del lampeggiamento
     * @param soundManager il gestore dei suoni, per poter riprodurre i suoni quando l'utente seleziona un'opzione
     * @param onPlay il callback da eseguire quando l'utente seleziona "Gioca"
     * @param onSkinSelection il callback da eseguire quando l'utente seleziona "Seleziona Skin"
     * @param onExit il callback da eseguire quando l'utente seleziona "Esci"
     */
    public StartMenuController(

        final StartMenuModel model, 
        final SoundManager soundManager, 
        final Runnable onPlay, 
        final Runnable onSkinSelection, 
        final Runnable onExit) {
        this.model = model;
        this.onPlay = onPlay;
        this.onSkinSelection = onSkinSelection;
        this.onExit = onExit;
        this.soundManager = soundManager;

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
            soundManager.playSound(SELECTION_SOUND_PATH);
        } else if (super.isDownPressed()) {
            model.selectNext();
            soundManager.playSound(SELECTION_SOUND_PATH);
        } else if (super.isEnterPressed()) {
            soundManager.playSound(ENTER_SOUND_PATH);
            this.stop();
            if (model.getSelectedIndex() == 0) {
                onPlay.run();
            } else if (model.getSelectedIndex() == 1) {
                onSkinSelection.run();
            } else {
                onExit.run();
            }
        }
    }

    /**
     * Ferma il timer del lampeggiamento.
     */
    public void stop() {
        blinkTimer.stop();
    }
}
