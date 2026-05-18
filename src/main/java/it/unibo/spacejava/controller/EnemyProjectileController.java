package it.unibo.spacejava.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.model.ProjectileImpl;

/**
 * Classe che funge come controller dei proittili.
 */
public final class EnemyProjectileController {

    private static List<ProjectileImpl> projectiles = new ArrayList<>();
    private static final double SPEED = 150.0;
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
        final int movement = Math.max(1, (int) Math.round(SPEED * delta));
        for (final ProjectileImpl p : projectiles) {
            p.setPosition(new Position(p.getPosition().getX(), p.getPosition().getY() + movement));
        }

        //removes out of bounds projectiles
        projectiles.removeIf(p -> p.getPosition().getY() >= screenHeight);
    }

    /**
     * Getter per la lista dei proiettili.
     * 
     * @return la lista dei proiettili
     */
    public static List<ProjectileImpl> getProjectileList() {
        return Collections.unmodifiableList(projectiles);
    }

    /**
     * Aggiunge un nuovo proiettile alla lista.
     * 
     * @param projectileImpl il proiettile da aggiungere
     */
    public static void addProjectile(final ProjectileImpl projectileImpl) {
        projectiles.add(projectileImpl);
    }

    /**
     * Rimuove un proiettile dalla lista.
     * 
     * @param projectileToRemove il proiettile da rimuovere
     */
    static void removeProjectile(final ProjectileImpl projectileToRemove) {
        projectiles.remove(projectileToRemove);
    }
}
