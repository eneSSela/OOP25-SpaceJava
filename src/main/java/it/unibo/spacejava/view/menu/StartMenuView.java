package it.unibo.spacejava.view.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import it.unibo.spacejava.controller.menu.StartMenuController;

/**
 * Rappresenta la view del menu iniziale del gioco.
 */
public final class StartMenuView extends JPanel {

    private static final long serialVersionUID = 1L;

    //Costanti per impostare il layout e la grafica
    private static final int SHADOW_OFFSET = 2;
    private static final int ARROW_OFFSET = 40;
    private static final int INSTRUCTION_OFFSET = 40;
    private static final int LOGO_Y_OFFSET = 30;
    private static final double MAX_LOGO_WIDTH_RATIO = 0.7;

    //Costanti per il font 
    private static final int MIN_TITLE_FONT_SIZE = 32;
    private static final int TITLE_FONOT_DIVISOR = 15;
    private static final int MIN_MENU_FONT_SIZE = 30;
    private static final int MENU_FONT_DIVISOR = 20;
    private static final int MIN_INSTRUCTION_FONT_SIZE = 12;
    private static final int INSTRUCTION_FONT_DIVISOR = 80;

    //Costanti per i colori
    private static final Color TITLE_COLOR = new Color(64, 224, 208);
    private static final Color BLINK_COLOR = new Color(57, 255, 20);

    private final transient StartMenuController controller;
    private final transient Image logo;
    private final transient Image background;

    /**
     * Costruisce la view del menu iniziale.
     * 
     * @param controller del menu
     */
    public StartMenuView(final StartMenuController controller) {
        this.controller = controller;
        setBackground(Color.BLACK);
        setFocusable(true);

        this.background = loadImage("/menu/background.png");
        this.logo = loadImage("/menu/logo.png");
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;
        final FontMetrics fm = g2.getFontMetrics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final int w = getWidth();
        final int h = getHeight();
        final int startY = h / 2;
        final int gap = fm.getHeight() + ARROW_OFFSET;
        final FontMetrics fontMetrics = g2.getFontMetrics();

        drawBackground(g2, w, h);
        drawLogo(g2, w, h, startY);
        drawMenuOption(g2, w, h, startY, gap, fontMetrics);
        drawInstruction(g2, w, h, startY, fontMetrics);
        drawInstruction(g2, w, h, startY, fm);
    }

    private void drawInstruction(final Graphics2D g2, final int w, final int h, final int startY, final FontMetrics fm) { //NOPMD
        final String instruction = "Usa frecce su/giù e Invio";
        final Font font = new Font(Font.MONOSPACED, Font.BOLD, Math.max(MIN_INSTRUCTION_FONT_SIZE, w / INSTRUCTION_FONT_DIVISOR));
        g2.setFont(font);
        g2.setColor(Color.LIGHT_GRAY);
        final int instructionW = fm.stringWidth(instruction);
        g2.drawString(instruction, (w - instructionW) / 2, h - INSTRUCTION_OFFSET);
    }

    private void drawBackground(final Graphics2D g2, final int w, final int h) {
        if (Objects.isNull(background)) {
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, w, h);
        } else {
            g2.drawImage(background, 0, 0, w, h, null);
        }
    }

    private void drawLogo(final Graphics2D g2, final int w, final int h, final int startY) {
        if (Objects.nonNull(logo)) {
            final int originalW = logo.getWidth(null);
            final int originalH = logo.getHeight(null);

            final int maxW = (int) (w * MAX_LOGO_WIDTH_RATIO);
            final int maxH = startY - ARROW_OFFSET;

            double scale = Math.min((double) maxW / originalW, (double) maxH / originalH);
            if (scale > 1.0) {
                scale = 1.0;
            }

            final int targetW = (int) (originalW * scale);
            final int targetH = (int) (originalH * scale);

            final int logoX = (w - targetW) / 2;
            final int logoY = startY - targetH - LOGO_Y_OFFSET;

            g2.drawImage(logo, logoX, logoY, targetW, targetH, null);
        } else {
            final Font titleFont = new Font(Font.MONOSPACED, Font.BOLD, Math.max(MIN_TITLE_FONT_SIZE, w / TITLE_FONOT_DIVISOR));
            g2.setFont(titleFont);
            g2.setColor(TITLE_COLOR);
            final String title = "SPACE JAVA";
            final int titleW = g2.getFontMetrics().stringWidth(title);
            g2.drawString(title, (w - titleW) / 2, h / 4);
        }
    }

    private void drawMenuOption(
        final Graphics2D g2,
        final int w,
        final int h, //NOPMD
        final int startY, 
        final int gap,
        final FontMetrics fm) {
        final Font menuFont = new Font(Font.MONOSPACED, Font.BOLD, Math.max(MIN_MENU_FONT_SIZE, w / MENU_FONT_DIVISOR));
        g2.setFont(menuFont);

        for (final String option : controller.getOptions()) {
            final int optionW = fm.stringWidth(option);
            final int i = controller.getOptions().indexOf(option);
            final int x = (w - optionW) / 2;
            final int y = startY + gap * i;

            final boolean selected = controller.getSelectedIndex() == i;
            final boolean blink = selected && controller.isBlinkOn();

            if (selected) {
                drawSelectedOption(g2, option, x, y, blink);
            } else {
                drawUnselectedOption(g2, option, x, y);
            }
        }
    }

    private void drawUnselectedOption(final Graphics2D g2, final String option, final int x, final int y) {
        g2.setColor(Color.BLACK);
        g2.drawString(option, x + SHADOW_OFFSET, y + SHADOW_OFFSET);

        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString(option, x, y);
    }

    private void drawSelectedOption(final Graphics2D g2, final String option, final int x, final int y, final boolean blink) {
        g2.setColor(Color.BLACK);
        g2.drawString(">", x - ARROW_OFFSET + SHADOW_OFFSET, y + SHADOW_OFFSET);
        g2.drawString(option, x + SHADOW_OFFSET, y + SHADOW_OFFSET);

        g2.setColor(blink ? Color.WHITE : BLINK_COLOR);
        g2.drawString(">", x - ARROW_OFFSET, y);
        g2.setColor(blink ? BLINK_COLOR : Color.WHITE);
        g2.drawString(option, x, y);
    }

    /**
     * Usato per comunicare al controller di far femrare timer del blinker. 
     */
    public void stop() {
        controller.stop();
    }

    //per il futuro, possiamo aggiungere questo metodo nella calsse utility
    //dato che è usato in piu punti nel programma, per caricare le immagini in modo più semplice e pulito
    /**
     * Carica un'immagine dal percorso specificato.
     * 
     * @param path il percorso dell'immagine
     * @return l'immagine caricata, oppure null in caso di errore
     */
    private Image loadImage(final String path) {
        try (InputStream in = getClass().getResourceAsStream(path)) {
            return ImageIO.read(Objects.requireNonNull(in));
        } catch (final IOException ignored) {
            System.err.println(ignored.getMessage()); //NOPMD
        }
        return null;
    }
}
