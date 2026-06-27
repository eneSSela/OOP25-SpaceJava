package it.unibo.spacejava.model.menu;

import java.util.List;
import java.util.Objects;

import it.unibo.spacejava.Skin;
import it.unibo.spacejava.api.MenuObserver;
import it.unibo.spacejava.api.Score;
import it.unibo.spacejava.api.Shop;

/**
 * Classe che funge da model per la schermata di selezione delle skin,
 * gestendo tutta la logica per la selezione, l'acquisto e il mantenimento dello stato delle skin.
 */
public final class ShopImpl implements Shop {

    //variabili per test (potrebbe cambiare)
    private int selectedIndex;

    // lista dove aggiungere tutte le possibili skin
    private final List<Skin> skins;
    private MenuObserver observer;

    /**
     * Costruttore per definire le due variabili (punti e l'idice di selezione della skin) a 0,
     * e inzializzo anche la lista delle skin.
     * 
     */
    public ShopImpl() {
        this.skins = SkinFactory.createListOfSkins();
        this.selectedIndex = 0;
    }

    /**
     * Getter per restituire la skin attualmente selezionata dall'utente.
     * 
     * @return un oggetto Skin che rappresenta la skin attalmente selezionata
     */
    public Skin getSelectedSkin() {
        return this.skins.get(selectedIndex);
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

    @Override
    public boolean buySelectedSkin(final Score score) {
        final Skin current = getSelectedSkin();
        if (!current.isUnlock() && score.getTotal() >= current.getPrice()) {
            score.consumePoints(current.getPrice()); // Scala i punti
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
