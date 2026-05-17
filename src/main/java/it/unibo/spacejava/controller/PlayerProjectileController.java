package it.unibo.spacejava.controller;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.model.ProjectileImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che finge da controller per i proiettili del giocatore,
 * gesttisce la lista goblale dei proiettili attivi, aggiornando la loro poszione , rimuovendo queelli che escono dallo schermo.
 */
public final class PlayerProjectileController {
    private static final List<ProjectileImpl> PROJECTILE_LIST = new ArrayList<>();
    private static final double PROJECTILE_SPEED = 400.0;

    private PlayerProjectileController() {
    }

    /**
     * Restituisce la lista globale dei proiettili del giocatore.
     * 
     * @return la lista dei proiettili attivi del giocatore
     */
    public static List<ProjectileImpl> getProjectileList() {
        return PROJECTILE_LIST;
    }

    /**
     * Aggiorna la posizione dei proiettili attivi e rimuove quelli fuori dallo schermo.
     * 
     * @param delta il tempo trascorso dall'ultimo aggiornamento, usato per calcolare il movimento dei proiettili
     */
    public static void update(final double delta) {
        // I proiettili del player si muovono verso l'alto (Y diminuisce)
        for (final ProjectileImpl p : PROJECTILE_LIST) {
            final int newY = p.getPosition().getY() - (int) (PROJECTILE_SPEED * delta);
            p.setPosition(new Position(p.getPosition().getX(), newY));
        }

        // Rimuove automaticamente i proiettili quando superano il bordo superiore (Y < 0)
        PROJECTILE_LIST.removeIf(p -> p.getPosition().getY() < 0);
    }
}
