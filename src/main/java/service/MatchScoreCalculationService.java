package service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import model.Match;

import java.util.UUID;
@Slf4j
public class MatchScoreCalculationService {
    @Getter
    private static final MatchScoreCalculationService instance = new MatchScoreCalculationService();
    private final MatchService matchService = MatchService.getInstance();

    private MatchScoreCalculationService() {
    }

    public void pointToPlayer(UUID matchId) {
        Match match = matchService.getMatch(matchId);
        if (match != null && !match.getScore().isMatchOver()) {
            log.info("Point won Player 1 in Match {}",matchId);
            match.getScore().pointWonBy(1);
        }
    }

    public void pointToPlayer2(UUID matchId) {
        Match match = matchService.getMatch(matchId);
        if (match != null && !match.getScore().isMatchOver()) {
            log.info("Point won Player 2 in Match {}",matchId);
            match.getScore().pointWonBy(2);
        }
    }
}


