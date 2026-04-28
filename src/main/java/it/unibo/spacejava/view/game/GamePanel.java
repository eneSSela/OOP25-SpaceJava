/* package it.unibo.spacejava.view.game;

import java.awt.Color;

import javax.swing.JPanel;

public class GamePanel extends JPanel{
    
    public GamePanel(int width, int height) {
        super.setSize(width, height);
        super.setBackground(Color.BLACK);
    }
} */
 
package it.unibo.spacejava.view.game;

import java.awt.Color;
import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.controller.EnemyProjectileController;
import it.unibo.spacejava.model.ProjectileImpl;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class GamePanel extends JPanel {

    private Image enemyImage;
    private List<Enemy> currentEnemies;
    private Image projectileImage;

    public GamePanel(int width, int height) {
        super.setSize(width, height);
        super.setBackground(Color.BLACK);
        loadImages();
    }

    private void loadImages() {
        try {
            URL imageUrl = getClass().getResource("/enemies/baseEnemy.png");
            if (imageUrl != null) {
                enemyImage = ImageIO.read(imageUrl);
            } else {
                System.err.println("Immagine del nemico non trovata!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            URL imageUrl = getClass().getResource("/enemies/projectile.png");
            if (imageUrl != null) {
                projectileImage = ImageIO.read(imageUrl);
            } else {
                System.err.println("Immagine del proiettile non trovata!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(List<Enemy> enemies) {
        this.currentEnemies = enemies;
        repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawEnemies(g, currentEnemies);
        List<ProjectileImpl> projectiles = EnemyProjectileController.getProjectileList();
        drawEnemyProjectiles(g, projectiles);
    }
    
    public void drawEnemies(Graphics g, List<Enemy> enemies) {
        if (enemyImage != null && enemies != null) {
            for (Enemy e : enemies) {
                g.drawImage(enemyImage, 
                            (int) e.getPosition().getX(), 
                            (int) e.getPosition().getY(), 
                            (int) e.getWidth(), 
                            (int) e.getHeight(), 
                            null);
            }
        }
    }

    public void drawEnemyProjectiles(Graphics g, List<ProjectileImpl> projectiles) {
        if (projectiles.isEmpty() == false) {
            for (ProjectileImpl projectileImpl : projectiles) {
                g.drawImage(projectileImage,
                    (int) projectileImpl.getPosition().getX(),
                    (int) projectileImpl.getPosition().getY(),
                    (int) projectileImpl.getWidth(),
                    (int) projectileImpl.getLenght(),
                    null);
            }
        }
    }
}