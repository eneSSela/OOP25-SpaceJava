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
    }

    public void render(List<Enemy> enemies) {
        this.currentEnemies = enemies;
        repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawEnemies(g, currentEnemies);
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
}