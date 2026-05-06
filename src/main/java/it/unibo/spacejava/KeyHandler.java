package it.unibo.spacejava;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Keylistener ustao per i vari controll del gioco.
 */
public class KeyHandler implements KeyListener {
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean spacePressed;
    private boolean enterPressed;

    /**
     * Gestisce l'evento di pressione di un tasto.
     * 
     * @param e l'evento generato dalla tastiera,
     * @implSpec Le sottoclassi che fanno l'override di questo metodo dovrebbero,
     *     chiamare {@code super.keyPressed(e)} per garantire che i tasti di base, 
     *     (movimento e azione) continuino a essere registrati correttamente.
     */
    @Override
    public void keyPressed(final KeyEvent e) {
        final int code = e.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }

    /**
     * Gestisce l'evento di rilascio di un tasto.
     * 
     * @param e l'evento generato dalla tastiera
     * @implSpec Le sottoclassi che fanno l'override di questo metodo dovrebbero
     *     chiamare {@code super.keyReleased(e)} per garantire che i tasti di base 
     *     (movimento e azione) continuino a essere registrati correttamente.
     */
    @Override
    public void keyReleased(final KeyEvent e) {
        final int code = e.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
    }

    @Override
    public void keyTyped(final KeyEvent e) { }

    /**
     * Verifica se il tasto Su (o W) è attualmente premuto.
     *
     * @return true se il tasto è premuto, false altrimenti
     */
    public boolean isUpPressed() {
        return upPressed;
    }

    /**
     * Verifica se il tasto Giù (o S) è attualmente premuto.
     *
     * @return true se il tasto è premuto, false altrimenti
     */
    public boolean isDownPressed() {
        return downPressed;
    }

    /**
     * Verifica se il tasto Sinistra (o A) è attualmente premuto.
     *
     * @return true se il tasto è premuto, false altrimenti
     */
    public boolean isLeftPressed() {
        return leftPressed;
    }

    /**
     * Verifica se il tasto Destra (o D) è attualmente premuto.
     *
     * @return true se il tasto è premuto, false altrimenti
     */
    public boolean isRightPressed() {
        return rightPressed;
    }

    /**
     * Verifica se il tasto Spazio è attualmente premuto.
     *
     * @return true se il tasto è premuto, false altrimenti
     */
    public boolean isSpacePressed() {
        return spacePressed;
    }

    /**
     * Verifica se il tasto Invio è attualmente premuto.
     *
     * @return true se il tasto è premuto, false altrimenti
     */
    public boolean isEnterPressed() {
        return enterPressed;
    }
}
