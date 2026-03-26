package it.unibo.spacejava.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import it.unibo.spacejava.model.StartMenuModel;
import it.unibo.spacejava.view.StartMenuView;

public class StartMenuController implements KeyListener {

    private final StartMenuModel model;
    private final StartMenuView view;
    private final Runnable onPlay;
    private final Runnable onExit;

    private boolean upDown = false;
    private boolean downDown = false;

    public StartMenuController(StartMenuModel model, StartMenuView view, Runnable onPlay, Runnable onExit) {
        this.model = model;
        this.view = view;
        this.onPlay = onPlay;
        this.onExit = onExit;
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

        view.repaint();
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
}
