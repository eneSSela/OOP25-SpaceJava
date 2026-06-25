package it.unibo.spacejava.view.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Objects;

import javax.swing.JPanel;

import it.unibo.spacejava.Skin;
import it.unibo.spacejava.Utils;
import it.unibo.spacejava.api.MenuObserver;
import it.unibo.spacejava.model.menu.ShopImpl;

/**
 * View dedicata alla rappresetazione della schermata per poter comprare o selezionare le skin disponibili per il giocatore.
 */
public final class SkinSelectionView extends JPanel implements MenuObserver {

    private static final long serialVersionUID = 1L;

    // --- COSTANTI DI DIMENSIONAMENTO ---
    private static final int SHIP_SIZE = 128;
    private static final int SHIP_OFFSET = SHIP_SIZE / 2;

    // --- COSTANTI DI MARGINE E SPAZIATURA ---
    private static final int MARGIN_TOP = 50;
    private static final int MARGIN_BOTTOM = 50;
    private static final int POINTS_MARGIN_TOP = 30;
    private static final int POINTS_PADDING_RIGHT = 20; // Distanza dal bordo destro
    private static final int LINE_SPACING = 20;         // Spazio tra due righe di testo
    private static final int TITLE_VERTICAL_OFFSET = 20;
    private static final int CARD_WIDTH = 250;
    private static final int CARD_HEIGHT = 250;
    private static final int CARD_ARC = 30;
    private static final int CARD_ALPHA = 200;
    private static final int CARD_BACKGROUND_RED = 40;
    private static final int CARD_BACKGROUND_GREEN = 40;
    private static final int CARD_BACKGROUND_BLUE = 50;
    private static final int CARD_BORDER_GREEN = 100;
    private static final int CARD_BORDER_BLUE = 150;
    private static final int NAV_ARROW_FONT_SIZE = 40;
    private static final int NAV_ARROW_LEFT_OFFSET = 50;
    private static final int NAV_ARROW_RIGHT_OFFSET = 20;
    private static final int NAV_ARROW_VERTICAL_OFFSET = 15;
    private static final int MIN_DYNAMIC_FONT_SIZE = 12;
    private static final int DYNAMIC_FONT_WIDTH_DIVISOR = 40;
    private static final int DOT_SIZE = 12;
    private static final int DOT_SPACING = 20;
    private static final int DOTS_BOTTOM_PADDING = 20;
    private static final int UNLOCKED_TEXT_BOTTOM_PADDING = 30;
    private static final int PROMPT_BUY_OFFSET = 10;

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

    private final transient ShopImpl model;

    /**
     * Costruisce la view per la selezione delle skin.
     * 
     * @param model del menu di selezione skin
     */
    public SkinSelectionView(final ShopImpl model) {
        this.model = Objects.requireNonNull(model, "Il model non può essere nullo");
        this.model.setObserver(this);
        setBackground(Color.BLACK);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;

        final int width = getWidth();
        final int height = getHeight();
        final FontMetrics fm = g2.getFontMetrics(); 

        final Skin currentSkin = model.getSelectedSkin();

        final String pointsText = TEXT_POINTS + model.getPoints();
        final int pointsWidth = fm.stringWidth(pointsText);
        g2.setColor(COLOR_POINTS);
        g2.drawString(pointsText, width - pointsWidth - POINTS_PADDING_RIGHT, POINTS_MARGIN_TOP);

        final String skinName = currentSkin.getName();
        final int nameWidth = fm.stringWidth(skinName);
        g2.setColor(COLOR_TITLE);
        g2.drawString(skinName, (width - nameWidth) / 2, MARGIN_TOP - TITLE_VERTICAL_OFFSET);

        final int cardWidth = CARD_WIDTH;
        final int cardHeight = CARD_HEIGHT;
        final int cardX = (width - cardWidth) / 2;
        final int cardY = (height - cardHeight) / 2;
        g2.setColor(new Color(CARD_BACKGROUND_RED, CARD_BACKGROUND_GREEN, CARD_BACKGROUND_BLUE, CARD_ALPHA));
        g2.fillRoundRect(cardX, cardY, cardWidth, cardHeight, CARD_ARC, CARD_ARC);
        g2.setColor(new Color(CARD_BORDER_GREEN, CARD_BORDER_GREEN, CARD_BORDER_BLUE));
        g2.drawRoundRect(cardX, cardY, cardWidth, cardHeight, CARD_ARC, CARD_ARC);

        final int shipX = (width / 2) - SHIP_OFFSET;
        final int shipY = (height / 2) - SHIP_OFFSET;
        g2.drawImage(Utils.loadImage(currentSkin.getImagePath()), shipX, shipY, SHIP_SIZE, SHIP_SIZE, this);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, NAV_ARROW_FONT_SIZE));
        g2.drawString("<", cardX - NAV_ARROW_LEFT_OFFSET, height / 2 + NAV_ARROW_VERTICAL_OFFSET);
        g2.drawString(">", cardX + cardWidth + NAV_ARROW_RIGHT_OFFSET, height / 2 + NAV_ARROW_VERTICAL_OFFSET);

        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, Math.max(MIN_DYNAMIC_FONT_SIZE, width / DYNAMIC_FONT_WIDTH_DIVISOR)));

        final int totalSkins = model.getTotalSkins();
        final int currentIndex = model.getSelectedIndex();
        final int dotSize = DOT_SIZE;
        final int dotSpacing = DOT_SPACING;
        final int startDotsX = (width - ((totalSkins * dotSize) + ((totalSkins - 1) * dotSpacing))) / 2;
        final int dotsY = cardY + cardHeight + DOTS_BOTTOM_PADDING;

        for (int i = 0; i < totalSkins; i++) {
            final int dotX = startDotsX + i * (dotSize + dotSpacing);
            if (i == currentIndex) {
                g2.setColor(Color.CYAN);
                g2.fillOval(dotX, dotsY, dotSize, dotSize);
            } else {
                g2.setColor(Color.GRAY);
                g2.drawOval(dotX, dotsY, dotSize, dotSize);
            }
        }

        final int bottomY = height - MARGIN_BOTTOM;

        if (currentSkin.isUnlock()) {
            final int unlockedWidth = fm.stringWidth(TEXT_UNLOCKED);
            g2.setColor(COLOR_UNLOCKED);
            g2.drawString(TEXT_UNLOCKED, (width - unlockedWidth) / 2, bottomY - UNLOCKED_TEXT_BOTTOM_PADDING);

            g2.setColor(Color.LIGHT_GRAY);
            final String prompt = "[INVIO] Seleziona ed Esci";
            g2.drawString(prompt, (width - fm.stringWidth(prompt)) / 2, bottomY);
        } else {
            // Testo con il costo
            final String lockedText = TEXT_LOCKED_PREFIX + currentSkin.getPrice();
            final int lockedWidth = fm.stringWidth(lockedText);
            g2.setColor(COLOR_LOCKED);
            g2.drawString(lockedText, (width - lockedWidth) / 2, bottomY - LINE_SPACING);

            // Suggerimento di acquisto dinamico
            if (model.getPoints() >= currentSkin.getPrice()) {
                final int buyWidth = fm.stringWidth(TEXT_CAN_BUY);
                g2.setColor(COLOR_CAN_BUY);
                g2.drawString(TEXT_CAN_BUY, (width - buyWidth) / 2, bottomY);
                g2.setColor(Color.YELLOW);
                final String promptBuy = "[SPAZIO] per Comprare";
                g2.drawString(promptBuy, (width - fm.stringWidth(promptBuy)) / 2, bottomY + PROMPT_BUY_OFFSET);
            } else {
                final int cannotBuyWidth = fm.stringWidth(TEXT_CANNOT_BUY);
                g2.setColor(COLOR_CANNOT_BUY);
                g2.drawString(TEXT_CANNOT_BUY, (width - cannotBuyWidth) / 2, bottomY);
            }
        }
    }

    @Override
    public void updateMenuState() {
        this.repaint();
    }
}
