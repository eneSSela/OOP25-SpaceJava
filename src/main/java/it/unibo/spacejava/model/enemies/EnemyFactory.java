package it.unibo.spacejava.model.enemies;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.model.EnemyType;

/**
 * Classe factory che gestisce la creazione dei singoli nemici.
 */
public final class EnemyFactory {

    private EnemyFactory() {
    }

    /**
     * Crea un nemico in base al tipo e posizione iniziali dati.
     * 
     * @param type tipo di nemico da creare
     * @param startPosition posizione iniziale per il nemico da creare
     * @param health vita del nemico da creare
     * @param damage danno del nemico da creare
     * @return lo specifico nemico creato
     */
    public static Enemy createEnemy(final EnemyType type, final Position startPosition, final int health, final int damage) {
        switch (type) {
            case BASE:
                return new BaseEnemy(startPosition, health, damage);

            case TANK:
                return new TankEnemy(startPosition, health, damage);

            case RED:
                return new RedEnemy(startPosition, health, damage);

            case BOSS:
                return new BossEnemy(startPosition, health, damage);

        }

        throw new IllegalArgumentException("Errore selezione tipo di nemico");
    }
}
