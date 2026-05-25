package it.unibo.spacejava.api;

/**
 * Interfaccia per il pattern Observer. 
 * Viene implementata da chiunque voglia essere notificato dei cambiamenti nel model.
 */
@FunctionalInterface
public interface MenuObserver {

    /**
     * Metodo chiamato dal Model quando il suo stato interno cambia 
     * (es. cambio selezione, lampeggio).
     */
    void updateMenuState();
}
