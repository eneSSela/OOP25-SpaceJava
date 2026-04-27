package it.unibo.spacejava.controller;

import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.model.BaseEnemy;
import java.util.ArrayList;
import java.util.List;

public class WaveManagerController {
    private List<Enemy> enemies;
    
    private double speedX = 75.0; // Movement pixels per second
    private double descent = 20.0; // Descent pixels
    private boolean movingRight = true;
    
    private double screenWidth;

    public WaveManagerController(double screenWidth) {
        this.screenWidth = screenWidth;
        this.enemies = new ArrayList<>();
        spawnWave();
    }

    // Spawns a grill of enemies
    private void spawnWave() {
        int rows = 2;
        int cols = 8;
        double startX = 50.0;
        double startY = 50.0;
        double spacingX = 60.0;
        double spacingY = 50.0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                double x = startX + (col * spacingX);
                double y = startY + (row * spacingY);
                enemies.add(new BaseEnemy(x, y));
            }
        }
    }

    public void update(double delta) {

        boolean hitEdge = false;

        // Checks if the wave is defeated
        if (enemies.isEmpty()) {
            return;
        }

        // Checks if an enemy touches the edge
        for (Enemy e : enemies) {
            double enemyRightEdge = e.getPosition().getX() + e.getWidth();
            double enemyLeftEdge = e.getPosition().getX();

            if (movingRight && enemyRightEdge >= screenWidth) {
                hitEdge = true;
                break;
            } else if (!movingRight && enemyLeftEdge <= 0) {
                hitEdge = true;
                break;
            }
        }

        // Checks if the edge is touched
        if (hitEdge) {
            movingRight = !movingRight; // Inverti direzione
            for (Enemy e : enemies) {
                // Sposta in basso tutti i nemici
                e.getPosition().setY(e.getPosition().getY() + descent);
            }
        }

        // Moves the wave horizontally
        double movement = speedX * delta;
        if (!movingRight) {
            movement = -movement;
        }

        for (Enemy e : enemies) {
            e.getPosition().setX(e.getPosition().getX() + movement);
        }
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
