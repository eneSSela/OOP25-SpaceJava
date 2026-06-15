package it.unibo.spacejava.model.enemies;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.model.EnemyType;

/**
 * TankEnemy è una estenzione della classe astratta AbstractEnemy.
 * Rappresenta una entità nemica con la propria posizione, vita e capacità di attacco.
 * Questo nemico è caratterizzato da una vita aumentata.
 */
public final class TankEnemy extends AbstractEnemy {
    private static final double DEFAULT_WIDTH = 40.0;
    private static final double DEFAULT_HEIGHT = 40.0;
    private static final int DEFAULT_PROJECTILE_WIDTH = 40;
    private static final int DEFAULT_PROJECTILE_HEIGHT = 30;
    private static final int DEFAULT_DAMAGE = 1;
    private static final int DEFAULT_HEALTH = 3;

    private static int health = DEFAULT_HEALTH;

    /**
     * Crea un TankEnemy data una posizione iniziale.
     *
     * @param position la posizione iniziale.
     */
    public TankEnemy(final Position position) {
        super(
            position,
            health,
            DEFAULT_HEIGHT,
            DEFAULT_WIDTH,
            EnemyType.TANK,
            DEFAULT_PROJECTILE_WIDTH,
            DEFAULT_PROJECTILE_HEIGHT,
            DEFAULT_DAMAGE
        );
    }

    /**
     * Aumenta la vita del nemico tank.
     */
    public static void upgrade() {
        health++;
    }

    /**
     * Restistuisce la vita del nemico tank per la HUD.
     * 
     * @return health
     */
    public static int getHUDHealth() {
        return health;
    }
}
