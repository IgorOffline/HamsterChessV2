package igoroffline.practice.hamsterchessv2.main.piece;

import igoroffline.practice.hamsterchessv2.main.board.Board;
import igoroffline.practice.hamsterchessv2.main.board.Number2;
import igoroffline.practice.hamsterchessv2.main.board.Piece;
import igoroffline.practice.hamsterchessv2.main.board.PieceColor;
import igoroffline.practice.hamsterchessv2.main.board.Square;
import igoroffline.practice.hamsterchessv2.main.piece.movement.Contact;
import igoroffline.practice.hamsterchessv2.main.piece.movement.FindSquare;
import igoroffline.practice.hamsterchessv2.main.piece.movement.MovementAttackOpponentCheck;
import igoroffline.practice.hamsterchessv2.main.piece.movement.MovementContact;
import igoroffline.practice.hamsterchessv2.main.piece.movement.PieceMovement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Pawn {

    public static MovementAttackOpponentCheck pawnMoves(Square pawnSquare, Board board) {

        final var movementSquares = new ArrayList<Square>();
        final var attackSquares = new ArrayList<Square>();

        final var movement1 = FindSquare.findSquare(Piece.PAWN, PieceMovement.PAWN_MOVE_ONE_SQUARE, pawnSquare, board);
        Optional<MovementContact> movement2 = Optional.empty();

        final var white2 = pawnSquare.getPieceColor() == PieceColor.WHITE && pawnSquare.getNumber() == Number2.N2;
        final var black7 = pawnSquare.getPieceColor() == PieceColor.BLACK && pawnSquare.getNumber() == Number2.N7;
        final var white2OrBlack7 = white2 || black7;

        if (white2OrBlack7 && movement1.contact() == Contact.NONE) {
            movement2 = Optional.of(
                    FindSquare.findSquare(Piece.PAWN, PieceMovement.PAWN_MOVE_TWO_SQUARES, pawnSquare, board));
        }

        final var attack1 =
                FindSquare.findSquare(Piece.PAWN, PieceMovement.PAWN_ATTACK_PREVIOUS_LETTER, pawnSquare, board);
        final var attack2 = FindSquare.findSquare(Piece.PAWN, PieceMovement.PAWN_ATTACK_NEXT_LETTER, pawnSquare, board);

        final var movements =
                movement2.map(contact -> List.of(movement1, contact)).orElseGet(() -> List.of(movement1));
        movements.stream()
                .filter(movement -> movement.contact() == Contact.NONE)
                .forEach(movement -> movementSquares.addAll(movement.squares()));

        final var attacks = List.of(attack1, attack2);
        attacks.forEach(attack -> attackSquares.addAll(attack.squares()));
        attacks.stream()
                .filter(attack -> attack.contact() == Contact.OPPONENT_NON_KING)
                .forEach(attack -> movementSquares.addAll(attack.squares()));

        final var opponentsKingInCheck =
                attacks.stream().anyMatch(movementContact -> movementContact.contact() == Contact.OPPONENT_KING);

        return new MovementAttackOpponentCheck(movementSquares, attackSquares, opponentsKingInCheck);
    }
}
