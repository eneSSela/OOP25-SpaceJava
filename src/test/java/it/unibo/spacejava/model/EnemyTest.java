package it.unibo.spacejava.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.controller.EnemyProjectileController;
import it.unibo.spacejava.model.enemies.EnemyFactory;

/**
 * Classe di test per i nemici e EnemyFactory.
 */
final class EnemyTest {
    private static final int MAXDMG = 999;
    private static final int SCREEN_HEIGTH = 576;
    private static final int DEFAULT_TANK_HEALTH = 3;
    private static final int DEFAULT_TANK_DAMAGE = 1;
    private static final int DEFAULT_RED_HEALTH = 1;
    private static final int DEFAULT_RED_DAMAGE = 2;
    private static final int DEFAULT_BOSS_HEALTH = 20;
    private static final int DEFAULT_BOSS_DAMAGE = 2;
    private static final int DEFAULT_BASE_HEALTH = 1;
    private static final int DEFAULT_BASE_DAMAGE = 1;
    private static final int BOSS_HEALT_UPGRADE = 5;

    //Verifica che l'inizializzazione dei nemici sia corretta, testando EnemyFactory e dei metodi getter di AbstractEnemy.
    @Test
    void testInitialization() {
        final Position startingPosition = new Position(100, 100);
        final Enemy baseEnemy = EnemyFactory.createEnemy(EnemyType.BASE,
            startingPosition,
            DEFAULT_BASE_HEALTH,
            DEFAULT_BASE_DAMAGE);

        //Controllo che la posizione sia stata assegnata correttamente.
        assertEquals(startingPosition, baseEnemy.getPosition());
        //Controllo che le statistiche abbiano senso.
        assertEquals(baseEnemy.getType(), EnemyType.BASE);
        assertTrue(baseEnemy.getHealth() > 0);
        assertTrue(baseEnemy.getHeight() > 0);
        assertTrue(baseEnemy.getWidth() > 0);

        final Enemy tankEnemy = EnemyFactory.createEnemy(EnemyType.TANK,
            startingPosition,
            DEFAULT_TANK_HEALTH,
            DEFAULT_TANK_DAMAGE);

        //Controllo che la posizione sia stata assegnata correttamente.
        assertEquals(startingPosition, tankEnemy.getPosition());
        //Controllo che le statistiche abbiano senso.
        assertEquals(tankEnemy.getType(), EnemyType.TANK);
        assertTrue(tankEnemy.getHealth() > 0);
        assertTrue(tankEnemy.getHeight() > 0);
        assertTrue(tankEnemy.getWidth() > 0);

        final Enemy bossEnemy = EnemyFactory.createEnemy(EnemyType.BOSS,
            startingPosition,
            DEFAULT_BOSS_HEALTH,
            DEFAULT_BOSS_DAMAGE);

        //Controllo che la posizione sia stata assegnata correttamente.
        assertEquals(startingPosition, bossEnemy.getPosition());
        //Controllo che le statistiche abbiano senso.
        assertEquals(bossEnemy.getType(), EnemyType.BOSS);
        assertTrue(bossEnemy.getHealth() > 0);
        assertTrue(bossEnemy.getHeight() > 0);
        assertTrue(bossEnemy.getWidth() > 0);

        final Enemy redEnemy = EnemyFactory.createEnemy(EnemyType.RED,
            startingPosition,
            DEFAULT_RED_HEALTH,
            DEFAULT_RED_DAMAGE);

        //Controllo che la posizione sia stata assegnata correttamente.
        assertEquals(startingPosition, redEnemy.getPosition());
        //Controllo che le statistiche abbiano senso.
        assertEquals(redEnemy.getType(), EnemyType.RED);
        assertTrue(redEnemy.getHealth() > 0);
        assertTrue(redEnemy.getHeight() > 0);
        assertTrue(redEnemy.getWidth() > 0);
    }

    /**
     * Testa i metodi per ricavare la vita, danneggiare o verificare la morte dei nemici.
     */
    @Test
    void testTakeDamageAndDeath() {
        //Controllo nemico base.
        final Enemy baseEnemy = EnemyFactory.createEnemy(EnemyType.BASE,
            new Position(0, 0),
            DEFAULT_BASE_HEALTH,
            DEFAULT_BASE_DAMAGE);
        assertEquals(baseEnemy.getHealth(), 1);
        baseEnemy.takeDamage(1);
        //Controllo che il nemico sia effettivamente morto.
        assertTrue(baseEnemy.isDead());

        //Controllo nemico tank.
        final Enemy tankEnemy = EnemyFactory.createEnemy(EnemyType.TANK,
            new Position(0, 0),
            DEFAULT_TANK_HEALTH,
            DEFAULT_TANK_DAMAGE);
        final int startingTankHealth = tankEnemy.getHealth();
        tankEnemy.takeDamage(1);
        //Controllo che la vita sia effettivamente diminuita.
        assertTrue(startingTankHealth > tankEnemy.getHealth());
        tankEnemy.takeDamage(MAXDMG);
        //Controllo che il nemico sia effettivamente morto.
        assertTrue(tankEnemy.isDead());

        //Controllo nemico red.
        final Enemy redEnemy = EnemyFactory.createEnemy(EnemyType.RED,
            new Position(0, 0),
            DEFAULT_RED_HEALTH,
            DEFAULT_RED_DAMAGE);
        final int startingRedHealth = redEnemy.getHealth();
        redEnemy.takeDamage(1);
        //Controllo che la vita sia effettivamente diminuita.
        assertTrue(startingRedHealth > redEnemy.getHealth());
        redEnemy.takeDamage(MAXDMG);
        //Controllo che il nemico sia effettivamente morto.
        assertTrue(redEnemy.isDead());

        //Controllo nemico boss.
        final Enemy bossEnemy = EnemyFactory.createEnemy(EnemyType.BOSS,
            new Position(0, 0),
            DEFAULT_BOSS_HEALTH,
            DEFAULT_BOSS_DAMAGE);
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
        final EnemyProjectileController projectileController = new EnemyProjectileController(SCREEN_HEIGTH);
        final Position startingPosition = new Position(100, 100);
        final Enemy enemy = EnemyFactory.createEnemy(EnemyType.BASE,
            startingPosition,
            DEFAULT_BASE_HEALTH,
            DEFAULT_BASE_DAMAGE);
        enemy.attack(projectileController);
        //Controllo se un proiettile è stato effettivamente aggiunto alla lista dei proiettili nemici.
        assertFalse(projectileController.getProjectileList().isEmpty(), "Il nemico non ha sparato alcun proiettile");
        final Position projPos = projectileController.getProjectileList().get(0).getPosition();
        //Controllo che il proiettile venga generato sotto al corrispondente nemico.
        assertTrue(projPos.getY() >= enemy.getPosition().getY(), 
                    "Il proiettile deve spawnare all'altezza del nemico o più in basso");
        assertTrue(projPos.getX() >= enemy.getPosition().getX()
                   && projPos.getX() <= enemy.getPosition().getX() + enemy.getWidth(),
                    "Il proiettile deve spawnare lungo l'asse X del nemico");
    }

    /**
     * Testa l'upgrade dei nemici.
     */
    @Test
    void testEnemyUpgrade() {
        final Position pos = new Position(0, 0);
        EnemyProjectileController projController = new EnemyProjectileController(SCREEN_HEIGTH);

        //Controllo il miglioramento del danno e vita del nemico base.
        final Enemy unupgradedBase = EnemyFactory.createEnemy(EnemyType.BASE,
            pos,
            DEFAULT_BASE_HEALTH,
            DEFAULT_BASE_DAMAGE);
        final Enemy upgradedBase = EnemyFactory.createEnemy(EnemyType.BASE,
            pos,
            DEFAULT_BASE_HEALTH + 1,
            DEFAULT_BASE_DAMAGE + 1);
        assertTrue(upgradedBase.getHealth() > unupgradedBase.getHealth());
        unupgradedBase.attack(projController);
        final int startingBaseDmg = projController.getProjectileList().get(0).getDamage();
        projController = new EnemyProjectileController(SCREEN_HEIGTH);
        upgradedBase.attack(projController);
        final int upgradedBaseDmg = projController.getProjectileList().get(0).getDamage();
        projController = new EnemyProjectileController(SCREEN_HEIGTH);
        assertTrue(upgradedBaseDmg > startingBaseDmg);

        //Controllo il miglioramento della vita e danno del nemico tank.
        final Enemy unupgradedTank = EnemyFactory.createEnemy(EnemyType.TANK,
            pos,
            DEFAULT_TANK_HEALTH,
            DEFAULT_TANK_DAMAGE);
        final Enemy upgradedTank = EnemyFactory.createEnemy(EnemyType.TANK,
            pos,
            DEFAULT_TANK_HEALTH + 1,
            DEFAULT_TANK_DAMAGE + 1);
        assertTrue(upgradedTank.getHealth() > unupgradedTank.getHealth());
        unupgradedTank.attack(projController);
        final int baseTankDmg = projController.getProjectileList().get(0).getDamage();
        projController = new EnemyProjectileController(SCREEN_HEIGTH);
        upgradedTank.attack(projController);
        final int upgradedTankDmg = projController.getProjectileList().get(0).getDamage();
        projController = new EnemyProjectileController(SCREEN_HEIGTH);
        assertTrue(upgradedTankDmg > baseTankDmg);

        //Controllo il miglioramento della vita e del danno del nemico red.
        final Enemy unupgradedRed = EnemyFactory.createEnemy(EnemyType.RED,
            pos,
            DEFAULT_RED_HEALTH,
            DEFAULT_RED_DAMAGE);
        final Enemy upgradedRed = EnemyFactory.createEnemy(EnemyType.RED,
            pos,
            DEFAULT_RED_HEALTH + 1,
            DEFAULT_RED_DAMAGE + 1);
        assertTrue(upgradedRed.getHealth() > unupgradedRed.getHealth());
        unupgradedRed.attack(projController);
        final int baseRedDmg = projController.getProjectileList().get(0).getDamage();
        projController = new EnemyProjectileController(SCREEN_HEIGTH);
        upgradedRed.attack(projController);
        final int upgradedRedDmg = projController.getProjectileList().get(0).getDamage();
        projController = new EnemyProjectileController(SCREEN_HEIGTH);
        assertTrue(upgradedRedDmg > baseRedDmg);

        //Controllo il miglioramento del danno e vita del nemico boss.
        final Enemy unupgradedBoss = EnemyFactory.createEnemy(EnemyType.BOSS,
            pos,
            DEFAULT_BOSS_HEALTH,
            DEFAULT_BOSS_DAMAGE);
        final Enemy upgradedBoss = EnemyFactory.createEnemy(EnemyType.BOSS,
            pos,
            DEFAULT_BOSS_HEALTH + BOSS_HEALT_UPGRADE,
            DEFAULT_BOSS_DAMAGE + 1);
        assertTrue(upgradedBoss.getHealth() > unupgradedBoss.getHealth());
        unupgradedBoss.attack(projController);
        final int baseBossDmg = projController.getProjectileList().get(0).getDamage();
        projController = new EnemyProjectileController(SCREEN_HEIGTH);
        upgradedBoss.attack(projController);
        final int upgradedBossDmg = projController.getProjectileList().get(0).getDamage();
        assertTrue(upgradedBossDmg > baseBossDmg);
    }
}
