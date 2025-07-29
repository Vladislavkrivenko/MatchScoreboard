package mapper;

import dto.MatchDto;
import model.Matches;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MatchMapper {
    MatchMapper instance = Mappers.getMapper(MatchMapper.class);

    @Mapping(source = "id", target = "matchId", numberFormat = "#")
    @Mapping(source = "firstPlayer.name", target = "player1Name")
    @Mapping(source = "secondPlayer.name", target = "player2Name")
    @Mapping(source = "winner.name", target = "winnerName")
    MatchDto toDto(Matches match);
}
