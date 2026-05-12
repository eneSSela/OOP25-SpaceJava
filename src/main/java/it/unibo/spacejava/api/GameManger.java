package it.unibo.spacejava.api;

/**
 * Questa è l'interfaccia che definisce il controller del gioco, per unire tutti i componenti. Come ad esempio il menu, 
 * la gestione del suono, la gestione delle onde di nemici e così via.
 */
@FunctionalInterface
public interface GameManger {

    /**
     * Questo è il metodo dove inizializamo tutti i componenti necasiri al gioco, come la view il controller ed il model del menu.
     */
    void startGame();

}
