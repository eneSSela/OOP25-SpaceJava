package it.unibo.spacejava.model;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.api.Projectile;

/**
 * Implementazione del model per i proiettili del gioco. Usati sia dai nemici che dal utente,
 * e contiene iformazioni necceserie per la gestione dei poiettili, coma la posizone e le dimensioni.
 */
public final class ProjectileImpl implements Projectile {

    private Position pos;
    private final int length;
    private final int width;
    private final int damage;

    /**
     * Costruzione di un proiettile.
     * 
     * @param pos instazza del record Position che rappresenta le cordinate x ed y
     * @param length lunghezza del proiettile per la gestioen delle collisioni
     * @param width larghezza del proiettile per la gestioen delle collisioni
     * @param damage danno specifico del proiettile
     */
    public ProjectileImpl(final Position pos, final int length, final int width, final int damage) {
        this.pos = new Position(pos.getX(), pos.getY());
        this.length = length;
        this.width = width;
        this.damage = damage;
    }

    /**
     * Setter per impostare la poszioen al proiettile.
     * 
     * @param newPos nuova posizione da assegnare al proiettile
     */
    @Override
    public void setPosition(final Position newPos) {
        this.pos = new Position(newPos.getX(), newPos.getY());
    }

    /**
     * Getter erp la posiozne.
     * 
     * @return la posizioen attual del proiettile
     */
    @Override
    public Position getPosition() {
        return new Position(this.pos.getX(), this.pos.getY());
    }

    /**
     * Getter per restituire la lunghezza del proiettile.
     * 
     * @return la lunghezza del proiettile, usata per la gestione delle collisioni
     */
    @Override
    public int getLenght() {
        return length;
    }

    /**
     * Getter per resituire la larghezza del proiettile.
     * 
     * @return la larghezza del proiettile, usata per la gestione delle collisioni
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * Getter per resituire il danno del proiettile.
     * 
     * @return il danno del proiettile.
     */
    @Override
    public int getDamage() {
        return damage;
    }
}
