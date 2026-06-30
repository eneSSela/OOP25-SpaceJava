package it.unibo.spacejava.view.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Objects;

import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.spacejava.api.MenuObserver;
import it.unibo.spacejava.model.menu.GameOverModel;

/**
 * View component responsible for rendering the GameOver screen.
 */
public final class GameOverView extends JPanel implements MenuObserver {

    private static final long serialVersionUID = 1L;
    private static final String FONT_NAME = "Monospaced";

    private static final int TITLE_FONT_SIZE = 60;
    private static final int TITLE_Y_DIVISOR = 3;
    private static final int SCORE_FONT_SIZE = 24;
    private static final int SCORE_Y_OFFSET = 30;
    private static final int MENU_START_Y_OFFSET = 50;
    private static final int MENU_GAP = 40;
    private static final int MENU_FONT_SIZE = 22;
    private static final int CURSOR_OFFSET_X = 30;
    private static final int SHADOW_OFFSET = 2;
    private static final int INSTR_FONT_SIZE = 14;
    private static final int INSTR_Y_OFFSET = 30;

    private final transient GameOverModel model;

    /**
     * Constructs the GameOver View.
     * 
     * @param model the model managing the GameOver state
     */
    @SuppressFBWarnings(value = "EI2", justification = "Il Model deve essere condiviso con il Controller")
    public GameOverView(final GameOverModel model) {
        this.model = Objects.requireNonNull(model);
        this.model.setObserver(this);
        setBackground(Color.BLACK);
        setFocusable(true);
    }

    /**
     * Paints the graphical interface of the GameOver screen.
     * 
     * @param g the Graphics context used for drawing
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final int width = getWidth();
        final int height = getHeight();

        g2.setFont(new Font(FONT_NAME, Font.BOLD, TITLE_FONT_SIZE));
        g2.setColor(Color.RED);
        final String title = "GAME OVER";
        g2.drawString(title, (width - g2.getFontMetrics().stringWidth(title)) / 2, height / TITLE_Y_DIVISOR);

        g2.setFont(new Font(FONT_NAME, Font.BOLD, SCORE_FONT_SIZE));
        g2.setColor(Color.YELLOW);
        final String scoreText = "Punteggio Finale : " + model.getFinalScore();
        g2.drawString(scoreText, (width - g2.getFontMetrics().stringWidth(scoreText)) / 2, (height / 2) - SCORE_Y_OFFSET);

        final int startY = (height / 2) + MENU_START_Y_OFFSET;
        g2.setFont(new Font(FONT_NAME, Font.BOLD, MENU_FONT_SIZE));

        for (int i = 0; i < this.model.getOptions().size(); i++) {
            final String option = this.model.getOptions().get(i);
            final int optionW = g2.getFontMetrics().stringWidth(option);
            final int x = (width - optionW) / 2;
            final int y = startY + (MENU_GAP * i);

            final boolean selected = this.model.getSelectedIndex() == i;
            final boolean blink = selected && this.model.isBlinkOn();

            if (selected) {
                g2.setColor(Color.BLACK);
                g2.drawString(">", x - CURSOR_OFFSET_X + SHADOW_OFFSET, y + SHADOW_OFFSET);
                g2.setColor(blink ? Color.WHITE : Color.RED);
                g2.drawString(">", x - CURSOR_OFFSET_X, y);

                g2.setColor(blink ? Color.RED : Color.WHITE);
                g2.drawString(option, x, y);
            } else {
                g2.setColor(Color.DARK_GRAY);
                g2.drawString(option, x, y);
            }
        }

        g2.setFont(new Font(FONT_NAME, Font.BOLD, INSTR_FONT_SIZE));
        g2.setColor(Color.LIGHT_GRAY);
        final String instr = "[FRECCE] Naviga - [INVIO] Conferma";
        g2.drawString(instr, (width - g2.getFontMetrics().stringWidth(instr)) / 2, height - INSTR_Y_OFFSET);
    }

    /**
     * Updates the visual state of the menu, triggering a repaint for animations.
     */
    @Override
    public void updateMenuState() {
        this.repaint();
    }
}
