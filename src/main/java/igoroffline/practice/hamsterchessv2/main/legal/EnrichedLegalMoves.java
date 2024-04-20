package igoroffline.practice.hamsterchessv2.main.legal;

import igoroffline.practice.hamsterchessv2.main.board.Square;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
@ToString
public class EnrichedLegalMoves {

    private final Map<Integer, List<Integer>> legalMoves;

    public EnrichedLegalMoves(LegalMoves legalMoves) {
        this.legalMoves = legalMoves.getLegalMoves().entrySet().stream().collect(
                Collectors.toMap(
                        fromIndex -> fromIndex.getKey().getIndex(),
                        toIndices -> toIndices.getValue().stream().map(Square::getIndex).collect(Collectors.toList())
                ));
    }
}
