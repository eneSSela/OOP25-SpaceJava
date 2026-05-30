package it.unibo.spacejava.api;

/**
 * Interfaccia funzionale per il Command pattern.
 * Permette di rappresentare un'azione da eseguire in risposta ad un input di un utente.
 */
@FunctionalInterface
public interface Command {

    /**
     * Esegue l'azione associata al comando.
     */
    void execute();
}
