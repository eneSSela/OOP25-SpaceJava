package it.unibo.spacejava.controller;

import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.spacejava.api.Bunker;
import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.api.Projectile;
import it.unibo.spacejava.model.BunkerImpl;

/**
 * Controller che gestisce i bunker difensivi del giocatore, 
 * creando 4 bunker posizionati equidistantemente tra loto e sopra il palyer.
 */
public final class BunkerController {

    private static final int BUNKER_WIDTH = 80;
    private static final int BUNKER_HEIGHT = 40;
    private static final int BUNKER_HEALTH = 10; // Punti vita per ogni bunker
    private static final int MAX_DAMAGE = 999;
    private final List<Bunker> bunkers = new ArrayList<>();

    private final PlayerProjectileController playerProjController;
    private final EnemyProjectileController enemyProjController;

    /**
     * Costruisce i 4 bunker posizionati equidistantemente tra loto e sopra il palyer.
     * 
     * @param screenWidth la larghezza dello schermo, usata per posizionare i bunker equidistantemente
     * @param screenHeight l'altezza dello schermo, usata per posizionare i bunker sopra il giocatore
     * @param playerProjController controller proiettili player
     * @param enemyProjController controller proiettili nemici
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Dependency injection is intended here")
    public BunkerController(final int screenWidth, final int screenHeight, 
                            final PlayerProjectileController playerProjController, 
                            final EnemyProjectileController enemyProjController) {
        this.playerProjController = playerProjController;
        this.enemyProjController = enemyProjController;

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
     * Verifica le collisioni tra i proiettili (sia del giocatore che nemici) e i bunker, 
     * applicando danno ai bunker e rimuovendo i proiettili che colpiscono un bunker.
     * 
     * @param playerProjectiles lista dei proiettili del giocatore
     * @param enemyProjectiles lista dei proiettili dei nemici
     */
    public void checkCollisions(final List<Projectile> playerProjectiles, final List<Projectile> enemyProjectiles) {
        //usole le copie delle liste dei proiettili per risolvere il problema di errori di concorenza
        final List<Projectile> playerProjectilesSnapshot = new ArrayList<>(playerProjectiles);
        final List<Projectile> enemyProjectilesSnapshot = new ArrayList<>(enemyProjectiles);

        for (final Bunker b : bunkers) {
            playerProjectilesSnapshot.stream().filter(p -> isColliding(b, p))
            .forEach(this.playerProjController::removeProjectile);
            enemyProjectilesSnapshot.stream()
            .filter(p -> isColliding(b, p))
            .forEach(p -> {
                b.takeDamage(p.getDamage());
               this.enemyProjController.removeProjectile(p);
            });
        }

        bunkers.removeIf(bunker -> bunker != null && bunker.isDestroyed());
    }

    /**
     * Check if enemies have descended until they physically collide with bunkers.
     * 
     * @param enemies the list of the enemies
     */
    public void checkEnemyCollisions(final List<Enemy> enemies) {
        for (final Bunker b : bunkers) {
            if (!b.isDestroyed()) {
                for (final Enemy e : enemies) {
                    if (isCollidingWithEnemy(b, e)) {
                        b.takeDamage(MAX_DAMAGE);
                        break;
                    }
                }
            }
        }
        bunkers.removeIf(bunker -> bunker != null && bunker.isDestroyed());
    }

    private boolean isColliding(final Bunker b, final Projectile p) {
        return p.getPosition().getX() < b.getPosition().getX() + b.getWidth()
            && p.getPosition().getX() + p.getWidth() > b.getPosition().getX() 
            && p.getPosition().getY() < b.getPosition().getY() + b.getHeight()
            && p.getPosition().getY() + p.getLenght() > b.getPosition().getY();
    }

    private boolean isCollidingWithEnemy(final Bunker b, final Enemy e) {
        return e.getPosition().getX() < b.getPosition().getX() + b.getWidth() 
            && e.getPosition().getX() + e.getWidth() > b.getPosition().getX()
            && e.getPosition().getY() < b.getPosition().getY() + b.getHeight()
            && e.getPosition().getY() + e.getHeight() > b.getPosition().getY();
    }
}
