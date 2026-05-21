package it.unibo.spacejava.api;

import it.unibo.spacejava.Position;

/**
 * This is a intarface that describe the method of a enemy.
 */

public interface Enemy {

    /**
     * Getter method that return the position of the enemy.
     * 
     * @return the position of the enemy
     */
    Position getPosition();

    /**
     * Getter method that return the width of the enemy.
     * 
     * @return the width of the enemy
     */
    double getWidth();

    /**
     * Method that return the height of the enemy.
     * 
     * @return the height of the enemy
     */
    double getHeight();

    /**
     * Method that return the health of the enemy.
     * 
     * @return the healt of the enemy
     */
    int getHealth();

    /**
     * Method used to decide the appearence for the enemy.
     * 
     * @return type of the enemy
     */
    int type();

    /**
     * Method that decrease the health of the enemy when it is hit by a projectile. 
     * 
     * @param damage the amount of damege to inflict on the enemy
     */ 
    void takeDamage(int damage);

    /**
     * Mtehod that make the enemy attack the player by shhoting a projectile.
     */
    void attack();

    /**
     * Method that chek if the enmy is dead or not.
     * 
     * @return true if the enemy is dead, false otherwise
     */
    boolean isDead();
}
