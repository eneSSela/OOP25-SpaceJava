package it.unibo.spacejava.model.enemies;

import it.unibo.spacejava.Position;
import it.unibo.spacejava.api.Enemy;
import it.unibo.spacejava.model.EnemyType;

/**
 * Classe factory che gestisce la creazione dei singoli nemici.
 */
public class EnemyFactory {

    /**
     * Crea un nemico in base al tipo e posizione iniziali dati.
     * 
     * @param type tipo di nemico da creare
     * @param startPosition posizione iniziale per il nemico da creare
     * @return lo specifico nemico creato
     */
    public Enemy createEnemy(final EnemyType type, final Position startPosition) {
        switch (type) {
            case BASE:
                return new BaseEnemy(startPosition);

            case TANK:
                return new TankEnemy(startPosition);

            case RED:
                return new RedEnemy(startPosition);

            case BOSS:
                return new BossEnemy(startPosition);

        }

        throw new IllegalArgumentException("Errore selezione tipo di nemico");
    }
}
