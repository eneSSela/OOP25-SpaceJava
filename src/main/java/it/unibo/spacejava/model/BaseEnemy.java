package it.unibo.spacejava.model;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.api.Enemy;

public class BaseEnemy implements Enemy {
    private Position position;
    private int health;
    private final double width;
    private final double height;

    public BaseEnemy(double startX, double startY) {
        this.position = new Position(startX, startY);
        this.health = 1;
        this.width = 40.0;
        this.height = 40.0;
    }

    @Override
    public Position getPosition() { return this.position; }

    @Override
    public double getWidth() { return this.width; }

    @Override
    public double getHeight() { return this.height; }

    @Override
    public int getHealth() { return this.health; }

    @Override
    public void takeDamage(int damage) {
        this.health -= damage;
    }

    @Override
    public boolean isDead() {
        return this.health <= 0;
    }

    @Override
    public int getDamage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDamage'");
    }


    @Override
    public void attack() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attack'");
    }

    @Override
    public int getSize() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSize'");
    }
}