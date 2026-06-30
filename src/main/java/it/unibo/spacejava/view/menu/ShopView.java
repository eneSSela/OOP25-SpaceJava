package it.unibo.spacejava.view.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Objects;

import javax.swing.JPanel;

import it.unibo.spacejava.Skin;
import it.unibo.spacejava.Utils;
import it.unibo.spacejava.api.MenuObserver;
import it.unibo.spacejava.model.PlayerShip;
import it.unibo.spacejava.model.menu.ShopImpl;

/**
 * View responsible for rendering the shop screen where the player can buy or select available skins.
 */
public final class ShopView extends JPanel implements MenuObserver {

    private static final long serialVersionUID = 1L;

    // --- COSTANTI DI DIMENSIONAMENTO ---
    private static final int SHIP_SIZE = 128;
    private static final int SHIP_OFFSET = SHIP_SIZE / 2;

    // --- COSTANTI DI MARGINE E SPAZIATURA ---
    private static final int MARGIN_TOP = 50;
    private static final int MARGIN_BOTTOM = 50;
    private static final int POINTS_MARGIN_TOP = 30;
    private static final int LINE_SPACING = 20;         // Spazio tra due righe di testo
    private static final int TITLE_VERTICAL_OFFSET = 20;
    private static final int CARD_WIDTH = 250;
    private static final int CARD_HEIGHT = 250;
    private static final int CARD_ARC = 30;
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

    private static final int POINTS_FONT_SIZE = 25;
    private static final int SKIN_NAME_FONT_SIZE = 18;
    private static final int POINTS_SHADOW_OFFSET_X = 18;
    private static final int POINTS_SHADOW_OFFSET_Y = 2;
    private static final int POINTS_OFFSET_X = 20;
    private static final int SHADOW_ALPHA = 150;
    private static final int CARD_ALPHA = 200;
    private static final int CARD_BACKGROUND_RED = 40;
    private static final int CARD_BACKGROUND_GREEN = 40;
    private static final int CARD_BACKGROUND_BLUE = 50;
    private static final int CARD_BORDER_GREEN = 100;
    private static final int CARD_BORDER_BLUE = 150;

    // --- COSTANTI CROMATICHE ---
    private static final Color COLOR_TITLE = Color.WHITE;
    private static final Color COLOR_UNLOCKED = Color.GREEN;
    private static final Color COLOR_LOCKED = Color.RED;
    private static final Color COLOR_CAN_BUY = Color.CYAN;
    private static final Color COLOR_CANNOT_BUY = Color.GRAY;
    private static final Color COLOR_GOLD = new Color(255, 215, 0);
    private static final Color COLOR_SHADOW = new Color(0, 0, 0, SHADOW_ALPHA);
    private static final Color COLOR_CARD_BG = new Color(CARD_BACKGROUND_RED, CARD_BACKGROUND_GREEN,
                                                         CARD_BACKGROUND_BLUE, CARD_ALPHA);
    private static final Color COLOR_CARD_BORDER = new Color(CARD_BORDER_GREEN, CARD_BORDER_GREEN, CARD_BORDER_BLUE);

    // --- TESTI FISSI ---
    private static final String TEXT_POINTS = "Punteggio : ";
    private static final String TEXT_UNLOCKED = "SBLOCCATA";
    private static final String TEXT_LOCKED_PREFIX = "BLOCCATA - Costo: ";
    private static final String TEXT_CAN_BUY = "Premi [SPAZIO] per Comprare!";
    private static final String TEXT_CANNOT_BUY = "Punti insufficienti";

    private final transient ShopImpl shop;
    private final transient PlayerShip player;

    /**
     * Costruisce la view per la selezione delle skin.
     * 
     * @param shop del menu di selezione skin
     * @param player riferimento al giocatre per poter accedere al proprio punteggio e verificare se può comprare la skin
     */
    public ShopView(final ShopImpl shop, final PlayerShip player) {
        this.shop = Objects.requireNonNull(shop, "Il model non può essere nullo");
        this.shop.setObserver(this);
        this.player = Objects.requireNonNull(player, "Il giocatore non può essere nullo");
        setBackground(Color.BLACK);
        setFocusable(true);
    }

    /**
     * Paints the graphical interface of the Shop screen.
     * 
     * @param g the graphics context used for drawing
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final int width = getWidth();
        final int height = getHeight();
        final Skin currentSkin = shop.getSelectedSkin();

        final String pointsText = TEXT_POINTS + player.getScore().getTotal();
        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, POINTS_FONT_SIZE));

        g2.setColor(COLOR_SHADOW);
        g2.drawString(pointsText, width - g2.getFontMetrics().stringWidth(pointsText) - POINTS_SHADOW_OFFSET_X, 
                      POINTS_MARGIN_TOP + POINTS_SHADOW_OFFSET_Y);

        g2.setColor(COLOR_GOLD);
        g2.drawString(pointsText, width - g2.getFontMetrics().stringWidth(pointsText) - POINTS_OFFSET_X, POINTS_MARGIN_TOP);

        final String skinName = currentSkin.getName();
        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, SKIN_NAME_FONT_SIZE));
        final int nameWidth = g2.getFontMetrics().stringWidth(pointsText);
        g2.setColor(COLOR_TITLE);
        g2.drawString(skinName, (width - nameWidth) / 2, MARGIN_TOP - TITLE_VERTICAL_OFFSET);

        final int cardX = (width - CARD_WIDTH) / 2;
        final int cardY = (height - CARD_HEIGHT) / 2;

        g2.setColor(COLOR_CARD_BG);
        g2.fillRoundRect(cardX, cardY, CARD_WIDTH, CARD_HEIGHT, CARD_ARC, CARD_ARC);
        g2.setColor(COLOR_CARD_BORDER);
        g2.fillRoundRect(cardX, cardY, CARD_WIDTH, CARD_HEIGHT, CARD_ARC, CARD_ARC);

        final int shipX = (width / 2) - SHIP_OFFSET;
        final int shipY = (height / 2) - SHIP_OFFSET;
        g2.drawImage(Utils.loadImage(currentSkin.getImagePath()), shipX, shipY, SHIP_SIZE, SHIP_SIZE, this);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, NAV_ARROW_FONT_SIZE));
        g2.drawString("<", cardX - NAV_ARROW_LEFT_OFFSET, height / 2 + NAV_ARROW_VERTICAL_OFFSET);
        g2.drawString(">", cardX + CARD_WIDTH + NAV_ARROW_RIGHT_OFFSET, height / 2 + NAV_ARROW_VERTICAL_OFFSET);

        g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, Math.max(MIN_DYNAMIC_FONT_SIZE, width / DYNAMIC_FONT_WIDTH_DIVISOR)));
        final int totalSkins = shop.getTotalSkins();
        final int currentIndex = shop.getSelectedIndex();
        final int startDotsX = (width - ((totalSkins * DOT_SIZE) + ((totalSkins - 1) * DOT_SPACING))) / 2;
        final int dotsY = cardY + CARD_HEIGHT + DOTS_BOTTOM_PADDING;

        for (int i = 0; i < totalSkins; i++) {
            final int dotX = startDotsX + i * (DOT_SIZE + DOT_SPACING);
            if (i == currentIndex) {
                g2.setColor(Color.CYAN);
                g2.fillOval(dotX, dotsY, DOT_SIZE, DOT_SIZE);
            } else {
                g2.setColor(Color.GRAY);
                g2.drawOval(dotX, dotsY, DOT_SIZE, DOT_SIZE);
            }
        }

        final int bottomY = height - MARGIN_BOTTOM;
        final FontMetrics bottomFm = g2.getFontMetrics();

        if (currentSkin.isUnlock()) {
            final int unlockedWidth = bottomFm.stringWidth(TEXT_UNLOCKED);
            g2.setColor(COLOR_UNLOCKED);
            g2.drawString(TEXT_UNLOCKED, (width - unlockedWidth) / 2, bottomY - UNLOCKED_TEXT_BOTTOM_PADDING);

            g2.setColor(Color.LIGHT_GRAY);
            final String prompt = "[INVIO] Seleziona ed Esci";
            g2.drawString(prompt, (width - bottomFm.stringWidth(prompt)) / 2, bottomY);
        } else {
            // Testo con il costo
            final String lockedText = TEXT_LOCKED_PREFIX + currentSkin.getPrice();
            final int lockedWidth = bottomFm.stringWidth(lockedText);
            g2.setColor(COLOR_LOCKED);
            g2.drawString(lockedText, (width - lockedWidth) / 2, bottomY - LINE_SPACING);

            // Suggerimento di acquisto dinamico
            if (player.getScore().getTotal() >= currentSkin.getPrice()) {
                final int buyWidth = bottomFm.stringWidth(TEXT_CAN_BUY);
                g2.setColor(COLOR_CAN_BUY);
                g2.drawString(TEXT_CAN_BUY, (width - buyWidth) / 2, bottomY + PROMPT_BUY_OFFSET);
            } else {
                final int cannotBuyWidth = bottomFm.stringWidth(TEXT_CANNOT_BUY);
                g2.setColor(COLOR_CANNOT_BUY);
                g2.drawString(TEXT_CANNOT_BUY, (width - cannotBuyWidth) / 2, bottomY);
            }
        }
    }

    /**
     * Updates the visual state of the menu, triggering a repaint for animations.
     */
    @Override
    public void updateMenuState() {
        this.repaint();
    }
}
