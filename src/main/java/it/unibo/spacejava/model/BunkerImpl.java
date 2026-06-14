package it.unibo.spacejava.model;

import java.util.Objects;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.api.Bunker;

/**
 * Implementazione di un bunker difensivo per il giocatore.
 */
public final class BunkerImpl implements Bunker {

    private final Position position;
    private final int width;
    private final int height;
    private int health;

    /**
     * Costruisce un nuovo bunker con la posizione, dimensione e salute specificate.
     * 
     * @param startX la coordinata X iniziale del bunker
     * @param startY la coordinata Y iniziale del bunker
     * @param widht la larghezza del bunker
     * @param height l'altezza del bunker
     * @param maxHealth i punti vita massimi del bunker
     */
    public BunkerImpl(final int startX, final int startY, final int widht, final int height, final int maxHealth) {
        this.position = new Position(startX, startY);
        this.width = widht;
        this.height = height;
        this.health = maxHealth;
    }

    @Override
    public Position getPosition() {
        return Objects.requireNonNull(this.position);
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void takeDamage(final int damage) {
        if (damage > 0) {
            this.health -= damage;
        }
    }

    @Override
    public boolean isDestroyed() {
        return this.health <= 0; 
    }
}
