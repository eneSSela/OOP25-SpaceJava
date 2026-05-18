package it.unibo.spacejava;

import java.awt.Image;
import java.awt.Toolkit;

public class Utils {
    public static Image loadImage(String paths) {
        if (paths == null || paths.isBlank()) {
            return null;
        }
        return Toolkit.getDefaultToolkit().getImage(Utils.class.getResource(paths));
    }

    /*Metodo helper per controllare se due rettangoli si sovrappongono (AABB).
    Per implementare la collisione tra i proiettili dei nemici e il giocatore, 
    useremo un algoritmo molto comune nello sviluppo di giochi in 
    2D chiamato AABB (Axis-Aligned Bounding Box). In parole povere, immagina 
    di disegnare un rettangolo invisibile attorno al giocatore e uno attorno 
    al proiettile: se i due rettangoli si sovrappongono, c'è una collisione.*/
    public static boolean isColliding(Position pos1, double w1, double h1, Position pos2, double w2, double h2) {
        return pos1.getX() < pos2.getX() + w2 &&
               pos1.getX() + w1 > pos2.getX() &&
               pos1.getY() < pos2.getY() + h2 &&
               pos1.getY() + h1 > pos2.getY();
    }
}
