package it.unibo.spacejava.view.game;

import java.awt.Color;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import it.unibo.spacejava.Utils;
import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.controller.EnemyProjectileController;
import it.unibo.spacejava.controller.PlayerController;
import it.unibo.spacejava.model.ProjectileImpl;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;
import java.util.Objects;

/**
 * Panel responsabile del rendering della UI di gioco (nemici, proiettili, giocatore e HUD).
 */
public final class GamePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(GamePanel.class.getName());
    private static final int HEALTH_FONT_SIZE = 20;
    private static final int HEALTH_X_POSITION = 20;
    private static final int HEALTH_Y_POSITION = 30;

    private transient Image baseEnemyImage;
    private transient Image tankEnemyImage;
    private transient Image bossEnemyImage;
    private transient Image redEnemyImage;
    private transient List<Enemy> currentEnemies;
    private transient Image projectileImage;
    private transient PlayerController crtlPlayer;
    private transient List<ProjectileImpl> playerProjectiles;

    /**
     * Costruisce un GamePanel con dimensioni specificate.
     *
     * @param width la larghezza del pannello
     * @param height l'altezza del pannello
     */
    public GamePanel(final int width, final int height) {
        super.setSize(width, height);
        //super.setBackground(Color.BLACK);
        loadImages();
    }

    private void loadImages() {
        baseEnemyImage = Utils.loadImage("/enemies/baseEnemy.png");
        projectileImage = Utils.loadImage("/enemies/projectile.png");
        tankEnemyImage = Utils.loadImage("/enemies/tankEnemy.png");
        bossEnemyImage = Utils.loadImage("/enemies/bossEnemy.png");
        redEnemyImage = Utils.loadImage("/enemies/redEnemy.png");

        if (Objects.isNull(baseEnemyImage) || Objects.isNull(projectileImage)) {
            LOGGER.log(Level.WARNING, "Immagini non caricate correttamente");
        }
    }

    /**
     * Aggiorna lo stato da renderizzare e richiede repaint.
     *
     * @param enemies lista dei nemici
     * @param player controller del giocatore
     * @param playerProjectile lista dei proiettili del giocatore
     */
    public void render(final List<Enemy> enemies, final PlayerController player,
            final List<ProjectileImpl> playerProjectile) {
        this.currentEnemies = List.copyOf(enemies);
        this.crtlPlayer = Objects.requireNonNull(player, "Non può essere nullo");
        this.playerProjectiles = List.copyOf(playerProjectile);
        repaint();
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        drawBackground(g, this.getWidth(), this.getHeight());
        drawEnemies(g, currentEnemies);
        final List<ProjectileImpl> projectiles = EnemyProjectileController.getProjectileList();
        drawEnemyProjectiles(g, projectiles);

        drawPlayer(g);
        drawPlayerProjectiles(g);
        drawHUD(g);
    }

    /**
     * Disegna i nemici.
     *
     * @param g il contesto grafico
     * @param enemies la lista dei nemici
     */
    public void drawEnemies(final Graphics g, final List<Enemy> enemies) {
        if (baseEnemyImage != null && enemies != null) {
            for (final Enemy e : enemies) {
                switch (e.type()) {
                    case BASE:
                        g.drawImage(baseEnemyImage,
                            e.getPosition().getX(),
                            e.getPosition().getY(),
                            (int) e.getWidth(),
                            (int) e.getHeight(),
                            null);
                        break;
                    case TANK:
                            g.drawImage(tankEnemyImage,
                                e.getPosition().getX(),
                                e.getPosition().getY(),
                                (int) e.getWidth(),
                                (int) e.getHeight(),
                                null);
                        break;
                    case RED:
                            g.drawImage(redEnemyImage,
                                e.getPosition().getX(),
                                e.getPosition().getY(),
                                (int) e.getWidth(),
                                (int) e.getHeight(),
                                null);
                        break;
                    case BOSS:
                            g.drawImage(bossEnemyImage,
                                e.getPosition().getX(),
                                e.getPosition().getY(),
                                (int) e.getWidth(),
                                (int) e.getHeight(),
                                null);
                        break;
                }
            }
        }
    }

    /**
     * Disegna i proiettili nemici.
     *
     * @param g il contesto grafico
     * @param projectiles la lista dei proiettili nemici
     */
    public void drawEnemyProjectiles(final Graphics g, final List<ProjectileImpl> projectiles) {
        if (projectiles != null && !projectiles.isEmpty()) {
            for (final ProjectileImpl projectileImpl : projectiles) {
                g.drawImage(projectileImage,
                            projectileImpl.getPosition().getX(),
                            projectileImpl.getPosition().getY(),
                            projectileImpl.getWidth(),
                            projectileImpl.getLenght(),
                        null);
            }
        }
    }

    private void drawPlayer(final Graphics g) {
        if (Objects.nonNull(crtlPlayer)) {
            g.drawImage(Utils.loadImage(crtlPlayer.getPlayerSkin().getImagePath()),
                    crtlPlayer.getPlayerShip().getPosition().getX(),
                    crtlPlayer.getPlayerShip().getPosition().getY(),
                    (int) crtlPlayer.getPlayerShip().getWidth(),
                    (int) crtlPlayer.getPlayerShip().getHeight(),
                    null);
        }
    }

    private void drawPlayerProjectiles(final Graphics g) {
        if (playerProjectiles != null && !playerProjectiles.isEmpty()) {
            g.setColor(Color.CYAN); // i proiettili del giocatore saranno azzurri
            for (final ProjectileImpl p : playerProjectiles) {
                g.fillRect(
                        p.getPosition().getX(),
                        p.getPosition().getY(),
                        p.getWidth(),
                        p.getLenght());
            }
        }
    }

    private void drawHUD(final Graphics g) {
        if (crtlPlayer != null) { // Controlliamo che il player sia stato caricato
            g.setFont(new Font("Monospaced", Font.BOLD, HEALTH_FONT_SIZE));

            final int health = crtlPlayer.getPlayerShip().getHealt();

            // Cambiamo colore in base alla vita rimanente
            if (health > 1) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.RED);
            }

            // Disegniamo la scritta "Vite: X" in alto a destra
            g.drawString("Vite: " + health, HEALTH_X_POSITION, HEALTH_Y_POSITION);
        }
    }

    private void drawBackground(final Graphics g, final int w, final int h) {
        final Image background = Utils.loadImage("/background/background_image.png");
        if (Objects.isNull(background)) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, w, h);
        } else {
            g.drawImage(background, 0, 0, w, h, null);
        }
    }
}
