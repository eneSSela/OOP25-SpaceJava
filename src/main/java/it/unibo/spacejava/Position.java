package it.unibo.spacejava;

/**
 * Rappresenta la posizine di un oggetto, definito da due cordinate x e y.
 */
public final class Position {
    private double x;
    private double y;

    /**
     * Costruisce una posizioen dell'oggeto, date due cordinate x e y.
     * 
     * @param x cordinata che rappresenta la posizione orizontale dell'oggeto
     * @param y cordinata che rappresenta la posizione verticale dell'oggeto
     */
    public Position(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Restittuisce la cordinata x dell'oggetto.
     * 
     * @return codinata x dell'oggetto
     */
    public double getX() {
        return x;
    }

    /**
     * Restittuisce la cordinata y dell'oggetto.
     * 
     * @return codinata y dell'oggetto
     */
    public double getY() {
        return y;
    }

    /**
     * Imposta la cordinata x dell'oggetto.
     * 
     * @param x cordinata x dell'oggetto
     */
    public void setX(final double x) {
        this.x = x;
    }

    /**
     * Imposta la cordinata y dell'oggetto.
     * 
     * @param y cordinata y dell'oggetto
     */
    public void setY(final double y) {
        this.y = y;
    }
}
