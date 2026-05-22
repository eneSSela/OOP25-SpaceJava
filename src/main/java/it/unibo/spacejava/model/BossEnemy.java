package it.unibo.spacejava.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.spacejava.Position;
import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.controller.EnemyProjectileController;

/**
 * Nemico boss.
 */
@SuppressFBWarnings(
    value = "EI_EXPOSE_REP", 
    justification = "Nel game loop è necessario condividere i riferimenti originali per le performance"
)
public final class BossEnemy implements Enemy {
    private static final double DEFAULT_WIDTH = 200.0;
    private static final double DEFAULT_HEIGHT = 120.0;
    private static final int DEFAULT_HEALTH = 20;
    private static final double ATTACK_OFFSET = 10.0;
    private static final int PROJECTILE_WIDTH = 80;
    private static final int PROJECTILE_HEIGHT = 60;
    private static final int TYPE = 3;
    private static final int DAMAGE = 2;

    private final Position position;
    private int health;
    private final double width;
    private final double height;

    /**
     * Constructs a BaseEnemy with initial position.
     *
     * @param startX the initial X coordinate
     * @param startY the initial Y coordinate
     */
    public BossEnemy(final int startX, final int startY) {
        this.position = new Position(startX, startY);
        this.health = DEFAULT_HEALTH;
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public double getHeight() {
        return this.height;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    /**
     * Reduces the health of this enemy by the given damage amount.
     *
     * @param damage the amount of damage to take
     */
    @Override
    public void takeDamage(final int damage) {
        this.health -= damage;
    }

    @Override
    public boolean isDead() {
        return this.health <= 0;
    }

    /**
     * Performs an attack by creating a projectile below the enemy.
     */
    @Override
    public void attack() {
        final int startX = this.position.getX() + (int) (this.width / 2 - ATTACK_OFFSET);
        final int startY = this.position.getY() + (int) this.height;

        final Position projectilePos = new Position(startX, startY);

        EnemyProjectileController.addProjectile(new ProjectileImpl(projectilePos, PROJECTILE_WIDTH, PROJECTILE_HEIGHT, DAMAGE));
    }

    @Override
    public int type() {
        return TYPE;
    }
}
