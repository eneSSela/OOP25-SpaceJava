package it.unibo.spacejava.controller;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.Utils;
import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.api.GameManger;
import it.unibo.spacejava.api.Projectile;
import it.unibo.spacejava.model.EnemyType;
import it.unibo.spacejava.model.enemies.BossEnemy;
import it.unibo.spacejava.model.enemies.EnemyFactory;
import it.unibo.spacejava.model.enemies.RedEnemy;
import it.unibo.spacejava.model.enemies.TankEnemy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.spacejava.model.sound.api.SoundManager;

/**
 * Classe che gestisce la ondata di nemici, il loro movimento e i loro attachi.
 */
public final class WaveManagerController {

    private static final int SCORE_BASE = 100;
    private static final int SCORE_RED = 150;
    private static final int SCORE_TANK = 200;
    private static final int SCORE_BOSS = 1000;

    private static final double SPEED_X = 60.0;
    private static final double DESCENT = 20.0; 
    private static final double COOLDOWN = 1.0; 
    private static final double SHOOT_PROBABILITY = 0.5;
    private static final int BOSS_WAVE_NUM = 3;
    private static final Random RANDOM_ENEMY = new Random();
    private static final String SHOOT_SOUND_PATH = "/audio/shoot.wav";
    private static final String HIT_SOUND_PATH = "/audio/hit.wav";

    private double dynamicSpeedX = SPEED_X;
    private double dynamicDescent = DESCENT;

    private final SoundManager soundManager;
    private final GameManger gameManager;
    private final PlayerProjectileController playerProjectileController;

    private boolean isMovingRight = true;
    private double timeSinceLastShot;
    private final List<Enemy> enemies;
    private final double screenWidth;
    private int waveNum = 1;
    private boolean waveCleared;

    private double fractionalMovementX;

    /**
     * Costruisce una nuova ondata di nemici, in base alla larghezza dello schermo.
     * 
     * @param screenWidth larghezza dello schermo
     * @param soundManager gestore dei suoni per riprodurre effeti sonori come lo sparo e l'impatto dei proitettili
     * @param gameManager manager of the score
     * @param playerProjectileController projectile controller of the player
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Dependency injection is intended here")
    public WaveManagerController(
            final double screenWidth, 
            final SoundManager soundManager, 
            final GameManger gameManager, 
            final PlayerProjectileController playerProjectileController) {
        this.screenWidth = screenWidth;
        this.enemies = new ArrayList<>();
        this.soundManager = soundManager;
        this.gameManager = gameManager;
        this.playerProjectileController = playerProjectileController;
        this.spawnWave();
    }

    /**
     * @return true if the current wave is completed.
     */
    public boolean isWaveCleared() {
        return this.waveCleared;
    }

    /**
     * Advances the wave counter and generates the new wave.
     * To be called after the player has chosen the power-up.
     */
    public void startNextWave() {
        this.waveCleared = false;
        this.waveNum++;
        this.spawnWave();
    }

    /**
     * Multiply the speed of the horde and projectiles to slow or speed them up.
     * 
     * @param factor the multiplier
     */
    public void multiplyEnemySpeed(final float factor) {
        this.dynamicSpeedX *= factor;
        this.dynamicDescent *= factor;
        EnemyProjectileController.multiplySpeed(factor);
    }

    /**
     * Gestisce la creazione delle ondate di nemici.
     */
    private void spawnWave() {
        final int rows = 2;
        final int cols = 8;
        final int startX = 50;
        final int startY = 50;
        final int spacingX = 60;
        final int spacingY = 50;

        switch (waveNum) {
            case 1:
                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        final int x = startX + (col * spacingX);
                        final int y = startY + (row * spacingY);
                        enemies.add(EnemyFactory.createEnemy(EnemyType.BASE, new Position(x, y)));
                    }
                }
                break;
            case 2:
                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        final int x = startX + (col * spacingX);
                        final int y = startY + (row * spacingY);
                        if (row == 0) {
                            enemies.add(EnemyFactory.createEnemy(EnemyType.BASE, new Position(x, y)));
                        } else {
                            enemies.add(EnemyFactory.createEnemy(EnemyType.TANK, new Position(x, y)));
                        }
                    }
                }
                break;
            case BOSS_WAVE_NUM:
                enemies.add(EnemyFactory.createEnemy(EnemyType.BOSS, new Position(startX, startY)));
                break;
            default:
                // Aumenta la difficoltà ogni roud dopo i primi tre.
                increaseDifficulty();
                if (waveNum % BOSS_WAVE_NUM == 0) {
                    enemies.add(EnemyFactory.createEnemy(EnemyType.BOSS, new Position(startX, startY)));
                } else {
                    // Crea un'ondata con nemici casuali.
                    for (int row = 0; row < rows; row++) {
                        for (int col = 0; col < cols; col++) {
                            final int x = startX + (col * spacingX);
                            final int y = startY + (row * spacingY);
                            final Position ePos = new Position(x, y);
                            final int randEnemy = RANDOM_ENEMY.nextInt(3);
                            switch (randEnemy) {
                                case 0:
                                    enemies.add(EnemyFactory.createEnemy(EnemyType.BASE, ePos));
                                    break;
                                case 1:
                                    enemies.add(EnemyFactory.createEnemy(EnemyType.TANK, ePos));
                                    break;
                                case 2:
                                    enemies.add(EnemyFactory.createEnemy(EnemyType.RED, ePos));
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
                break;
        }
    }

    /**
     * Aggiorna la poszione dei nemici e gestische i loro attachi.
     * 
     * @param delta tempo trascorso
     */
    public void update(final double delta) {

        // Controlla se l'ondata è stata sconfitta
        if (enemies.isEmpty()) {
            this.waveCleared = true;
            return;
        }

        checkhitEnemies();

        for (final Enemy e : enemies) {
            if (e.isDead()) {
                switch (e.getType()) {
                    case BASE:
                        this.gameManager.addScore(SCORE_BASE);
                        break;
                    case TANK:
                        this.gameManager.addScore(SCORE_TANK);
                        break;
                    case RED:
                        this.gameManager.addScore(SCORE_RED);
                        break;
                    case BOSS:
                        this.gameManager.addScore(SCORE_BOSS);
                        break;
                }
            }
        }

        enemies.removeIf(e -> e != null && e.isDead());

        boolean hitEdge = false;
        for (final Enemy e : enemies) {
            final int enemyRightEdge = e.getPosition().getX() + (int) e.getWidth();
            final int enemyLeftEdge = e.getPosition().getX();

            if (isMovingRight && enemyRightEdge >= screenWidth) {
                hitEdge = true;
                break;
            } else if (!isMovingRight && enemyLeftEdge <= 0) {
                hitEdge = true;
                break;
            }
        }

        if (hitEdge) {
            isMovingRight = !isMovingRight;
            for (final Enemy e : enemies) {
                e.getPosition().setY((int) (e.getPosition().getY() + dynamicDescent));
            }
        }

        double exactMovement = dynamicSpeedX * delta;
        if (!isMovingRight) {
            exactMovement = -exactMovement;
        }

        fractionalMovementX += exactMovement;
        final int movePixels = (int) fractionalMovementX;

        if (movePixels != 0) {
            fractionalMovementX -= movePixels;
            for (final Enemy e : enemies) {
                e.getPosition().setX(e.getPosition().getX() + movePixels);
            }
        }

        timeSinceLastShot += delta;
        if (!enemies.isEmpty() && timeSinceLastShot >= COOLDOWN) {
            if (Math.random() < SHOOT_PROBABILITY) {
                shoot();
            }
            timeSinceLastShot = 0.0; 
        }
    }

    /**
     * Getter per restituire la lista dei nemici.
     * 
     * @return lista dei nemici attivi
     */
    public List<Enemy> getEnemies() {
        return Collections.unmodifiableList(enemies);
    }

    /**
     * Sceglie un nemico casuale e lo fa attaccare.
     */
    private void shoot() {
        soundManager.playSound(SHOOT_SOUND_PATH);
        final int randomIndex = RANDOM_ENEMY.nextInt(enemies.size());
        enemies.get(randomIndex).attack();
    }

    /**
     * Controlla se un nemico viene colpito.
     */
    private void checkhitEnemies() {
        final List<Projectile> playerProjectiles = this.playerProjectileController.getProjectileList();
        final List<Projectile> projectilesToRemove = new ArrayList<>();
        Boolean hit = false;
        // Controlla se un nemico è colpito
        for (final Enemy e : enemies) {
            for (final Projectile p : playerProjectiles) {
                if (Utils.isColliding(e.getPosition(), e.getWidth(), e.getHeight(), 
                                      p.getPosition(), p.getWidth(), p.getLenght())) {
                    e.takeDamage(p.getDamage());
                    projectilesToRemove.add(p);
                    hit = true;
                }
            }
        }

        if (hit) {
            soundManager.playSound(HIT_SOUND_PATH);
        }

        for (final Projectile p : projectilesToRemove) {
            this.playerProjectileController.removeProjectile(p);
        }
    }

    /**
     * Sceglie casualmente che tipo di nemico rendere più forte.
     */
    private void increaseDifficulty() {
        final int select = RANDOM_ENEMY.nextInt(3);
        switch (select) {
            case 0:
                TankEnemy.upgrade();
                break;
            case 1:
                RedEnemy.upgrade();
                break;
            case 2:
                BossEnemy.upgrade();
                break;
            default:
                break;
        }
    }
}
