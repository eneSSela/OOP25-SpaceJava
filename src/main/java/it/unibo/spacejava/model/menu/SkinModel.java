package it.unibo.spacejava.model.menu;

import java.util.List;

import it.unibo.spacejava.Skin;

/**
 * Classe che funge da model per la schermata di selezione delle skin,
 * gestendo tutta la logica per la selezione, l'acquisto e il mantenimento dello stato delle skin.
 */
public final class SkinModel {

    //variabili per test (potrebbe cambiare)
    private int playerPoints;
    private int selectedIndex;
    // lista dove aggiungere tutte le possibili skin
    private final List<Skin> skins = List.of(
        new Skin("Default", "/skins/spaceShips_001.png", 0, true), // Skin di default, già sbloccata
        new Skin("ship2", "/skins/spaceShips_002.png", 100, false),
        new Skin("ship3", "/skins/spaceShips_003.png", 300, false)
    );

    /**
     * Costruttore per definire le due variabili (punti e l'idice di selezione della skin) a 0,
     * e inzializzo anche la lista delle skin.
     */
    public SkinModel() {
        this.playerPoints = 0;
        this.selectedIndex = 0;
    }

    /**
     * Getter per restituire i punti attuali del giocatore.
     * 
     * @return integer che identifica i punti attuali del giocatore, utilizzabili per sbloccare skins
     */
    public int getPoints() {
        return playerPoints;
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
     * Seleziona la skin precedente nella lista.
     */
    public void selectPrevious() {
        selectedIndex = (selectedIndex - 1 + skins.size()) % skins.size();
    }

    /**
     * Seleziona la skin successiva nella lista.
     */
    public void selectNext() {
        selectedIndex = (selectedIndex + 1) % skins.size();
    }

    /**
     * Tenta di comprare la skin attualmente selezionata.
     * 
     * @return true se l'acquisto va a buon fine, false altrimenti.
     */
    public boolean buyCurrentSkin() {
        final Skin current = getSelectedSkin();
        if (!current.isUnlock() && playerPoints >= current.getPrice()) {
            playerPoints -= current.getPrice(); // Scala i punti
            current.unlock();                   // Sblocca la skin
            return true;
        }
        return false;
    }

}
