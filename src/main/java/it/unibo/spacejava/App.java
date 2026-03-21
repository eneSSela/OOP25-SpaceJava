package it.unibo.spacejava;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibo.spacejava.controller.StartMenuController;
import it.unibo.spacejava.model.StartMenuModel;
import it.unibo.spacejava.view.GamePanel;
import it.unibo.spacejava.view.StartMenuView;

/**
 * Use it for lauch the game.
 */
public class App {
    public static void main(final String... args) {
        int tileSize = 48; // 16 * 3 (scala originale)
        int screenWidth = tileSize * 16;
        int screenHeight = tileSize * 12;

        JFrame window = new JFrame("SpaceJava");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        CardLayout cardLayout = new CardLayout();
        JPanel cards = new JPanel(cardLayout);

        GamePanel gamePanel = new GamePanel(screenWidth, screenHeight);
        gamePanel.setPreferredSize(new Dimension(screenWidth, screenHeight));

        StartMenuModel model = new StartMenuModel();
        StartMenuView menuView = new StartMenuView(model);

        StartMenuController controller = new StartMenuController(model, menuView,
            () -> {
                cardLayout.show(cards, "GAME");
                gamePanel.requestFocusInWindow();
            },
            () -> System.exit(0)
        );

        menuView.addKeyListener(controller);

        cards.add(menuView, "MENU");
        cards.add(gamePanel, "GAME");

        window.setContentPane(cards);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        menuView.requestFocusInWindow();
    }
}
