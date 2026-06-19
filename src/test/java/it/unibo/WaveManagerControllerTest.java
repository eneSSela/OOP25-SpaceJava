package it.unibo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.controller.WaveManagerController;

/**
 * Classe di test per WaveMangareController.
 */
final class WaveManagerControllerTest {
    private static final int SCREEN_WIDTH = 768;
    private static final double FRAME = 0.016;
    private static final int FATAL_DAMAGE = 999;

    private WaveManagerController waveManager;

    @BeforeEach
    void setUp() {
        waveManager = new WaveManagerController(SCREEN_WIDTH, null);
    }

    //Verifico che inizialmente ci siano già nemici caricati.
    @Test
    void testStartingState() {
        assertFalse(waveManager.getEnemies().isEmpty());
    }

    //Controllo che la orda si muova.
    @Test
    void testWaveMovement() {
        final double startingX = waveManager.getEnemies().get(0).getPosition().getX();
        waveManager.update(FRAME * 10);
        assertNotEquals(startingX, waveManager.getEnemies().get(0).getPosition().getX());
    }

    //Testo che i nemici morti vengano rimossi.
    @Test
    void testDeadEnemyRemoval() {
        final int startingEnemyNumber = waveManager.getEnemies().size();
        waveManager.getEnemies().get(0).takeDamage(FATAL_DAMAGE);
        waveManager.update(FRAME);
        assertEquals(startingEnemyNumber - 1, waveManager.getEnemies().size());
    }

    //Testo l'avanzamento delle ondate.
    @Test
    void testNewWave() {
        //Uccido tutti i nemici
        for (final Enemy e : waveManager.getEnemies()) {
            e.takeDamage(FATAL_DAMAGE);
        }
        waveManager.update(FRAME);
        assertNotNull(waveManager.getEnemies());
    }
}
