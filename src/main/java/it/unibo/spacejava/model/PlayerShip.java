package it.unibo.spacejava.model;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.Skin;
import it.unibo.spacejava.controller.PlayerProjectileController;

/**
 * Classe che rappresenta la logica della navicella dell'utente.
 * Contiene informazioni sulla posizione, sulla salute e sui metodi di movimento.
 */
public final class PlayerShip {
    private static final double WIDTH = 50.0;
    private static final double HEIGHT = 55.0;
    private static final double SPEED = 300.0;
    private static final int PROJECTILE_WIDTH = 10;
    private static final int PROJECTILE_LENGTH = 40;
    private static final int DAMAGE = 1;

    private int healt = 3;
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
        return WIDTH;
    }

    /**
     * Restituisce l'altezza della navicella.
     *
     * @return l'altezza
     */
    public double getHeight() {
        return HEIGHT;
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
        return healt;
    }

    /**
     * Riduce la salute della navicella.
     *
     * @param damage il danno ricevuto
     */
    public void takeDamage(final int damage) {
        this.healt -= damage;
    }

    /**
     * Controlla se la navicella è distrutta.
     *
     * @return true se la salute è minore o uguale a zero
     */
    public boolean isDead() {
        return this.healt <= 0;
    }

    /**
     * Spara un proiettile dal centro superiore della navicella.
     */
    public void shoot() {
        final int startX = position.getX() + (int) (getWidth() / 2) - (int) (PROJECTILE_WIDTH / 2.0);
        final int startY = position.getY();

        PlayerProjectileController.addProjectile(
                new ProjectileImpl(new Position(startX, startY), PROJECTILE_LENGTH, PROJECTILE_WIDTH, DAMAGE));
    }
}
