package it.unibo.spacejava;

import it.unibo.spacejava.api.GameManger;
import it.unibo.spacejava.model.GameManagerImpl;


/**
 * Use it for lauch the game.
 */
public class App {
    public static void main(final String... args) {
    
        GameManger game = new GameManagerImpl();
        game.startGame();
    }
}
