package it.unibo.spacejava;

import javax.swing.JFrame;

import it.unibo.spacejava.view.GamePanel;

/**
 * Use it for lauch the game.
 */
public class App {
    public static void main(final String... args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        GamePanel panel = new GamePanel(48*16, 48*12);
        window.add(panel);
        window.setVisible(true);
    }
}
