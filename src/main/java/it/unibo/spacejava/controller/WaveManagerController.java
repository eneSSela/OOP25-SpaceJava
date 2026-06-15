package it.unibo.spacejava.controller;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.Utils;
import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.model.EnemyType;
import it.unibo.spacejava.model.ProjectileImpl;
import it.unibo.spacejava.model.enemies.BossEnemy;
import it.unibo.spacejava.model.enemies.EnemyFactory;
import it.unibo.spacejava.model.enemies.RedEnemy;
import it.unibo.spacejava.model.enemies.TankEnemy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import it.unibo.spacejava.model.sound.api.SoundManager;

/**
 * Classe che gestisce la ondata di nemici, il loro movimento e i loro attachi.
 */
public final class WaveManagerController {

    private static final double SPEED_X = 60.0;
    private static final double DESCENT = 20.0; 
    private static final double COOLDOWN = 1.0; 
    private static final double SHOOT_PROBABILITY = 0.5;
    private static final int BOSS_WAVE_NUM = 3;
    private static final Random RANDOM_ENEMY = new Random();
    private static final String SHOOT_SOUND_PATH = "/audio/shoot.wav";
    private static final String HIT_SOUND_PATH = "/audio/hit.wav";

    private final SoundManager soundManager;
    private boolean isMovingRight = true;
    private double timeSinceLastShot;
    private final List<Enemy> enemies;
    private final double screenWidth;
    private int waveNum = 1;
    private final EnemyFactory enemyFactory = new EnemyFactory();

    /**
     * Costruisce una nuova oindata di nemici, in base alla larghezza dello schermo.
     * 
     * @param screenWidth larghezza dello schermo
     * @param soundManager gestore dei suoni per riprodurre effeti sonori come lo sparo e l'impatto dei proitettili
     */
    public WaveManagerController(final double screenWidth, final SoundManager soundManager) {
        this.screenWidth = screenWidth;
        this.enemies = new ArrayList<>();
        this.spawnWave();
        this.soundManager = soundManager;
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
                        final Position enemyPos = new Position(x, y);
                        enemies.add(enemyFactory.createEnemy(EnemyType.BASE, enemyPos));
                    }
                }
                break;
            case 2:
                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        final int x = startX + (col * spacingX);
                        final int y = startY + (row * spacingY);
                        final Position enemyPos = new Position(x, y);
                        if (row == 0) {
                            enemies.add(enemyFactory.createEnemy(EnemyType.BASE, enemyPos));
                        } else {
                            enemies.add(enemyFactory.createEnemy(EnemyType.TANK, enemyPos));
                        }
                    }
                }
                break;
            case BOSS_WAVE_NUM:
                final Position enemyPos = new Position(startX, startY);
                enemies.add(enemyFactory.createEnemy(EnemyType.BOSS, enemyPos));
                break;
            default:
                // Aumenta la difficoltà ogni roud dopo i primi tre.
                increaseDifficulty();
                if (waveNum % BOSS_WAVE_NUM == 0) {
                    final Position ePos = new Position(startX, startY);
                    enemies.add(enemyFactory.createEnemy(EnemyType.BOSS, ePos));
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
                                    enemies.add(enemyFactory.createEnemy(EnemyType.BASE, ePos));
                                    break;
                                case 1:
                                    enemies.add(enemyFactory.createEnemy(EnemyType.TANK, ePos));
                                    break;
                                case 2:
                                    enemies.add(enemyFactory.createEnemy(EnemyType.RED, ePos));
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
            ++waveNum;
            spawnWave();
        }

        checkhitEnemies();

        boolean hitEdge = false;
        // Controlla se un nemico tocca il bordo
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
                e.getPosition().setY((int) (e.getPosition().getY() + DESCENT));
            }
        }

        // Sposta l'orda in orizzontale
        double movement = SPEED_X * delta;
        if (!isMovingRight) {
            movement = -movement;
        }

        for (final Enemy e : enemies) {
            e.getPosition().setX((int) (e.getPosition().getX() + movement));
        }

        // Decide se un nemico spara
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
        final int randomIndex = (int) (Math.random() * enemies.size());
        enemies.get(randomIndex).attack();
    }

    /**
     * Controlla se un nemico viene colpito e se un nemico muore e quindi lo rimuove dall'ondata.
     */
    private void checkhitEnemies() {
        final List<ProjectileImpl> playerProjectiles = PlayerProjectileController.getProjectileList();
        Enemy rmEnemy = enemyFactory.createEnemy(EnemyType.BASE, new Position(0, 0));
        ProjectileImpl rmProjectile = new ProjectileImpl(new Position(0, 0), 0, 0, 0);
        Boolean kill = false;
        Boolean hit = false;
        // Controlla se un nemico è colpito
        for (final Enemy e : enemies) {
            for (final ProjectileImpl p : playerProjectiles) {
                if (Utils
                    .isColliding(e.getPosition(), e.getWidth(), e.getHeight(), p.getPosition(), p.getWidth(), p.getLenght())) {
                    e.takeDamage(p.getDamage());
                    rmProjectile = p;
                    hit = true;
                    if (e.isDead()) {
                        rmEnemy = e;
                        kill = true;
                    }
                }
            }
        }

        if (hit) {
            soundManager.playSound(HIT_SOUND_PATH);
            PlayerProjectileController.removeProjectile(rmProjectile);
            // Se un nemico ha esaurito le vite viene rimosso dall'ondata.
            if (kill) {
                enemies.remove(rmEnemy);
            }
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
