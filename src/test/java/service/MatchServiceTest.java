package service;

import model.Match;
import model.Player;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MatchServiceTest {

    @Test
    void createMatch() {
        Player player1 = new Player();
        player1.setName("Player1");

        Player player2 = new Player();
        player1.setName("Player1");

        MatchService matchService = MatchService.getInstance();
        UUID matchId = matchService.createMatch(player1, player2);
        assertNotNull(matchId, "Match ID should not be null");

        Match match = matchService.getMatch(matchId);
        assertNotNull(match, "Match should not be null");

        assertEquals(player1, match.getPlayer1(), "Player 1 should match");
        assertEquals(player2, match.getPlayer2(), "Player 2 should match");

        assertNotNull(match.getScore(), "Score should be initialized");
    }

    @Test
    void removeMatch() {
        Player player1 = new Player();
        player1.setName("Player1");

        Player player2 = new Player();
        player1.setName("Player1");

        MatchService matchService = MatchService.getInstance();
        UUID matchId = matchService.createMatch(player1, player2);
        assertNotNull(matchId, "Match ID should not be null");

        matchService.removeMatch(matchId);
        assertNull(matchService.getMatch(matchId));
    }
}