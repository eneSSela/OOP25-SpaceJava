package it.unibo.spacejava.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.spacejava.Skin;

/**
 * Test per verificare la logica di base e di movimento della navicella del giocatore.
 */
final class PlayerShipTest {

    private static final int START_X = 100;
    private static final int START_Y = 500;
    private static final double MOVE_TIME = 5.0;
    private static final double SCREEN_WIDTH = 800.0;

    private PlayerShip player;

    @BeforeEach
    void setUp() {
        //Creiamo una Skin fittizia solo per il test
        final Skin dummySkin = new Skin("TestSkin", "path/dummy.png", 0, true,
                                        1.0, false);
        player = new PlayerShip(START_X, START_Y, dummySkin, new ScoreImpl());
    }

    @Test
    void testInitialState() {
        assertEquals(3, player.getHealth(), "Il giocatore deve nascere con 3 vite");
        assertFalse(player.isDead(), "Il giocatore non deve essere morto all'inizio");
        assertEquals(START_X, player.getPosition().getX(), "La posizione X iniziale è errata");
        assertEquals(START_Y, player.getPosition().getY(), "La posizione Y iniziale è errata");
    }

    @Test
    void testTakeDamageAndDeath() {
        player.takeDamage(1);
        assertEquals(2, player.getHealth(), "La vita deve diminuire a 2");
        assertFalse(player.isDead());

        player.takeDamage(2); //Danno fatale
        assertTrue(player.isDead(), "Il giocatore deve risultare morto con 0 vita");
    }

    @Test
    void testMovementAndScreenLimits() {
        //MUOVIAMO A SINISTRA contro il limite 0
        //Con speed = 300 e delta = 1 secondo (per comodità), si sposterebbe di 300 pixel
        //Ma partendo da 100 e avendo il limite a 0, si deve fermare a 0!
        player.moveLeft(1.0, 0);
        assertEquals(0, player.getPosition().getX(), "La navicella deve sbattere contro il limite sinistro (0)");

        //MUOVIAMO A DESTRA contro il limite 800
        //Da 0 + 300 * 5 secondi = 1500, dovrebbe andare fuori schermo, ma si devefermare al limite (800 - larghezza)
        player.moveRight(MOVE_TIME, SCREEN_WIDTH);

        final int expectedRightLimit = (int) (SCREEN_WIDTH - (player.getWidth() / 2));
        assertEquals(expectedRightLimit, player.getPosition().getX(), "La navicella deve sbattere contro il limite destro");
    }
}
