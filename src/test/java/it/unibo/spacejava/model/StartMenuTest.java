package it.unibo.spacejava.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.spacejava.api.MenuObserver;
import it.unibo.spacejava.model.menu.StartMenuModel;
import it.unibo.spacejava.view.menu.StartMenuView;

/**
 * Classe di test sul model del star menu.
 */
final class StartMenuTest {

    private StartMenuModel model;

    @BeforeEach
    void setUp() {
        this.model = new StartMenuModel();
        this.model.setObserver(new StartMenuView(model));
    }

    @Test
    void testInitialState() {
        assertEquals(0, model.getSelectedIndex());
        assertTrue(model.getOptions().stream().anyMatch(option -> option.equals(model.getSelectedOption())));
        assertTrue(model.isBlinkOn());
    }

    @Test
    void testCircularNavigationTheMenu() {
        // Seleziona la opzione successiva (indice 1 -> Seleziona Skin)
        model.selectNext();
        assertEquals(1, model.getSelectedIndex());
        assertTrue(model.getOptions().stream().anyMatch(option -> option.equals(model.getSelectedOption())));

        //Seleziona la opzione successiva (indice 2 -> Esci)
        model.selectNext();
        assertEquals(2, model.getSelectedIndex());
        assertTrue(model.getOptions().stream().anyMatch(option -> option.equals(model.getSelectedOption())));

        // Ora testo la nvigazione Cicolare, se vado avanti dall'ultimo devo tornare alla prima opzione
        model.selectNext();
        assertEquals(0, model.getSelectedIndex());
        assertTrue(model.getOptions().stream().anyMatch(option -> option.equals(model.getSelectedOption())));

        // Ora testo la nvigazione Cicolare inversa , se vado indietro dal primo devo tornare al ultima opzione
        model.selectPrevious();
        assertEquals(2, model.getSelectedIndex());
        assertTrue(model.getOptions().stream().anyMatch(option -> option.equals(model.getSelectedOption())));
    }

    @Test
    void testObserverNotification() {
        //Creo un observer finto
        final boolean[] observerCalled = {false};

        final MenuObserver mockObserver = () -> observerCalled[0] = true;
        model.setObserver(mockObserver);

        //provoco un azzione per far scatter l'observer
        model.selectNext();
        assertTrue(observerCalled[0], "L'observer doveva essere notificato del cambiamento");
    }
}
