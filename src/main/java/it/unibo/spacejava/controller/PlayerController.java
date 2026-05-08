package it.unibo.spacejava.controller;

import java.util.ArrayList;
import java.util.List;

import it.unibo.spacejava.KeyHandler;
import it.unibo.spacejava.Position;
import it.unibo.spacejava.model.PlayerShip;
import it.unibo.spacejava.model.ProjectileImpl;

import it.unibo.spacejava.model.sound.api.SoundManager;

public class PlayerController {
    private final PlayerShip playerShip;
    private final KeyHandler keyHandler;
    private final double screenWidth;

    private final SoundManager soundManager;

    private final List<ProjectileImpl> projectiles = new ArrayList<>();
    private final double projectileSpeed = 400.0;

    private double shootCoolDown = 0.5; //Mezzo secondo tra uno sparo e l'altro
    private double timeSinceLastShot = shootCoolDown;

    public PlayerController(PlayerShip playerShip, KeyHandler keyHandler, double screenWidth, SoundManager soundManager) {
        this.playerShip = playerShip;
        this.keyHandler = keyHandler;
        this.screenWidth = screenWidth;
        this.soundManager = soundManager; 
    }

    public void update(double delta) {
        //Gestione movimento
        if (keyHandler.isLeftPressed()) {
            playerShip.moveLeft(delta, 0);
        }
        if (keyHandler.isRightPressed()) {
            playerShip.moveRight(delta, screenWidth);
        }

        //Gestione sparo
        timeSinceLastShot += delta;
        if (keyHandler.isSpacePressed() && timeSinceLastShot >= shootCoolDown) {
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
        int projWidth = 10;
        //Centra il proiettile rispetto alla navicella
        double startX = playerShip.getPosition().getX() + (playerShip.getWidth() / 2);
        double startY = playerShip.getPosition().getY();
        projectiles.add(new ProjectileImpl(new Position(startX, startY), 40, projWidth));

        soundManager.playSound("/audio/shoot.wav");
    }

    /*Metodo helper per controllare se due rettangoli si sovrappongono (AABB).
    Per implementare la collisione tra i proiettili dei nemici e il giocatore, 
    useremo un algoritmo molto comune nello sviluppo di giochi in 
    2D chiamato AABB (Axis-Aligned Bounding Box). In parole povere, immagina 
    di disegnare un rettangolo invisibile attorno al giocatore e uno attorno 
    al proiettile: se i due rettangoli si sovrappongono, c'è una collisione.*/
    private boolean isColliding(Position pos1, double w1, double h1, Position pos2, double w2, double h2) {
        return pos1.getX() < pos2.getX() + w2 &&
               pos1.getX() + w1 > pos2.getX() &&
               pos1.getY() < pos2.getY() + h2 &&
               pos1.getY() + h1 > pos2.getY();
    }

    //Metodo che verifica le collisioni tra il giocatore e i proiettili nemici
    public void checkEnemyCollision() {
        //Recuperiamo la lista dei proiettili nemici
        List<ProjectileImpl> enemyProjectiles = EnemyProjectileController.getProjectileList();
        List<ProjectileImpl> projectilesToRemove = new ArrayList<>();

        for (ProjectileImpl p : enemyProjectiles) {
            //Controlla se il rettangolo del player si sovrappone a quello del proiettile
            if (isColliding(playerShip.getPosition(), playerShip.getWidth(), playerShip.getHeight(), p.getPosition(), p.getWidth(), p.getLenght())) {
                playerShip.takeDamage(1); //Rimuove un punto vita
                projectilesToRemove.add(p); //Segna il proiettile per la rimozione

                System.out.println("Sei stato colpito! Vita rimanente: " + playerShip.getHealth());

                soundManager.playSound("/audio/hit.wav");
            }
        }

        //Rimuove i proiettili che ci hanno colpito per non prendere danno doppio
        enemyProjectiles.removeAll(projectilesToRemove);
    }

    public PlayerShip getPlayerShip() {
        return playerShip;
    }

    public List<ProjectileImpl> getProjectiles() {
        return projectiles;
    }
}
