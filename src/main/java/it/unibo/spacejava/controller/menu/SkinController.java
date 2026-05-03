package it.unibo.spacejava.controller.menu;

import it.unibo.spacejava.KeyHandler;
import it.unibo.spacejava.model.menu.SkinModel;
import it.unibo.spacejava.Skin;

public class SkinController extends KeyHandler {

    private final SkinModel model;
    private final Runnable onBack;

    public SkinController(SkinModel model, Runnable onBack) {
        this.model = model;
        this.onBack = onBack;
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        super.keyPressed(e);
        Skin  currentSkin = model.getSelectedSkin();

        if (super.leftPressed) {
            model.selectPrevious();
        } else if (super.rightPressed) {
            model.selectNext();
        } else if (super.spacePressed) {
            if(model.buyCurrentSkin()) {
                System.out.println("Hai comprato la skin: " + currentSkin.getName());
            }else if (!currentSkin.isUnlock()) {
                System.out.println("Non hai abbastanza punti per comprare questa skin!");
            }
        } else if (super.enterPressed) {
            if (currentSkin.isUnlock()) {
                onBack.run();
            }
        }
    }
}
