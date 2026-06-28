package it.unibo.spacejava.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import it.unibo.spacejava.api.Projectile;
import it.unibo.spacejava.KeyHandler;
import it.unibo.spacejava.Position;
import it.unibo.spacejava.Skin;
import it.unibo.spacejava.model.PlayerShip;
import it.unibo.spacejava.model.ProjectileImpl;
import it.unibo.spacejava.model.sound.SoundManagerImpl;

/**
 * La calsse che funge da controller del giocatore, gestice il movimento e lo sparo, 
 * altre verfiche come la collisione con i proiettili dei nemici,
 * e la gestione della skin del giocatore.
 */
public class PlayerController {

    private static final double SHOOT_COOL_DOWN = 0.5; //Mezzo secondo tra uno sparo e l'altro
    private static final String SHOOT_SOUND_PATH = "/audio/shoot.wav";
    private static final String HIT_SOUND_PATH = "/audio/hit.wav";

    private static final int PROJECTILE_WIDTH = 10;
    private static final int PROJECTILE_LENGTH = 40;
    private static final int DAMAGE = 1;

    private final PlayerShip playerShip;
    private final KeyHandler keyHandler;
    private final double screenWidth;

    private final PlayerProjectileController projectileController;
    private final EnemyProjectileController enemyProjectileController;

    private double timeSinceLastShot = SHOOT_COOL_DOWN;

    /**
     * Castruttore del PlayerController.
     * 
     * @param playerShip model del giocatore
     * @param keyHandler gestore degli input da tastiera
     * @param screenWidth larghezza delllo shermo per limitare il movimento del giocatore
     * @param projectileController il controller dei proiettili utente.
     * @param enemyProjectileController il controller dei proiettili nemici.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "Dependency injection is intended here")
    public PlayerController(
        final PlayerShip playerShip,
        final KeyHandler keyHandler,
        final PlayerProjectileController projectileController,
        final double screenWidth,
        final EnemyProjectileController enemyProjectileController) {
        this.playerShip = Objects.requireNonNull(playerShip, "Non può esser nullo");
        this.keyHandler = Objects.requireNonNull(keyHandler, "Non puo esser nullo");
        this.projectileController = Objects.requireNonNull(projectileController);
        this.screenWidth = screenWidth;
        this.enemyProjectileController = enemyProjectileController;
    }

    /**
     * Aggiorna lo stato del giocatore in base agli input ricevuti.
     * 
     * @param delta il tempo trascorso dall'ultimo aggioranmento, usato per calcolare il movimento e il tempo di sparo
     */
    public void update(final double delta) {
        //Gestione movimento
        if (keyHandler.isLeftPressed()) {
            playerShip.moveLeft(delta, 0);
        }
        if (keyHandler.isRightPressed()) {
            playerShip.moveRight(delta, screenWidth);
        }

        //Gestione sparo
        timeSinceLastShot += delta;
        if (keyHandler.isSpacePressed() && timeSinceLastShot >= SHOOT_COOL_DOWN) {
            handleShooting();
            timeSinceLastShot = 0;
        }
    }

    /**
     * Firing logic managed by the controller. Calculate the position and create the projectile
     */
    private void handleShooting() {
        final int startX = playerShip.getPosition().getX() + (int) (playerShip.getWidth() / 2) - (PROJECTILE_WIDTH / 2);
        final int startY = playerShip.getPosition().getY();

        final ProjectileImpl newProjectile = new ProjectileImpl(
            new Position(startX, startY), PROJECTILE_LENGTH, PROJECTILE_WIDTH, DAMAGE);
        projectileController.addProjectile(newProjectile);

        SoundManagerImpl.getInstance().playSound(SHOOT_SOUND_PATH);
    }

    /**
     * Metodo helper per controllare se due rettangoli si sovrappongono (AABB).
     * Per implementare la collisione tra i proiettili dei nemici e il giocatore, 
     * useremo un algoritmo molto comune nello sviluppo di giochi in 
     * 2D chiamato AABB (Axis-Aligned Bounding Box). In parole povere, immagina 
     * di disegnare un rettangolo invisibile attorno al giocatore e uno attorno 
     * al proiettile: se i due rettangoli si sovrappongono, c'è una collisione.
     * 
     * @param pos1 posizione del primo rettangolo (giocatore)
     * @param w1 larghezza del primo rettangolo
     * @param h1 altezza del primo rettangolo
     * @param pos2 posizione del secondo rettangolo (proiettile)
     * @param w2 larghezza del secondo rettangolo
     * @param h2 altezza del secondo rettangolo
     * @return true se i rettangoli si sovrappongono, false altrimenti
     */
    private boolean isColliding(
        final Position pos1,
        final double w1,
        final double h1,
        final Position pos2,
        final double w2,
        final double h2) { 
        return pos1.getX() < pos2.getX() + w2
            && pos1.getX() + w1 > pos2.getX()
            && pos1.getY() < pos2.getY() + h2
            && pos1.getY() + h1 > pos2.getY();
    }

    /**
     * Metodo che verifica le collisioni tra il giocatore e i proiettili nemici.
     */
    public void checkEnemyCollision() {
        //Recuperiamo la lista dei proiettili nemici
        final List<Projectile> enemyProjectiles = new ArrayList<>(enemyProjectileController.getProjectileList());

        for (final Projectile p : enemyProjectiles) {
            //Controlla se il rettangolo del player si sovrappone a quello del proiettile
            if (isColliding(playerShip.getPosition(), playerShip.getWidth(), playerShip.getHeight(), 
                            p.getPosition(), p.getWidth(), p.getLenght())) {
                playerShip.takeDamage(p.getDamage()); //Rimuove un punto vita
                enemyProjectileController.removeProjectile(p); //Rimuove il proiettile che ha colpito il giocatore
                System.out.println("Sei stato colpito! Vita rimanente: " + playerShip.getHealth()); //NOPMD

                SoundManagerImpl.getInstance().playSound(HIT_SOUND_PATH);
            }
        }
    }

    /**
     * Getter per la skin del giocatore.
     *
     * @return la skin che l'utente ha selezionata
     */
    public Skin getPlayerSkin() {
        return playerShip.getSkin();
    }

    /**
     * Getter per il model del gicoatore, utile per accedere a informazioni come la posizone, lav vita, ecc.
     * 
     * @return il model del giocatore
     */
    public PlayerShip getPlayerShip() {
        return Objects.requireNonNull(this.playerShip);
    }
}
