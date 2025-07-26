package service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import model.Match;
import model.Player;
import model.Score;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class MatchService {
    @Getter
    private static final MatchService instance = new MatchService();
    private final Map<UUID, Match> ongoingMatches = new HashMap<>();

    private MatchService() {
    }

    public UUID createMatch(Player player1, Player player2) {
        UUID matchId = UUID.randomUUID();
        Match match = new Match(matchId,
                player1,
                player2,
                new Score());
        log.info("Match created with id: {}", matchId);
        ongoingMatches.put(matchId, match);
        return matchId;
    }

    public Match getMatch(UUID matchId) {
        log.info("getMatch id {}", matchId);
        return ongoingMatches.get(matchId);
    }

    public void removeMatch(UUID matchId) {
        log.info("Removing match with id {}", matchId);
        ongoingMatches.remove(matchId);
    }
}
