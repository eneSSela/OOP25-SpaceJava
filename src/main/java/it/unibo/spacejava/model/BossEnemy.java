package it.unibo.spacejava.model;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.controller.EnemyProjectileController;

/**
 * BossEnemy è una estenzione della classe astratta AbstractEnemy.
 * Rappresenta una entità nemica boss con la propria posizione, vita e capacità di attacco.
 * Questo nemico è caratterizzato da dimensioni maggiori, danno maggiore e vita maggiore.
 */
public final class BossEnemy extends AbstractEnemy {
    private static final double DEFAULT_WIDTH = 200.0;
    private static final double DEFAULT_HEIGHT = 120.0;
    private static final int DEFAULT_HEALTH = 20;
    private static final double ATTACK_OFFSET = 10.0;
    private static final int PROJECTILE_WIDTH = 80;
    private static final int PROJECTILE_HEIGHT = 60;
    private static final int DEFAULT_TYPE = 3;
    private static final int DEFAULT_DAMAGE = 2;

    /**
     * Constructs a BossEnemy with initial position.
     *
     * @param startX the initial X coordinate
     * @param startY the initial Y coordinate
     */
    public BossEnemy(final int startX, final int startY) {
        super(new Position(startX, startY), DEFAULT_HEALTH, DEFAULT_HEIGHT, DEFAULT_WIDTH, DEFAULT_TYPE);
    }

    /**
     * Performs an attack by creating a projectile below the enemy.
     */
    @Override
    public void attack() {
        final int startX = super.getPosition().getX() + (int) (DEFAULT_WIDTH / 2 - ATTACK_OFFSET);
        final int startY = super.getPosition().getY() + (int) DEFAULT_HEIGHT;

        final Position projectilePos = new Position(startX, startY);

        EnemyProjectileController.addProjectile(
            new ProjectileImpl(projectilePos, PROJECTILE_WIDTH, PROJECTILE_HEIGHT, DEFAULT_DAMAGE)
        );
    }
}
