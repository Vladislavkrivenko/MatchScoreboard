package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    @Test
    void pointWonBy() {
        Score score = new Score();

        assertEquals(0, score.getPointsPlayer1());

        score.pointWonBy(1);
        assertEquals(15, score.getPointsPlayer1());

        score.pointWonBy(1);
        assertEquals(30, score.getPointsPlayer1());


        assertEquals(30, score.getPointsPlayer1());

        score.pointWonBy(1);
        assertEquals(0, score.getPointsPlayer1());
    }

    @Test
    void isTiebreak() {
        Score score = new Score();
        score.setGamesPlayer1(5);
        score.setGamesPlayer2(6);
        score.winGame(1);
        assertTrue(score.isTiebreak());
    }

    @Test
    void isMatchOver() {
        Score score = new Score();
        assertFalse(score.isMatchOver());
        score.setSetsPlayer1(2);
        score.setMatchOver(true);
        assertTrue(score.isMatchOver());
    }
}