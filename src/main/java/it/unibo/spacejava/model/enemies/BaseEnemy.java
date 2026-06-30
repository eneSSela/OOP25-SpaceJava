package it.unibo.spacejava.model.enemies;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.model.EnemyType;

/**
 * BaseEnemy è una estensione della classe astratta AbstractEnemy.
 * Rappresenta una entità nemica base con la propria posizione, vita e capacità di attacco.
 */
public final class BaseEnemy extends AbstractEnemy {
    private static final double DEFAULT_WIDTH = 40.0;
    private static final double DEFAULT_HEIGHT = 40.0;
    private static final int DEFAULT_PROJECTILE_WIDTH = 40;
    private static final int DEFAULT_PROJECTILE_HEIGHT = 30;
    private static final double DEAFAULT_ATTACK_OFFSET = 15.0;
    private static final int SCORE_BASE = 100;

    /**
     * Crea un BaseEnemy data una posizione iniziale.
     *
     * @param position la posizione iniziale
     * @param health vita attuale
     * @param damage danno attuale
     */
    public BaseEnemy(final Position position, final int health, final int damage) {
        super(
            position,
            health, 
            DEFAULT_HEIGHT, 
            DEFAULT_WIDTH, 
            EnemyType.BASE, 
            DEFAULT_PROJECTILE_WIDTH, 
            DEFAULT_PROJECTILE_HEIGHT, 
            damage,
            DEAFAULT_ATTACK_OFFSET
        );
    }

    @Override
    public int getPoints() {
        return SCORE_BASE;
    }
}
