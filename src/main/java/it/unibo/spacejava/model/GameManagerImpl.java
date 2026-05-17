package it.unibo.spacejava.model;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibo.spacejava.KeyHandler;
import it.unibo.spacejava.api.GameManger;
import it.unibo.spacejava.controller.EnemyProjectileController;
import it.unibo.spacejava.controller.PlayerController;
import it.unibo.spacejava.controller.PlayerProjectileController;
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

/**
 * Implementazione del GameManager che gestisce il gico in generale, dal metodo starGame che izniailizza tutte le componenti,
 * al game loop che associato ad un thread, che si occupoa di aggiornare lo stato del gioco e renderizzare a schermo l'ui,
 * ad un costante di frequnza(FPS) pari a 60.
 */
public final class GameManagerImpl implements GameManger, Runnable {

    //Costanti per il game loop e le dimensioni dello schermo 
    private static final int FPS = 60;
    private static final double TIME_PER_FRAME = 1_000_000_000.0 / FPS;
    private static final int TILESIZE = 48; 
    private static final int SCREEN_WIDTH = TILESIZE * 16;
    private static final int SCREEN_HEIGTH = TILESIZE * 12;

    //Comonenti del gioco, tra cui il thread del gioco, il pannello di gioco, il gestore dei suoni, 
    // il gestore degli input da tastiera e il layout a schede per gestire le diverse schermate (menu, gioco, selezione skin)
    private Thread gameThread;
    private final GamePanel gamePanel = new GamePanel(SCREEN_WIDTH, SCREEN_HEIGTH);
    private final SoundManager soundManager = new SoundManagerImpl();
    private final KeyHandler gameKeyHandler = new KeyHandler();
    private final CardLayout cardLayout = new CardLayout();

    //Componenti del menu
    private final StartMenuModel startMenuModel = new StartMenuModel();
    private StartMenuView startMenuView;

    //Componenti della schermata di selezione skin
    private final SkinModel skinModel = new SkinModel();
    private SkinController skinController;

    private final SkinSelectionView skinSelectionView = new SkinSelectionView(skinController);
    //Compononenti dei nemici e del player
    private final WaveManagerController waveManager = new WaveManagerController(SCREEN_WIDTH);
    private final EnemyProjectileController projectileController = new EnemyProjectileController(SCREEN_HEIGTH);
    private PlayerController playerController;


    /**
     * Inizia il gioco creando la finestra principale, inizializzando le componenti del menu e del gioco, 
     * e avviando il thread del gioco.
     */
    @Override
    public void startGame() {

        final JFrame window = new JFrame("SpaceJava");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        final JPanel cards = new JPanel(cardLayout);

        gamePanel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGTH));

        //Iniziallizazione Controller e view del menu
        final StartMenuController startMenuController = new StartMenuController(startMenuModel, soundManager,
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

        //Inizializzazione controller e view della schermata di selezione skin
        skinController = new SkinController(skinModel,
            () -> {
                cardLayout.show(cards, "MENU");
                startMenuView.requestFocusInWindow();
            }
        );
        skinSelectionView.setFocusable(true);
        skinSelectionView.addKeyListener(skinController);

        startMenuView.setFocusable(true);
        cards.add(startMenuView, "MENU");
        cards.add(gamePanel, "GAME");
        cards.add(skinSelectionView, "SKIN");

        final double startX = (SCREEN_WIDTH / 2.0) - 32;
        final double startY = SCREEN_HEIGTH - 100;
        final PlayerShip playerModel = new PlayerShip(startX, startY, skinController.getPlayerSelectedSkin());
        playerController = new PlayerController(playerModel, gameKeyHandler, SCREEN_WIDTH, soundManager);

        gamePanel.addKeyListener(gameKeyHandler);
        startMenuView.setFocusable(true);
        startMenuView.requestFocusInWindow();
        window.setContentPane(cards);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        this.startThreadGame();
    }

    private void startThreadGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Game loop implementation based on https://www.youtube.com/watch?v=H2aW5V46bFE.
     * The game loop runs in a separate thread and is responsible for updating the game state 
     * and rendering the game at a consistent frame rate (FPS). It calculates the time taken for each frame and 
     * adjusts the timing to maintain a steady FPS, while also printing the actual FPS achieved every second.
     */
    @Override
    public void run() {
        double delta = 0;
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        int frames = 0;

        final double timePerFrame = 1.0 / FPS;

        while (gameThread != null) {
            final long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / TIME_PER_FRAME;
            lastTime = currentTime;

            if (delta >= 1) {
                if (startMenuView.isVisible()) {
                    startMenuView.repaint();
                } else if (gamePanel.isVisible()) {
                    if (skinController.getPlayerSelectedSkin() != playerController.getPlayerSkin()) {
                        playerController.setPlayerSkin(skinController.getPlayerSelectedSkin());
                    }
                    waveManager.update(timePerFrame);
                    projectileController.update(timePerFrame);
                    playerController.update(timePerFrame);
                    PlayerProjectileController.update(timePerFrame);
                    playerController.checkEnemyCollision();

                    gamePanel.render(waveManager.getEnemies(), playerController, PlayerProjectileController.getProjectileList());
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
