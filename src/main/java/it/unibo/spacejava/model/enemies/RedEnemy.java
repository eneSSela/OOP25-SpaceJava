package it.unibo.spacejava.model.enemies;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.model.EnemyType;

/**
 * RedEnemy è una estenzione della classe astratta AbstractEnemy.
 * Rappresenta una entità nemica con la propria posizione, vita e capacità di attacco.
 * Questo nemico è caratterizzato da un danno aumentato.
 */
public final class RedEnemy extends AbstractEnemy {
    private static final double DEFAULT_WIDTH = 40.0;
    private static final double DEFAULT_HEIGHT = 40.0;
    private static final int DEFAULT_HEALTH = 2;
    private static final int DEFAULT_PROJECTILE_WIDTH = 40;
    private static final int DEFAULT_PROJECTILE_HEIGHT = 30;
    private static final int DEFAULT_DAMAGE = 2;
    private static final int SCORE_RED = 150;

    private static int damage = DEFAULT_DAMAGE;

    /**
     * Crea un RedEnemy data una posizione iniziale.
     *
     * @param position la posizione iniziale.
     */
    public RedEnemy(final Position position) {
        super(
            position,
            DEFAULT_HEALTH,
            DEFAULT_HEIGHT,
            DEFAULT_WIDTH,
            EnemyType.RED,
            DEFAULT_PROJECTILE_WIDTH,
            DEFAULT_PROJECTILE_HEIGHT,
            DEFAULT_DAMAGE
        );
    }

    /**
     * Aumenta il danno del nemico rosso.
     */
    public static void upgrade() {
        damage++;
    }

    /**
     * Restistuisce il danno del nemico rosso per la HUD.
     * 
     * @return damage
     */
    public static int getHUDDamage() {
        return damage;
    }

    /**
     * Resets the red enemy's damage stat to its default value.
     */
    public static void resetStats() {
        damage = DEFAULT_DAMAGE;
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
