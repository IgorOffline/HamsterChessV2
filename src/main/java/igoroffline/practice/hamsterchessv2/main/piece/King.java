package igoroffline.practice.hamsterchessv2.main.piece;

import igoroffline.practice.hamsterchessv2.main.board.Board;
import igoroffline.practice.hamsterchessv2.main.board.LetterNumber;
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

        for (var square : board.getBoard()) {
            if (square.getPiece() == Piece.KING && square.getPieceColor() == oppositeColor) {
                oppositeKingSquare = Optional.of(square);
                break;
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
        final var letterMinus = LetterNumber.getLetterEnum(letterIndexMinus);
        final var letterMinusLegal = LetterNumber.isEnumLegal(letterMinus);
        final var letterPlus = LetterNumber.getLetterEnum(letterIndexPlus);
        final var letterPlusLegal = LetterNumber.isEnumLegal(letterPlus);
        final var number = kingSquare.getNumber();
        final var numberMinus = LetterNumber.getNumberEnum(numberIndexMinus);
        final var numberMinusLegal = LetterNumber.isEnumLegal(numberMinus);
        final var numberPlus = LetterNumber.getNumberEnum(numberIndexPlus);
        final var numberPlusLegal = LetterNumber.isEnumLegal(numberPlus);

        if (letterMinusLegal) {
            moves.add(new Square(letterMinus, number, Piece.NONE, PieceColor.NONE));
            if (numberPlusLegal) {
                moves.add(new Square(letterMinus, numberPlus, Piece.NONE, PieceColor.NONE));
            }
            if (numberMinusLegal) {
                moves.add(new Square(letterMinus, numberMinus, Piece.NONE, PieceColor.NONE));
            }
        }
        if (letterPlusLegal) {
            moves.add(new Square(letterPlus, number, Piece.NONE, PieceColor.NONE));
            if (numberPlusLegal) {
                moves.add(new Square(letterPlus, numberPlus, Piece.NONE, PieceColor.NONE));
            }
            if (numberMinusLegal) {
                moves.add(new Square(letterPlus, numberMinus, Piece.NONE, PieceColor.NONE));
            }
        }
        if (numberPlusLegal) {
            moves.add(new Square(letter, numberPlus, Piece.NONE, PieceColor.NONE));
        }
        if (numberMinusLegal) {
            moves.add(new Square(letter, numberMinus, Piece.NONE, PieceColor.NONE));
        }

        return moves;
    }
}
