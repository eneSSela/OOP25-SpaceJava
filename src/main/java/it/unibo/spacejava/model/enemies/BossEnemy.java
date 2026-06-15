package it.unibo.spacejava.model.enemies;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.controller.EnemyProjectileController;
import it.unibo.spacejava.model.EnemyType;
import it.unibo.spacejava.model.ProjectileImpl;

/**
 * BossEnemy è una estenzione della classe astratta AbstractEnemy.
 * Rappresenta una entità nemica boss con la propria posizione, vita e capacità di attacco.
 * Questo nemico è caratterizzato da dimensioni maggiori, danno maggiore e vita maggiore.
 */
public final class BossEnemy extends AbstractEnemy {
    private static final double DEFAULT_WIDTH = 200.0;
    private static final double DEFAULT_HEIGHT = 120.0;
    private static final double ATTACK_OFFSET = 10.0;
    private static final int PROJECTILE_WIDTH = 80;
    private static final int PROJECTILE_HEIGHT = 60;
    private static final int HEALTH_UPGRADE = 5;
    private static final int DEFAULT_HEALTH = 20;
    private static final int DEFAULT_DAMAGE = 2;

    private static int health = DEFAULT_HEALTH;
    private static int damage = DEFAULT_DAMAGE;

    /**
     * Crea un BossEnemy data una posizione iniziale.
     *
     * @param position la posizione iniziale
     */
    public BossEnemy(final Position position) {
        super(position, health, DEFAULT_HEIGHT, DEFAULT_WIDTH, EnemyType.BOSS);
    }

    /**
     * Attacca creando un proiettile sotto di sé.
     */
    @Override
    public void attack() {
        final int startX = super.getPosition().getX() + (int) (DEFAULT_WIDTH / 2 - ATTACK_OFFSET);
        final int startY = super.getPosition().getY() + (int) DEFAULT_HEIGHT;

        final Position projectilePos = new Position(startX, startY);

        EnemyProjectileController.addProjectile(
            new ProjectileImpl(projectilePos, PROJECTILE_WIDTH, PROJECTILE_HEIGHT, damage)
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
}
