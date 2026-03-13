package it.unibo.spacejava.api;

public interface PowerUp {

    //applayEffect(PlayerShip player);
    /**
     *  retrun the type of powerUp.
     *  * @return the name of powerUp
     */
    String getType();
    
    /**
     * this method return the multipler of powerUp
     * @return multipler
     */
    float getMultipler();
}
