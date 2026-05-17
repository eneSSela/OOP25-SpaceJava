package it.unibo.spacejava.controller;

import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.model.BaseEnemy;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che gestisce la ondata di nemici, il loro movimento e i loro attachi.
 */
public final class WaveManagerController {

    private static final double SPEED_X = 75.0; // Movement pixels per second
    private static final double DESCENT = 20.0; // Descent pixels
    private static final double COOLDOWN = 1.0; 
    private static final double SHOOT_PROBABILITY = 0.5;

    private boolean isMovingRight = true;
    private double timeSinceLastShot;
    private final List<Enemy> enemies;
    private final double screenWidth;

    /**
     * Costruisce una nuova oindata di nemici, in base alla larghezza dello schermo.
     * 
     * @param screenWidth larghezza dello schermo
     */
    public WaveManagerController(final double screenWidth) {
        this.screenWidth = screenWidth;
        this.enemies = new ArrayList<>();
        this.spawnWave();
    }

    // Spawns a grill of enemies
    private void spawnWave() {
        final int rows = 2;
        final int cols = 8;
        final int startX = 50;
        final int startY = 50;
        final int spacingX = 60;
        final int spacingY = 50;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                final int x = startX + (col * spacingX);
                final int y = startY + (row * spacingY);
                enemies.add(new BaseEnemy(x, y));
            }
        }
    }

    /**
     * Aggiorna la poszione dei nemici e gestische i loro attachi.
     * 
     * @param delta tempo trascorso
     */
    public void update(final double delta) {

        // Checks if the wave is defeated
        if (enemies.isEmpty()) {
            return;
        }

        boolean hitEdge = false;
        // Checks if an enemy touches the edge
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

        // Checks if the edge is touched
        if (hitEdge) {
            isMovingRight = !isMovingRight;
            for (final Enemy e : enemies) {
                e.getPosition().setY((int) (e.getPosition().getY() + DESCENT));
            }
        }

        // Moves the wave horizontally
        double movement = SPEED_X * delta;
        if (!isMovingRight) {
            movement = -movement;
        }

        for (final Enemy e : enemies) {
            e.getPosition().setX((int) (e.getPosition().getX() + movement));
        }

        // Calculates shot cooldown and randomly decides if an enemy gets to shoot
        timeSinceLastShot += delta;
        if (timeSinceLastShot >= COOLDOWN) {
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
        return enemies;
    }

    // Selects a random enemy and makes it attak
    private void shoot() {
        final int randomIndex = (int) (Math.random() * enemies.size());
        enemies.get(randomIndex).attack();
    }
}
