package it.unibo.spacejava.model;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.api.Enemy;

import it.unibo.spacejava.controller.EnemyProjectileController;

public class BaseEnemy implements Enemy {
    private Position position;
    private int health = 1;
    private final double width = 40.0;
    private final double height = 40.0;
    private final int type = 0;
    private final int damage = 1;

    public BaseEnemy(double startX, double startY) {
        this.position = new Position(startX, startY);
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public double getHeight() {
        return this.height;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

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
        return damage;
    }


    @Override
    public void attack() {
        double startX = this.position.getX() + (this.width / 2 - 10);
        double startY = this.position.getY() + this.height;
        
        Position projectilePos = new Position(startX, startY);
        
        EnemyProjectileController.getProjectileList().add(new ProjectileImpl(projectilePos, 40, 30));

    }

    @Override
    public int getSize() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSize'");
    }

    @Override
    public int type() {
        return this.type;
    }
}