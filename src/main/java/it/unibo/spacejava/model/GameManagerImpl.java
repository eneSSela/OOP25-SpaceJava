package it.unibo.spacejava.model;


import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibo.spacejava.api.GameManger;
import it.unibo.spacejava.controller.StartMenuController;
import it.unibo.spacejava.view.GamePanel;
import it.unibo.spacejava.view.StartMenuView;

public class GameManagerImpl implements GameManger, Runnable{

    private Thread gameThread;
    private final int FPS = 60;
    private int tileSize = 48; 
    private int screenWidth = tileSize * 16;
    private int screenHeight = tileSize * 12;
    private  GamePanel gamePanel = new GamePanel(screenWidth, screenHeight);
    private StartMenuModel startMenuModel = new StartMenuModel();
    private StartMenuController startMenuController;
    private StartMenuView startMenuView;

    @Override
    public void startGame() {
        /*
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        
        panel.addKeyListener(keyHandler);
        panel.setFocusable(true);
        
        window.add(panel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        // Ensure the game panel has keyboard focus so key events are received
        panel.requestFocusInWindow();
        
        this.startThreadGame();
        */
        JFrame window = new JFrame("SpaceJava");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
       
        CardLayout cardLayout = new CardLayout();
        JPanel cards = new JPanel(cardLayout);
       
        gamePanel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        startMenuController = new StartMenuController(startMenuModel,  () -> cardLayout.show(cards, "GAME"), () -> System.exit(0));
        startMenuView = new StartMenuView(startMenuController);
        startMenuView.addKeyListener(startMenuController);
        cards.add(startMenuView, "MENU");
        cards.add(gamePanel, "GAME");
        
        window.setContentPane(cards);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        //startMenu.requestFocusInWindow();
        this.startThreadGame();
    }
    
    private void startThreadGame() {
        gameThread = new Thread(this);
        gameThread.start();
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

    /**
     * Game loop implementation based on https://www.youtube.com/watch?v=H2aW5V46bFE.
     * The game loop runs in a separate thread and is responsible for updating the game state and rendering the game at a consistent frame rate (FPS). 
     * It calculates the time taken for each frame and adjusts the timing to maintain a steady FPS, while also printing the actual FPS achieved every second.
     * @return void.
     */
    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                startMenuView.repaint();
                frames++;
                delta--;
            }

            

            if (System.currentTimeMillis() - timer >= 1000) {
                System.out.println("FPS: " + frames);
                frames = 0;
                timer += 1000;
            }
        }
    }
}
