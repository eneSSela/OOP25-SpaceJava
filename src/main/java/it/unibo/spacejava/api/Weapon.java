package it.unibo.spacejava.api;

public interface Weapon {
    
    /**
     * Erminio
     */
    void fire();

    /**
     * Erminio
     * @return String
     */
    String getType();

    /**
     * Erminio
     * @return float
     */
    float getDamage();

    /**
     * Erminio
     * @return float
     */
    float getFireRate();
}
