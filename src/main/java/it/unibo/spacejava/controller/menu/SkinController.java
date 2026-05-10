package it.unibo.spacejava.controller.menu;

import java.awt.event.KeyEvent;

import it.unibo.spacejava.KeyHandler;
import it.unibo.spacejava.model.menu.SkinModel;
import it.unibo.spacejava.Skin;

/**
 * Classe che funge come controller per la shermnata della selezione delle skins, 
 * gestendo l'input del utente interagendo con il model.
 */
public final class SkinController extends KeyHandler {

    private final SkinModel model;
    private final Runnable onBack;

    /**
     * Costruttore della classe SkinController, che prende in input il model da utilizzare ,
     * e una callback da eseguire quando l'utente decide di tornare indietro.
     * 
     * @param model è il model che contiene tutta la logica per la selezione delle skin.
     * @param onBack l'azione che viene eseguita quando l'utente decide di tornare indietro.
     */
    public SkinController(final SkinModel model, final Runnable onBack) {
        this.model = model;
        this.onBack = onBack;
    }

    /**
     * Getter per ottenere la skin attualmente selezionata dall'utente.
     * 
     * @return la skin che comparisce attaumente nella schermata di selezione,
     *         che può essere acquistata o selezionata per il gioco.
     */
    public Skin getPlayerSelectedSkin() {
        return model.getSelectedSkin();
    }

    /**
     * Getter per richiere al model il numero di punti attauli del giocatre, 
     * utilizati dalla view per poterli disegnre a schermo.
     * 
     * @return intero che indica i punti attauli del giocatore
     */
    public int getPlayerPoints() {
        return model.getPoints();
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        super.keyPressed(e);
        final Skin currentSkin = model.getSelectedSkin();

        if (super.isLeftPressed()) {
            model.selectPrevious();
        } else if (super.isRightPressed()) {
            model.selectNext();
        } else if (super.isSpacePressed()) {
            if (model.buyCurrentSkin()) {
                System.out.println("Hai comprato la skin: " + currentSkin.getName()); //NOPMD
            } else if (!currentSkin.isUnlock()) {
                System.out.println("Non hai abbastanza punti per comprare questa skin!"); //NOPMD
            }
        } else if (super.isEnterPressed() && currentSkin.isUnlock()) {
            onBack.run();
        }
    }
}
