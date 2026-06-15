package it.unibo.spacejava.model.enemies;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.controller.EnemyProjectileController;
import it.unibo.spacejava.model.EnemyType;
import it.unibo.spacejava.model.ProjectileImpl;

/**
 * TankEnemy è una estenzione della classe astratta AbstractEnemy.
 * Rappresenta una entità nemica con la propria posizione, vita e capacità di attacco.
 * Questo nemico è caratterizzato da una vita aumentata.
 */
public final class TankEnemy extends AbstractEnemy {
    private static final double DEFAULT_WIDTH = 40.0;
    private static final double DEFAULT_HEIGHT = 40.0;
    private static final double ATTACK_OFFSET = 10.0;
    private static final int PROJECTILE_WIDTH = 40;
    private static final int PROJECTILE_HEIGHT = 30;
    private static final int DEFAULT_DAMAGE = 1;
    private static final int DEFAULT_HEALTH = 3;

    private static int health = DEFAULT_HEALTH;

    /**
     * Crea un TankEnemy data una posizione iniziale.
     *
     * @param position la posizione iniziale.
     */
    public TankEnemy(final Position position) {
        super(position, health, DEFAULT_HEIGHT, DEFAULT_WIDTH, EnemyType.TANK);
    }

    /**
     * Attacca creando un proiettile sotto di sé.
     */
    @Override
    public void attack() {
        final int startX = super.getPosition().getX() + (int) (DEFAULT_WIDTH / 2 - ATTACK_OFFSET);
        final int startY = super.getPosition().getY() + (int) DEFAULT_HEIGHT;

        final Position projectilePos = new Position(startX, startY);

        EnemyProjectileController.addProjectile(
            new ProjectileImpl(projectilePos, PROJECTILE_WIDTH, PROJECTILE_HEIGHT, DEFAULT_DAMAGE)
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
