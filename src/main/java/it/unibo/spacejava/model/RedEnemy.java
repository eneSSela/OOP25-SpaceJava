package it.unibo.spacejava.model;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.controller.EnemyProjectileController;

/**
 * RedEnemy è una estenzione della classe astratta AbstractEnemy.
 * Rappresenta una entità nemica con la propria posizione, vita e capacità di attacco.
 * Questo nemico è caratterizzato da un danno aumentato.
 */
public final class RedEnemy extends AbstractEnemy {
    private static final double DEFAULT_WIDTH = 40.0;
    private static final double DEFAULT_HEIGHT = 40.0;
    private static final int DEFAULT_HEALTH = 2;
    private static final double ATTACK_OFFSET = 10.0;
    private static final int PROJECTILE_WIDTH = 40;
    private static final int PROJECTILE_HEIGHT = 30;
    private static final int DEFAULT_DAMAGE = 2;

    /**
     * Constructs a RedEnemy with initial position.
     *
     * @param position the starting position
     */
    public RedEnemy(final Position position) {
        super(position, DEFAULT_HEALTH, DEFAULT_HEIGHT, DEFAULT_WIDTH, EnemyType.RED);
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
