package it.unibo.spacejava.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

import it.unibo.spacejava.model.sound.SoundManagerImpl;

/**
 * Classe che test il soundmager.
 */
final class SoundManagerTest {

@Test
    void testSingletonInstance() {
        final SoundManagerImpl instance1 = SoundManagerImpl.getInstance();
        final SoundManagerImpl instance2 = SoundManagerImpl.getInstance();

        assertNotNull(instance1, "L'istanza non deve essere nulla");
        assertSame(instance1, instance2, "Il pattern Singleton deve restituire sempre la stessa identica istanza in memoria");
    }
}
