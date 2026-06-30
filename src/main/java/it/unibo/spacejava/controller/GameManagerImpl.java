package it.unibo.spacejava.controller;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.spacejava.KeyHandler;
import it.unibo.spacejava.api.GameManager;
import it.unibo.spacejava.api.PowerUp;
import it.unibo.spacejava.api.Score;
import it.unibo.spacejava.controller.menu.GameOverController;
import it.unibo.spacejava.controller.menu.PowerUpController;
import it.unibo.spacejava.controller.menu.ShopController;
import it.unibo.spacejava.controller.menu.StartMenuController;
import it.unibo.spacejava.model.PlayerShip;
import it.unibo.spacejava.model.ScoreImpl;
import it.unibo.spacejava.model.menu.GameOverModel;
import it.unibo.spacejava.model.menu.PowerUpSelectionModel;
import it.unibo.spacejava.model.menu.ShopImpl;
import it.unibo.spacejava.model.menu.StartMenuModel;
import it.unibo.spacejava.model.sound.SoundManagerImpl;
import it.unibo.spacejava.view.game.GamePanel;
import it.unibo.spacejava.view.menu.GameOverView;
import it.unibo.spacejava.view.menu.PowerUpSelectionView;
import it.unibo.spacejava.view.menu.ShopView;
import it.unibo.spacejava.view.menu.StartMenuView;

/**
 * Implementazione del GameManager che gestisce il gico in generale, dal metodo starGame che izniailizza tutte le componenti,
 * al game loop che associato ad un thread, che si occupoa di aggiornare lo stato del gioco e renderizzare a schermo l'ui,
 * ad un costante di frequnza(FPS) pari a 60.
 */
public final class GameManagerImpl implements GameManager, Runnable {

    //Costanti per il game loop e le dimensioni dello schermo
    private static final int FPS = 60;
    private static final double TIME_PER_FRAME = 1_000_000_000.0 / FPS;
    private static final int TILESIZE = 48;
    private static final int SCREEN_WIDTH = TILESIZE * 16;
    private static final int SCREEN_HEIGTH = TILESIZE * 12;
    private static final int MAX_DAMAGE = 999;
    private static final String BACKGROUND_MUSIC_PATH = "/audio/background_music.wav";

    //Componenti del gioco, tra cui il thread del gioco, il pannello di gioco, il gestore dei suoni, 
    private static final String CARD_GAME = "GAME";
    private static final String CARD_MENU = "MENU";
    private static final String CARD_SKIN = "SKIN";
    private static final String CARD_POWERUP = "POWERUP";
    private static final String CARD_GAMEOVER = "GAMEOVER";

    //Comonenti del gioco, tra cui il thread del gioco, il pannello di gioco, il gestore dei suoni, 
    // il gestore degli input da tastiera e il layout a schede per gestire le diverse schermate (menu, gioco, selezione skin)
    private Thread gameThread;
    private final GamePanel gamePanel = new GamePanel(SCREEN_WIDTH, SCREEN_HEIGTH);
    private final KeyHandler gameKeyHandler = new KeyHandler();
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);

    //Componenti del menu
    private final StartMenuModel startMenuModel = new StartMenuModel();
    private StartMenuView startMenuView;
    private StartMenuController startMenuController;

    //Componenti della schermata di selezione skin
    private final ShopImpl shop;
    private ShopView skinSelectionView;

    private GameOverModel gameOverModel;
    private GameOverController gameOverController;
    private GameOverView gameOverView;

    //Compononenti dei nemici e del player
    private EnemyProjectileController enemyProjectileController;
    private PlayerProjectileController playerProjController;
    private WaveManagerController waveManager;
    private PlayerController playerController;
    private BunkerController bunkerController;
    private PowerUpSelectionView powerUpView;

    private final Score persistentScore = new ScoreImpl();
    private volatile boolean justResumed;

    /**
     * Costruisce il GameManager inizializzando i punteggi e i controller.
     */
    public GameManagerImpl() {
        this.shop = new ShopImpl();
    }

    /**
     * Inizia il gioco creando la finestra principale, inizializzando le componenti del menu e del gioco, 
     * e avviando il thread del gioco.
     */
    @Override
    public void startGame() {
        final JFrame window = new JFrame("SpaceJava");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        gamePanel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGTH));

        //Iniziallizazione Controller e view del menu
        startMenuController = new StartMenuController(startMenuModel,
            () -> {
                resetGameState();
                cardLayout.show(cards, CARD_GAME);
                gamePanel.requestFocusInWindow();
            },
            () -> {
                resetGameState();
                cardLayout.show(cards, CARD_SKIN);
                skinSelectionView.requestFocusInWindow();
            },
            this::stopGame
        );
        startMenuView = new StartMenuView(startMenuModel);
        startMenuView.addKeyListener(startMenuController);
        resetGameState();

        //Inizializzazione controller e view della schermata di selezione skin
        final ShopController skinController = new ShopController(shop, playerController.getPlayerShip(),
            () -> {
                cardLayout.show(cards, CARD_MENU);
                startMenuView.requestFocusInWindow();
                if (startMenuController != null) {
                    startMenuController.start();
                }
            }
        );
        skinSelectionView = new ShopView(shop, playerController.getPlayerShip());
        skinSelectionView.setFocusable(true);
        skinSelectionView.addKeyListener(skinController);

        gameOverModel = new GameOverModel();
        gameOverView = new GameOverView(gameOverModel);
        gameOverController = new GameOverController(gameOverModel,
            () -> {
                resetGameState();
                cardLayout.show(cards, CARD_GAME);
                gamePanel.requestFocusInWindow();
            },
            () -> {
                cardLayout.show(cards, CARD_MENU);
                startMenuView.requestFocusInWindow();
                startMenuController.start();
            }
        );
        gameOverView.addKeyListener(gameOverController);

        cards.add(startMenuView, CARD_MENU);
        cards.add(gamePanel, CARD_GAME);
        cards.add(skinSelectionView, CARD_SKIN);
        cards.add(gameOverView, CARD_GAMEOVER);

        gamePanel.addKeyListener(gameKeyHandler);
        startMenuView.setFocusable(true);
        startMenuView.requestFocusInWindow();

        window.setContentPane(cards);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        this.startThreadGame();
    }

    private void resetGameState() {
        gameKeyHandler.resetState();

        playerProjController = new PlayerProjectileController();
        enemyProjectileController = new EnemyProjectileController(SCREEN_HEIGTH);

        final int startX = (int) (SCREEN_WIDTH / 2.0) - 32;
        final int startY = SCREEN_HEIGTH - 100;

        final PlayerShip playerModel = new PlayerShip(startX, startY, shop.getSelectedSkin(), this.persistentScore);

        playerController = new PlayerController(playerModel, gameKeyHandler,
                                                playerProjController, SCREEN_WIDTH, enemyProjectileController);

        waveManager = new WaveManagerController(SCREEN_WIDTH, SoundManagerImpl.getInstance(),
                                                playerModel, playerProjController, enemyProjectileController);

        bunkerController = new BunkerController(SCREEN_WIDTH, SCREEN_HEIGTH, 
                                                playerProjController, enemyProjectileController);
        this.justResumed = true;
    }

    private void startThreadGame() {
        gameThread = new Thread(this);
        SoundManagerImpl.getInstance().playBackgroundMusic(BACKGROUND_MUSIC_PATH);
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

        final double timePerFrame = 1.0 / FPS;

        while (gameThread != null) {
            if (justResumed) {
                lastTime = System.nanoTime();
                justResumed = false;
            }

            final long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / TIME_PER_FRAME;
            lastTime = currentTime;

            if (delta >= 1) {
                if (startMenuView.isVisible()) {
                    startMenuView.repaint();
                } else if (skinSelectionView.isVisible()) {
                    skinSelectionView.repaint();
                } else if (gameOverView.isVisible()) {
                    gameOverView.repaint();
                } else if (powerUpView != null && powerUpView.isVisible()) {
                    powerUpView.repaint();
                } else if (gamePanel.isVisible()) {

                    if (playerController.getPlayerShip().isDead()) {
                        triggerGameOverScreen();
                        playerController.getPlayerShip().getScore().resetCurrentRun();
                    } else {
                        if (waveManager.isWaveCleared()) {
                            triggerPowerUpScreen();
                        }

                        final var enemyProjectiles = enemyProjectileController.getProjectileList();
                        final var playerProjectiles = playerProjController.getProjectileList();
                        waveManager.update(timePerFrame);
                        enemyProjectileController.update(timePerFrame);
                        playerProjController.update(timePerFrame);
                        playerController.update(timePerFrame);
                        playerController.checkEnemyCollision();
                        bunkerController.checkCollisions(playerProjectiles, enemyProjectiles);

                        bunkerController.checkEnemyCollisions(waveManager.getEnemies());
                        if (waveManager.getLowestEnemyY() >= playerController.getPlayerShip().getPosition().getY()) {
                            playerController.getPlayerShip().takeDamage(MAX_DAMAGE);
                        }

                        gamePanel.render(waveManager.getEnemies(), playerController, playerProjectiles,
                                        enemyProjectiles, bunkerController.getBunkers(),
                                        waveManager.getBaseHealth(), waveManager.getBaseDamage(),
                                        waveManager.getTankHealth(), waveManager.getTankDamage(),
                                        waveManager.getRedHealth(), waveManager.getRedDamage(),
                                        waveManager.getBossHealth(), waveManager.getBossDamage()
                                    );
                    }
                }
                delta--;
            }
        }
    }

    private void triggerGameOverScreen() {
        gameKeyHandler.resetState();
        gameOverModel.setFinalScore(playerController.getPlayerShip().getScore().getCurrentRunScore());
        gameOverController.start();
        cardLayout.show(cards, CARD_GAMEOVER);
        gameOverView.requestFocusInWindow();
    }

    private void triggerPowerUpScreen() {
        gameKeyHandler.resetState();

        final PowerUpSelectionModel puModel = new PowerUpSelectionModel(waveManager);
        powerUpView = new PowerUpSelectionView(puModel);

        final PowerUpController puController = new PowerUpController(puModel, () -> {
            final PowerUp scelta = puModel.getSelectedPowerUp();
            scelta.applayEffect(playerController.getPlayerShip());
            waveManager.startNextWave();

            cardLayout.show(cards, CARD_GAME);
            cards.remove(powerUpView);
            powerUpView = null;
            justResumed = true;
            gamePanel.requestFocusInWindow();
        });

        powerUpView.addKeyListener(puController);

        cards.add(powerUpView, CARD_POWERUP);
        cardLayout.show(cards, CARD_POWERUP);
        powerUpView.requestFocusInWindow();
    }

    /**
     * Chiude forzatamente il gioco e la Java Virtual Machine.
     */
    @SuppressFBWarnings(
        value = "DM_EXIT", 
        justification = "È l'azione voluta per il pulsante Esci del menu principale"
    )
    @Override
    public void stopGame() {
        System.exit(0);
    }
}
