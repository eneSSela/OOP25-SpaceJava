package it.unibo.spacejava.controller;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.Utils;
import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.api.Projectile;
import it.unibo.spacejava.model.EnemyType;
import it.unibo.spacejava.model.PlayerShip;
import it.unibo.spacejava.model.enemies.EnemyFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
    private static final String NULL_PARAM_MESSAGE = "Non può esser nullo";
    private static final int DEFAULT_TANK_HEALTH = 3;
    private static final int DEFAULT_TANK_DAMAGE = 1;
    private static final int DEFAULT_RED_HEALTH = 1;
    private static final int DEFAULT_RED_DAMAGE = 2;
    private static final int DEFAULT_BOSS_HEALTH = 20;
    private static final int DEFAULT_BOSS_DAMAGE = 2;
    private static final int DEFAULT_BASE_HEALTH = 1;
    private static final int DEFAULT_BASE_DAMAGE = 1;
    private static final int BOSS_HEALT_UPGRADE = 5;

    private static final EnemyType[] RANDOM_SPAWN_TYPES = {
        EnemyType.BASE,
        EnemyType.TANK,
        EnemyType.RED,
    };

    private enum UpgradeType {
        BASE_HEALTH,
        BASE_DAMAGE,
        TANK_HEALTH,
        TANK_DAMAGE,
        RED_HEALTH,
        RED_DAMAGE,
        BOSS
    }

    private double dynamicSpeedX = SPEED_X;
    private double dynamicDescent = DESCENT;

    private final SoundManager soundManager;
    private final PlayerShip playerShip;
    private final PlayerProjectileController playerProjectileController;
    private final EnemyProjectileController enemyProjectileController;
    private boolean isMovingRight = true;
    private double timeSinceLastShot;
    private final List<Enemy> enemies;
    private final double screenWidth;
    private int waveNum = 1;
    private boolean waveCleared;
    private int tankHealth = DEFAULT_TANK_HEALTH;
    private int tankDamage = DEFAULT_TANK_DAMAGE;
    private int redHealth = DEFAULT_RED_HEALTH;
    private int redDamage = DEFAULT_RED_DAMAGE;
    private int bossHealth = DEFAULT_BOSS_HEALTH;
    private int bossDamage = DEFAULT_BOSS_DAMAGE;
    private int baseHealth = DEFAULT_BASE_HEALTH;
    private int baseDamage = DEFAULT_BASE_DAMAGE;

    private double fractionalMovementX;

    /**
     * Costruisce una nuova ondata di nemici, in base alla larghezza dello schermo.
     * 
     * @param screenWidth larghezza dello schermo
     * @param soundManager gestore dei suoni per riprodurre effeti sonori come lo sparo e l'impatto dei proitettili
     * @param playerShip nave del giocatore
     * @param playerProjectileController controller dei proiettili
     * @param enemyProjectileController controller dei proiettili nemici
     */
    public WaveManagerController(final double screenWidth, final SoundManager soundManager, 
                                final PlayerShip playerShip, 
                                final PlayerProjectileController playerProjectileController,
                                final EnemyProjectileController enemyProjectileController) {
        this.screenWidth = screenWidth;
        this.enemies = new ArrayList<>();
        this.soundManager = soundManager;
        this.playerShip = Objects.requireNonNull(playerShip, NULL_PARAM_MESSAGE);
        this.playerProjectileController = Objects.requireNonNull(playerProjectileController, NULL_PARAM_MESSAGE);
        this.enemyProjectileController = Objects.requireNonNull(enemyProjectileController, NULL_PARAM_MESSAGE);
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
                enemyStatsReset();
                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        final int x = startX + (col * spacingX);
                        final int y = startY + (row * spacingY);
                        enemies.add(EnemyFactory.createEnemy(EnemyType.BASE, new Position(x, y), baseHealth, baseDamage));
                    }
                }
                break;
            case 2:
                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < cols; col++) {
                        final int x = startX + (col * spacingX);
                        final int y = startY + (row * spacingY);
                        if (row == 0) {
                            enemies.add(EnemyFactory.createEnemy(EnemyType.BASE, new Position(x, y), baseHealth, baseDamage));
                        } else {
                            enemies.add(EnemyFactory.createEnemy(EnemyType.TANK, new Position(x, y), tankHealth, tankDamage));
                        }
                    }
                }
                break;
            case BOSS_WAVE_NUM:
                enemies.add(EnemyFactory.createEnemy(EnemyType.BOSS, new Position(startX, startY), bossHealth, bossDamage));
                break;
            default:
                // Aumenta la difficoltà ogni roud dopo i primi tre.
                increaseDifficulty();
                if (waveNum % BOSS_WAVE_NUM == 0) {
                    enemies.add(EnemyFactory.createEnemy(EnemyType.BOSS, new Position(startX, startY), bossHealth, bossDamage));
                } else {
                    // Crea un'ondata con nemici casuali.
                    for (int row = 0; row < rows; row++) {
                        for (int col = 0; col < cols; col++) {
                            final int x = startX + (col * spacingX);
                            final int y = startY + (row * spacingY);
                            final Position ePos = new Position(x, y);
                            final EnemyType randomType = RANDOM_SPAWN_TYPES[RANDOM_ENEMY.nextInt(RANDOM_SPAWN_TYPES.length)];
                            switch (randomType) {
                                case BASE:
                                    enemies.add(EnemyFactory.createEnemy(EnemyType.BASE, ePos, baseHealth, baseDamage));
                                    break;
                                case TANK:
                                    enemies.add(EnemyFactory.createEnemy(EnemyType.TANK, ePos, tankHealth, tankDamage));
                                    break;
                                case RED:
                                    enemies.add(EnemyFactory.createEnemy(EnemyType.RED, ePos, redHealth, redDamage));
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
                break;
        }
        if (waveNum % BOSS_WAVE_NUM == 0 && playerShip.getSkin().hasBossShield()) {
            playerShip.addShieldCharges(3);
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

        // Prima di rimuoverli, controlliamo chi è morto e diamo i punti.
        for (final Enemy e : enemies) {
            if (e.isDead()) {
                final double multiplier = playerShip.getSkin().getScoreMultiplier();
                final int finalPoints = (int) (e.getPoints() * multiplier);
                playerShip.getScore().addPoints(finalPoints);
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
        final int randomIndex = (int) (Math.random() * enemies.size());
        enemies.get(randomIndex).attack(enemyProjectileController);
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
        final UpgradeType[] upgrades = UpgradeType.values();
        final UpgradeType selected = upgrades[RANDOM_ENEMY.nextInt(upgrades.length)];
        switch (selected) {
            case BASE_HEALTH:
                baseHealth++;
                break;
            case BASE_DAMAGE:
                baseDamage++;
                break;
            case TANK_HEALTH:
                tankHealth++;
                break;
            case TANK_DAMAGE:
                tankDamage++;
                break;
            case RED_HEALTH:
                redHealth++;
                break;
            case RED_DAMAGE:
                redDamage++;
                break;
            case BOSS:
                bossDamage++;
                bossHealth += BOSS_HEALT_UPGRADE;
                break;
        }
    }

    /**
     * Riporta le statistiche dei nemici al loro stato iniziale.
     */
    private void enemyStatsReset() {
        tankHealth = DEFAULT_TANK_HEALTH;
        tankDamage = DEFAULT_TANK_DAMAGE;
        redHealth = DEFAULT_RED_HEALTH;
        redDamage = DEFAULT_RED_DAMAGE;
        bossHealth = DEFAULT_BOSS_HEALTH;
        bossDamage = DEFAULT_BOSS_DAMAGE;
        baseHealth = DEFAULT_BASE_HEALTH;
        baseDamage = DEFAULT_BASE_DAMAGE;
    }

    /**
     * Restituisce la vita del nemico base.
     * 
     * @return vita nemico base
     */
    public int getBaseHealth() {
        return baseHealth;
    }

    /**
     * Restituisce il danno del nemico base.
     * 
     * @return danno nemico base
     */
    public int getBaseDamage() {
        return baseDamage;
    }

    /**
     * Restituisce la vita del nemico tank.
     * 
     * @return vita nemico tank
     */
    public int getTankHealth() {
        return tankHealth;
    }

    /**
     * Restituisce il danno del nemico tank.
     * 
     * @return danno nemico tank
     */
    public int getTankDamage() {
        return tankDamage;
    }

    /**
     * Restituisce la vita del nemico red.
     * 
     * @return vita nemico red
     */
    public int getRedHealth() {
        return redHealth;
    }

    /**
     * Restituisce il danno del nemico red.
     * 
     * @return danno nemico red
     */
    public int getRedDamage() {
        return redDamage;
    }

    /**
     * Restituisce la vita del nemico boss.
     * 
     * @return vita nemico boss
     */
    public int getBossHealth() {
        return bossHealth;
    }

    /**
     * Restituisce il danno del nemico boss.
     * 
     * @return danno nemico boss
     */
    public int getBossDamage() {
        return bossDamage;
    }

    /**
     * Returns the Y coordinate of the lowest point reached by the alien wave.
     * 
     * @return the maximum Y (including enemy height) or 0 if there are no enemies
     */
    public double getLowestEnemyY() {
        return this.enemies.stream()
                .mapToDouble(enemy -> enemy.getPosition().getY() + enemy.getHeight())
                .max()
                .orElse(0.0);
    }
}
