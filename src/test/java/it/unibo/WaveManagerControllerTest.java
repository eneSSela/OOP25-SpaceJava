package it.unibo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.api.GameManger;
import it.unibo.spacejava.controller.EnemyProjectileController;
import it.unibo.spacejava.controller.PlayerProjectileController;
import it.unibo.spacejava.controller.WaveManagerController;
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
        final GameManger fakeGameManager = new GameManger() {
            @Override 
            public void startGame() {
            }

            @Override 
            public void addScore(final int points) {
            }

            @Override 
            public int getScore() {
                return 0;
            }

            @Override 
            public void decreaseScore(final int points) {
            }

            @Override 
            public void resetScore() {
            }
        };

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
        final EnemyProjectileController eProjCrtl = new EnemyProjectileController(SCREEN_HEIGTH);

        waveManager = new WaveManagerController(SCREEN_WIDTH, fakeSound, fakeGameManager, projCtrl, eProjCrtl);
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
