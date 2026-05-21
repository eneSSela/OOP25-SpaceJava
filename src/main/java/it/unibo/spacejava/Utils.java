package it.unibo.spacejava;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.Objects;

/**
 * Classe di metodi di utility, che fornisce funzionalità comuni.
 */
public final class Utils {

    private Utils() {
        throw new UnsupportedOperationException("Questa è una classe di utilità e non può essere istanziata");
    }

    /**
     * Metodo per poter caricare l'immagine dato un percorso.
     * 
     * @param paths il percorso dell'immagine da caricare
     * @return l'immagine carcata, oppure null se il percorso è null o vuoto
     */
    public static Image loadImage(final String paths) {
        if (Objects.isNull(paths) || paths.isBlank()) {
            return null;
        }
        return Toolkit.getDefaultToolkit().getImage(Utils.class.getResource(paths));
    }

    /**
     * Metodo helper per controllare se due rettangoli si sovrappongono (AABB).
     * Per implementare la collisione tra i proiettili dei nemici e il giocatore, 
     * useremo un algoritmo molto comune nello sviluppo di giochi in 
     * 2D chiamato AABB (Axis-Aligned Bounding Box). In parole povere, immagina 
     * di disegnare un rettangolo invisibile attorno al giocatore e uno attorno 
     * al proiettile: se i due rettangoli si sovrappongono, c'è una collisione.
     *
     * @param pos1 posizione del primo oggetto
     * @param w1 larghezza del primo oggetto
     * @param h1 altezza del primo oggetto
     * @param pos2 posizione del secondo oggetto
     * @param w2 larghezza del secondo oggetto
     * @param h2 altezza del secondo oggetto
     * @return true se i due oggetti collidono, altrimenti false
     */
    public static boolean isColliding(
        final Position pos1, final double w1, final double h1, final Position pos2, final double w2, final double h2) {
        return pos1.getX() < pos2.getX() + w2
               &&
               pos1.getX() + w1 > pos2.getX()
               &&
               pos1.getY() < pos2.getY() + h2
               &&
               pos1.getY() + h1 > pos2.getY();
    }
}
