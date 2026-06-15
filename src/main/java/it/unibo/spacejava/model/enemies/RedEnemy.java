package it.unibo.spacejava.model.enemies;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.controller.EnemyProjectileController;
import it.unibo.spacejava.model.EnemyType;
import it.unibo.spacejava.model.ProjectileImpl;

/**
 * RedEnemy è una estenzione della classe astratta AbstractEnemy.
 * Rappresenta una entità nemica con la propria posizione, vita e capacità di attacco.
 * Questo nemico è caratterizzato da un danno aumentato.
 */
public final class RedEnemy extends AbstractEnemy {
    private static final double DEFAULT_WIDTH = 40.0;
    private static final double DEFAULT_HEIGHT = 40.0;
    private static final int DEFAULT_HEALTH = 2;
    private static final double ATTACK_OFFSET = 10.0;
    private static final int PROJECTILE_WIDTH = 40;
    private static final int PROJECTILE_HEIGHT = 30;
    private static final int DEFAULT_DAMAGE = 2;

    private static int damage = DEFAULT_DAMAGE;

    /**
     * Crea un RedEnemy data una posizione iniziale.
     *
     * @param position la posizione iniziale.
     */
    public RedEnemy(final Position position) {
        super(position, DEFAULT_HEALTH, DEFAULT_HEIGHT, DEFAULT_WIDTH, EnemyType.RED);
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
            new ProjectileImpl(projectilePos, PROJECTILE_WIDTH, PROJECTILE_HEIGHT, damage)
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
}
