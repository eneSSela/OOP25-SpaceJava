package it.unibo.spacejava.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.spacejava.Skin;
import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.api.Score;
import it.unibo.spacejava.model.PlayerShip;
import it.unibo.spacejava.model.ScoreImpl;
import it.unibo.spacejava.model.sound.api.SoundManager;

/**
 * Classe di test per WaveMangareController.
 */
final class WaveManagerControllerTest {
    private static final int SCREEN_WIDTH = 768;
    private static final int SCREEN_HEIGTH = 576;
    private static final double FRAME = 0.016;
    private static final int FATAL_DAMAGE = 999;

    private WaveManagerController waveManager;

    @BeforeEach
    void setUp() {
        // Creiamo istanze finte per far funzionare il costruttore in isolamento

        final SoundManager fakeSound = new SoundManager() {
            @Override 
            public void playSound(final String path) {
            }

            @Override 
            public void playBackgroundMusic(final String path) {
            }

            @Override 
            public void stopBackgroundMusic() {
            }
        };

        final PlayerProjectileController projCtrl = new PlayerProjectileController();
        final Score fakePlayerScore = new ScoreImpl();
        final EnemyProjectileController enemyProjCtrl = new EnemyProjectileController(SCREEN_HEIGTH);
        final Skin fakeSkin = new Skin("Fake", "path", 0, true, 1.0, false);
        final PlayerShip fakeShip = new PlayerShip(0, 0, fakeSkin, fakePlayerScore);
        waveManager = new WaveManagerController(SCREEN_WIDTH, fakeSound, fakeShip, projCtrl, enemyProjCtrl);
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
