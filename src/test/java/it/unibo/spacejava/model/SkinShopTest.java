package it.unibo.spacejava.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.spacejava.model.menu.ShopImpl;
import it.unibo.spacejava.model.menu.SkinFactory;
import it.unibo.spacejava.view.menu.ShopView;

/**
 * Classe che permette di testare il model dello skin model.
 */
final class SkinShopTest {

    private static final int POINTS_TO_ADD = 15_000;
    private ShopImpl model;
    private PlayerShip player;
    private Path temporarySaveFile;

    @BeforeEach
    void setUp() throws IOException {
        temporarySaveFile = Files.createTempFile("spacejava-save", ".properties");
        System.setProperty("spacejava.saveFile", temporarySaveFile.toString());
        Files.deleteIfExists(temporarySaveFile);

        model = new ShopImpl();
        player = new PlayerShip(0, 0, SkinFactory.createListOfSkins().get(0),
                                new ScoreImpl());
        this.model.setObserver(new ShopView(model, player));
    }

    @AfterEach
    void tearDown() throws IOException {
        System.clearProperty("spacejava.saveFile");
        Files.deleteIfExists(temporarySaveFile);
    }

    @Test
    void testInitialSkinIsUnlocked() {
        assertEquals("Default", model.getSelectedSkin().getName());
        assertTrue(model.getSelectedSkin().isUnlock(), "La skin di default deve essere sbloccata");
        assertEquals(0, player.getScore().getTotal(), "Il punteggio iniziale del giocatore deve essere 0");
    }

    @Test
    void testBuySkinWithInsufficientPoints() {
        model.selectNext(); // Passa alla "ship2" (costa 10_000)
        assertFalse(model.getSelectedSkin().isUnlock(), "La ship2 dovrebbe essere bloccata all'inizio");

        final boolean result = model.buySelectedSkin(player.getScore());

        assertFalse(result, "L'acquisto deve fallire perché i punti sono 0");
        assertFalse(model.getSelectedSkin().isUnlock(), "La skin deve rimanere bloccata");
    }

    @Test
    void testBuySkinWithSufficientPoints() {
        model.selectNext(); // Passa alla "ship2" (costa 100)
        player.getScore().addPoints(POINTS_TO_ADD); // Aggiunge 5000 punti al giocatore
        final boolean result = model.buySelectedSkin(player.getScore());

        assertTrue(result, "L'acquisto deve avere successo");
        assertTrue(model.getSelectedSkin().isUnlock(), "La skin deve risultare sbloccata dopo l'acquisto");
        assertEquals(
            POINTS_TO_ADD - model.getSelectedSkin().getPrice(),
            player.getScore().getTotal(),
            "I punti devono essere stati scalati correttamente (5000 - 1000 = 4000)");
    }
}
