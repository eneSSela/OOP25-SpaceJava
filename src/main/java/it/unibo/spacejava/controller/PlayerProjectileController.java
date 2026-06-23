package it.unibo.spacejava.controller;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.api.Projectile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe che finge da controller per i proiettili del giocatore,
 * gesttisce la lista goblale dei proiettili attivi, aggiornando la loro poszione , rimuovendo queelli che escono dallo schermo.
 */
public final class PlayerProjectileController {
    private final List<Projectile> PROJECTILE_LIST;
    private static final double PROJECTILE_SPEED = 400.0;

    public PlayerProjectileController() {
        this.PROJECTILE_LIST = new ArrayList<>();
    }

    /**
     * Restituisce la lista globale dei proiettili del giocatore.
     * 
     * @return la lista dei proiettili attivi del giocatore
     */
    public List<Projectile> getProjectileList() {
        synchronized (PROJECTILE_LIST) {
            return Collections.unmodifiableList(PROJECTILE_LIST);
        }
    }

    /**
     * Aggiorna la posizione dei proiettili attivi e rimuove quelli fuori dallo schermo.
     * 
     * @param delta il tempo trascorso dall'ultimo aggiornamento, usato per calcolare il movimento dei proiettili
     */
    public void update(final double delta) {
        // I proiettili del player si muovono verso l'alto (Y diminuisce)
        synchronized (PROJECTILE_LIST) {
            for (final Projectile p : PROJECTILE_LIST) {
                final int newY = p.getPosition().getY() - (int) (PROJECTILE_SPEED * delta);
                p.setPosition(new Position(p.getPosition().getX(), newY));
            }

            // Rimuove automaticamente i proiettili quando superano il bordo superiore (Y < 0)
            PROJECTILE_LIST.removeIf(p -> p.getPosition().getY() < 0);
        }
    }

    /**
     * Aggiunge un nuovo proiettile alla lista dei proiettili attivi del giocatore.
     * 
     * @param projectileImpl il proiettile da aggiungere alla lista dei proiettili attivi del giocatore
     */
    public void addProjectile(final Projectile projectileImpl) {
        synchronized (PROJECTILE_LIST) {
            PROJECTILE_LIST.add(projectileImpl);
        }
    }

    /**
     * Rimuove un proiettile specifico dalla lista dei proiettili attivi del giocatore.
     * 
     * @param projectileImpl il proiettile da rimuovere dalla lista dei proiettili attivi del giocatore
     */
    public void removeProjectile(final Projectile projectileImpl) {
        synchronized (PROJECTILE_LIST) {
            PROJECTILE_LIST.remove(projectileImpl);
        }
    }
}
