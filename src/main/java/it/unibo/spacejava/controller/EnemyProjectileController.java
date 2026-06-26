package it.unibo.spacejava.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.api.Projectile;

/**
 * Classe che funge come controller dei proittili.
 */
public final class EnemyProjectileController {

    private static final List<Projectile> PROJECTILES_LIST = new ArrayList<>();
    private static final double SPEED = 150.0;
    private static double dynamicSpeed = SPEED;
    private final int screenHeight;

    /**
     * Construtte del controller.
     * 
     * @param screenHeight altezza dello schermo 
     */
    public EnemyProjectileController(final int screenHeight) {
        this.screenHeight = screenHeight;
    } 

    /**
     * Aggiorna la poszione dei poriettili e rimuove quelli fuori dallo shermo.
     * 
     * @param delta il tempo trascorso dall'ultimo aggiornamento 
     */
    public void update(final double delta) {
        final double currentSpeed = getDynamicSpeed();
        final int movement = Math.max(1, (int) Math.round(currentSpeed * delta));
        synchronized (PROJECTILES_LIST) {
            for (final Projectile p : PROJECTILES_LIST) {
                p.setPosition(new Position(p.getPosition().getX(), p.getPosition().getY() + movement));
            }
            PROJECTILES_LIST.removeIf(p -> p.getPosition().getY() >= screenHeight);
        }
    }

    /**
     * Getter per la lista dei proiettili.
     * 
     * @return la lista dei proiettili
     */
    public static List<Projectile> getProjectileList() {
        synchronized (PROJECTILES_LIST) {
            return Collections.unmodifiableList(new ArrayList<>(PROJECTILES_LIST));
        }
    }

    /**
     * Aggiunge un nuovo proiettile alla lista.
     * 
     * @param projectileImpl il proiettile da aggiungere
     */
    public static void addProjectile(final Projectile projectileImpl) {
        synchronized (PROJECTILES_LIST) {
            PROJECTILES_LIST.add(projectileImpl);
        }
    }

    /**
     * Rimuove un proiettile dalla lista.
     * 
     * @param projectileToRemove il proiettile da rimuovere
     */
    static void removeProjectile(final Projectile projectileToRemove) {
        synchronized (PROJECTILES_LIST) {
            PROJECTILES_LIST.remove(projectileToRemove);
        }
    }

    /**
     * Multiply the speed of projectiles using the effect of "Distorsione Tempo".
     * The method is synchronized to solve the warnings of SpotBugs (atomic operation).
     * 
     * @param factor is the factor of the multiplication
     */
    public static synchronized void multiplySpeed(final double factor) {
        dynamicSpeed *= factor;
    }

    /**
     * Thread-safe reading of the dynamic speed.
     * 
     * @return the speed of the projectile
     */
    private static synchronized double getDynamicSpeed() {
        return dynamicSpeed;
    }
}
