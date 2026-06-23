package it.unibo.spacejava.model;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.Skin;

/**
 * Classe che rappresenta la logica della navicella dell'utente.
 * Contiene informazioni sulla posizione, sulla salute e sui metodi di movimento.
 */
public final class PlayerShip {
    private static final double WIDTH = 50.0;
    private static final double HEIGHT = 55.0;
    private static final double SPEED = 300.0;

    private int health = 3;
    private final Position position;
    private Skin currentSkin;

    /**
     * Costruisce una PlayerShip con posizione e skin iniziali.
     *
     * @param startX la coordinata iniziale X
     * @param startY la coordinata iniziale Y
     * @param defaultSkin la skin di default
     */
    public PlayerShip(final int startX, final int startY, final Skin defaultSkin) {
        this.position = new Position(startX, startY);
        this.currentSkin = defaultSkin;
    }

    /**
     * Restituisce la posizione corrente della navicella.
     *
     * @return la posizione corrente
     */
    public Position getPosition() {
        return new Position(this.position.getX(), this.position.getY());
    }

    /**
     * Restituisce la larghezza della navicella.
     *
     * @return la larghezza
     */
    public double getWidth() {
        return WIDTH * 2;
    }

    /**
     * Restituisce l'altezza della navicella.
     *
     * @return l'altezza
     */
    public double getHeight() {
        return HEIGHT * 2;
    }

    /**
     * Restituisce la skin attiva della navicella.
     *
     * @return la skin corrente
     */
    public Skin getSkin() {
        return currentSkin;
    }

    /**
     * Imposta una nuova skin per la navicella.
     *
     * @param newSkin la nuova skin
     */
    public void setSkin(final Skin newSkin) {
        this.currentSkin = newSkin;
    }

    /**
     * Muove la navicella verso sinistra entro il limite dato.
     *
     * @param delta il tempo trascorso in secondi
     * @param limit il limite sinistro
     */
    public void moveLeft(final double delta, final double limit) {
        final int newX = position.getX() - (int) (SPEED * delta);
        position.setX(Math.max(newX, (int) limit));
    }

    /**
     * Muove la navicella verso destra entro il limite dato.
     *
     * @param delta il tempo trascorso in secondi
     * @param limit il limite destro
     */
    public void moveRight(final double delta, final double limit) {
        final int newX = position.getX() + (int) (SPEED * delta);
        position.setX(Math.min(newX, (int) (limit - WIDTH)));
    }

    /**
     * Restituisce la salute corrente della navicella.
     *
     * @return la salute corrente
     */
    public int getHealt() {
        return health;
    }

    /**
     * Riduce la salute della navicella.
     *
     * @param damage il danno ricevuto
     */
    public void takeDamage(final int damage) {
        this.health -= damage;
    }

    /**
     * Controlla se la navicella è distrutta.
     *
     * @return true se la salute è minore o uguale a zero
     */
    public boolean isDead() {
        return this.health <= 0;
    }

}
