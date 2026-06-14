package it.unibo.spacejava.controller;

import java.util.ArrayList;
import java.util.List;

import it.unibo.spacejava.api.Bunker;
import it.unibo.spacejava.model.BunkerImpl;
import it.unibo.spacejava.model.ProjectileImpl;

/**
 * Controller che gestisce i bunker difensivi del giocatore, creando 4 bunker posizionati equidistantemente tra loto e sopra il palyer.
 */
public final class BunkerController {

    private final List<Bunker> bunkers = new ArrayList<>();
    private static final int BUNKER_WIDTH = 80;
    private static final int BUNKER_HEIGHT = 40;
    private static final int BUNKER_HEALTH = 10; // Punti vita per ogni bunker

    /**
     * Costruisce i 4 bunker posizionati equidistantemente tra loto e sopra il palyer.
     * 
     * @param screenWidth la larghezza dello schermo, usata per posizionare i bunker equidistantemente
     * @param screenHeight l'altezza dello schermo, usata per posizionare i bunker sopra il giocatore
     */
    public BunkerController(final int screenWidth, final int screenHeight) {
        // Generiamo 4 bunker distanziati equamente
        final int spacing = screenWidth / 5; 
        final int startY = screenHeight - 180; // Posizionati sopra il giocatore
        
        for (int i = 1; i <= 4; i++) {
            bunkers.add(new BunkerImpl(spacing * i - (BUNKER_WIDTH / 2), startY, BUNKER_WIDTH, BUNKER_HEIGHT, BUNKER_HEALTH));
        }
    }

    /**
     * Getter della lsita dei bunker.
     * 
     * @return lista dei bunker attivi
     */
    public List<Bunker> getBunkers() {
        return List.copyOf(this.bunkers);
    }

    /**
     * Verifica le collsiosi tra i proiettili (sia del giocatore che nemici) e i bunker, 
     * applicando danno ai bunker e rimuovendo i proiettili che colpiscono un bunker.
     */
    public void checkCollisions() {
        final List<ProjectileImpl> playerProjectiles = PlayerProjectileController.getProjectileList();
        final List<ProjectileImpl> enemyProjectiles = EnemyProjectileController.getProjectileList();

        for (final Bunker b : bunkers) {
            for (final ProjectileImpl p : playerProjectiles) {
                if (isColliding(b, p)) {
                    PlayerProjectileController.removeProjectile(p);
                    break;
                }
            }

            for (final ProjectileImpl p : enemyProjectiles) {
                if (isColliding(b, p)) {
                    b.takeDamage(p.getDamage());
                    EnemyProjectileController.removeProjectile(p);
                    break;
                }
            }
        }

        bunkers.removeIf(Bunker::isDestroyed);
    }

    private boolean isColliding(final Bunker b, final ProjectileImpl p) {
        return p.getPosition().getX() < b.getPosition().getX() + b.getWidth() &&
               p.getPosition().getX() + p.getWidth() > b.getPosition().getX() &&
               p.getPosition().getY() < b.getPosition().getY() + b.getHeight() &&
               p.getPosition().getY() + p.getLenght() > b.getPosition().getY();
    }

}
