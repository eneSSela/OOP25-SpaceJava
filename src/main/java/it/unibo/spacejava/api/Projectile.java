package it.unibo.spacejava.api;

import it.unibo.spacejava.Position;

public interface Projectile {

    void setPosition(Position pos);
    
    /**
     * Erminio
     * @return Position
     */
    Position getPosition();

    int getLenght();

    int getWidth();
}
