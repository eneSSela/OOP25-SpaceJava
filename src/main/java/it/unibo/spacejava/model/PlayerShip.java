package it.unibo.spacejava.model;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.Skin;
import it.unibo.spacejava.controller.PlayerProjectileController;

public class PlayerShip {
    private final Position position;
    private final double width = 50.0;
    private final double height = 55.0;
    private final double speed = 300.0;
    private int health = 3;
    private Skin currentSkin;

    public PlayerShip(double startX, double startY, Skin defaultSkin) {
        this.position = new Position(startX, startY);
        this.currentSkin = defaultSkin;
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

    public Skin getSkin() {
        return currentSkin;
    }

    public void setSkin(Skin newSkin) {
        this.currentSkin = newSkin;
    }

    public void moveLeft(double delta, double limit) {
        double newX = position.getX() - (speed * delta);
        position.setX(Math.max(newX, limit)); //impedisce di uscire dallo schermo a sinistra
    }

    public void moveRight(double delta, double limit) {
        double newX = position.getX() + (speed * delta);
        position.setX(Math.min(newX, limit - width)); //impedisce di uscire dallo schermo a destra
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    public void shoot() {
        int projWidth = 10;
        //Centra il proiettile rispetto alla navicella
        double startX = position.getX() + (getWidth() / 2) - (projWidth / 2.0);
        double startY = position.getY();
        PlayerProjectileController.getProjectileList().add(new ProjectileImpl(new Position(startX, startY), 40, projWidth));
    }
}
