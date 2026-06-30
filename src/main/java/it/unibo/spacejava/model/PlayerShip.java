package it.unibo.spacejava.model;

import java.util.Objects;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.spacejava.Position;
import it.unibo.spacejava.Skin;
import it.unibo.spacejava.api.Score;

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
    private final Score score;

    private double dynamicSpeed = SPEED;
    private double shootCooldownMultiplier = 1.0;
    private int dynamicDamage = 1;
    private int projectileWidthDynamic = 10;
    private int shieldCharges; 

    /**
     * Costruisce una PlayerShip con posizione e skin iniziali.
     *
     * @param startX la coordinata iniziale X
     * @param startY la coordinata iniziale Y
     * @param defaultSkin la skin di default
     * @param sharedScore the shared score
     */
    @SuppressFBWarnings(value = "EI2", justification = "The score is shared intentionally")
    public PlayerShip(final int startX, final int startY, final Skin defaultSkin, final Score sharedScore) {
        this.position = new Position(startX, startY);
        this.currentSkin = defaultSkin;
        this.score = sharedScore;
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
     * Restituisce la rappresentazione del punteggio del giocatore.
     * 
     * @return l'oggetto Score associaot al giocatore
     */
    public Score getScore() {
        return Objects.requireNonNull(this.score, "score ca not null");
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
        final int newX = position.getX() - (int) (dynamicSpeed * delta);
        position.setX(Math.max(newX, (int) limit));
    }

    /**
     * Muove la navicella verso destra entro il limite dato.
     *
     * @param delta il tempo trascorso in secondi
     * @param limit il limite destro
     */
    public void moveRight(final double delta, final double limit) {
        final int newX = position.getX() + (int) (dynamicSpeed * delta);
        position.setX(Math.min(newX, (int) (limit - WIDTH)));
    }

    /**
     * Restituisce la salute corrente della navicella.
     *
     * @return la salute corrente
     */
    public int getHealth() {
        return health;
    }

    /**
     * Riduce la salute della navicella.
     *
     * @param damage il danno ricevuto
     */
    public void takeDamage(final int damage) {
        if (this.shieldCharges > 0) {
            this.shieldCharges--;
        } else {
            this.health -= damage;
        }
    }

    /**
     * Controlla se la navicella è distrutta.
     *
     * @return true se la salute è minore o uguale a zero
     */
    public boolean isDead() {
        return this.health <= 0;
    }

    /**
     * Add max health.
     * 
     * @param amount the quantity to add
     */
    public void addMaxHealth(final int amount) {
        this.health += amount;
    }

    /**
     * Multiply the movement speed.
     * 
     * @param factor the multiplier
     */
    public void multiplySpeed(final float factor) {
        this.dynamicSpeed *= factor;
    }

    /**
     * Reduce the time of cooldown of the shots.
     * 
     * @param factor the multiplier
     */
    public void multiplyShootCooldown(final float factor) {
        this.shootCooldownMultiplier *= factor;
    }

    /**
     * Return the cooldown multiplier.
     * 
     * @return the multiplier
     */
    public double getShootCooldownMultiplier() {
        return this.shootCooldownMultiplier;
    }

    /**
     * Increase the damage caused.
     * 
     * @param amount the quantity of damage to add
     */
    public void addDamage(final int amount) {
        this.dynamicDamage += amount;
    }

    /**
     * Return the current damage.
     * 
     * @return the dynamic damage
     */
    public int getDynamicDamage() {
        return this.dynamicDamage;
    }

    /**
     * Multiply the width of the projectiles.
     * 
     * @param factor the multiplier
     */
    public void multiplyProjectileWidth(final float factor) {
        this.projectileWidthDynamic = (int) (this.projectileWidthDynamic * factor);
    }

    /**
     * Return the width of the current projectile.
     * 
     * @return the width of the projectile
     */
    public int getProjectileWidthDynamic() {
        return this.projectileWidthDynamic;
    }

    /**
     * Add charges to the shield.
     * 
     * @param amount number of charges
     */
    public void addShieldCharges(final int amount) {
        this.shieldCharges += amount;
    }

    /**
     * Return the active charges of the shield.
     * 
     * @return the number of charges
     */
    public int getShieldCharges() {
        return this.shieldCharges;
    }
}
