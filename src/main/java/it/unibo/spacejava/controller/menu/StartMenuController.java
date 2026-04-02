package it.unibo.spacejava.controller.menu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.Timer;

import it.unibo.spacejava.model.menu.StartMenuModel;

public class StartMenuController implements KeyListener {

    private final StartMenuModel model;
    private final Runnable onPlay;
    private final Runnable onExit;
    private final Timer blinkTimer;

    private boolean upDown = false;
    private boolean downDown = false;

    public StartMenuController(StartMenuModel model, Runnable onPlay, Runnable onExit) {
        this.model = model;
        this.onPlay = onPlay;
        this.onExit = onExit;

        blinkTimer = new Timer(500, e -> model.setBlinkOn(!model.isBlinkOn()));
        blinkTimer.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            if (!upDown) {
                model.selectPrevious();
                upDown = true;
            }
        } else if (code == KeyEvent.VK_DOWN) {
            if (!downDown) {
                model.selectNext();
                downDown = true;
            }
        } else if (code == KeyEvent.VK_ENTER) {
            if (model.getSelectedIndex() == 0) {
                onPlay.run();
            } else {
                onExit.run();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP) {
            upDown = false;
        } else if (code == KeyEvent.VK_DOWN) {
            downDown = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    public List<String> getOptions() {
        return model.getOptions();
    }

    public int getSelectedIndex() {
        return model.getSelectedIndex();
    }

    public boolean isBlinkOn() {
        return model.isBlinkOn();
    }
    public void stop() {
        blinkTimer.stop();
    }
}
