package it.unibo.spacejava.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.spacejava.Skin;
import it.unibo.spacejava.model.menu.SkinFactory;

/**
 * Test class to apply the PowerUp into the player.
 */
final class PowerUpTest {

    private PlayerShip player;

    @BeforeEach
    void setUp() {
        final Skin baseSkin = SkinFactory.createListOfSkins().get(0);
        player = new PlayerShip(0, 0, baseSkin, new ScoreImpl());
    }

    @Test
    void testShieldPowerUp() {
        player.addShieldCharges(3);
    }

    @Test
    void testHealthPowerUp() {
        final int maxHealth = player.getHealth();
        player.takeDamage(1);
        assertEquals(maxHealth - 1, player.getHealth(), "Il giocatore non ha preso danno");
    }
}
