package it.unibo.spacejava.api;

import it.unibo.spacejava.model.PlayerShip;

/**
 * This is an interface that describe the method of a powerUp.
 */
public interface PowerUp {

    /**
     * Method that use for apply the effect of the powerUp on the player.
     * 
     * @param player the player on which the effect of the powerUp will be applied
     */
    void applayEffect(PlayerShip player);

    /**
     * Return the type of powerUp.
     * 
     *  @return the name of powerUp
     */
    String getType();

    /**
     * Return a short description of the power-up's effect.
     * 
     * @return a string containing a short description of the power-up's effect
     */
    String getDescription();

    /**
     * This method return the multipler of powerUp.
     * 
     * @return multipler
     */
    float getMultipler();
}
