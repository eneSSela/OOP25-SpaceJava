package it.unibo.spacejava.controller.menu;

import java.awt.event.KeyEvent;
import java.util.Objects;
import javax.swing.Timer;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.spacejava.KeyHandler;
import it.unibo.spacejava.api.Command;
import it.unibo.spacejava.model.menu.GameOverModel;
import it.unibo.spacejava.model.sound.SoundManagerImpl;

/**
 * Controller for the GameOver menu.
 * Listen directly to the event code to avoid "ghost input".
 */
public final class GameOverController extends KeyHandler {

    private static final String SELECTION_SOUND_PATH = "/audio/selection.wav";
    private static final String ENTER_SOUND_PATH = "/audio/enter.wav";
    private static final int BLINK_INTERVAL = 500;

    private final GameOverModel model;
    private final Command onRestart;
    private final Command onMainMenu;
    private final Timer blinkTimer;

    /**
     * Constructs a new Controller for the GameOver screen.
     * 
     * @param model the model managing the game over menu state
     * @param onRestart the command to execute when the restart option is selected
     * @param onMainMenu the command to execute when the main menu option is selected
     */
    @SuppressFBWarnings(value = "EI2", justification = "Il Model deve essere condiviso con il Controller")
    public GameOverController(final GameOverModel model, final Command onRestart, final Command onMainMenu) {
        this.model = Objects.requireNonNull(model);
        this.onRestart = Objects.requireNonNull(onRestart);
        this.onMainMenu = Objects.requireNonNull(onMainMenu);

        this.blinkTimer = new Timer(BLINK_INTERVAL, e -> model.setBlinkOn(!model.isBlinkOn()));
    }

    /**
     * Manages key presses to navigate the menu and confirm.
     * 
     * @param e event associated with key press
     */
    @Override
    public void keyPressed(final KeyEvent e) {
        super.keyPressed(e);
        final int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
            this.model.selectPrevious();
            SoundManagerImpl.getInstance().playSound(SELECTION_SOUND_PATH);
        } else if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            this.model.selectNext();
            SoundManagerImpl.getInstance().playSound(SELECTION_SOUND_PATH);
        } else if (code == KeyEvent.VK_ENTER) {
            SoundManagerImpl.getInstance().playSound(ENTER_SOUND_PATH);
            this.stop();
            if (this.model.getSelectedIndex() == 0) {
                this.onRestart.execute();
            } else {
                this.onMainMenu.execute();
            }
        }
    }

    /**
     * Stops the timer responsible for the menu's blinking effect.
     */
    public void stop() {
        this.blinkTimer.stop();
    }

    /**
     * Starts the timer responsible for the menu's blinking effect.
     * If it's not already running.
     */
    public void start() {
        if (!this.blinkTimer.isRunning()) {
            this.blinkTimer.start();
        }
    }
}
