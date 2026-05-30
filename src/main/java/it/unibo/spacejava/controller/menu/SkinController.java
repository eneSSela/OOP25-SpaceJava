package it.unibo.spacejava.controller.menu;

import java.awt.event.KeyEvent;
import java.util.Objects;

import it.unibo.spacejava.KeyHandler;
import it.unibo.spacejava.model.menu.SkinModel;
import it.unibo.spacejava.Skin;
import it.unibo.spacejava.api.Command;

/**
 * Classe che funge come controller per la shermnata della selezione delle skins, 
 * gestendo l'input del utente interagendo con il model.
 */
public final class SkinController extends KeyHandler {

    private final SkinModel model;
    private final Command onBack;

    /**
     * Costruttore della classe SkinController, che prende in input il model da utilizzare ,
     * e una callback da eseguire quando l'utente decide di tornare indietro.
     * 
     * @param model è il model che contiene tutta la logica per la selezione delle skin.
     * @param onBack l'azione che viene eseguita quando l'utente decide di tornare indietro.
     */
    public SkinController(final SkinModel model, final Command onBack) {
        this.model = Objects.requireNonNull(model, "Il model non poò essere nullo");
        this.onBack = onBack;
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
            onBack.execute();
        }
    }
}
