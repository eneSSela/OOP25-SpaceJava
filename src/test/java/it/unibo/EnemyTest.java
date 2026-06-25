package it.unibo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.controller.EnemyProjectileController;
import it.unibo.spacejava.model.EnemyType;
import it.unibo.spacejava.model.enemies.BossEnemy;
import it.unibo.spacejava.model.enemies.EnemyFactory;
import it.unibo.spacejava.model.enemies.RedEnemy;
import it.unibo.spacejava.model.enemies.TankEnemy;

/**
 * Classe di test per i nemici.
 */
final class EnemyTest {
    private static final int MAXDMG = 999;

    //Verifica che l'inizializzazione dei nemici sia corretta, testando EnemyFactory e i metodi di AbstractEnemy.
    @Test
    void testInitialization() {
        final Position startingPosition = new Position(100, 100);
        final Enemy basEnemy = EnemyFactory.createEnemy(EnemyType.BASE, startingPosition);

        //Controllo che la posizione sia stata assegnata correttamente.
        assertEquals(startingPosition, basEnemy.getPosition());
        //Controllo che le statistiche abbiano senso.
        assertEquals(basEnemy.getType(), EnemyType.BASE);
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
        final Enemy baseEnemy = EnemyFactory.createEnemy(EnemyType.BASE, new Position(0, 0));
        assertEquals(baseEnemy.getHealth(), 1);
        baseEnemy.takeDamage(1);
        //Controllo che il nemico sia effettivamente morto.
        assertTrue(baseEnemy.isDead());

        //Controllo nemico tank.
        final Enemy tankEnemy = EnemyFactory.createEnemy(EnemyType.TANK, new Position(0, 0));
        final int startingTankHealth = tankEnemy.getHealth();
        tankEnemy.takeDamage(1);
        //Controllo che la vita sia effettivamente diminuita.
        assertTrue(startingTankHealth > tankEnemy.getHealth());
        tankEnemy.takeDamage(MAXDMG);
        //Controllo che il nemico sia effettivamente morto.
        assertTrue(tankEnemy.isDead());

        //Controllo nemico red.
        final Enemy redEnemy = EnemyFactory.createEnemy(EnemyType.RED, new Position(0, 0));
        final int startingRedHealth = redEnemy.getHealth();
        redEnemy.takeDamage(1);
        //Controllo che la vita sia effettivamente diminuita.
        assertTrue(startingRedHealth > redEnemy.getHealth());
        redEnemy.takeDamage(MAXDMG);
        //Controllo che il nemico sia effettivamente morto.
        assertTrue(redEnemy.isDead());

        //Controllo nemico boss.
        final Enemy bossEnemy = EnemyFactory.createEnemy(EnemyType.BOSS, new Position(0, 0));
        final int startingBossHealth = bossEnemy.getHealth();
        bossEnemy.takeDamage(1);
        //Controllo che la vita sia effettivamente diminuita.
        assertTrue(startingBossHealth > bossEnemy.getHealth());
        bossEnemy.takeDamage(MAXDMG);
        //Controllo che il nemico sia effettivamente morto.
        assertTrue(bossEnemy.isDead());
    }

    /**
     * Testa la generazione dei proiettili.
     */
    @Test
    void testProjectileGeneration() {
        final Position startingPosition = new Position(100, 100);
        final Enemy enemy = EnemyFactory.createEnemy(EnemyType.BASE, startingPosition);
        enemy.attack();
        //Controllo se un proiettile è stato effettivamente aggiunto alla lista dei proiettili nemici.
        assertFalse(EnemyProjectileController.getProjectileList().isEmpty());
        //Controllo che il proiettile venga generato sotto al corrispondente nemico.
        assertTrue(EnemyProjectileController.getProjectileList().get(0).getPosition().getY() > enemy.getPosition().getY());
        assertEquals(EnemyProjectileController.getProjectileList().get(0).getPosition().getX(), enemy.getPosition().getX() + 10);
    }

    /**
     * Testa l'upgrade dei nemici.
     */
    @Test
    void testEnemyUpgrade() {
        final Position pos = new Position(0, 0);
        //Controllo il miglioramento della vita del nemico tank. 
        final Enemy unupgradedTank = EnemyFactory.createEnemy(EnemyType.TANK, pos);
        TankEnemy.upgrade();
        final Enemy upgradedTank = EnemyFactory.createEnemy(EnemyType.TANK, pos);
        assertTrue(upgradedTank.getHealth() > unupgradedTank.getHealth());
        //Controllo il miglioramento del danno del nemico red. 
        final int unupgradedRedDmg = RedEnemy.getHUDDamage();
        RedEnemy.upgrade();
        final int upgradedRedDmg = RedEnemy.getHUDDamage();
        assertTrue(upgradedRedDmg > unupgradedRedDmg);
        //Controllo il miglioramento del danno e vita del nemico boss.
        final Enemy unupgradedBoss = EnemyFactory.createEnemy(EnemyType.BOSS, pos);
        final int unupgradedBossDmg = BossEnemy.getHUDDamage();
        BossEnemy.upgrade();
        final Enemy upgradedBoss = EnemyFactory.createEnemy(EnemyType.BOSS, pos);
        final int upgradedBossDmg = BossEnemy.getHUDDamage();
        assertTrue(upgradedBoss.getHealth() > unupgradedBoss.getHealth());
        assertTrue(upgradedBossDmg > unupgradedBossDmg);
    }
}
