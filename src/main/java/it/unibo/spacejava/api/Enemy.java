package it.unibo.spacejava.api;

import it.unibo.spacejava.Position;

/**
 * This is a intarface that describe the method of a enemy.
 */

public interface Enemy {

    Position getPosition();

    double getWidth();

    double getHeight();

    int getHealth();

    /**
     * Erminio
     * @return int
     */
    int getDamage();

    void takeDamage(int damage);

    /**
     * Erminio
     * @return int
     */
    int getSize();

    /**
     * Erminio
     */
    void attack();

    boolean isDead();
}
