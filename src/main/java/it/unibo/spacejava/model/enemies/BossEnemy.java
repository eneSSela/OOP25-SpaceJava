package it.unibo.spacejava.model.enemies;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.model.EnemyType;

/**
 * BossEnemy è una estenzione della classe astratta AbstractEnemy.
 * Rappresenta una entità nemica boss con la propria posizione, vita e capacità di attacco.
 * Questo nemico è caratterizzato da dimensioni maggiori, danno maggiore e vita maggiore.
 */
public final class BossEnemy extends AbstractEnemy {
    private static final double DEFAULT_WIDTH = 200.0;
    private static final double DEFAULT_HEIGHT = 120.0;
    private static final int DEFAULT_PROJECTILE_WIDTH = 80;
    private static final int DEFAULT_PROJECTILE_HEIGHT = 60;
    private static final int HEALTH_UPGRADE = 5;
    private static final int DEFAULT_HEALTH = 20;
    private static final int DEFAULT_DAMAGE = 2;
    private static final double DEAFAULT_ATTACK_OFFSET = 30.0;
    private static final int SCORE_BOSS = 1000;

    private static int health = DEFAULT_HEALTH;
    private static int damage = DEFAULT_DAMAGE;

    /**
     * Crea un BossEnemy data una posizione iniziale.
     *
     * @param position la posizione iniziale
     */
    public BossEnemy(final Position position) {
        super(
            position,
            health,
            DEFAULT_HEIGHT,
            DEFAULT_WIDTH,
            EnemyType.BOSS,
            DEFAULT_PROJECTILE_WIDTH,
            DEFAULT_PROJECTILE_HEIGHT,
            DEFAULT_DAMAGE,
            DEAFAULT_ATTACK_OFFSET
        );
    }

    /**
     * Aumenta il danno e la vita del nemico boss.
     */
    public static void upgrade() {
        damage++;
        health = health + HEALTH_UPGRADE;
    }

    /**
     * Restistuisce la vita del nemico boss per la HUD.
     * 
     * @return health
     */
    public static int getHUDHealth() {
        return health;
    }

    /**
     * Restistuisce il danno del nemico boss per la HUD.
     * 
     * @return damage
     */
    public static int getHUDDamage() {
        return damage;
    }

    @Override
    public int getPoints() {
        return SCORE_BOSS;
    }
}
