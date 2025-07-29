package dto;

import lombok.Builder;

@Builder
public record MatchDto(String matchId, String player1Name, String player2Name, String winnerName) {
}
