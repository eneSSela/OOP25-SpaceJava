package it.unibo.spacejava.controller;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.http.cookie.CookieAttributeHandler;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.spacejava.KeyHandler;
import it.unibo.spacejava.api.GameManger;
import it.unibo.spacejava.api.PowerUp;
import it.unibo.spacejava.controller.menu.PowerUpController;
import it.unibo.spacejava.controller.menu.SkinController;
import it.unibo.spacejava.controller.menu.StartMenuController;
import it.unibo.spacejava.model.PlayerShip;
import it.unibo.spacejava.model.menu.PowerUpSelectionModel;
import it.unibo.spacejava.model.menu.SkinModel;
import it.unibo.spacejava.model.menu.StartMenuModel;
import it.unibo.spacejava.model.sound.SoundManagerImpl;
import it.unibo.spacejava.view.game.GamePanel;
import it.unibo.spacejava.view.menu.SkinSelectionView;
import it.unibo.spacejava.view.menu.StartMenuView;

/**
 * Implementazione del GameManager che gestisce il gico in generale, dal metodo starGame che izniailizza tutte le componenti,
 * al game loop che associato ad un thread, che si occupoa di aggiornare lo stato del gioco e renderizzare a schermo l'ui,
 * ad un costante di frequnza(FPS) pari a 60.
 */
@SuppressFBWarnings(
    value = "UwF", 
    justification = "I campi dell'interfaccia grafica vengono inizializzati in modo sicuro nel metodo startGame()"
)
public final class GameManagerImpl implements GameManger, Runnable {

    //Costanti per il game loop e le dimensioni dello schermo 
    private static final int FPS = 60;
    private static final double TIME_PER_FRAME = 1_000_000_000.0 / FPS;
    private static final int TILESIZE = 48; 
    private static final int SCREEN_WIDTH = TILESIZE * 16;
    private static final int SCREEN_HEIGTH = TILESIZE * 12;
    private static final String BACKGROUND_MUSIC_PATH = "/audio/background_music.wav";

    private static final String CARD_GAME = "GAME";
    private static final String CARD_MENU = "MENU";
    private static final String CARD_SKIN = "SKIN";
    private static final String CARD_POWERUP = "POWERUP";

    private int score;

    //Comonenti del gioco, tra cui il thread del gioco, il pannello di gioco, il gestore dei suoni, 
    // il gestore degli input da tastiera e il layout a schede per gestire le diverse schermate (menu, gioco, selezione skin)
    private Thread gameThread;
    private final GamePanel gamePanel = new GamePanel(SCREEN_WIDTH, SCREEN_HEIGTH);
    private final KeyHandler gameKeyHandler = new KeyHandler();
    private final PlayerProjectileController playerProjController = new PlayerProjectileController();
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);

    //Componenti del menu
    private final StartMenuModel startMenuModel = new StartMenuModel();
    private StartMenuView startMenuView;
    private StartMenuController startMenuController;

    //Componenti della schermata di selezione skin
    private final SkinModel skinModel = new SkinModel();
    private SkinSelectionView skinSelectionView;
    private PowerUpSelectionView powerUpView;

    //Compononenti dei nemici e del player
    private final WaveManagerController waveManager;
    private final EnemyProjectileController projectileController = new EnemyProjectileController(SCREEN_HEIGTH);
    private PlayerController playerController;
    private BunkerController bunkerController;

    private boolean justResumed;

    public GameManagerImpl() {
        this.score = 0;
        this.skinModel = new SkinModel(this);
        this.waveManager = new WaveManagerController(SCREEN_WIDTH, SoundManagerImpl.getInstance(),
                                                     this, this.playerProjController);
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
                cardLayout.show(cards, CARD_GAME);
                gamePanel.requestFocusInWindow();
            },
            () -> {
                cardLayout.show(cards, CARD_SKIN);
                skinSelectionView.requestFocusInWindow();
            },
            this::stopGame
        );
        startMenuView = new StartMenuView(startMenuModel);
        startMenuView.addKeyListener(startMenuController);

        //Inizializzazione controller e view della schermata di selezione skin
        final SkinController skinController = new SkinController(skinModel,
            () -> {
                cardLayout.show(cards, CARD_MENU);
                startMenuView.requestFocusInWindow();
                if (startMenuController != null) {
                    startMenuController.start();
                }
            }
        );
        skinSelectionView = new SkinSelectionView(skinModel);
        skinSelectionView.setFocusable(true);
        skinSelectionView.addKeyListener(skinController);

        startMenuView.setFocusable(true);
        cards.add(startMenuView, CARD_MENU);
        cards.add(gamePanel, CARD_GAME);
        cards.add(skinSelectionView, CARD_SKIN);

        final int startX = (int) (SCREEN_WIDTH / 2.0) - 32;
        final int startY = SCREEN_HEIGTH - 100;
        final PlayerShip playerModel = new PlayerShip(startX, startY, skinModel.getSelectedSkin());
        playerController = new PlayerController(playerModel, gameKeyHandler, 
                                                this.playerProjController, SCREEN_WIDTH);
        bunkerController = new BunkerController(SCREEN_WIDTH, SCREEN_HEIGTH
                                                this.playerProjController, this.projectileController);

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
        long timer = System.currentTimeMillis();
        int frames = 0;

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
                } else if (powerUpView != null && powerUpView.isVisible()) {
                    powerUpView.repaint();
                } else if (gamePanel.isVisible()) {
                    if (waveManager.isWaveCleared()) {
                        triggerPowerUpScreen();
                    } else {
                        if (!skinModel.getSelectedSkin().equals(playerController.getPlayerSkin())) {
                            playerController.setPlayerSkin(skinModel.getSelectedSkin());
                        }
                        waveManager.update(timePerFrame);
                        projectileController.update(timePerFrame);
                        playerController.update(timePerFrame);
                        playerProjController.update(timePerFrame);

                        playerController.checkEnemyCollision();
                        bunkerController.checkCollisions(playerProjController.getProjectileList(), 
                                                         EnemyProjectileController.getProjectileList());

                        gamePanel.render(waveManager.getEnemies(), playerController,
                                         playerProjController.getProjectileList(), bunkerController.getBunkers());
                    }
                }
                delta--;
            }

            if (System.currentTimeMillis() - timer >= 1000) {
                timer += 1000;
            }
        }
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
     * Increase the current score of the game.
     * 
     * @param points to add
     */
    @Override
    public synchronized void addScore(final int points) {
        if (points > 0) {
            this.score += points;
        }
    } 

    /**
     * Decrease the score.
     * 
     * @param points to subtract
     */
    @Override
    public synchronized void decreaseScore(final int points) {
        if (points > 0 && this.score >= points) {
            this.score -= points;
        }
    }

    /**
     * Return the current score.
     * @return 
     * 
     * @return the actual score
     */
    @Override
    public synchronized int getScore() {
        return this.score;
    }

    /**
     * Reset the score at the start of a new match.
     */
    @Override
    public synchronized void resetScore() {
        this.score = 0;
    }

    /**
     * Chiude forzatamente il gioco e la Java Virtual Machine.
     */
    @SuppressFBWarnings(value = "DM_EXIT", justification = "Spegnimento intenzionale e lecito dell'applicazione")
    private void stopGame() {
        System.exit(0);
    }
}
