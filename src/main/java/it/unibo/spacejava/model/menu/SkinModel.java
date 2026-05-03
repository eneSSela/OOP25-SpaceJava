package it.unibo.spacejava.model.menu;

import java.util.List;

import it.unibo.spacejava.Skin;

public class SkinModel {
    //variabili per test (potrebbe cambiare)
    private int playerPoints = 500;
    private int selectedIndex = 0;

    // lista dove aggiungere tutte le possibili skin
    private final List<Skin> skins = List.of(
        new Skin("Default","/skins/spaceShips_001.png", 0, true), // Skin di default, già sbloccata
       new Skin("ship2","/skins/spaceShips_002.png", 100, false),
       new Skin("ship3","/skins/spaceShips_003.png", 300, false)
    );

    public int getPlayerPoints() { return playerPoints; }
    public Skin getSelectedSkin() { return skins.get(selectedIndex); }

    public void selectPrevious() {
        selectedIndex = (selectedIndex - 1 + skins.size()) % skins.size();
    }
    public void selectNext() {
        selectedIndex = (selectedIndex + 1) % skins.size();
    }

    /**
     * Tenta di comprare la skin attualmente selezionata.
     * @return true se l'acquisto va a buon fine, false altrimenti.
     */
    public boolean buyCurrentSkin() {
        Skin current = getSelectedSkin();
        if (!current.isUnlock() && playerPoints >= current.getPrice()) {
            playerPoints -= current.getPrice(); // Scala i punti
            current.unlock();                   // Sblocca la skin
            return true;
        }
        return false;
    }

}
