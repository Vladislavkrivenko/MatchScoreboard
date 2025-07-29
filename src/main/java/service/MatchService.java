package service;

import dto.MatchDto;
import dto.MatchesDto;
import dto.MatchesFilterDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import mapper.MatchMapper;
import model.Match;
import model.Matches;
import model.Player;
import model.Score;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class MatchService {
    @Getter
    private static final MatchService instance = new MatchService();
    private final Map<UUID, Match> ongoingMatches = new HashMap<>();
    private final FinishedMatchesPersistenceService service = FinishedMatchesPersistenceService.getInstance();

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

    public MatchesDto findMatches(MatchesFilterDto filter) {
        int pageSize = 10;
        int page = Math.max(filter.page(), 1);

        List<Matches> matches = service.findFinishedMatches(filter.playerName(), page, pageSize);

        int totalMatches = service.countFilteredMatches(filter.playerName());

        int totalPages = (int) Math.ceil((double) totalMatches / pageSize);

        List<MatchDto> matchDto = matches.stream().map(MatchMapper.instance::toDto)
                .toList();
        return MatchesDto.builder().matches(matchDto)
                .currentPage(page)
                .totalPages(totalPages)
                .build();
    }
}
