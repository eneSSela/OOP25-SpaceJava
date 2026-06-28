package it.unibo.spacejava.model.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import it.unibo.spacejava.api.MenuObserver;
import it.unibo.spacejava.api.PowerUp;
import it.unibo.spacejava.controller.WaveManagerController;
import it.unibo.spacejava.model.PlayerShip;

/**
 * Model for the power-up screen.
 * Manages a power pool and selects 3 random powers at each wave.
 */
public final class PowerUpSelectionModel {
    private static final float SHIELD_MULT = 2.0f;
    private static final float TIME_MULT = 0.7f;
    private static final float FIRE_MULT = 0.4f;
    private static final float HULL_MULT = 1.0f;
    private static final float SPEED_MULT = 1.2f;
    private static final float LASER_MULT = 1.0f;

    private final List<PowerUp> proposedOptions;
    private int selectedIndex;
    private MenuObserver observer;

    /**
     * Model builder that generate the generates options for the menu.
     * 
     * @param waveManager the wave controller to apply the effects
     */
    public PowerUpSelectionModel(final WaveManagerController waveManager) {
        this.proposedOptions = generateThreeRandomPowerUps(waveManager);
        this.selectedIndex = 0;
    }

    /**
     * Generate 3 random power-up.
     * 
     * @param waveManager wave controller
     * @return list of powers
     */
    private List<PowerUp> generateThreeRandomPowerUps(final WaveManagerController waveManager) {
        final List<PowerUp> allPowerUps = new ArrayList<>();

        allPowerUps.add(new PowerUp() {

            @Override
            public void applayEffect(final PlayerShip player) {
                player.addShieldCharges(2);
            }

            @Override
            public String getType() {
                return "Scudo Energetico";
            }

            @Override
            public String getDescription() {
                return "Assorbe 2 colpi nemici diretti";
            }

            @Override
            public float getMultipler() {
                return SHIELD_MULT;
            }
        });

        allPowerUps.add(new PowerUp() {

            @Override
            public void applayEffect(final PlayerShip player) {
                waveManager.multiplyEnemySpeed(TIME_MULT);
            }

            @Override
            public String getType() {
                return "Distorsione Tempo";
            }

            @Override
            public String getDescription() {
                return "Rallenta nemici e proiettile del 30%";
            }

            @Override
            public float getMultipler() {
                return TIME_MULT;
            }
        });

        allPowerUps.add(new PowerUp() {

            @Override
            public void applayEffect(final PlayerShip player) {
                player.multiplyShootCooldown(FIRE_MULT);
            }

            @Override
            public String getType() {
                return "Mitragliatrice";
            }

            @Override
            public String getDescription() {
                return "Riduce il tempo di ricarica del 60%";
            }

            @Override
            public float getMultipler() {
                return FIRE_MULT;
            }
        });

        allPowerUps.add(new PowerUp() {

            @Override
            public void applayEffect(final PlayerShip player) {
                player.addMaxHealth(1);
            }

            @Override
            public String getType() {
                return "Riparazione Navicella";
            }

            @Override
            public String getDescription() {
                return "Ripristina e aggiunge +1 Punto Vita";
            }

            @Override
            public float getMultipler() {
                return HULL_MULT;
            }
        });

        allPowerUps.add(new PowerUp() {

            @Override
            public void applayEffect(final PlayerShip player) {
                player.multiplySpeed(SPEED_MULT);
            }

            @Override
            public String getType() {
                return "Iper-Propulsori";
            }

            @Override
            public String getDescription() {
                return "Aumenta la velocità di movimento del 20%";
            }

            @Override
            public float getMultipler() {
                return SPEED_MULT;
            }
        });

        allPowerUps.add(new PowerUp() {

            @Override
            public void applayEffect(final PlayerShip player) {
                player.addDamage(1);
            }

            @Override
            public String getType() {
                return "Laser Pesante";
            }

            @Override
            public String getDescription() {
                return "Aumenta i danni inflitti +1";
            }

            @Override
            public float getMultipler() {
                return LASER_MULT;
            }
        });

        Collections.shuffle(allPowerUps);
        return List.of(allPowerUps.get(0), allPowerUps.get(1), allPowerUps.get(2));
    }

    /**
     * Return a copy of the list to avoid data exposure.
     * 
     * @return available options
     */
    public List<PowerUp> getAvailableOptions() {
        return List.copyOf(this.proposedOptions);
    }

    /**
     * Return the index.
     * 
     * @return the current selected index
     */
    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    /**
     * Return the selected power up.
     * 
     * @return the current selected power-up
     */
    public PowerUp getSelectedPowerUp() {
        return this.proposedOptions.get(this.selectedIndex);
    }

    /**
     * Select the next power-up.
     */
    public void selectNext() {
        this.selectedIndex = (this.selectedIndex + 1) % this.proposedOptions.size();
        this.notifyListener();
    }

    /**
     * Select the previous power-up.
     */
    public void selectPrevious() {
        this.selectedIndex = (this.selectedIndex - 1 + this.proposedOptions.size()) % this.proposedOptions.size();
        this.notifyListener();
    }

    /**
     * Set the observer.
     * 
     * @param obs the observer
     */
    public void setObserver(final MenuObserver obs) {
        this.observer = Objects.requireNonNull(obs);
    }

    private void notifyListener() {
        if (this.observer != null) {
            this.observer.updateMenuState();
        }
    }
}
