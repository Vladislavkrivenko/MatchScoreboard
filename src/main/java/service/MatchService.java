package service;

import lombok.Getter;
import model.Match;
import model.Players;
import model.Score;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MatchService {
    @Getter
    private static final MatchService instance = new MatchService();
    private final Map<UUID, Match> ongoingMatches = new HashMap<>();

    private MatchService() {
    }

    public UUID createMatch(Players player1, Players player2) {
        UUID matchId = UUID.randomUUID();
        Match match = new Match(matchId,
                player1,
                player2,
                new Score());
        ongoingMatches.put(matchId, match);
        return matchId;
    }

    public Match getMatch(UUID matchId) {
        return ongoingMatches.get(matchId);
    }

    public void removeMatch(UUID matchId) {
        ongoingMatches.remove(matchId);
    }
}
