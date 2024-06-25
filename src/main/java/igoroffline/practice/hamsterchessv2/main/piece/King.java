package igoroffline.practice.hamsterchessv2.main.piece;

import igoroffline.practice.hamsterchessv2.main.board.Board;
import igoroffline.practice.hamsterchessv2.main.board.Letter;
import igoroffline.practice.hamsterchessv2.main.board.LetterNumber;
import igoroffline.practice.hamsterchessv2.main.board.Number2;
import igoroffline.practice.hamsterchessv2.main.board.Piece;
import igoroffline.practice.hamsterchessv2.main.board.PieceColor;
import igoroffline.practice.hamsterchessv2.main.board.Square;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class King {

    public static List<Square> kingMoves(Square kingSquare, Board board) {

        final var potentialMoves = kingMovesInner(kingSquare);

        final var oppositeColor = kingSquare.getPieceColor() == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;

        Optional<Square> oppositeKingSquare = Optional.empty();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                final var square = board.getBoard()[row][col];
                if (square.getPiece() == Piece.KING && square.getPieceColor() == oppositeColor) {
                    // TODO extract method
                    oppositeKingSquare = Optional.of(square);
                }
            }
        }

        final var illegalMoves = kingMovesInner(oppositeKingSquare.orElseThrow());

        potentialMoves.removeAll(illegalMoves);

        return potentialMoves;
    }

    private static List<Square> kingMovesInner(Square kingSquare) {

        final var moves = new ArrayList<Square>();

        final var letterIndexMinus = kingSquare.getLetter().index - 1;
        final var letterIndexPlus = kingSquare.getLetter().index + 1;
        final var numberIndexMinus = kingSquare.getNumber().index - 1;
        final var numberIndexPlus = kingSquare.getNumber().index + 1;

        final var letter = kingSquare.getLetter();
        final var letterMinus = getKingLetterNumber(letterIndexMinus, true);
        final var letterPlus = getKingLetterNumber(letterIndexPlus, true);
        final var number = kingSquare.getNumber();
        final var numberMinus = getKingLetterNumber(numberIndexMinus, false);
        final var numberPlus = getKingLetterNumber(numberIndexPlus, false);

        if (letterMinus.legal) {
            moves.add(new Square(letterMinus.letter.get(), number, Piece.NONE, PieceColor.NONE));
            if (numberPlus.legal) {
                moves.add(new Square(letterMinus.letter.get(), numberPlus.number.get(), Piece.NONE, PieceColor.NONE));
            }
            if (numberMinus.legal) {
                moves.add(new Square(letterMinus.letter.get(), numberMinus.number.get(), Piece.NONE, PieceColor.NONE));
            }
        }
        if (letterPlus.legal) {
            moves.add(new Square(letterPlus.letter.get(), number, Piece.NONE, PieceColor.NONE));
            if (numberPlus.legal) {
                moves.add(new Square(letterPlus.letter.get(), numberPlus.number.get(), Piece.NONE, PieceColor.NONE));
            }
            if (numberMinus.legal) {
                moves.add(new Square(letterPlus.letter.get(), numberMinus.number.get(), Piece.NONE, PieceColor.NONE));
            }
        }
        if (numberPlus.legal) {
            moves.add(new Square(letter, numberPlus.number.get(), Piece.NONE, PieceColor.NONE));
        }
        if (numberMinus.legal) {
            moves.add(new Square(letter, numberMinus.number.get(), Piece.NONE, PieceColor.NONE));
        }

        return moves;
    }

    private record KingLetterNumber(boolean legal, Optional<Letter> letter, Optional<Number2> number) {}

    private static KingLetterNumber getKingLetterNumber(int index, boolean isLetter) {
        if (LetterNumber.isLetterNumberIllegal(index)) {
            return new KingLetterNumber(false, Optional.empty(), Optional.empty());
        }

        final Optional<Letter> letter = isLetter ? Optional.of(LetterNumber.getLetterEnum(index)) : Optional.empty();
        final Optional<Number2> number = isLetter ? Optional.empty() : Optional.of(LetterNumber.getNumberEnum(index));

        return new KingLetterNumber(true, letter, number);
    }
}
