package it.unibo.spacejava.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.api.Projectile;
import it.unibo.spacejava.model.ProjectileImpl;

/**
 * Classe di test per EnemyProjectileController.
 */
final class EnemyProjectileControllerTest {

    private static final int SCREEN_HEIGTH = 576;
    private static final int DEFAULT_PROJECTILE_WIDTH = 40;
    private static final int DEFAULT_PROJECTILE_HEIGHT = 30;
    private static final int STARTING_COORD = 100;
    private static final double FRAME = 0.016;
    private static final int SOME_TIME = 999;

    private EnemyProjectileController projectileController;

    @BeforeEach
    void setUp() {
        //Inizializzo un nuovo controller.
        projectileController = new EnemyProjectileController(SCREEN_HEIGTH);
    }

    /**
     * Testo che il movimento dei proiettili sia corretto.
     */
    @Test
    void testProjectileMovement() {
        final Position startPos = new Position(STARTING_COORD, STARTING_COORD);
        final Projectile projectile = new ProjectileImpl(startPos, DEFAULT_PROJECTILE_HEIGHT, DEFAULT_PROJECTILE_WIDTH, 1);
        projectileController.addProjectile(projectile);
        projectileController.update(FRAME);
        assertTrue(projectile.getPosition().getY() > STARTING_COORD);
        assertEquals(projectile.getPosition().getX(), STARTING_COORD);
    }

    /**
     * Testo che i proiettili vengano eliminati dopo essere usciti dallo schermo.
     */
    @Test
    void testProjectileOutOfScreenRemoval() {
        final Position startPos = new Position(STARTING_COORD, STARTING_COORD);
        final Projectile projectile = new ProjectileImpl(startPos, DEFAULT_PROJECTILE_HEIGHT, DEFAULT_PROJECTILE_WIDTH, 1);
        projectileController.addProjectile(projectile);
        assertEquals(1, projectileController.getProjectileList().size());
        projectileController.update(FRAME * SOME_TIME);
        assertEquals(0, projectileController.getProjectileList().size());
    }
}
