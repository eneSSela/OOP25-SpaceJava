package it.unibo.spacejava.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * SaveManager classe di util per poter salvere il punteggio totale e il punteggio più alto del giocatore in un file di proprietà.
 */
public final class SaveManager {

    private static final String KEY_TOTAL_SCORE = "totalScore";
    private static final String KEY_HIGH_SCORE = "highScore";

    private SaveManager() { }

    private static String resolveSaveFilePath() {
        final String overridePath = System.getProperty("spacejava.saveFile");
        if (overridePath != null && !overridePath.isBlank()) {
            return overridePath;
        }
        return Path.of(System.getProperty("user.home"), ".spacejava_save.properties").toString();
    }

    /**
     * Salva i puteggi sul file properties.
     * 
     * @param totalScore il punteggio totale del gicaotre
     * @param highScore il milgior punteggio fatto dal giocarte in una partita
     */
    public static void saveScores(final int totalScore, final int highScore) {
        final Properties props = new Properties();
        props.setProperty(KEY_TOTAL_SCORE, String.valueOf(totalScore));
        props.setProperty(KEY_HIGH_SCORE, String.valueOf(highScore));

        final Path savePath = Path.of(resolveSaveFilePath());
        final Path parent = savePath.getParent();
        if (parent != null) {
            try {
                Files.createDirectories(parent);
            } catch (final IOException e) {
                System.err.println("Errore durante la creazione della directory di salvataggio: " + e.getMessage()); //NOPMD
                return;
            }
        }

        try (OutputStream out = new FileOutputStream(savePath.toFile())) {
            props.store(out, "Salvataggio di Space Java");
        } catch (final IOException e) {
            System.err.println("Errore durante il salvataggio: " + e.getMessage()); //NOPMD
        }
    }

    /**
     * Carica il punteggio totale del giocare dal file di properties.
     * 
     * @return il punteggio totale del giocatore, o 0 se non esiste un salvataggio
     */
    public static int loadTotalScore() {
        return loadInt(KEY_TOTAL_SCORE, 0);
    }

    /**
     * Carica il punteggio più alto del giocare dal file di properties.
     * 
     * @return il punteggio più alto del giocatore, o 0 se non esiste un salvataggio
     */
    public static int loadHighScore() {
        return loadInt(KEY_HIGH_SCORE, 0);
    }

    private static int loadInt(final String key, final int defaultValue) {
        final File file = new File(resolveSaveFilePath());
        if (file.exists()) {
            final Properties props = new Properties();
            try (InputStream in = new FileInputStream(file)) {
                props.load(in);
                return Integer.parseInt(props.getProperty(key, String.valueOf(defaultValue)));
            } catch (final IOException | NumberFormatException e) {
                System.err.println("Errore durante il caricamento: " + e.getMessage()); //NOPMD
            }
        }
        return defaultValue;
    }
}
