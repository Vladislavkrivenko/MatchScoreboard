package service;

import lombok.Getter;
import model.Match;

import java.util.UUID;

public class MatchScoreCalculationService {
    @Getter
    private static final MatchScoreCalculationService instance = new MatchScoreCalculationService();
    private final MatchService matchService = MatchService.getInstance();

    private MatchScoreCalculationService() {
    }

    public void pointTOPlayer(UUID matchId) {
        Match match = matchService.getMatch(matchId);
        if (match != null && !match.getScore().isMatchOver()) {
            match.getScore().pointWonBy(1);
        }
    }

    public void pointToPlayer2(UUID matchId) {
        Match match = matchService.getMatch(matchId);
        if (match != null && !match.getScore().isMatchOver()) {
            match.getScore().pointWonBy(2);
        }
    }
}


