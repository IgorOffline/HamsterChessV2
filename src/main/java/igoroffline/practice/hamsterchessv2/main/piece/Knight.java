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

public class Knight {

    public static MovementAttackOpponentCheck knightMoves(Square knightSquare, Board board) {

        final var list = new ArrayList<Square>();

        final var movement1 = FindSquare.findSquare(Piece.KNIGHT, PieceMovement.PP_LETTER_NEXT_NUMBER, knightSquare, board);
        final var movement2 = FindSquare.findSquare(Piece.KNIGHT, PieceMovement.NN_NUMBER_PREVIOUS_LETTER, knightSquare, board);
        final var movement3 = FindSquare.findSquare(Piece.KNIGHT, PieceMovement.NN_NUMBER_NEXT_LETTER, knightSquare, board);
        final var movement4 = FindSquare.findSquare(Piece.KNIGHT, PieceMovement.NN_LETTER_NEXT_NUMBER, knightSquare, board);
        final var movement5 = FindSquare.findSquare(Piece.KNIGHT, PieceMovement.PP_LETTER_PREVIOUS_NUMBER, knightSquare, board);
        final var movement6 = FindSquare.findSquare(Piece.KNIGHT, PieceMovement.PP_NUMBER_PREVIOUS_LETTER, knightSquare, board);
        final var movement7 = FindSquare.findSquare(Piece.KNIGHT, PieceMovement.PP_NUMBER_NEXT_LETTER, knightSquare, board);
        final var movement8 = FindSquare.findSquare(Piece.KNIGHT, PieceMovement.NN_LETTER_PREVIOUS_NUMBER, knightSquare, board);

        final var movements = List.of(movement1, movement2, movement3, movement4,
                movement5, movement6, movement7, movement8);
        movements.forEach(movement -> list.addAll(movement.squares()));
        final var opponentsKingInCheck = movements.stream().anyMatch(movementContact ->
                movementContact.contact() == Contact.OPPONENT_KING);

        return new MovementAttackOpponentCheck(list, Collections.emptyList(), opponentsKingInCheck);
    }
}
