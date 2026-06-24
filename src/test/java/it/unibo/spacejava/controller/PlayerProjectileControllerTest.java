package it.unibo.spacejava.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.model.ProjectileImpl;

/**
 * Test per il gestore dei proiettili del giocatore.
 */
final class PlayerProjectileControllerTest {

    private static final double FRAME = 0.016;
    private static final int PROJ_START_Y = 500;
    private static final double OUT_OF_BOUNDS_TIME = 1.0;

    private PlayerProjectileController controller;

    @BeforeEach
    void setUp() {
        controller = new PlayerProjectileController();
    }

    @Test
    void testAddAndRemoveProjectile() {
        final ProjectileImpl p = new ProjectileImpl(new Position(100, 100), 20, 10, 1);

        controller.addProjectile(p);
        assertEquals(1, controller.getProjectileList().size(), "La lista deve contenere 1 proiettile");

        controller.removeProjectile(p);
        assertTrue(controller.getProjectileList().isEmpty(), "La lista deve essere vuota dopo la rimozione");
    }

    @Test
    void testProjectileMovement() {
        final ProjectileImpl p = new ProjectileImpl(new Position(100, PROJ_START_Y), 20, 10, 1);
        controller.addProjectile(p);

        // Simuliamo l'avanzamento del gioco
        controller.update(FRAME);

        // I proiettili del player vanno verso l'alto, quindi la Y deve diminuire (essere < 500)
        assertTrue(controller.getProjectileList().get(0).getPosition().getY() < PROJ_START_Y,
                "Il proiettile deve spostarsi verso l'alto (Y diminuisce)");
    }

    @Test
    void testOutOfBoundsProjectileRemoval() {
        // Creiamo un proiettile vicinissimo al bordo superiore (es. Y = 2)
        final ProjectileImpl p = new ProjectileImpl(new Position(100, 2), 20, 10, 1);
        controller.addProjectile(p);

        // Simuliamo un tempo abbastanza lungo da farlo uscire dallo schermo (Y < 0)
        controller.update(OUT_OF_BOUNDS_TIME);

        assertTrue(controller.getProjectileList().isEmpty(),
                    "I proiettili usciti dallo schermo (Y < 0) devono essere rimossi in automatico");
    }
}
