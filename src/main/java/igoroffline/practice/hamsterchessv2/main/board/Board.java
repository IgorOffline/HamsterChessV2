package igoroffline.practice.hamsterchessv2.main.board;

import igoroffline.practice.hamsterchessv2.main.piece.movement.pawn.PawnAttackMovementDirection;
import igoroffline.practice.hamsterchessv2.main.piece.movement.pawn.PawnMove;
import java.util.List;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Board {

    private final Square[][] board;

    public Board() {
        this.board = new Square[8][];
        createBoard();
    }

    public Board(Square[][] board) {
        this.board = board;
    }

    private void createBoard() {
        final var filledSquares = List.of(
                new Square(Letter.C, Number2.N6, Piece.KING, PieceColor.BLACK),
                new Square(Letter.C, Number2.N4, Piece.ROOK, PieceColor.BLACK),
                new Square(Letter.E, Number2.N6, Piece.BISHOP, PieceColor.BLACK),
                new Square(Letter.B, Number2.N6, Piece.KNIGHT, PieceColor.BLACK),
                new Square(Letter.A, Number2.N7, Piece.PAWN, PieceColor.BLACK),
                new Square(Letter.E, Number2.N3, Piece.KING, PieceColor.WHITE),
                new Square(Letter.D, Number2.N2, Piece.ROOK, PieceColor.WHITE),
                new Square(Letter.G, Number2.N4, Piece.BISHOP, PieceColor.WHITE),
                new Square(Letter.F, Number2.N2, Piece.KNIGHT, PieceColor.WHITE),
                new Square(Letter.H, Number2.N2, Piece.PAWN, PieceColor.WHITE));

        //        final var filledSquares = List.of(
        //                new Square(Letter.C, Number2.N6, Piece.KING, PieceColor.BLACK),
        //                new Square(Letter.A, Number2.N7, Piece.PAWN, PieceColor.BLACK),
        //                new Square(Letter.G, Number2.N5, Piece.PAWN, PieceColor.BLACK),
        //                new Square(Letter.G, Number2.N6, Piece.PAWN, PieceColor.BLACK),
        //                new Square(Letter.H, Number2.N6, Piece.PAWN, PieceColor.BLACK),
        //                new Square(Letter.F, Number2.N7, Piece.PAWN, PieceColor.BLACK),
        //                new Square(Letter.G, Number2.N7, Piece.PAWN, PieceColor.BLACK),
        //                new Square(Letter.H, Number2.N7, Piece.PAWN, PieceColor.BLACK),
        //                new Square(Letter.E, Number2.N3, Piece.KING, PieceColor.WHITE),
        //                new Square(Letter.H, Number2.N4, Piece.PAWN, PieceColor.WHITE),
        //                new Square(Letter.H, Number2.N2, Piece.PAWN, PieceColor.WHITE));

        //        final var filledSquares = List.of(
        //                new Square(Letter.E, Number2.N7, Piece.KING, PieceColor.BLACK),
        //                new Square(Letter.E, Number2.N3, Piece.KING, PieceColor.WHITE),
        //                new Square(Letter.A, Number2.N1, Piece.ROOK, PieceColor.WHITE));

        for (int row = 0; row < 8; row++) {
            board[row] = new Square[8];
            for (int col = 0; col < 8; col++) {
                final var letter = LetterNumber.getLetterEnum(row);
                final var number = LetterNumber.getNumberEnumReverse(col);
                var letterNumberInFilled = false;
                for (final var filled : filledSquares) {
                    if (filled.getLetter() == letter && filled.getNumber() == number) {
                        board[row][col] = filled;
                        letterNumberInFilled = true;
                        break;
                    }
                }
                if (!letterNumberInFilled) {
                    final var square = new Square(letter, number, Piece.NONE, PieceColor.NONE);
                    board[row][col] = square;
                }
            }
        }
    }

    public Optional<Square> findNextNumberSquare(Letter letter, Number2 number) {
        final var nextNumberIndex = number.index + 1;
        if (!LetterNumber.isEnumLegal(LetterNumber.getNumberEnum(nextNumberIndex))) {
            return Optional.empty();
        }

        final var col = 7 - nextNumberIndex;

        return Optional.of(board[letter.index][col]);
    }

    public Optional<Square> findPreviousNumberSquare(Letter letter, Number2 number) {
        final var previousNumberIndex = number.index - 1;
        if (!LetterNumber.isEnumLegal(LetterNumber.getNumberEnum(previousNumberIndex))) {
            return Optional.empty();
        }

        final var col = 7 - previousNumberIndex;

        return Optional.of(board[letter.index][col]);
    }

    public Optional<Square> findNextLetterSquare(Letter letter, Number2 number) {
        final var nextLetterIndex = letter.index + 1;
        if (!LetterNumber.isEnumLegal(LetterNumber.getLetterEnum(nextLetterIndex))) {
            return Optional.empty();
        }

        final var col = 7 - number.index;

        return Optional.of(board[nextLetterIndex][col]);
    }

    public Optional<Square> findPreviousLetterSquare(Letter letter, Number2 number) {
        final var previousLetterIndex = letter.index - 1;
        if (!LetterNumber.isEnumLegal(LetterNumber.getLetterEnum(previousLetterIndex))) {
            return Optional.empty();
        }

        final var col = 7 - number.index;

        return Optional.of(board[previousLetterIndex][col]);
    }

    public Optional<Square> findPreviousLetterNextNumberSquare(Letter letter, Number2 number) {
        final var previousLetterIndex = letter.index - 1;
        final var nextNumberIndex = number.index + 1;

        if (!LetterNumber.isEnumLegal(LetterNumber.getLetterEnum(previousLetterIndex))
                || !LetterNumber.isEnumLegal(LetterNumber.getNumberEnum(nextNumberIndex))) {
            return Optional.empty();
        }

        final var col = 7 - nextNumberIndex;

        return Optional.of(board[previousLetterIndex][col]);
    }

    public Optional<Square> findNextLetterNextNumberSquare(Letter letter, Number2 number) {
        final var nextLetterIndex = letter.index + 1;
        final var nextNumberIndex = number.index + 1;

        if (!LetterNumber.isEnumLegal(LetterNumber.getLetterEnum(nextLetterIndex))
                || !LetterNumber.isEnumLegal(LetterNumber.getNumberEnum(nextNumberIndex))) {
            return Optional.empty();
        }

        final var col = 7 - nextNumberIndex;

        return Optional.of(board[nextLetterIndex][col]);
    }

    public Optional<Square> findPreviousLetterPreviousNumberSquare(Letter letter, Number2 number) {
        final var previousLetterIndex = letter.index - 1;
        final var previousNumberIndex = number.index - 1;

        if (!LetterNumber.isEnumLegal(LetterNumber.getLetterEnum(previousLetterIndex))
                || !LetterNumber.isEnumLegal(LetterNumber.getNumberEnum(previousNumberIndex))) {
            return Optional.empty();
        }

        final var col = 7 - previousNumberIndex;

        return Optional.of(board[previousLetterIndex][col]);
    }

    public Optional<Square> findNextLetterPreviousNumberSquare(Letter letter, Number2 number) {
        final var nextLetterIndex = letter.index + 1;
        final var previousNumberIndex = number.index - 1;

        if (!LetterNumber.isEnumLegal(LetterNumber.getLetterEnum(nextLetterIndex))
                || !LetterNumber.isEnumLegal(LetterNumber.getNumberEnum(previousNumberIndex))) {
            return Optional.empty();
        }

        final var col = 7 - previousNumberIndex;

        return Optional.of(board[nextLetterIndex][col]);
    }

    public Optional<Square> findPpLetterNextNumberSquare(Letter letter, Number2 number) {
        final var ppLetterIndex = letter.index - 2;
        final var nextNumberIndex = number.index + 1;

        if (!LetterNumber.isEnumLegal(LetterNumber.getLetterEnum(ppLetterIndex))
                || !LetterNumber.isEnumLegal(LetterNumber.getNumberEnum(nextNumberIndex))) {
            return Optional.empty();
        }

        final var col = 7 - nextNumberIndex;

        return Optional.of(board[ppLetterIndex][col]);
    }

    public Optional<Square> findNnNumberPreviousLetterSquare(Letter letter, Number2 number) {
        final var previousLetterIndex = letter.index - 1;
        final var nnNumberIndex = number.index + 2;

        if (!LetterNumber.isEnumLegal(LetterNumber.getLetterEnum(previousLetterIndex))
                || !LetterNumber.isEnumLegal(LetterNumber.getNumberEnum(nnNumberIndex))) {
            return Optional.empty();
        }

        final var col = 7 - nnNumberIndex;

        return Optional.of(board[previousLetterIndex][col]);
    }

    public Optional<Square> findNnNumberNextLetterSquare(Letter letter, Number2 number) {
        final var nextLetterIndex = letter.index + 1;
        final var nnNumberIndex = number.index + 2;

        if (!LetterNumber.isEnumLegal(LetterNumber.getLetterEnum(nextLetterIndex))
                || !LetterNumber.isEnumLegal(LetterNumber.getNumberEnum(nnNumberIndex))) {
            return Optional.empty();
        }

        final var col = 7 - nnNumberIndex;

        return Optional.of(board[nextLetterIndex][col]);
    }

    public Optional<Square> findNnLetterNextNumberSquare(Letter letter, Number2 number) {
        final var nnLetterIndex = letter.index + 2;
        final var nextNumberIndex = number.index + 1;

        if (!LetterNumber.isEnumLegal(LetterNumber.getLetterEnum(nnLetterIndex))
                || !LetterNumber.isEnumLegal(LetterNumber.getNumberEnum(nextNumberIndex))) {
            return Optional.empty();
        }

        final var col = 7 - nextNumberIndex;

        return Optional.of(board[nnLetterIndex][col]);
    }

    public Optional<Square> findPpLetterPreviousNumberSquare(Letter letter, Number2 number) {
        final var ppLetterIndex = letter.index - 2;
        final var previousNumberIndex = number.index - 1;

        if (!LetterNumber.isEnumLegal(LetterNumber.getLetterEnum(ppLetterIndex))
                || !LetterNumber.isEnumLegal(LetterNumber.getNumberEnum(previousNumberIndex))) {
            return Optional.empty();
        }

        final var col = 7 - previousNumberIndex;

        return Optional.of(board[ppLetterIndex][col]);
    }

    public Optional<Square> findPpNumberPreviousLetterSquare(Letter letter, Number2 number) {
        final var previousLetterIndex = letter.index - 1;
        final var ppNumberIndex = number.index - 2;

        if (!LetterNumber.isEnumLegal(LetterNumber.getLetterEnum(previousLetterIndex))
                || !LetterNumber.isEnumLegal(LetterNumber.getNumberEnum(ppNumberIndex))) {
            return Optional.empty();
        }

        final var col = 7 - ppNumberIndex;

        return Optional.of(board[previousLetterIndex][col]);
    }

    public Optional<Square> findPpNumberNextLetterSquare(Letter letter, Number2 number) {
        final var nextLetterIndex = letter.index + 1;
        final var ppNumberIndex = number.index - 2;

        if (!LetterNumber.isEnumLegal(LetterNumber.getLetterEnum(nextLetterIndex))
                || !LetterNumber.isEnumLegal(LetterNumber.getNumberEnum(ppNumberIndex))) {
            return Optional.empty();
        }

        final var col = 7 - ppNumberIndex;

        return Optional.of(board[nextLetterIndex][col]);
    }

    public Optional<Square> findNnLetterPreviousNumberSquare(Letter letter, Number2 number) {
        final var nnLetterIndex = letter.index + 2;
        final var previousNumberIndex = number.index - 1;

        if (!LetterNumber.isEnumLegal(LetterNumber.getLetterEnum(nnLetterIndex))
                || !LetterNumber.isEnumLegal(LetterNumber.getNumberEnum(previousNumberIndex))) {
            return Optional.empty();
        }

        final var col = 7 - previousNumberIndex;

        return Optional.of(board[nnLetterIndex][col]);
    }

    public Optional<Square> findPawnMoveSquare(
            PawnMove pawnMove, PieceColor pieceColor, Letter letter, Number2 number) {

        var numberIndex = -1;

        switch (pawnMove) {
            case ONE_SQUARE -> numberIndex = switch (pieceColor) {
                case WHITE -> number.index + 1;
                case BLACK -> number.index - 1;
                case NONE -> throw new IllegalArgumentException("PieceColor.NONE not supported!");
            };
            case TWO_SQUARES -> numberIndex = switch (pieceColor) {
                case WHITE -> number.index + 2;
                case BLACK -> number.index - 2;
                case NONE -> throw new IllegalArgumentException("PieceColor.NONE not supported! (2)");
            };
        }

        if (!LetterNumber.isEnumLegal(LetterNumber.getLetterEnum(letter.index))
                || !LetterNumber.isEnumLegal(LetterNumber.getNumberEnum(numberIndex))) {
            return Optional.empty();
        }

        final var row = letter.index;
        final var col = 7 - numberIndex;

        return Optional.of(board[row][col]);
    }

    public Optional<Square> findPawnAttackPreviousOrNextLetterSquare(
            PawnAttackMovementDirection movementDirection, PieceColor pieceColor, Letter letter, Number2 number) {

        final var letterIndex =
                switch (movementDirection) {
                    case PREVIOUS -> letter.index - 1;
                    case NEXT -> letter.index + 1;
                };

        final var numberIndex =
                switch (pieceColor) {
                    case WHITE -> number.index + 1;
                    case BLACK -> number.index - 1;
                    case NONE -> throw new IllegalArgumentException("PieceColor.NONE not supported!");
                };

        if (!LetterNumber.isEnumLegal(LetterNumber.getLetterEnum(letterIndex))
                || !LetterNumber.isEnumLegal(LetterNumber.getNumberEnum(numberIndex))) {
            return Optional.empty();
        }

        final var col = 7 - numberIndex;

        return Optional.of(board[letterIndex][col]);
    }

    public Board deepCopy() {
        final var newBoard = new Square[8][];
        for (int row = 0; row < 8; row++) {
            newBoard[row] = new Square[8];
            for (int col = 0; col < 8; col++) {
                final var oldSquare = board[row][col];
                newBoard[row][col] = oldSquare.copy();
            }
        }

        return new Board(newBoard);
    }
}
