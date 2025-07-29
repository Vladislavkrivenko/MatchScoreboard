package dto;

import lombok.Builder;

@Builder
public record MatchesFilterDto(String playerName, int page) {
}
