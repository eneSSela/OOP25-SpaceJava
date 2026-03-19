package it.unibo.spacejava.model;


import javax.swing.JFrame;


import it.unibo.spacejava.api.GameManger;
import it.unibo.spacejava.view.GamePanel;

public class GameManagerImpl implements GameManger, Runnable{

    private Thread gameThread;
    private final int FPS = 60;
    private int tileSize = 48; // 16 * 3 (scala originale)
    private int screenWidth = tileSize * 16;
    private int screenHeight = tileSize * 12;
    private  GamePanel panel = new GamePanel(screenWidth, screenHeight);
    private KeyHandler keyHandler = new KeyHandler();

    @Override
    public void startGame() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        
        panel.addKeyListener(keyHandler);
        panel.setFocusable(true);

        window.add(panel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Ensure the game panel has keyboard focus so key events are received
        panel.requestFocusInWindow();

        this.startThreadGame();
    }

    private void startThreadGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void pauseGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pauseGame'");
    }

    @Override
    public void endGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'endGame'");
    }

    @Override
    public void restartGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'restartGame'");
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                

                panel.repaint();
                frames++;
                delta--;
            }

            

            if (System.currentTimeMillis() - timer >= 1000) {
                System.out.println("FPS: " + frames);
                frames = 0;
                timer += 1000;
            }
        }
    }
}
