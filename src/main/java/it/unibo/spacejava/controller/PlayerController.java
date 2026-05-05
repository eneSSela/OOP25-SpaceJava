package it.unibo.spacejava.controller;

import java.util.ArrayList;
import java.util.List;

import it.unibo.spacejava.KeyHandler;
import it.unibo.spacejava.Position;
import it.unibo.spacejava.model.PlayerShip;
import it.unibo.spacejava.model.ProjectileImpl;

public class PlayerController {
    private final PlayerShip playerShip;
    private final KeyHandler keyHandler;
    private final double screenWidth;
    
    private final List<ProjectileImpl> projectiles = new ArrayList<>();
    private final double projectileSpeed = 400.0;

    private double shootCoolDown = 0.5; //Mezzo secondo tra uno sparo e l'altro
    private double timeSinceLastShot = shootCoolDown;

    public PlayerController(PlayerShip playerShip, KeyHandler keyHandler, double screenWidth) {
        this.playerShip = playerShip;
        this.keyHandler = keyHandler;
        this.screenWidth = screenWidth;
    }

    public void update(double delta) {
        //Gestione movimento
        if (keyHandler.leftPressed) {
            playerShip.moveLeft(delta, 0);
        }
        if (keyHandler.rightPressed) {
            playerShip.moveRight(delta, screenWidth);
        }

        //Gestione sparo
        timeSinceLastShot += delta;
        if (keyHandler.spacePressed && timeSinceLastShot >= shootCoolDown) {
            shoot();
            timeSinceLastShot = 0;
        }

        //Aggiornamento posizione proiettile (Y diminuisce perchè si muovono verso l'alto)
        for (ProjectileImpl p : projectiles) {
            double newY = p.getPosition().getY() - (projectileSpeed * delta);
            p.setPosition(new Position(p.getPosition().getX(), newY));
        }

        //Rimuove i proiettili usciti dallo schermo
        projectiles.removeIf(p -> p.getPosition().getY() < 0);
    }

    private void shoot() {
        //Centra il proiettile rispetto alla navicella
        double startX = playerShip.getPosition().getX() + (playerShip.getWidth() / 2);
        double startY = playerShip.getPosition().getY();
        projectiles.add(new ProjectileImpl(new Position(startX, startY), 40));
    }

    public PlayerShip getPlayerShip() {
        return playerShip;
    }

    public List<ProjectileImpl> getProjectiles() {
        return projectiles;
    }
}
