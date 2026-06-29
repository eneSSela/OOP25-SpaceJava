package it.unibo.spacejava;

import it.unibo.spacejava.api.GameManager;
import it.unibo.spacejava.controller.GameManagerImpl;

/**
 * Use it for lauch the game.
 */
public final class App {

    private App() {

    }

    /**
     * Main method, uset to lunch the game.
     * 
     * @param args arguments from command line, not used
     */
    public static void main(final String... args) {
        final GameManager gameManager = new GameManagerImpl();
        gameManager.startGame();
    }
}
