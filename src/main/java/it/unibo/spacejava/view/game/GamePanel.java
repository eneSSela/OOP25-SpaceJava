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
import java.awt.Font;

import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.controller.EnemyProjectileController;
import it.unibo.spacejava.model.PlayerShip;
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

    private Image playerImage;
    private PlayerShip currentPlayer;
    private List<ProjectileImpl> playerProjectiles;

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

        try {
            URL imageUrl = getClass().getResource("/skins/spaceShips_001.png");
            if (imageUrl != null) {
                playerImage = ImageIO.read(imageUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(List<Enemy> enemies, PlayerShip player, List<ProjectileImpl> playerProjectiles) {
        this.currentEnemies = enemies;
        this.currentPlayer = player;
        this.playerProjectiles = playerProjectiles;
        repaint(); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawEnemies(g, currentEnemies);
        List<ProjectileImpl> projectiles = EnemyProjectileController.getProjectileList();
        drawEnemyProjectiles(g, projectiles);

        drawPlayer(g);
        drawPlayerProjectiles(g);
        drawHUD(g);
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

    private void drawPlayer(Graphics g) {
        if (playerImage != null && currentPlayer != null) {
            g.drawImage(playerImage,
                        (int) currentPlayer.getPosition().getX(),
                        (int) currentPlayer.getPosition().getY(),
                        (int) currentPlayer.getWidth(),
                        (int) currentPlayer.getHeight(),
                        null);
        }
    }

    private void drawPlayerProjectiles(Graphics g) {
        if (playerProjectiles != null && !playerProjectiles.isEmpty()) {
            g.setColor(Color.CYAN); // i proiettili del giocatore saranno azzurri
            for (ProjectileImpl p : playerProjectiles) {
                g.fillRect(
                    (int) p.getPosition().getX(),
                    (int) p.getPosition().getY(),
                    p.getWidth(),
                    p.getLenght()
                );
            }
        }
    }

    private void drawHUD(Graphics g) {
        if (currentPlayer != null) { //Controlliamo che il player sia stato caricato
            g.setFont(new Font("Monospaced", Font.BOLD, 20));

            int health = currentPlayer.getHealth();

            //Cambiamo colore in base alla vita rimanente
            if (health > 1) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.RED);
            }

            //Disegnamo la scritta "Vite: X" in alto a destra
            g.drawString("Vite: " + health, 20, 30);
        }
    }
}