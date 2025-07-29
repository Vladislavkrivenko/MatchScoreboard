package dto;

import lombok.Builder;

import java.util.List;

@Builder
public record MatchesDto(List<MatchDto> matches, int totalPages, int currentPage) {
}
