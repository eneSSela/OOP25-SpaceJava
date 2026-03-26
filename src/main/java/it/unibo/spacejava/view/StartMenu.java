package it.unibo.spacejava.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JPanel;


public class StartMenu extends JPanel {

    private final List<String> optionMenu = List.of("Gioca", "Esci");
    private final AtomicInteger selected = new AtomicInteger(0);
    private Runnable onPlay = () -> {};
    private Runnable onExit = () -> {};
    private KeyListener keyHandler;

    public StartMenu(KeyListener keyHandler) {
        this.keyHandler = keyHandler;
        setBackground(Color.BLACK);
        setFocusable(true);
        //addKeyListener(this);
    }

    public void setOnPlay(Runnable r) {
        onPlay = r;
    }

    public void setOnExit(Runnable r) {
        onExit = r;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // sfondo stile arcade
        int w = getWidth();
        int h = getHeight();
        for (int y = 0; y < h; y += 5) {
            float ratio = (float) y / h;
            g2.setColor(new Color(0, 0, 10 + (int) (150 * (1 - ratio))));
            g2.fillRect(0, y, w, 5);
        }

        // logo
        Font titleFont = new Font("Monospaced", Font.BOLD, Math.max(32, w / 15));
        g2.setFont(titleFont);
        g2.setColor(Color.CYAN);
        String title = "SPACE  JAVA";
        int titleW = g2.getFontMetrics().stringWidth(title);
        g2.drawString(title, (w - titleW) / 2, h / 4);

        // menu
        Font menuFont = new Font("Monospaced", Font.BOLD, Math.max(24, w / 30));
        g2.setFont(menuFont);
        FontMetrics fm = g2.getFontMetrics();

        int startY = h / 2;
        int gap = fm.getHeight() + 12;

        for (int i = 0; i < optionMenu.size(); i++) {
            String option = optionMenu.get(i);
            int optionW = fm.stringWidth(option);
            int x = (w - optionW) / 2;
            int y = startY + i * gap;
            if (selected.get() == i) {
                g2.setColor(Color.YELLOW);
                g2.drawString(">", x - 40, y);
                g2.setColor(Color.ORANGE);
                g2.drawString(option, x, y);
            } else {
                g2.setColor(Color.WHITE);
                g2.drawString(option, x, y);
            }
        }

        // istruzioni
        g2.setFont(new Font("Monospaced", Font.PLAIN, Math.max(12, w / 80)));
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString("Usa frecce su/giù e Invio", w / 3, h - 40);
    }

    public Runnable getOnPlay() {
        return onPlay;
    }

    public Runnable getOnExit() {
        return onExit;
    }

    public AtomicInteger getSelected() {
        return selected;
    }

    public void setSelected(AtomicInteger selected) {
        this.selected.set(selected.get());
    }
    
    public String getSelectedOption() {
        return optionMenu.get(selected.get());
    }
    
    public List<String> getOptionMenu() {
        return optionMenu;
    }
}

