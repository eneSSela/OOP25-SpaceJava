package it.unibo.spacejava.view.menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.List;
import java.util.Objects;
import javax.swing.JPanel;

import it.unibo.spacejava.Utils;
import it.unibo.spacejava.api.MenuObserver;
import it.unibo.spacejava.api.PowerUp;
import it.unibo.spacejava.model.menu.PowerUpSelectionModel;

/**
 * View dedicated to the power-ups.
 */
public final class PowerUpSelectionView extends JPanel implements MenuObserver {

    private static final long serialVersionUID = 1L;

    private static final String FONT_NAME = "Monospaced";
    private static final int BANNER_WIDTH = 520;
    private static final int BANNER_HEIGHT = 85;
    private static final int BANNER_SPACING = 25;
    private static final int ARC_SIZE = 15;
    private static final int ICON_BOX_SIZE = 65;

    private static final int TITLE_FONT_SIZE = 28;
    private static final int TITLE_Y_POS = 75;
    private static final int INSTR_FONT_SIZE = 16;
    private static final int INSTR_Y_OFFSET = 55;
    private static final float SELECTED_STROKE = 3.5f;
    private static final int STAR_FONT_SIZE = 28;
    private static final int NAME_FONT_SIZE = 19;
    private static final int DESC_FONT_SIZE = 14;

    private static final int STAR_X_OFFSET = 22;
    private static final int STAR_Y_OFFSET = 43;
    private static final int TEXT_X_OFFSET = 15;
    private static final int NAME_Y_OFFSET = 35;
    private static final int DESC_Y_OFFSET = 58;

    private static final Color BANNER_BG = new Color(30, 30, 45, 240);
    private static final Color SELECTED_BORDER = Color.CYAN;
    private static final Color UNSELECTED_BORDER = Color.DARK_GRAY;
    private static final Color ICON_BG = new Color(50, 50, 70);

    private final transient PowerUpSelectionModel model;

    private final transient Image shieldIconImage;
    private final transient Image timeIconImage;
    private final transient Image machinegunIconImage;
    private final transient Image repairIconImage;
    private final transient Image speedIconImage;
    private final transient Image damageIconImage;

    /**
     * Creates the view for the power-ups.
     * 
     * @param model the model 
     */
    public PowerUpSelectionView(final PowerUpSelectionModel model) {
        this.model = Objects.requireNonNull(model, "Il model NON può essere nullo");
        this.model.setObserver(this);
        setBackground(Color.BLACK);
        setFocusable(true);

        this.shieldIconImage = Utils.loadImage("/powerups/shield.png");
        this.timeIconImage = Utils.loadImage("/powerups/time.png");
        this.machinegunIconImage = Utils.loadImage("/powerups/machinegun.png");
        this.repairIconImage = Utils.loadImage("/powerups/repair.png");
        this.speedIconImage = Utils.loadImage("/powerups/speed.png");
        this.damageIconImage = Utils.loadImage("/powerups/damage.png");
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        final int width = getWidth();
        final int height = getHeight();

        g2.setFont(new Font(FONT_NAME, Font.BOLD, TITLE_FONT_SIZE));
        g2.setColor(Color.YELLOW);
        final String title = "Ondata Superata! Scegli un potenziamento";
        final FontMetrics fmTitle = g2.getFontMetrics();
        g2.drawString(title, (width - fmTitle.stringWidth(title)) / 2, TITLE_Y_POS);

        final List<PowerUp> options = this.model.getAvailableOptions();
        final int totalHeight = (BANNER_HEIGHT * 3) + (BANNER_SPACING * 2);
        final int startX = (width - BANNER_WIDTH) / 2;
        final int startY = (height - totalHeight) / 2 + 15;

        for (int i = 0; i < options.size(); i++) {
            final PowerUp pu = options.get(i);
            final int bannerY = startY + (i * (BANNER_HEIGHT + BANNER_SPACING));
            final boolean isSelected = i == this.model.getSelectedIndex();

            drawBanner(g2, pu, startX, bannerY, isSelected);
        }

        g2.setFont(new Font(FONT_NAME, Font.BOLD, INSTR_FONT_SIZE));
        g2.setColor(Color.LIGHT_GRAY);
        final String instruction = "[FRECCIA SU/GIU'] Naviga   [INVIO] Conferma Selezione";
        final FontMetrics fmInstruction = g2.getFontMetrics();
        g2.drawString(instruction, (width - fmInstruction.stringWidth(instruction)) / 2, height - INSTR_Y_OFFSET);
    }

    /**
     * Draw the single banner.
     * 
     * @param g2 graphic context
     * @param pu enhancement
     * @param x coordinate
     * @param y coordinate
     * @param isSelected if it is selected
     */
    private void drawBanner(final Graphics2D g2, final PowerUp pu, final int x, final int y, final boolean isSelected) {
        g2.setColor(BANNER_BG);
        g2.fillRoundRect(x, y, BANNER_WIDTH, BANNER_HEIGHT, ARC_SIZE, ARC_SIZE);

        g2.setStroke(new BasicStroke(isSelected ? SELECTED_STROKE : 1f));
        g2.setColor(isSelected ? SELECTED_BORDER : UNSELECTED_BORDER);
        g2.drawRoundRect(x, y, BANNER_WIDTH, BANNER_HEIGHT, ARC_SIZE, ARC_SIZE);

        final int iconX = x + 10;
        final int iconY = y + 10;
        g2.setColor(ICON_BG);
        g2.fillRoundRect(iconX, iconY, ICON_BOX_SIZE, ICON_BOX_SIZE, 10, 10);

        if ("Scudo Energetico".equals(pu.getType()) && this.shieldIconImage != null) {
            g2.drawImage(this.shieldIconImage, iconX, iconY, ICON_BOX_SIZE, ICON_BOX_SIZE, null);
        } else if ("Distorsione Tempo".equals(pu.getType()) && this.timeIconImage != null) {
            g2.drawImage(this.timeIconImage, iconX, iconY, ICON_BOX_SIZE, ICON_BOX_SIZE, null);
        } else if ("Mitragliatrice".equals(pu.getType()) && this.machinegunIconImage != null) {
            g2.drawImage(this.machinegunIconImage, iconX, iconY, ICON_BOX_SIZE, ICON_BOX_SIZE, null);
        } else if ("Riparazione Navicella".equals(pu.getType()) && this.repairIconImage != null) {
            g2.drawImage(this.repairIconImage, iconX, iconY, ICON_BOX_SIZE, ICON_BOX_SIZE, null);
        } else if ("Iper-Propulsori".equals(pu.getType()) && this.speedIconImage != null) {
            g2.drawImage(this.speedIconImage, iconX, iconY, ICON_BOX_SIZE, ICON_BOX_SIZE, null);
        } else if ("Laser Pesante".equals(pu.getType()) && this.damageIconImage != null) {
            g2.drawImage(this.damageIconImage, iconX, iconY, ICON_BOX_SIZE, ICON_BOX_SIZE, null);
        } else {
            g2.setColor(isSelected ? Color.CYAN : Color.WHITE);
            g2.setFont(new Font(FONT_NAME, Font.BOLD, STAR_FONT_SIZE));
            g2.drawString("★", iconX + STAR_X_OFFSET, iconY + STAR_Y_OFFSET);
        }

        g2.setFont(new Font(FONT_NAME, Font.BOLD, NAME_FONT_SIZE));
        g2.setColor(Color.WHITE);
        g2.drawString(pu.getType(), iconX + ICON_BOX_SIZE + TEXT_X_OFFSET, y + NAME_Y_OFFSET);

        g2.setFont(new Font(FONT_NAME, Font.PLAIN, DESC_FONT_SIZE));
        g2.setColor(Color.GREEN);
        g2.drawString(pu.getDescription(), iconX + ICON_BOX_SIZE + TEXT_X_OFFSET, y + DESC_Y_OFFSET);
    }

    @Override
    public void updateMenuState() {
        this.repaint();
    }
}
