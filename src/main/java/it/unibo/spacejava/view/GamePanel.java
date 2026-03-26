package it.unibo.spacejava.view;

import java.awt.Color;

import javax.swing.JPanel;

public class GamePanel extends JPanel{
    
    public GamePanel(int width, int height) {
        super.setSize(width, height);
        super.setBackground(Color.BLACK);
    }
}
