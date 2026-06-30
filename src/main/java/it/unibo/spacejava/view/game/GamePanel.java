package it.unibo.spacejava.view.game;

import java.awt.Color;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import it.unibo.spacejava.Utils;
import it.unibo.spacejava.api.Bunker;
import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.api.Projectile;
import it.unibo.spacejava.controller.PlayerController;

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
    private static final int ENEMY_DATA_FONT_SIZE = 15;
    private static final int HEALTH_X_POSITION = 20;
    private static final int HUD_TEXT_Y_POSITION = 30;
    private static final int HUD_IMG_Y_POSITION = 15;
    private static final int HUD_ENEMY_IMG_SIZE = 20;
    private static final int BASE_HUD_IMG_X = 265;
    private static final int TANK_HUD_IMG_X = 375;
    private static final int RED_HUD_IMG_X = 495;
    private static final int BOSS_HUD_IMG_X = 615;
    private static final int BASE_STATS_X = 285;
    private static final int TANK_STATS_X = 400;
    private static final int RED_STATS_X = 520;
    private static final int BOSS_STATS_X = 660;
    private static final String HP_STRING = "Hp:";
    private static final String DMG_STRING = " Dmg:";
    private static final String FONT_NAME = "Monospaced";

    private transient Image baseEnemyImage;
    private transient Image tankEnemyImage;
    private transient Image bossEnemyImage;
    private transient Image redEnemyImage;
    private transient Image projectileImage;
    private transient Image bunkerImage;
    private transient Image backgroundImage;
    private transient List<Enemy> currentEnemies;
    private transient PlayerController crtlPlayer;
    private transient List<Projectile> playerProjectiles;
    private transient List<Projectile> enemyProjectiles;
    private transient List<Bunker> listBunker;
    private transient int hudBaseHealth;
    private transient int hudBaseDamage;
    private transient int hudTankHealth;
    private transient int hudTankDamage;
    private transient int hudRedHealth;
    private transient int hudRedDamage;
    private transient int hudBossHealth;
    private transient int hudBossDamage;

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
        backgroundImage = Utils.loadImage("/background/background_image.png");
        bunkerImage = Utils.loadImage("/background/bunker_image.png");

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
     * @param enemyProjectile lista dei proiettili nemici
     * @param bunkers lista dei bunker attivi
     * @param baseHealth vita nemico base
     * @param baseDamage danno nemico base
     * @param tankHealth vita nemico tank
     * @param tankDamage danno nemico tank
     * @param redHealth vita nemico red
     * @param redDamage danno nemico red
     * @param bossHealth vita nemico boss
     * @param bossDamage danno nemico boss
     */
    public void render(final List<Enemy> enemies, final PlayerController player,
            final List<Projectile> playerProjectile, final List<Projectile> enemyProjectile,
            final List<Bunker> bunkers,
            final int baseHealth, final int baseDamage,
            final int tankHealth, final int tankDamage,
            final int redHealth, final int redDamage,
            final int bossHealth, final int bossDamage) {
        this.currentEnemies = List.copyOf(enemies);
        this.crtlPlayer = Objects.requireNonNull(player, "Non può essere nullo");
        this.playerProjectiles = List.copyOf(playerProjectile);
        this.enemyProjectiles = List.copyOf(enemyProjectile);
        this.listBunker = List.copyOf(bunkers);
        this.hudBaseHealth = baseHealth;
        this.hudBaseDamage = baseDamage;
        this.hudTankHealth = tankHealth;
        this.hudTankDamage = tankDamage;
        this.hudRedHealth = redHealth;
        this.hudRedDamage = redDamage;
        this.hudBossHealth = bossHealth;
        this.hudBossDamage = bossDamage;
        repaint();
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        drawBackground(g, this.getWidth(), this.getHeight());
        drawEnemies(g, currentEnemies);
        drawEnemyProjectiles(g);
        drawBunkers(g, listBunker);
        drawPlayer(g);
        drawPlayerProjectiles(g);
        drawHUD(g);
    }

    private void drawBunkers(final Graphics g, final List<Bunker> bunkers) {
        if (bunkers != null && !bunkers.isEmpty()) {
            for (final Bunker bunker : bunkers) {
                final int bunkerX = bunker.getPosition().getX();
                final int bunkerY = bunker.getPosition().getY();
                final int bunkerWidth = bunker.getWidth();
                final int bunkerHeight = bunker.getHeight();

                g.setFont(new Font(FONT_NAME, Font.BOLD, HEALTH_FONT_SIZE));
                g.setColor(Color.WHITE);
                g.drawString("HP: " + bunker.getHealth(), bunkerX, bunkerY - 10);

                if (bunkerImage != null) {
                    g.drawImage(bunkerImage, bunkerX, bunkerY, bunkerWidth, bunkerHeight, null);
                } else {
                    g.setColor(Color.GRAY);
                    g.fillRect(bunkerX, bunkerY, bunkerWidth, bunkerHeight);
                }
            }
        }
    }

    private void drawEnemies(final Graphics g, final List<Enemy> enemies) {
        if (baseEnemyImage != null && enemies != null) {
            for (final Enemy e : enemies) {
                switch (e.getType()) {
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

    private void drawEnemyProjectiles(final Graphics g) {
        if (enemyProjectiles != null && !enemyProjectiles.isEmpty()) {
            for (final Projectile projectileImpl : enemyProjectiles) {
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
            for (final Projectile p : playerProjectiles) {
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
            g.setFont(new Font(FONT_NAME, Font.BOLD, HEALTH_FONT_SIZE));

            final int health = crtlPlayer.getPlayerShip().getHealth();

            // Cambiamo colore in base alla vita rimanente
            if (health > 1) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.RED);
            }

            // Disegniamo la scritta "Vite: X" in alto a destra
            final String healthText = "Vite:" + health;
            g.drawString(healthText, HEALTH_X_POSITION, HUD_TEXT_Y_POSITION);

            final var score = crtlPlayer.getPlayerShip().getScore();
            final int hudX = g.getFontMetrics().stringWidth(healthText) + HEALTH_X_POSITION + 10;
            g.setColor(Color.WHITE);
            g.drawString("Score:" + score.getCurrentRunScore(), hudX, HUD_TEXT_Y_POSITION);

            //Disegno le statistiche che possono incrementare dei nemici

            g.setColor(Color.ORANGE);
            g.setFont(new Font(FONT_NAME, Font.BOLD, ENEMY_DATA_FONT_SIZE));

            g.drawImage(baseEnemyImage,
                            BASE_HUD_IMG_X,
                            HUD_IMG_Y_POSITION,
                            HUD_ENEMY_IMG_SIZE,
                            HUD_ENEMY_IMG_SIZE,
                            null);
            g.drawString(HP_STRING + hudBaseHealth + DMG_STRING + hudBaseDamage, BASE_STATS_X, HUD_TEXT_Y_POSITION);

            g.drawImage(tankEnemyImage,
                            TANK_HUD_IMG_X,
                            HUD_IMG_Y_POSITION,
                            HUD_ENEMY_IMG_SIZE,
                            HUD_ENEMY_IMG_SIZE,
                            null);
            g.drawString(HP_STRING + hudTankHealth + DMG_STRING + hudTankDamage, TANK_STATS_X, HUD_TEXT_Y_POSITION);

            g.drawImage(redEnemyImage,
                            RED_HUD_IMG_X,
                            HUD_IMG_Y_POSITION,
                            HUD_ENEMY_IMG_SIZE,
                            HUD_ENEMY_IMG_SIZE,
                            null);
            g.drawString(HP_STRING + hudRedHealth + DMG_STRING + hudRedDamage, RED_STATS_X, HUD_TEXT_Y_POSITION);

            g.drawImage(bossEnemyImage,
                            BOSS_HUD_IMG_X,
                            HUD_IMG_Y_POSITION,
                            HUD_ENEMY_IMG_SIZE * 2,
                            HUD_ENEMY_IMG_SIZE,
                            null);
            g.drawString(HP_STRING + hudBossHealth + DMG_STRING + hudBossDamage,
             BOSS_STATS_X, HUD_TEXT_Y_POSITION);
        }
    }

    private void drawBackground(final Graphics g, final int w, final int h) {
        if (Objects.isNull(backgroundImage)) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, w, h);
        } else {
            g.drawImage(backgroundImage, 0, 0, w, h, null);
        }
    }
}
