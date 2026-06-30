package it.unibo.spacejava.model.enemies;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.model.EnemyType;

/**
 * RedEnemy è una estensione della classe astratta AbstractEnemy.
 * Rappresenta una entità nemica con la propria posizione, vita e capacità di attacco.
 * Questo nemico è caratterizzato da un danno aumentato.
 */
public final class RedEnemy extends AbstractEnemy {
    private static final double DEFAULT_WIDTH = 40.0;
    private static final double DEFAULT_HEIGHT = 40.0;
    private static final int DEFAULT_PROJECTILE_WIDTH = 40;
    private static final int DEFAULT_PROJECTILE_HEIGHT = 30;
    private static final double DEAFAULT_ATTACK_OFFSET = 15.0;
    private static final int SCORE_RED = 150;

    /**
     * Crea un RedEnemy data una posizione iniziale.
     *
     * @param position la posizione iniziale
     * @param health vita attuale
     * @param damage danno attuale
     */
    public RedEnemy(final Position position, final int health, final int damage) {
        super(
            position,
            health,
            DEFAULT_HEIGHT,
            DEFAULT_WIDTH,
            EnemyType.RED,
            DEFAULT_PROJECTILE_WIDTH,
            DEFAULT_PROJECTILE_HEIGHT,
            damage,
            DEAFAULT_ATTACK_OFFSET
        );
    }

    /**
     * Gets the base points awarded for defeating this red enemy.
     * 
     * @return the score value of the red enemy
     */
    @Override
    public int getPoints() {
       return SCORE_RED;
    }
}
