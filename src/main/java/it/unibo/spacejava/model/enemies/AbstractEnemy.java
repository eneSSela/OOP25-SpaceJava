package it.unibo.spacejava.model.enemies;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.spacejava.Position;
import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.controller.EnemyProjectileController;
import it.unibo.spacejava.model.EnemyType;
import it.unibo.spacejava.model.ProjectileImpl;

/**
 * Classe astratta che implementa Enemy, che accomuna i metodi uguali di tutti i nemici.
 */
public abstract class AbstractEnemy implements Enemy {

    private final double attackOffset;
    private final Position position;
    private final double width;
    private final double height;
    private final EnemyType type;
    private final int projectileWidth;
    private final int projectileHeight;
    private final int damage;
    private int health;

    /**
     * Crea un nemico.
     *
     * @param position Posizione iniziale.
     * @param health vita iniziale.
     * @param height altezza.
     * @param width larghezza.
     * @param type tipo di nemico.
     * @param projectileWidth larghezza proiettile.
     * @param projectileHeight altezza proiettile.
     * @param damage danno.
     * @param attackOffset offset per centrare il proiettile sotto il nemico.
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2", 
        justification = "Position non richiede copie difensive"
    )
    public AbstractEnemy(
        final Position position,
        final int health,
        final double height,
        final double width,
        final EnemyType type,
        final int projectileWidth,
        final int projectileHeight,
        final int damage,
        final double attackOffset
    ) {
        this.position = position;
        this.health = health;
        this.height = height;
        this.width = width;
        this.type = type;
        this.projectileWidth = projectileWidth;
        this.projectileHeight = projectileHeight;
        this.damage = damage;
        this.attackOffset = attackOffset;
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
    public final EnemyType getType() {
        return this.type;
    }

    /**
     * Attacca creando un proiettile sotto di sé.
     * 
     * @param projectileController controller dei proiettili nemici dove il nemico aggiunge il proprio proiettile. 
     */
    @Override
    public void attack(final EnemyProjectileController projectileController) {
        final int startX = position.getX() + (int) (width / 2 - attackOffset);
        final int startY = position.getY() + (int) height;

        final Position projectilePos = new Position(startX, startY);

        projectileController.addProjectile(
            new ProjectileImpl(projectilePos, projectileWidth, projectileHeight, damage)
        );
    }

    /**
     * Riduce la vita del nemico in base al danno ricevuto.
     *
     * @param damageReceived danno ricevuto
     */
    @Override
    public void takeDamage(final int damageReceived) {
        this.health -= damageReceived;
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

    /**
     * Restituisce i punti che il nemico fornisce quando viene sconfitto.
     * 
     * @return punti del nemico
     */
    @Override
    public abstract int getPoints();
}
