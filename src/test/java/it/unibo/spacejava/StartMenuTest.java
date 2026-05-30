package it.unibo.spacejava;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.spacejava.model.PlayerShip;
import it.unibo.spacejava.model.menu.StartMenuModel;

public class StartMenuTest {

    private StartMenuModel model;

    @BeforeEach
    void setUp() {
        this.model = new StartMenuModel();
    }

    @Test
    void testInitialState() {

    }
}
