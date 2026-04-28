package it.unibo.spacejava.controller;

import java.util.ArrayList;
import java.util.List;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.model.ProjectileImpl;

public class EnemyProjectileController{

    private final int speed = 5;
    private static List<ProjectileImpl> projectiles = new ArrayList<>();
    private final int screenHeight;

    public EnemyProjectileController(int screenHeight) {
        this.screenHeight = screenHeight;
    } 

    /* public void addProjectile(Position pos, int size) {
        projectiles.add(new ProjectileImpl(pos, size));
    } */

    // Moves projectiles downwards
    public void update(double delta) {
        for (ProjectileImpl p : projectiles) {
            p.setPosition(new Position(p.getPosition().getX(), p.getPosition().getY() + speed));
        }

        //removes out of bounds projectiles
        projectiles.removeIf(p -> p.getPosition().getY() >= screenHeight);
    }

    public static List<ProjectileImpl> getProjectileList() {
        return projectiles;
    }
    
}
