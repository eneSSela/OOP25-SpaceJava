package it.unibo.spacejava.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.spacejava.Position;
import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.controller.EnemyProjectileController;

<<<<<<< HEAD
public class BaseEnemy implements Enemy {
    private Position position;
    private int health = 1;
    private final double width = 40.0;
    private final double height = 40.0;
    private final int type = 0;
    private final int damage = 1;
=======
/**
 * BaseEnemy is an implementation of the Enemy interface.
 * It represents a basic enemy entity in the game with position, health, and attack capabilities.
 */
@SuppressFBWarnings(
    value = "EI_EXPOSE_REP", 
    justification = "Nel game loop è necessario condividere i riferimenti originali per le performance"
)
public final class BaseEnemy implements Enemy {
    private static final double DEFAULT_WIDTH = 40.0;
    private static final double DEFAULT_HEIGHT = 40.0;
    private static final int DEFAULT_HEALTH = 1;
    private static final double ATTACK_OFFSET = 10.0;
    private static final int PROJECTILE_WIDTH = 40;
    private static final int PROJECTILE_HEIGHT = 30;

    private final Position position;
    private int health;
    private final double width;
    private final double height;
>>>>>>> 75e7d5e76bc054d24745ce7720965cd5a99822f5

    /**
     * Constructs a BaseEnemy with initial position.
     *
     * @param startX the initial X coordinate
     * @param startY the initial Y coordinate
     */
    public BaseEnemy(final int startX, final int startY) {
        this.position = new Position(startX, startY);
<<<<<<< HEAD
=======
        this.health = DEFAULT_HEALTH;
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
>>>>>>> 75e7d5e76bc054d24745ce7720965cd5a99822f5
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

<<<<<<< HEAD
    @Override
    public int getDamage() {
        return damage;
    }


=======
    /**
     * Performs an attack by creating a projectile below the enemy.
     */
>>>>>>> 75e7d5e76bc054d24745ce7720965cd5a99822f5
    @Override
    public void attack() {
        final int startX = this.position.getX() + (int) (this.width / 2 - ATTACK_OFFSET);
        final int startY = this.position.getY() + (int) this.height;

        final Position projectilePos = new Position(startX, startY);

        EnemyProjectileController.addProjectile(new ProjectileImpl(projectilePos, PROJECTILE_WIDTH, PROJECTILE_HEIGHT));
    }
<<<<<<< HEAD

    @Override
    public int type() {
        return this.type;
    }
}
=======
}
>>>>>>> 75e7d5e76bc054d24745ce7720965cd5a99822f5
