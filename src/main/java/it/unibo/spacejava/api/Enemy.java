package it.unibo.spacejava.api;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.controller.EnemyProjectileController;
import it.unibo.spacejava.model.EnemyType;

/**
 * Interfaccia che modella un nemico all'interno del gioco.
 */

public interface Enemy {

    /**
     * Restituisce la posizione attuale del nemico.
     * 
     * @return la posizione corrente del nemico
     */
    Position getPosition();

    /**
     * Restituisce la larghezza del nemico.
     * 
     * @return la larghezza del nemico
     */
    double getWidth();

    /**
     * Restituisce l'altezza del nemico.
     * 
     * @return la altezza del nemico
     */
    double getHeight();

    /**
     * Restituisce la vita attuale del nemico.
     * 
     * @return la vita attuale del nemico
     */
    int getHealth();

    /**
     * Restituisce il tipo di nemico.
     * 
     * @return il tipo di nemico
     */
    EnemyType getType();

    /**
     * Riduce la vita del nemico in base al danno ricevuto.
     *
     * @param damageReceived danno ricevuto
     */
    void takeDamage(int damageReceived);

    /**
     * Attacca creando un proiettile sotto di sé.
     * 
     * @param projectileController controller dei proiettili nemici dove il nemico aggiunge il proprio proiettile. 
     */
    void attack(EnemyProjectileController projectileController);

    /**
     * Controlla se il nemico è vivo.
     * 
     * @return true se la vita del nemico ha raggiunto o è sotto allo zero
     */
    boolean isDead();

    /**
     * Restituisce i punti che il nemico fornisce quando viene sconfitto.
     * 
     * @return punti del nemico
     */
    int getPoints();
}
