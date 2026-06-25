package it.unibo.spacejava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.spacejava.api.GameManger;
import it.unibo.spacejava.model.menu.ShopImpl;
import it.unibo.spacejava.view.menu.SkinSelectionView;

/**
 * Classe che permette di testare il model dello skin model.
 */
final class SkinModelTest {

    private static final int POINTS_TO_ADD = 5000;
    private ShopImpl model;
    private GameManger fakeGameManager;

    @BeforeEach
    void setUp() {
        //Creiamo un finto gestore per simulare i punti durante il test
        fakeGameManager = new GameManger() {
            private int score;
            @Override
            public void startGame() {
            }

            @Override
            public void addScore(final int points) {
                this.score += points;
            }

            @Override
            public int getScore() {
                return this.score;
            }

            @Override
            public void decreaseScore(final int points) {
                this.score -= points;
            }

            @Override
            public void resetScore() {
                this.score = 0;
            }
        };

        model = new ShopImpl(fakeGameManager);
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

        final boolean result = model.buySelectedSkin();

        assertFalse(result, "L'acquisto deve fallire perché i punti sono 0");
        assertFalse(model.getSelectedSkin().isUnlock(), "La skin deve rimanere bloccata");
    }

    @Test
    void testBuySkinWithSufficientPoints() {
        model.selectNext(); // Passa alla "ship2" (costa 100)
        fakeGameManager.addScore(POINTS_TO_ADD); // Aggiungiamo punti (usando il metodo suggerito)

        final boolean result = model.buySelectedSkin();

        assertTrue(result, "L'acquisto deve avere successo");
        assertTrue(model.getSelectedSkin().isUnlock(), "La skin deve risultare sbloccata dopo l'acquisto");
        assertEquals(
            POINTS_TO_ADD - model.getSelectedSkin().getPrice(),
            fakeGameManager.getScore(),
            "I punti devono essere stati scalati correttamente (5000 - 1000 = 4000)");
    }
}
