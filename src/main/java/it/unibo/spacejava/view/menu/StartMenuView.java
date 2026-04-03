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

public class StartMenuView extends JPanel {

    private final StartMenuController controller;
    private Image logo;
    private Image background;

    public StartMenuView(StartMenuController controller) {
        this.controller = controller;
        setBackground(Color.BLACK);
        setFocusable(true);

        this.background = loadImage("/menu/background.png");
        this.logo = loadImage("/menu/logo.png");
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        FontMetrics fm = g2.getFontMetrics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int w = getWidth();
        int h = getHeight();
        int startY = h / 2;
        int gap = fm.getHeight() + 40;
        

        if (background != null) {
            g2.drawImage(background, 0, 0, w, h, null);
        } else {
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, w, h);
        }

        if (logo != null) {
            int originalW = logo.getWidth(null);
            int originalH = logo.getHeight(null);

            int maxW = (int)(w * 0.7);
            int maxH = startY - 40;

            double scale = Math.min((double) maxW / originalW, (double) maxH / originalH);

            if (scale > 1.0) {
                scale = 1.0;
            }

            int targetW = (int) (originalW * scale);
            int targetH = (int) (originalH * scale);

            int logoX = (w - targetW) / 2;
            int logoY = startY - targetH - 30;

            g2.drawImage(logo, logoX, logoY, targetW, targetH, null);
        } else {
            Font titleFont = new Font("Monospaced", Font.BOLD, Math.max(32, w / 15));
            g2.setFont(titleFont);
            g2.setColor(new Color(64, 224, 208));
            String title = "SPACE JAVA";
            int titleW = g2.getFontMetrics().stringWidth(title);
            g2.drawString(title, (w - titleW) / 2, h / 4);
        }

        // write the menu options
        Font menuFont = new Font("SansSerif", Font.BOLD, Math.max(30, w / 20));
        g2.setFont(menuFont);
        for (int i = 0; i < controller.getOptions().size(); i++) {
            String option = controller.getOptions().get(i);
            // calculate the position of the option
            int optionW = fm.stringWidth(option);
            int x = (w - optionW) / 2; // center the option horizontally
            int y = (startY + 40) + i * gap; // position the option vertically with a gap

            boolean selected = controller.getSelectedIndex() == i;
            boolean blink = selected && controller.isBlinkOn();

            if (selected) {
                // questa è l'ombra della freccia e della scritta normale
                g2.setColor(Color.BLACK);
                g2.drawString(">", x - 40 + 2, y + 2);
                g2.drawString(option, x + 2, y + 2);

                // questa è la freccia e la scritta normale
                g2.setColor(blink ? Color.WHITE : new Color(57, 255, 20));
                g2.drawString(">", x - 40, y);
                g2.setColor(blink ? new Color(57, 255, 20) : Color.WHITE);
                g2.drawString(option, x, y);
            } else {
                // queta è l'ombra della scritta normale
                g2.setColor(Color.BLACK);
                g2.drawString(option, x + 2, y + 2);

                g2.setColor(Color.LIGHT_GRAY);
                g2.drawString(option, x, y);
            }
        }

        // write the instuction at the bottom of the screen
        String instruction = "Usa frecce su/giù e Invio";
        g2.setFont(new Font("SansSerif", Font.BOLD, Math.max(12, w / 80)));
        g2.setColor(Color.LIGHT_GRAY);
        int instructionW = fm.stringWidth(instruction);
        g2.drawString(instruction, (w - instructionW) / 2, h - 40);
    }

    public void stop() {
        controller.stop();
    }

    private Image loadImage(String path) {
        try (InputStream in = getClass().getResourceAsStream(path)) {
            return ImageIO.read(Objects.requireNonNull(in));
        } catch (IOException ignored) {
            System.out.println(ignored.getMessage());
        }
        return null;
    }
}
