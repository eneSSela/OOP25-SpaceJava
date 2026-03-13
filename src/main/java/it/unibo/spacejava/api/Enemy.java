package it.unibo.spacejava.api;

/**
 * This is a intarface that describe the method of a enemy.
 */

public interface Enemy {

    int getDamage();

    int getHealth();

    int getSize();

    void attack();
}
