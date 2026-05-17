package it.unibo.spacejava.controller;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.model.ProjectileImpl;
import java.util.ArrayList;
import java.util.List;

public class PlayerProjectileController {
    private static final List<ProjectileImpl> projectileList = new ArrayList<>();
    private static final double projectileSpeed = 400.0;

    /**
     * Restituisce la lista globale dei proiettili del giocatore
     */
    public static List<ProjectileImpl> getProjectileList() {
        return projectileList;
    }

    /**
     * Aggiorna la posizione dei proiettili attivi e rimuove quelli fuori dallo schermo
     */
    public static void update(double delta) {
        // I proiettili del player si muovono verso l'alto (Y diminuisce)
        for(ProjectileImpl p : projectileList) {
            double newY = p.getPosition().getY() - (projectileSpeed * delta);
            p.setPosition(new Position(p.getPosition().getX(), newY));
        }

        // Rimuove automaticamente i proiettili quando superano il bordo superiore (Y < 0)
        projectileList.removeIf(p -> p.getPosition().getY() < 0);
    }
}
