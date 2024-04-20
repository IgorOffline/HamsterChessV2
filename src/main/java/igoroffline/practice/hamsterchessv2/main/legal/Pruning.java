package igoroffline.practice.hamsterchessv2.main.legal;

import igoroffline.practice.hamsterchessv2.main.GameMaster;
import igoroffline.practice.hamsterchessv2.main.Messages;
import igoroffline.practice.hamsterchessv2.main.board.Piece;
import igoroffline.practice.hamsterchessv2.main.board.PieceColor;
import igoroffline.practice.hamsterchessv2.main.piece.Rook;

public class Pruning {

    public static void prune(GameMaster gameMaster, PieceColor pieceColor) {

        final var oppositePieceColor = pieceColor == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;

        gameMaster.getBoard().getBoard().forEach(square -> {
            if (square.getPieceColor() == oppositePieceColor && square.getPiece() == Piece.ROOK) {
                final var rookMoves = Rook.rookMoves(square, gameMaster.getBoard());
                if (rookMoves.opponentsKingInCheck()) {
                    kingStillInCheck(gameMaster, pieceColor);
                }
            }
        });
    }

    private static void kingStillInCheck(GameMaster gameMaster, PieceColor pieceColor) {
        switch (pieceColor) {
            case WHITE -> gameMaster.setWhiteKingInCheck(true);
            case BLACK -> gameMaster.setBlackKingInCheck(true);
            default -> throw new IllegalArgumentException(Messages.UNKNOWN_PIECE_COLOR);
        }
    }
}
