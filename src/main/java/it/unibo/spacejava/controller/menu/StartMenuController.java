package it.unibo.spacejava.controller.menu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.Timer;

import it.unibo.spacejava.KeyHandler;
import it.unibo.spacejava.model.menu.StartMenuModel;
import it.unibo.spacejava.model.sound.api.SoundManager;

/**
 * Controller per il menu di start. Gestisce l'input da tastiera e aggiorna il model di conseguenza.
 * Inoltre, gestisce il timer per il lampeggiamento dell'opzione selezionata e riproduce i suoni associati alla selezione e alla conferma delle opzioni.
 * Infine, fornisce dei callback per le azioni da eseguire quando l'utente seleziona "Gioca", "Seleziona Skin" o "Esci".
 */
public class StartMenuController extends KeyHandler {

    private final String selectionSoundPath = "/audio/selection.wav";
    private final String enterSoundPath = "/audio/enter.wav";

    private final StartMenuModel model;
    private final SoundManager soundManager;
    private final Runnable onPlay;
    private final Runnable onSkinSelection;
    private final Runnable onExit;
    private final Timer blinkTimer;

    

    public StartMenuController(StartMenuModel model, SoundManager soundManager, Runnable onPlay, Runnable onSkinSelection, Runnable onExit) {
        this.model = model;
        this.onPlay = onPlay;
        this.onSkinSelection = onSkinSelection;
        this.onExit = onExit;
        this.soundManager = soundManager;

        blinkTimer = new Timer(500, e -> model.setBlinkOn(!model.isBlinkOn()));
        blinkTimer.start();
    }
    
    /**
     * Gestisce la pressione di un tasto.
     * @param e l'evento di pressione del tasto
     */
    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);

        if (super.upPressed) {
            model.selectPrevious();
            soundManager.playSound(selectionSoundPath);
        
        } else if (super.downPressed) {
            model.selectNext();
            soundManager.playSound(selectionSoundPath);
            
        } else if (super.enterPressed) {
            soundManager.playSound(enterSoundPath);
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
     * Gestisce il rilascio di un tasto.
     * @param e l'evento di rilascio del tasto
     */
    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        
    }
    
    /**
     * Restituisce la lista delle opzioni del menu.
     * @return la lista delle opzioni del menu sotto forma di stringhe per poterle visualizzare nella view
     */
    public List<String> getOptions() {
        return model.getOptions();
    }

    /**
     * Restittuisce l'idice dell'opzione che l'utente ha selezionata.
     * @return l'idice dell'opzione selzionata, per poterza evideziare nella view
     */
    public int getSelectedIndex() {
        return model.getSelectedIndex();
    }

    /**
     * Metodo utile per notificare che il lampeggio dell'opzione selezionata è attivo o meno, per poterlo visualizzare nella view.
     * @return true se il lampeggiamento è attivo, false altrimenti
     */
    public boolean isBlinkOn() {
        return model.isBlinkOn();
    }

    /**
     * Ferma il timer del lampeggiamento.   
     */
    public void stop() {
        blinkTimer.stop();
    }
}
