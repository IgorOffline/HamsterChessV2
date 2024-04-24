package igoroffline.practice.hamsterchessv2.main.piece.movement;

import igoroffline.practice.hamsterchessv2.main.board.Square;
import java.util.List;

public record MovementAttackOpponentCheck(
        List<Square> movementSquares, List<Square> attackSquares, boolean opponentsKingInCheck) {}
