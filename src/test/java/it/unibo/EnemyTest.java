package it.unibo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.controller.EnemyProjectileController;
import it.unibo.spacejava.model.EnemyType;
import it.unibo.spacejava.model.enemies.EnemyFactory;

/**
 * Classe di test per i nemici.
 */
public class EnemyTest {

    //Verifica che l'inizializzazione dei nemici sia corretta, testando EnemyFactory e i metodi di AbstractEnemy.
    @Test
    void testInitialization() {
        Position startingPosition = new Position(100, 100);
        Enemy basEnemy = EnemyFactory.createEnemy(EnemyType.BASE, startingPosition);

        //Controllo che la posizione sia stata assegnata correttamente.
        assertEquals(startingPosition, basEnemy.getPosition());
        //Controllo che le statistiche abbiano senso.
        assertTrue(basEnemy.getType() == EnemyType.BASE);
        assertTrue(basEnemy.getHealth() > 0);
        assertTrue(basEnemy.getHeight() > 0);
        assertTrue(basEnemy.getWidth() > 0);
    }

    /**
     * Testa i metodi per ricavare la vita, danneggiare o verificare la morte dei nemici.
     */
    @Test
    void testTakeDamageAndDeath() {
        //Controllo nemico base.
        Enemy baseEnemy = EnemyFactory.createEnemy(EnemyType.BASE, new Position(0, 0));
        assertTrue(baseEnemy.getHealth() == 1);
        baseEnemy.takeDamage(1);
        //Controllo che il nemico sia effettivamente morto.
        assertTrue(baseEnemy.isDead());

        //Controllo nemico tank.
        Enemy tankEnemy = EnemyFactory.createEnemy(EnemyType.TANK, new Position(0, 0));
        int startingTankHealth = tankEnemy.getHealth();
        tankEnemy.takeDamage(1);
        //Controllo che la vita sia effettivamente diminuita.
        assertTrue(startingTankHealth > tankEnemy.getHealth());
        tankEnemy.takeDamage(999);
        //Controllo che il nemico sia effettivamente morto.
        assertTrue(tankEnemy.isDead());

        //Controllo nemico red.
        Enemy redEnemy = EnemyFactory.createEnemy(EnemyType.RED, new Position(0, 0));
        int startingRedHealth = redEnemy.getHealth();
        redEnemy.takeDamage(1);
        //Controllo che la vita sia effettivamente diminuita.
        assertTrue(startingRedHealth > redEnemy.getHealth());
        redEnemy.takeDamage(999);
        //Controllo che il nemico sia effettivamente morto.
        assertTrue(redEnemy.isDead());

        //Controllo nemico boss.
        Enemy bossEnemy = EnemyFactory.createEnemy(EnemyType.BOSS, new Position(0, 0));
        int startingBossHealth = bossEnemy.getHealth();
        bossEnemy.takeDamage(1);
        //Controllo che la vita sia effettivamente diminuita.
        assertTrue(startingBossHealth > bossEnemy.getHealth());
        bossEnemy.takeDamage(999);
        //Controllo che il nemico sia effettivamente morto.
        assertTrue(bossEnemy.isDead());
    }

    /**
     * Testa la generazione dei proiettili.
     */
    @Test
    void testProjectileGeneration() {
        Position startingPosition = new Position(100, 100);
        Enemy enemy = EnemyFactory.createEnemy(EnemyType.BASE, startingPosition);
        enemy.attack();
        //Controllo se un proiettile è stato effettivamente aggiunto alla lista dei proiettili nemici.
        assertFalse(EnemyProjectileController.getProjectileList().isEmpty());
        //Controllo che il proiettile venga generato sotto al corrispondente nemico.
        assertTrue(EnemyProjectileController.getProjectileList().get(0).getPosition().getY() > enemy.getPosition().getY());
        assertEquals(EnemyProjectileController.getProjectileList().get(0).getPosition().getX(), enemy.getPosition().getX() + 10);
    }
}
