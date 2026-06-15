package it.unibo.spacejava.model.enemies;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.spacejava.Position;
import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.model.EnemyType;

/**
 * Classe astratta che implementa Enemy, che accomuna i metodi uguali di tutti i nemici.
 */
public abstract class AbstractEnemy implements Enemy {

    private final Position position;
    private final double width;
    private final double height;
    private final EnemyType type;
    private int health;

    /**
     * Crea un nemico.
     *
     * @param position Posizione iniziale.
     * @param health vita iniziale.
     * @param height altezza.
     * @param width larghezza.
     * @param type tipo di nemico.
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2", 
        justification = "Position non richiede copie difensive"
    )
    public AbstractEnemy(
        final Position position, final int health, final double height, final double width, final EnemyType type
    ) {
        this.position = position;
        this.health = health;
        this.height = height;
        this.width = width;
        this.type = type;
    }

    /**
     * Restituisce la posizione attuale del nemico.
     * 
     * @return la posizione corrente del nemico
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP", 
        justification = "Position non richiede copie difensive"
    )
    @Override
    public final Position getPosition() {
        return this.position;
    }

    /**
     * Restituisce la larghezza del nemico.
     * 
     * @return la larghezza del nemico
     */
    @Override
    public final double getWidth() {
        return this.width;
    }

    /**
     * Restituisce l'altezza del nemico.
     * 
     * @return la altezza del nemico
     */
    @Override
    public final double getHeight() {
        return this.height;
    }

    /**
     * Restituisce la vita attuale del nemico.
     * 
     * @return la vita attuale del nemico
     */
    @Override
    public final int getHealth() {
        return this.health;
    }

    /**
     * Restituisce il tipo di nemico.
     * 
     * @return il tipo di nemico
     */
    @Override
    public final EnemyType type() {
        return this.type;
    }

    /**
     * Riduce la vita del nemico in base al danno ricevuto.
     *
     * @param damage danno ricevuto
     */
    @Override
    public void takeDamage(final int damage) {
        this.health -= damage;
    }

    /**
     * Controlla se il nemico è vivo.
     * 
     * @return true se la vita del nemico ha raggiunto o è sotto allo zero
     */
    @Override
    public final boolean isDead() {
        return this.health <= 0;
    }
}
