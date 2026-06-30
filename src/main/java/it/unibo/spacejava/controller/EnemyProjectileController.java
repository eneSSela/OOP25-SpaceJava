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

    private static final double SPEED = 150.0;
    private static double dynamicSpeed = SPEED;
    private final int screenHeight;
    private final List<Projectile> projectileList;

    /**
     * Construtte del controller.
     * 
     * @param screenHeight altezza dello schermo 
     */
    public EnemyProjectileController(final int screenHeight) {
        this.screenHeight = screenHeight;
        this.projectileList = new ArrayList<>();
    } 

    /**
     * Aggiorna la poszione dei poriettili e rimuove quelli fuori dallo shermo.
     * 
     * @param delta il tempo trascorso dall'ultimo aggiornamento 
     */
    public void update(final double delta) {
        final double currentSpeed = getDynamicSpeed();
        final int movement = Math.max(1, (int) Math.round(currentSpeed * delta));
        synchronized (this.projectileList) {
            for (final Projectile p : this.projectileList) {
                p.setPosition(new Position(p.getPosition().getX(), p.getPosition().getY() + movement));
            }
            this.projectileList.removeIf(p -> p.getPosition().getY() >= screenHeight);
        }
    }

    /**
     * Getter per la lista dei proiettili.
     * 
     * @return la lista dei proiettili
     */
    public List<Projectile> getProjectileList() {
        synchronized (this.projectileList) {
            return Collections.unmodifiableList(new ArrayList<>(this.projectileList));
        }
    }

    /**
     * Aggiunge un nuovo proiettile alla lista.
     * 
     * @param projectileImpl il proiettile da aggiungere
     */
    public void addProjectile(final Projectile projectileImpl) {
        synchronized (this.projectileList) {
            this.projectileList.add(projectileImpl);
        }
    }

    /**
     * Rimuove un proiettile dalla lista.
     * 
     * @param projectileToRemove il proiettile da rimuovere
     */
    public void removeProjectile(final Projectile projectileToRemove) {
        synchronized (this.projectileList) {
            this.projectileList.remove(projectileToRemove);
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
