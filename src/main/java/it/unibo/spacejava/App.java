package it.unibo.spacejava;


import it.unibo.spacejava.model.GameManagerImpl;

/**
 * Use it for lauch the game.
 */
public class App {
    public static void main(final String... args) {
        GameManagerImpl gameManager = new GameManagerImpl();
        gameManager.startGame();
    }
}
