package it.unibo.spacejava.model.enemies;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.model.EnemyType;

/**
 * BossEnemy è una estensione della classe astratta AbstractEnemy.
 * Rappresenta una entità nemica boss con la propria posizione, vita e capacità di attacco.
 * Questo nemico è caratterizzato da dimensioni maggiori, danno maggiore e vita maggiore.
 */
public final class BossEnemy extends AbstractEnemy {
    private static final double DEFAULT_WIDTH = 200.0;
    private static final double DEFAULT_HEIGHT = 120.0;
    private static final int DEFAULT_PROJECTILE_WIDTH = 80;
    private static final int DEFAULT_PROJECTILE_HEIGHT = 60;
    private static final double DEFAULT_ATTACK_OFFSET = 30.0;
    private static final int SCORE_BOSS = 1000;

    /**
     * Crea un BossEnemy data una posizione iniziale.
     *
     * @param position la posizione iniziale
     * @param health vita attuale
     * @param damage danno attuale
     */
    public BossEnemy(final Position position, final int health, final int damage) {
        super(
            position,
            health,
            DEFAULT_HEIGHT,
            DEFAULT_WIDTH,
            EnemyType.BOSS,
            DEFAULT_PROJECTILE_WIDTH,
            DEFAULT_PROJECTILE_HEIGHT,
            damage,
            DEFAULT_ATTACK_OFFSET
        );
    }

    /**
     * Gets the base points awarded for defeating this boss enemy.
     * 
     * @return the score value of the boss
     */
    @Override
    public int getPoints() {
        return SCORE_BOSS;
    }
}
