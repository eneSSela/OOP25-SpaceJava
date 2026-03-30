package it.unibo.spacejava.api;

/**
 * This is a intarface that describe the method of a enemy.
 */

public interface Enemy {

    /**
     * Erminio
     * @return int
     */
    int getDamage();

    /**
     * Erminio
     * @return int
     */
    int getHealth();

    /**
     * Erminio
     * @return int
     */
    int getSize();

    /**
     * Erminio
     */
    void attack();
}
