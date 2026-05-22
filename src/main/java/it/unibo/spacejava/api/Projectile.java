package it.unibo.spacejava.api;

import it.unibo.spacejava.Position;

/**
 * This is a interface that describe the method of a projectile.
 */
public interface Projectile {

    /**
     * Method that set the position of the projectile.
     * 
     * @param pos the position to set for the projectile, consistin the first pos of the projectile
     *            when a entity shoot it
     */
    void setPosition(Position pos);

    /**
     * Getter method that return the position of the projectile.
     * 
     * @return the position of the projectile
     */
    Position getPosition();

    /**
     * Getter method that return the length of the projectile.
     * 
     * @return the length of the projectile
     */
    int getLenght();

    /**
     * Getter method that return the width of the projectile.
     * 
     * @return the width of the projectile
     */
    int getWidth();

    /**
     * Getter method that return the damage of the projectile.
     * 
     * @return the damage of the projectile
     */
    int getDamage();
}
