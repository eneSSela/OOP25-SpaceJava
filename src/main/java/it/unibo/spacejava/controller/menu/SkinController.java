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

    public Skin getPlayerSelectedSkin() {
        return model.getSelectedSkin();
    }

    public int getPlayerPoints() {
        return model.getPoints();
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        super.keyPressed(e);
        Skin  currentSkin = model.getSelectedSkin();

        if (super.isLeftPressed()) {
            model.selectPrevious();
        } else if (super.isRightPressed()) {
            model.selectNext();
        } else if (super.isSpacePressed()) {
            if(model.buyCurrentSkin()) {
                System.out.println("Hai comprato la skin: " + currentSkin.getName());
            }else if (!currentSkin.isUnlock()) {
                System.out.println("Non hai abbastanza punti per comprare questa skin!");
            }
        } else if (super.isEnterPressed()) {
            if (currentSkin.isUnlock()) {
                onBack.run();
            }
        }
    }
}
