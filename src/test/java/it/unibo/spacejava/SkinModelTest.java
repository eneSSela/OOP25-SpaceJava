package it.unibo.spacejava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.spacejava.model.PlayerShip;
import it.unibo.spacejava.model.menu.SkinModel;
import it.unibo.spacejava.view.menu.SkinSelectionView;

/**
 * Classe che permette di testare il model dello skin model.
 */
final class SkinModelTest {

    private static final int POINTS_TO_ADD = 150;
    private SkinModel model;

    @BeforeEach
    void setUp() {
        PlayerShip.resetPoints();
        model = new SkinModel();
        this.model.setObserver(new SkinSelectionView(model));
    }

    @Test
    void testInitialSkinIsUnlocked() {
        assertEquals("Default", model.getSelectedSkin().getName());
        assertTrue(model.getSelectedSkin().isUnlock(), "La skin di default deve essere sbloccata");
        assertEquals(0, model.getPoints());
    }

    @Test
    void testBuySkinWithInsufficientPoints() {
        model.selectNext(); // Passa alla "ship2" (costa 100)
        assertFalse(model.getSelectedSkin().isUnlock(), "La ship2 dovrebbe essere bloccata all'inizio");

        final boolean result = model.buyCurrentSkin();

        assertFalse(result, "L'acquisto deve fallire perché i punti sono 0");
        assertFalse(model.getSelectedSkin().isUnlock(), "La skin deve rimanere bloccata");
    }

    @Test
    void testBuySkinWithSufficientPoints() {
        model.selectNext(); // Passa alla "ship2" (costa 100)
        PlayerShip.addPoints(POINTS_TO_ADD); // Aggiungiamo punti (usando il metodo suggerito)

        final boolean result = model.buyCurrentSkin();

        assertTrue(result, "L'acquisto deve avere successo");
        assertTrue(model.getSelectedSkin().isUnlock(), "La skin deve risultare sbloccata dopo l'acquisto");
        assertEquals(
            POINTS_TO_ADD - model.getSelectedSkin().getPrice(),
            model.getPoints(),
            "I punti devono essere stati scalati correttamente (150 - 100 = 50)");
    }
}
