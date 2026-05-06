package it.unibo.spacejava.model;


import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.List;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibo.spacejava.api.GameManger;
import it.unibo.spacejava.controller.EnemyProjectileController;
import it.unibo.spacejava.controller.WaveManagerController;
import it.unibo.spacejava.controller.menu.SkinController;
import it.unibo.spacejava.controller.menu.StartMenuController;
import it.unibo.spacejava.model.menu.SkinModel;
import it.unibo.spacejava.model.menu.StartMenuModel;
import it.unibo.spacejava.model.sound.SoundManagerImpl;
import it.unibo.spacejava.model.sound.api.SoundManager;
import it.unibo.spacejava.view.game.GamePanel;
import it.unibo.spacejava.view.menu.SkinSelectionView;
import it.unibo.spacejava.view.menu.StartMenuView;

public class GameManagerImpl implements GameManger, Runnable{

    private Thread gameThread;
    private final int FPS = 60;
    private int tileSize = 48; 
    private int screenWidth = tileSize * 16;
    private int screenHeight = tileSize * 12;
    private  GamePanel gamePanel = new GamePanel(screenWidth, screenHeight);
    private SoundManager soundManager = new SoundManagerImpl();
    private StartMenuModel startMenuModel = new StartMenuModel();
    private StartMenuController startMenuController;
    private StartMenuView startMenuView;
    private WaveManagerController waveManager = new WaveManagerController(screenWidth);
    private EnemyProjectileController projectileController = new EnemyProjectileController(screenHeight);
    private boolean isGameActive = false;
    
    private SkinModel skinModel;
    private SkinController skinController;
    private SkinSelectionView skinSelectionView;
    private CardLayout cardLayout = new CardLayout();

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
       
        JPanel cards = new JPanel(cardLayout);
       
        gamePanel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        startMenuController = new StartMenuController(startMenuModel, soundManager, 
            () -> {
                cardLayout.show(cards, "GAME");
                gamePanel.requestFocusInWindow();
            },
            () -> {
                cardLayout.show(cards, "SKIN");
                skinSelectionView.requestFocusInWindow();
            },
            () -> System.exit(0));
        startMenuView = new StartMenuView(startMenuController);
        startMenuView.addKeyListener(startMenuController);
        
        skinModel = new SkinModel();
        skinController = new SkinController(skinModel,
            () -> {
                cardLayout.show(cards, "MENU");
                startMenuView.requestFocusInWindow();
            }
        );
        skinSelectionView = new SkinSelectionView(skinModel);
        skinSelectionView.setFocusable(true);
        skinSelectionView.addKeyListener(skinController);
        
        startMenuView.setFocusable(true);
        cards.add(startMenuView, "MENU");
        cards.add(gamePanel, "GAME");
        cards.add(skinSelectionView, "SKIN");
        window.setContentPane(cards);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        startMenuView.setFocusable(true);
        startMenuView.requestFocusInWindow();
        
        //startMenu.requestFocusInWindow();
        this.startThreadGame();
    }
    
    private void startThreadGame() {
        gameThread = new Thread(this);
        gameThread.start();
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

        double timePerFrame = 1.0 / FPS;

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                if (startMenuView.isVisible()) {
                    startMenuView.repaint();
                } else if (gamePanel.isVisible()) {
                    waveManager.update(timePerFrame);
                    projectileController.update(timePerFrame);
                    gamePanel.render(waveManager.getEnemies());
                } else if (skinSelectionView.isVisible()) {
                    skinSelectionView.repaint();
                }
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
