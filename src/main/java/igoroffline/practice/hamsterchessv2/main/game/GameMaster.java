package igoroffline.practice.hamsterchessv2.main.game;

import igoroffline.practice.hamsterchessv2.main.board.Board;
import igoroffline.practice.hamsterchessv2.main.board.Piece;
import igoroffline.practice.hamsterchessv2.main.board.PieceColor;
import igoroffline.practice.hamsterchessv2.main.board.Square;
import igoroffline.practice.hamsterchessv2.main.legal.EnrichedLegalMoves;
import igoroffline.practice.hamsterchessv2.main.legal.LegalMoves;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

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
        legalMoves.calculate(this);
    }

    public GameMaster(Board board, LegalMoves legalMoves) {
        this.board = board;
        this.legalMoves = legalMoves;
    }

    public void moveAndCalculate() {
        if (legalMoves.getLegalMoves().containsKey(fromSquare.orElseThrow())) {
            legalMoves.getLegalMoves().get(fromSquare.get()).forEach(pieceLegalMove -> {
                if (toSquareEquals(pieceLegalMove)) {
                    moveAndCalculateInner();

                    return;
                }
            });
        }
    }

    private boolean toSquareEquals(Square square) {
        return Square.isLetterNumberEqual(toSquare.orElseThrow().getLetter(), square.getLetter(), toSquare.get().getNumber(), square.getNumber());
    }

    private void moveAndCalculateInner() {
        move();
        calculate();
    }

    public void move() {
        toSquare.orElseThrow().setPiece(fromSquare.orElseThrow().getPiece());
        toSquare.get().setPieceColor(fromSquare.get().getPieceColor());
        fromSquare.get().setPiece(Piece.NONE);
        fromSquare.get().setPieceColor(PieceColor.NONE);
    }

    private void calculate() {
        whiteToMove = !whiteToMove;

        legalMoves.calculate(this);
    }
}
