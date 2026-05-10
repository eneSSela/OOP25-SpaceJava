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
}
