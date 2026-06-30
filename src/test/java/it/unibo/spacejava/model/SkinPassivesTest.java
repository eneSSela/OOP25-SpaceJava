package it.unibo.spacejava.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.spacejava.Skin;
import it.unibo.spacejava.model.menu.SkinFactory;

/**
 * Test class to verify the correct assignment of the passive skills to Skins.
 */
final class SkinPassivesTest {

    private static final double DELTA = 0.001;
    private static final double MULTIPLIER_DEFAULT = 1.0;
    private static final double MULTIPLIER_SHIP2 = 1.5;
    private static final double MULTIPLIER_SHIP3 = 1.0;

    @Test
    void testFactoryCreatesCorrectPassives() {
        final List<Skin> skins = SkinFactory.createListOfSkins();
        assertEquals(3, skins.size(), "La factory deve generare esattamente 3 skin.");

        final Skin defaultSkin = skins.get(0);
        assertEquals("Default", defaultSkin.getName());
        assertEquals(MULTIPLIER_DEFAULT, defaultSkin.getScoreMultiplier(), DELTA,
                    "La skin di default non deve avere moltiplicatori");
        assertFalse(defaultSkin.hasBossShield(), "La skin di default non deve avere lo scudo basso.");

        final Skin ship2 = skins.get(1);
        assertEquals("ship2", ship2.getName());
        assertEquals(MULTIPLIER_SHIP2, ship2.getScoreMultiplier(), DELTA,
                    "La ship2 deve avere un moltiplicatore di 1.5");
        assertFalse(ship2.hasBossShield(), "La ship2 non deve avere lo scudo boss.");

        final Skin ship3 = skins.get(2);
        assertEquals("ship3", ship3.getName());
        assertEquals(MULTIPLIER_SHIP3, ship3.getScoreMultiplier(), DELTA,
                    "La ship3 non deve avere moltiplicatori");
        assertFalse(ship2.hasBossShield(), "La ship3  deve avere lo scudo boss.");
    }
}
