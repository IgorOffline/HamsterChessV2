package igoroffline.practice.hamsterchessv2.main.piece;

import igoroffline.practice.hamsterchessv2.main.board.Board;
import igoroffline.practice.hamsterchessv2.main.board.Piece;
import igoroffline.practice.hamsterchessv2.main.board.Square;
import igoroffline.practice.hamsterchessv2.main.piece.movement.Contact;
import igoroffline.practice.hamsterchessv2.main.piece.movement.FindSquare;
import igoroffline.practice.hamsterchessv2.main.piece.movement.MovementAttackOpponentCheck;
import igoroffline.practice.hamsterchessv2.main.piece.movement.PieceMovement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bishop {

    public static MovementAttackOpponentCheck bishopMoves(Square bishopSquare, Board board) {

        final var list = new ArrayList<Square>();

        final var movement1 =
                FindSquare.findSquare(Piece.BISHOP, PieceMovement.PREVIOUS_LETTER_NEXT_NUMBER, bishopSquare, board);
        final var movement2 =
                FindSquare.findSquare(Piece.BISHOP, PieceMovement.NEXT_LETTER_NEXT_NUMBER, bishopSquare, board);
        final var movement3 =
                FindSquare.findSquare(Piece.BISHOP, PieceMovement.PREVIOUS_LETTER_PREVIOUS_NUMBER, bishopSquare, board);
        final var movement4 =
                FindSquare.findSquare(Piece.BISHOP, PieceMovement.NEXT_LETTER_PREVIOUS_NUMBER, bishopSquare, board);

        final var movements = List.of(movement1, movement2, movement3, movement4);
        movements.forEach(movement -> list.addAll(movement.squares()));
        final var opponentsKingInCheck =
                movements.stream().anyMatch(movementContact -> movementContact.contact() == Contact.OPPONENT_KING);

        return new MovementAttackOpponentCheck(list, Collections.emptyList(), opponentsKingInCheck);
    }
}
