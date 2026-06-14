package it.unibo.spacejava.api;

import it.unibo.spacejava.Position;

/**
 * Interface that represents a defensive bunker/shield for the player.
 */
public interface Bunker {

    /**
     * @return the position of the bunker
     */
    Position getPosition();

    /**
     * @return the width of the bunker
     */
    int getWidth();

    /**
     * @return the height of the bunker
     */
    int getHeight();

    /**
     * @return the current health of the bunker
     */
    int getHealth();

    /**
     * Reduces the health of the bunker when hit.
     * 
     * @param damage the amount of damage taken
     */
    void takeDamage(int damage);

    /**
     * @return true if the bunker is fully destroyed, false otherwise
     */
    boolean isDestroyed();
}
