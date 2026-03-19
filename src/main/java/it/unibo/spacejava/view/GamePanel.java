package it.unibo.spacejava.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel{
    
    public GamePanel(int width, int height) {
        super.setSize(width, height);
        super.setBackground(Color.BLACK);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
    }
}
