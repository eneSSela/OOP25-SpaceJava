package it.unibo.spacejava.model;

public class Skin {
    
    private final String name;
    private final String imagePath;
    private final int price;
    private boolean isUnlocked;
    
    public Skin(String name, String imagePath, int price, boolean isUnlocked) {
        this.name = name;
        this.imagePath = imagePath;
        this.price = price;
        this.isUnlocked = isUnlocked;
    }

    public String getName() { return this.name; }
    public String getImagePath() { return this.imagePath; }
    public int getPrice() { return this.price; }
    public boolean isUnlock() {return this.isUnlocked; }
    
    public void unlock() { this.isUnlocked = true; }
}
