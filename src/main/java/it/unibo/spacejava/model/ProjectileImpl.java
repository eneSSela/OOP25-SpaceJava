package it.unibo.spacejava.model;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.api.Projectile;

public class ProjectileImpl implements Projectile {

    private Position pos;
    private final int length;
    private final int width = 30;

    public ProjectileImpl(Position pos, int length) {
        this.pos = pos;
        this.length = length;
    }

    @Override
    public void setPosition(Position pos) {
        this.pos = pos;
    }

    @Override
    public Position getPosition() {
        return pos;
    }

    @Override
    public int getLenght() {
        return length;
    }

    @Override
    public int getWidth() {
        return width;
    }

    
    
}
