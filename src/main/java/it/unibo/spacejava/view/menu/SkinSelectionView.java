package it.unibo.spacejava.view.menu;

import java.awt.*;
import javax.swing.JPanel;

import it.unibo.spacejava.Skin;
import it.unibo.spacejava.Utils;
import it.unibo.spacejava.controller.menu.SkinController;

public class SkinSelectionView extends JPanel {

   // --- COSTANTI DI DIMENSIONAMENTO ---
    private static final int SHIP_SIZE = 64;
    private static final int SHIP_OFFSET = SHIP_SIZE / 2;
    
    // --- COSTANTI DI MARGINE E SPAZIATURA ---
    private static final int MARGIN_TOP = 50;
    private static final int MARGIN_BOTTOM = 50;
    private static final int POINTS_MARGIN_TOP = 30;
    private static final int POINTS_PADDING_RIGHT = 20; // Distanza dal bordo destro
    private static final int LINE_SPACING = 20;         // Spazio tra due righe di testo

    // --- COSTANTI CROMATICHE ---
    private static final Color COLOR_POINTS = Color.YELLOW;
    private static final Color COLOR_TITLE = Color.WHITE;
    private static final Color COLOR_UNLOCKED = Color.GREEN;
    private static final Color COLOR_LOCKED = Color.RED;
    private static final Color COLOR_CAN_BUY = Color.CYAN;
    private static final Color COLOR_CANNOT_BUY = Color.GRAY;

    // --- TESTI FISSI ---
    private static final String TEXT_POINTS = "Punti: ";
    private static final String TEXT_UNLOCKED = "SBLOCCATA - Premi INVIO per usare";
    private static final String TEXT_LOCKED_PREFIX = "BLOCCATA - Costo: ";
    private static final String TEXT_CAN_BUY = "Premi SPAZIO per comprare!";
    private static final String TEXT_CANNOT_BUY = "Punti insufficienti";

    private final SkinController controller;

    public SkinSelectionView(SkinController controller) {
        this.controller = controller;
        setBackground(Color.BLACK);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        int width = getWidth();
        int height = getHeight();
        FontMetrics fm = g2.getFontMetrics(); // Strumento vitale per centrare il testo!

        Skin currentSkin = controller.getPlayerSelectedSkin();

        // 1. PUNTI DEL GIOCATORE (in alto a destra)
        String pointsText = TEXT_POINTS + controller.getPlayerPoints();
        int pointsWidth = fm.stringWidth(pointsText); // Calcola la larghezza esatta della scritta
        g2.setColor(COLOR_POINTS);
        g2.drawString(pointsText, width - pointsWidth - POINTS_PADDING_RIGHT, POINTS_MARGIN_TOP);

        // 2. NOME DELLA SKIN (in alto al centro)
        String skinName = currentSkin.getName();
        int nameWidth = fm.stringWidth(skinName);
        g2.setColor(COLOR_TITLE);
        g2.drawString(skinName, (width - nameWidth) / 2, MARGIN_TOP);

        // 3. ASTRONAVE (al centro esatto)
        int shipX = (width / 2) - SHIP_OFFSET;
        int shipY = (height / 2) - SHIP_OFFSET;
        g2.drawImage(Utils.loadImage(currentSkin.getImagePath()), shipX, shipY, SHIP_SIZE, SHIP_SIZE, this);

        // 4. STATO DELLA SKIN (in basso)
        int bottomY = height - MARGIN_BOTTOM;
        
        if (currentSkin.isUnlock()) {
            int unlockedWidth = fm.stringWidth(TEXT_UNLOCKED);
            g2.setColor(COLOR_UNLOCKED);
            g2.drawString(TEXT_UNLOCKED, (width - unlockedWidth) / 2, bottomY);
            
        } else {
            // Testo con il costo
            String lockedText = TEXT_LOCKED_PREFIX + currentSkin.getPrice();
            int lockedWidth = fm.stringWidth(lockedText);
            g2.setColor(COLOR_LOCKED);
            g2.drawString(lockedText, (width - lockedWidth) / 2, bottomY - LINE_SPACING);
            
            // Suggerimento di acquisto dinamico
            if (controller.getPlayerPoints() >= currentSkin.getPrice()) {
                int buyWidth = fm.stringWidth(TEXT_CAN_BUY);
                g2.setColor(COLOR_CAN_BUY);
                g2.drawString(TEXT_CAN_BUY, (width - buyWidth) / 2, bottomY);
            } else {
                int cannotBuyWidth = fm.stringWidth(TEXT_CANNOT_BUY);
                g2.setColor(COLOR_CANNOT_BUY);
                g2.drawString(TEXT_CANNOT_BUY, (width - cannotBuyWidth) / 2, bottomY);
            }
        }
    }
}
