package service;

import model.Match;
import model.Player;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MatchScoreCalculationServiceTest {

    @Test
    void pointToPlayer() {
        Player player1 = new Player();
        player1.setName("player1");

        Player player2 = new Player();
        player1.setName("player2");

        MatchService matchService = MatchService.getInstance();

        UUID matchId = matchService.createMatch(player1, player2);

        Match matchBefore = matchService.getMatch(matchId);
        assertNotNull(matchBefore);
        assertEquals(0, matchBefore.getScore().getPointsPlayer1());

        MatchScoreCalculationService calculationService = MatchScoreCalculationService.getInstance();
        calculationService.pointToPlayer(matchId);

        Match matchAfter = matchService.getMatch(matchId);
        assertNotNull(matchAfter);
        assertEquals(15, matchAfter.getScore().getPointsPlayer1());
    }

    @Test
    void pointToPlayer2() {
        Player player1 = new Player();
        player1.setName("player1");

        Player player2 = new Player();
        player1.setName("player2");

        MatchService matchService = MatchService.getInstance();

        UUID matchId = matchService.createMatch(player1, player2);

        Match matchBefore = matchService.getMatch(matchId);
        assertNotNull(matchBefore);
        assertEquals(0, matchBefore.getScore().getPointsPlayer2());

        MatchScoreCalculationService calculationService = MatchScoreCalculationService.getInstance();
        calculationService.pointToPlayer2(matchId);

        Match matchAfter = matchService.getMatch(matchId);
        assertNotNull(matchAfter);
        assertEquals(15, matchAfter.getScore().getPointsPlayer2());
    }
}