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
}
