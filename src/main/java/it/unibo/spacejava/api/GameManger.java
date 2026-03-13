package it.unibo.spacejava.api;

public interface GameManger {

    /**
     * this method uses for start the game.
     */
    public void startGame();

    /**
     * 
     */
    public void pauseGame();
    
    public void endGame();
    
    public void restartGame();
}
