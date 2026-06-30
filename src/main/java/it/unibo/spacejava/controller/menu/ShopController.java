package it.unibo.spacejava.controller.menu;

import java.awt.event.KeyEvent;
import java.util.Objects;

import it.unibo.spacejava.KeyHandler;
import it.unibo.spacejava.model.PlayerShip;
import it.unibo.spacejava.model.menu.ShopImpl;
import it.unibo.spacejava.Skin;
import it.unibo.spacejava.api.Command;
import it.unibo.spacejava.api.Score;

/**
 * Classe che funge come controller per la shermnata della selezione delle skins, 
 * gestendo l'input del utente interagendo con il model.
 */
public final class ShopController extends KeyHandler {

    private final ShopImpl model;
    private final PlayerShip player;
    private final Command onBack;

    /**
     * Costruttore della classe SkinController, che prende in input il model da utilizzare ,
     * e una callback da eseguire quando l'utente decide di tornare indietro.
     * 
     * @param model è il model che contiene tutta la logica per la selezione delle skin.
     * @param player è il giocatore che possiede le skin e il punteggio.
     * @param onBack l'azione che viene eseguita quando l'utente decide di tornare indietro.
     */
    public ShopController(final ShopImpl model, final PlayerShip player, final Command onBack) {
        this.model = Objects.requireNonNull(model, "Il model non poò essere nullo");
        this.player = Objects.requireNonNull(player, "Il player non può essere nullo");
        this.onBack = onBack;
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        super.keyPressed(e);
        final Skin currentSkin = model.getSelectedSkin();
        final Score score = player.getScore();
        if (super.isLeftPressed()) {
            model.selectPrevious();
        } else if (super.isRightPressed()) {
            model.selectNext();
        } else if (super.isSpacePressed()) {
            final boolean bought = model.buySelectedSkin(score);
            if (bought) {
                currentSkin.unlock();
                player.setSkin(currentSkin);
            }
        } else if (super.isEnterPressed() && currentSkin.isUnlock()) {
            onBack.execute();
        }
    }
}
