package it.unibo.spacejava.model;

import it.unibo.spacejava.Position;

public class PlayerShip {
    private final Position position;
    private final double width = 64.0;
    private final double height = 64.0;
    private final double speed = 300.0;

    public PlayerShip(double startX, double startY) {
        this.position = new Position(startX, startY);
    }

    public Position getPosition() {
        return position;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void moveLeft(double delta, double limit) {
        double newX = position.getX() - (speed * delta);
        position.setX(Math.max(newX, limit)); //impedisce di uscire dallo schermo a sinistra
    }

    public void moveRight(double delta, double limit) {
        double newX = position.getX() + (speed * delta);
        position.setX(Math.min(newX, limit - width)); //impedisce di uscire dallo schermo a destra
    }
}
