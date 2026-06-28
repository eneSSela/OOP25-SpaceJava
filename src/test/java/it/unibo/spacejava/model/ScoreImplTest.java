package it.unibo.spacejava.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.spacejava.api.Score;

final class ScoreImplTest {

    private static final Path SAVE_FILE = Path.of(System.getProperty("user.home"), ".spacejava_save.properties");
    private static final int STARTING_SCORE = 0;
    private static final int FIRST_SCORE = 120;
    private static final int SECOND_SCORE = 150;
    private static final int CONSUMED_POINTS = 100;
    private static final int REMAINING_POINTS = 50;
    private static final int HIGH_SCORE = 80;
    private static final int LOWER_RUN_SCORE = 40;
    private static final int INITIAL_HIGH_SCORE = 90;
    private static final int TOTAL_AFTER_SECOND_RESET = 130;
    private static final int PERSISTED_SCORE = 70;

    private Score score;

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(SAVE_FILE);
        score = new ScoreImpl();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(SAVE_FILE);
    }

    @Test
    void constructorStartsWithZeroValues() {
        assertAll(
            () -> assertEquals(STARTING_SCORE, score.getTotal()),
            () -> assertEquals(STARTING_SCORE, score.getCurrentRunScore()),
            () -> assertEquals(STARTING_SCORE, score.getHighScore())
        );
    }

    @Test
    void addPointsUpdatesCurrentRunAndTotal() {
        score.addPoints(FIRST_SCORE);

        assertAll(
            () -> assertEquals(FIRST_SCORE, score.getCurrentRunScore()),
            () -> assertEquals(FIRST_SCORE, score.getTotal()),
            () -> assertEquals(STARTING_SCORE, score.getHighScore())
        );
    }

    @Test
    void consumePointsOnlyWhenEnoughPointsAreAvailable() {
        score.addPoints(SECOND_SCORE);

        assertTrue(score.consumePoints(CONSUMED_POINTS));
        assertEquals(REMAINING_POINTS, score.getTotal());
        assertFalse(score.consumePoints(CONSUMED_POINTS));
        assertEquals(REMAINING_POINTS, score.getTotal());
    }

    @Test
    void resetCurrentRunUpdatesHighScoreAndResetsCurrentRun() {
        score.addPoints(HIGH_SCORE);

        score.resetCurrentRun();

        assertAll(
            () -> assertEquals(HIGH_SCORE, score.getHighScore()),
            () -> assertEquals(STARTING_SCORE, score.getCurrentRunScore()),
            () -> assertEquals(HIGH_SCORE, score.getTotal())
        );
    }

    @Test
    void resetCurrentRunKeepsExistingHighScoreWhenCurrentRunIsLower() {
        score.addPoints(INITIAL_HIGH_SCORE);
        score.resetCurrentRun();
        score.addPoints(LOWER_RUN_SCORE);

        score.resetCurrentRun();

        assertAll(
            () -> assertEquals(INITIAL_HIGH_SCORE, score.getHighScore()),
            () -> assertEquals(STARTING_SCORE, score.getCurrentRunScore()),
            () -> assertEquals(TOTAL_AFTER_SECOND_RESET, score.getTotal())
        );
    }

    @Test
    void constructorLoadsPersistedScoresFromSaveFile() {
        score.addPoints(PERSISTED_SCORE);
        score.resetCurrentRun();

        final ScoreImpl reloadedScore = new ScoreImpl();

        assertAll(
            () -> assertEquals(PERSISTED_SCORE, reloadedScore.getHighScore()),
            () -> assertEquals(PERSISTED_SCORE, reloadedScore.getTotal()),
            () -> assertEquals(STARTING_SCORE, reloadedScore.getCurrentRunScore())
        );
    }
}
