package it.unibo.spacejava.model.menu;

import java.util.List;
import java.util.Objects;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import it.unibo.spacejava.Skin;
import it.unibo.spacejava.api.MenuObserver;
import it.unibo.spacejava.api.GameManger;

/**
 * Classe che funge da model per la schermata di selezione delle skin,
 * gestendo tutta la logica per la selezione, l'acquisto e il mantenimento dello stato delle skin.
 */
public final class SkinModel {

    //variabili per test (potrebbe cambiare)
    private int selectedIndex;

    // lista dove aggiungere tutte le possibili skin
    private final List<Skin> skins;
    private MenuObserver observer;

    private final GameManger gameManager;

    /**
     * Costruttore per definire le due variabili (punti e l'idice di selezione della skin) a 0,
     * e inzializzo anche la lista delle skin.
     * 
     * @param gameManager il gestore del gioco
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Dependency injection is intended here")
    public SkinModel(final GameManger gameManager) {
        this.skins = SkinFactory.createListOfSkins();
        this.selectedIndex = 0;
        this.gameManager = Objects.requireNonNull(gameManager);
    }

    /**
     * Getter per restituire i punti attuali del giocatore.
     * 
     * @return integer che identifica i punti attuali del giocatore, utilizzabili per sbloccare skins
     */
    public int getPoints() {
        return gameManager.getScore();
    }

    /**
     * Getter per restituire la skin attualmente selezionata dall'utente.
     * 
     * @return un oggetto Skin che rappresenta la skin attalmente selezionata
     */
    public Skin getSelectedSkin() {
        return skins.get(selectedIndex);
    }

    /**
     * Getter per restituire l'idece di selezione.
     * 
     * @return integer che idica l'idice della skin 
     */
    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    /**
     * Getter che serve per sapere il numeor esatto di skin.
     * 
     * @return la lunghezza della lista delle skin
     */
    public int getTotalSkins() {
        return this.skins.size();
    }

    /**
     * Seleziona la skin precedente nella lista.
     */
    public void selectPrevious() {
        selectedIndex = (selectedIndex - 1 + skins.size()) % skins.size();
        this.notifyListener();
    }

    /**
     * Seleziona la skin successiva nella lista.
     */
    public void selectNext() {
        selectedIndex = (selectedIndex + 1) % skins.size();
        this.notifyListener();
    }

    /**
     * Tenta di comprare la skin attualmente selezionata.
     * 
     * @return true se l'acquisto va a buon fine, false altrimenti.
     */
    public boolean buyCurrentSkin() {
        final Skin current = getSelectedSkin();
        if (!current.isUnlock() && this.gameManager.getScore() >= current.getPrice()) {
            this.gameManager.decreaseScore(current.getPrice()); // Scala i punti
            current.unlock();                   // Sblocca la skin
            this.notifyListener();
            return true;
        }
        return false;
    }

    /**
     * Aggiunge un observer che invia una notifica ogni volta che il model subisce una modifica.
     * 
     * @param obs il listener da aggiungere alla lista
     */
    public void setObserver(final MenuObserver obs) {
        observer = Objects.requireNonNull(obs);
    }

    /**
     * Metodo usato per poter notificare al observer che il model a subito un cambiamento.
     */
    private void notifyListener() {
        observer.updateMenuState();
    }
}
