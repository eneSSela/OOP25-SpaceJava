package it.unibo.spacejava.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.spacejava.model.menu.GameOverModel;

/**
 * Test class to verify the logic part of the GameOver menu.
 */
final class GameOverTest {

    private GameOverModel model;

    @BeforeEach
    void setUp() {
        model = new GameOverModel();
    }

    @Test
    void testInitialState() {
        assertEquals(0, model.getSelectedIndex(), "Il menu deve partire dalla prima opzione");
        assertTrue(model.isBlinkOn(), "Il blink deve essere attivo di default");
        assertEquals(0, model.getFinalScore(), "Il punteggio iniziale deve essere 0");
        assertEquals("Gioca di nuovo", model.getSelectedOption());
    }

    @Test
    void testCircularNavigation() {
        final List<String> options = model.getOptions();
        final int totalOptions = options.size();

        model.selectNext();
        assertEquals(1, model.getSelectedIndex(), "Dovrebbe essersi spostato alla seconda opzione");

        model.selectNext();
        assertEquals(0, model.getSelectedIndex(), "Dovrebbe essersi tornato alla prima opzione");

        model.selectPrevious();
        assertEquals(totalOptions - 1, model.getSelectedIndex(), "Dovrebbe essere andato all'ultima opzione");
    }

    @Test
    void testScoreManagament() {
        final int testScore = 15_500;
        model.setFinalScore(testScore);
        assertEquals(testScore, model.getFinalScore(), "Il punteggio finale salvato non coincide");
    }

    @Test
    void testBlinkToggle() {
        model.setBlinkOn(false);
        assertFalse(model.isBlinkOn(), "Il blink dovrebbe essere disattivato");

        model.setBlinkOn(true);
        assertTrue(model.isBlinkOn(), "Il blink dovrebbe essere attivato");
    }
}
