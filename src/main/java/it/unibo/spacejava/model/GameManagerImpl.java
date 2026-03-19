package it.unibo.spacejava.model;

import javax.swing.JFrame;


import it.unibo.spacejava.api.GameManger;
import it.unibo.spacejava.view.GamePanel;

public class GameManagerImpl implements GameManger, Runnable{



    @Override
    public void startGame() {
       JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        int tileSize = 48; // 16 * 3 (scala originale)
        int screenWidth = tileSize * 16;
        int screenHeight = tileSize * 12;
        GamePanel panel = new GamePanel(screenWidth, screenHeight);
        window.add(panel);
        window.setVisible(true);
    }

    @Override
    public void pauseGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pauseGame'");
    }

    @Override
    public void endGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'endGame'");
    }

    @Override
    public void restartGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'restartGame'");
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

}
