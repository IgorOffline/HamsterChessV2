package igoroffline.practice.hamsterchessv2.main.legal;

import igoroffline.practice.hamsterchessv2.main.board.Piece;
import igoroffline.practice.hamsterchessv2.main.board.PieceColor;
import igoroffline.practice.hamsterchessv2.main.game.GameMaster;
import igoroffline.practice.hamsterchessv2.main.piece.Bishop;
import igoroffline.practice.hamsterchessv2.main.piece.Knight;
import igoroffline.practice.hamsterchessv2.main.piece.Pawn;
import igoroffline.practice.hamsterchessv2.main.piece.Rook;
import igoroffline.practice.hamsterchessv2.main.util.Messages;

public class Pruning {

    public static void prune(GameMaster gameMaster, PieceColor pieceColor) {

        final var oppositePieceColor = pieceColor == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                final var sq = gameMaster.getBoard().getBoard()[row][col];
                if (sq.getPieceColor() == oppositePieceColor && sq.getPiece() == Piece.ROOK) {
                    final var moves = Rook.rookMoves(sq, gameMaster.getBoard());
                    if (moves.opponentsKingInCheck()) {
                        kingStillInCheck(gameMaster, pieceColor);
                    }
                } else if (sq.getPieceColor() == oppositePieceColor && sq.getPiece() == Piece.BISHOP) {
                    final var moves = Bishop.bishopMoves(sq, gameMaster.getBoard());
                    if (moves.opponentsKingInCheck()) {
                        kingStillInCheck(gameMaster, pieceColor);
                    }
                } else if (sq.getPieceColor() == oppositePieceColor && sq.getPiece() == Piece.KNIGHT) {
                    final var moves = Knight.knightMoves(sq, gameMaster.getBoard());
                    if (moves.opponentsKingInCheck()) {
                        kingStillInCheck(gameMaster, pieceColor);
                    }
                } else if (sq.getPieceColor() == oppositePieceColor && sq.getPiece() == Piece.PAWN) {
                    final var moves = Pawn.pawnMoves(sq, gameMaster.getBoard());
                    if (moves.opponentsKingInCheck()) {
                        kingStillInCheck(gameMaster, pieceColor);
                    }
                }
            }
        }
    }

    private static void kingStillInCheck(GameMaster gameMaster, PieceColor pieceColor) {
        switch (pieceColor) {
            case WHITE -> gameMaster.setWhiteKingInCheck(true);
            case BLACK -> gameMaster.setBlackKingInCheck(true);
            default -> throw new IllegalArgumentException(Messages.UNKNOWN_PIECE_COLOR);
        }
    }
}
