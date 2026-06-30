package it.unibo.spacejava.model.menu;

import java.util.List;

import it.unibo.spacejava.Skin;

/**
 * Factory class dedicata alla creazione e fornitura 
 * delle skin disponibili all'interno del gioco.
 */
public final class SkinFactory {

    private static final int DEFAULT_SKIN_PRICE = 0;
    private static final int SHIP2_SKIN_PRICE = 1000;
    private static final int SHIP3_SKIN_PRICE = 100_000;

    private static final double DEFAULT_MULTIPLIER = 1.0;
    private static final double SHIP2_MULTIPLIER = 1.5;
    private static final double SHIP3_MULTIPLIER = 1.0;

    private SkinFactory() {

    }

    /**
     * Restituisce la lista completa di tutte le skin del gioco.
     * 
     * @return una lista immutabile di oggetti Skin preconfigurati.
     */
    public static List<Skin> createListOfSkins() {
        return List.of(
            new Skin("Default", "/skins/spaceShips_001.png", DEFAULT_SKIN_PRICE, true, DEFAULT_MULTIPLIER, false),
            new Skin("ship2", "/skins/spaceShips_002.png", SHIP2_SKIN_PRICE, false, SHIP2_MULTIPLIER, false), 
            new Skin("ship3", "/skins/spaceShips_003.png", SHIP3_SKIN_PRICE, false, SHIP3_MULTIPLIER, true)
        ); 
    }
}
