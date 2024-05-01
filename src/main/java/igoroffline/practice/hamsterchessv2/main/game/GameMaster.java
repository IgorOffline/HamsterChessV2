package igoroffline.practice.hamsterchessv2.main.game;

import igoroffline.practice.hamsterchessv2.main.board.Board;
import igoroffline.practice.hamsterchessv2.main.board.Piece;
import igoroffline.practice.hamsterchessv2.main.board.Square;
import igoroffline.practice.hamsterchessv2.main.legal.EnrichedLegalMoves;
import igoroffline.practice.hamsterchessv2.main.legal.LegalMoves;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class GameMaster {

    private final Board board;
    private final LegalMoves legalMoves;

    @Setter
    private Optional<Square> fromSquare;

    @Setter
    private Optional<Square> toSquare;

    @Setter
    private boolean whiteToMove = true;

    @Setter
    private boolean whiteKingInCheck = false;

    @Setter
    private boolean blackKingInCheck = false;

    @Setter
    private boolean whiteKingCheckmated = false;

    @Setter
    private boolean blackKingCheckmated = false;

    public EnrichedLegalMoves getEnrichedLegalMoves() {
        return new EnrichedLegalMoves(legalMoves);
    }

    public GameMaster() {
        this.board = new Board();
        this.legalMoves = new LegalMoves();
        legalMoves.calculate(this, false);
    }

    public GameMaster(Board board, LegalMoves legalMoves) {
        this.board = board;
        this.legalMoves = legalMoves;
    }

    public void moveAndCalculate(int fromIndex, int toIndex) {
        setFromSquare(board.getBoard().stream()
                .filter(sq -> sq.getIndex() == fromIndex && sq.getPiece() != Piece.NONE)
                .findFirst());
        setToSquare(
                board.getBoard().stream().filter(sq -> sq.getIndex() == toIndex).findFirst());
        moveAndCalculateInner();
    }

    private void moveAndCalculateInner() {
        if (legalMoves.getLegalMoves().containsKey(fromSquare.orElseThrow())) {
            legalMoves.getLegalMoves().get(fromSquare.get()).forEach(pieceLegalMove -> {
                if (toSquareEquals(pieceLegalMove)) {
                    legalMoves.move(this);
                    legalMoves.calculate(this, true);

                    return;
                }
            });
        }
    }

    private boolean toSquareEquals(Square square) {
        return Square.isLetterNumberEqual(
                toSquare.orElseThrow().getLetter(),
                square.getLetter(),
                toSquare.get().getNumber(),
                square.getNumber());
    }
}
