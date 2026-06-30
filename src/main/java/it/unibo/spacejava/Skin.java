package it.unibo.spacejava;

/**
 * Classe che rappresetna una skin per il giocatore defina con un nome, 
 * il percorso delle'immagine al interno delle risorse del progetto,
 * il prezzo per poterla sbloccarla ed infine un dato boolean che indica se e bloccata o sbloccata.
 */
public final class Skin {

    private final String name;
    private final String imagePath;
    private final int price;
    private boolean isUnlocked;

    private final double scoreMultiplier;
    private final boolean hasBossShield;

    /**
     * Costruttore della classe Skin.
     * 
     * @param name il nome della skin 
     * @param imagePath percorse della risorsa dell'immagine della skin
     * @param price prezzo della skin
     * @param isUnlocked boolean che indica se la skin e sbloccata o bloccata
     * @param scoreMultiplier moltiplicatore punteggio
     * @param hasBossShield scudo del boss 
     */
    public Skin(final String name, final String imagePath, final int price, final boolean isUnlocked,
                final double scoreMultiplier, final boolean hasBossShield) {
        this.name = name;
        this.imagePath = imagePath;
        this.price = price;
        this.isUnlocked = isUnlocked;
        this.scoreMultiplier = scoreMultiplier;
        this.hasBossShield = hasBossShield;
    }

    /**
     * Getter del nome della skin.
     * 
     * @return il nome della skin
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter del percorso dell'immagine dell skinì.
     * 
     * @return percorso dell'immagine
     */
    public String getImagePath() {
        return this.imagePath;
    }

    /**
     * Getter del prezzo della skin.
     * 
     * @return interoi del prezzo della skin
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Getter che indica se la skin è sblocata o meno.
     * 
     * @return boolean indica lo stato del blocco della skin
     */
    public boolean isUnlock() {
        return this.isUnlocked;
    }

    /**
     * Metodo usato per sbloccre la skin, cambiano il suo stato da bloccata a sbloccata.
     */
    public void unlock() {
        this.isUnlocked = true;
    }

    /**
     * Retrives the score multiplier provided by this skin.
     * 
     * @return the score multiplier value
     */
    public double getScoreMultiplier() {
        return this.scoreMultiplier;
    }

    /**
     * Checks wheter this skin includes a boss shield.
     * 
     * @return true if the skin provides a boss shield, false otherwise
     */
    public boolean hasBossShield() {
        return this.hasBossShield;
    }
}
