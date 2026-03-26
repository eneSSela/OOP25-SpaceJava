package it.unibo.spacejava.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import it.unibo.spacejava.model.StartMenuModel;

public class StartMenuView extends JPanel {

    private final StartMenuModel model;
    private final Timer blinkTimer;

    public StartMenuView(StartMenuModel model) {
        this.model = model;
        setBackground(Color.BLACK);
        setFocusable(true);

        model.addChangeListener(this::repaint);

        blinkTimer = new Timer(500, e -> model.setBlinkOn(!model.isBlinkOn()));
        blinkTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        /*
        for (int y = 0; y < h; y += 5) {
            float ratio = (float) y / h;
            g2.setColor(new Color(0, 0, 10 + (int) (150 * (1 - ratio))));
            g2.fillRect(0, y, w, 5);
        }
        */

        Font titleFont = new Font("Monospaced", Font.BOLD, Math.max(32, w / 15));
        g2.setFont(titleFont);
        g2.setColor(new Color(64, 224, 208));
        String title = "SPACE JAVA";
        int titleW = g2.getFontMetrics().stringWidth(title);
        g2.drawString(title, (w - titleW) / 2, h / 4);

        Font menuFont = new Font("Monospaced", Font.BOLD, Math.max(24, w / 30));
        g2.setFont(menuFont);
        FontMetrics fm = g2.getFontMetrics();

        int startY = h / 2;
        int gap = fm.getHeight() + 12;

        for (int i = 0; i < model.getOptions().size(); i++) {
            String option = model.getOptions().get(i);
            int optionW = fm.stringWidth(option);
            int x = (w - optionW) / 2;
            int y = startY + i * gap;

            boolean selected = model.getSelectedIndex() == i;
            boolean blink = selected && model.isBlinkOn();

            if (selected) {
                g2.setColor(blink ? Color.YELLOW : Color.ORANGE);
                //g2.setColor(Color.ORANGE);
                g2.drawString(">", x - 40, y);
                g2.setColor(blink ? Color.WHITE : Color.LIGHT_GRAY);
                g2.drawString(option, x, y);
            } else {
                g2.setColor(Color.WHITE);
                g2.drawString(option, x, y);
            }
        }

        g2.setFont(new Font("Monospaced", Font.PLAIN, Math.max(12, w / 80)));
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString("Usa frecce su/giù e Invio", w / 3, h - 40);
    }

    public void stop() {
        blinkTimer.stop();
    }
}
