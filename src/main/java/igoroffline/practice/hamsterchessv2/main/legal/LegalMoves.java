package igoroffline.practice.hamsterchessv2.main.legal;

import igoroffline.practice.hamsterchessv2.main.board.Piece;
import igoroffline.practice.hamsterchessv2.main.board.PieceColor;
import igoroffline.practice.hamsterchessv2.main.board.Square;
import igoroffline.practice.hamsterchessv2.main.game.GameMaster;
import igoroffline.practice.hamsterchessv2.main.piece.Bishop;
import igoroffline.practice.hamsterchessv2.main.piece.King;
import igoroffline.practice.hamsterchessv2.main.piece.Knight;
import igoroffline.practice.hamsterchessv2.main.piece.Pawn;
import igoroffline.practice.hamsterchessv2.main.piece.Rook;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class LegalMoves {

    private Map<Square, List<Square>> legalMoves = new HashMap<>();

    public void calculate(GameMaster gameMaster) {

        final var phase1LegalMoves = new HashMap<Square, List<Square>>();
        final var phase2LegalMoves = new HashMap<Square, List<Square>>();

        final var pieceColor = gameMaster.isWhiteToMove() ? PieceColor.WHITE : PieceColor.BLACK;
        final var oppositePieceColor = pieceColor == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;

        gameMaster.setWhiteKingInCheck(false);
        gameMaster.setBlackKingInCheck(false);

        Optional<Square> king = Optional.empty();

        for (final var square : gameMaster.getBoard().getBoard()) {
            if (square.getPiece() == Piece.KING && square.getPieceColor() == pieceColor) {
                king = Optional.of(square);
                break;
            }
        }

        final var kingLegalMoves = King.kingMoves(king.orElseThrow(), gameMaster.getBoard());

        gameMaster.getBoard().getBoard().forEach(boardSquare -> {
            if (boardSquare.getPiece() == Piece.ROOK && boardSquare.getPieceColor() == pieceColor) {
                final var rookMoves = Rook.rookMoves(boardSquare, gameMaster.getBoard());
                phase1LegalMoves.put(boardSquare, rookMoves.movementSquares());
                kingLegalMoves.removeIf(square ->
                        square.getLetter() == boardSquare.getLetter() && square.getNumber() == boardSquare.getNumber());
            } else if (boardSquare.getPiece() == Piece.ROOK && boardSquare.getPieceColor() == oppositePieceColor) {
                final var oppositeRookMoves = Rook.rookMoves(boardSquare, gameMaster.getBoard());
                kingLegalMoves.removeAll(oppositeRookMoves.movementSquares());
                if (oppositeRookMoves.opponentsKingInCheck()) {
                    gameMaster.setWhiteKingInCheck(pieceColor == PieceColor.WHITE);
                    gameMaster.setBlackKingInCheck(pieceColor == PieceColor.BLACK);
                }
            } else if (boardSquare.getPiece() == Piece.BISHOP && boardSquare.getPieceColor() == pieceColor) {
                final var bishopMoves = Bishop.bishopMoves(boardSquare, gameMaster.getBoard());
                phase1LegalMoves.put(boardSquare, bishopMoves.movementSquares());
                kingLegalMoves.removeIf(square ->
                        square.getLetter() == boardSquare.getLetter() && square.getNumber() == boardSquare.getNumber());
            } else if (boardSquare.getPiece() == Piece.BISHOP && boardSquare.getPieceColor() == oppositePieceColor) {
                final var oppositeBishopMoves = Bishop.bishopMoves(boardSquare, gameMaster.getBoard());
                kingLegalMoves.removeAll(oppositeBishopMoves.movementSquares());
                if (oppositeBishopMoves.opponentsKingInCheck()) {
                    gameMaster.setWhiteKingInCheck(pieceColor == PieceColor.WHITE);
                    gameMaster.setBlackKingInCheck(pieceColor == PieceColor.BLACK);
                }
            } else if (boardSquare.getPiece() == Piece.KNIGHT && boardSquare.getPieceColor() == pieceColor) {
                final var knightMoves = Knight.knightMoves(boardSquare, gameMaster.getBoard());
                phase1LegalMoves.put(boardSquare, knightMoves.movementSquares());
                kingLegalMoves.removeIf(square ->
                        square.getLetter() == boardSquare.getLetter() && square.getNumber() == boardSquare.getNumber());
            } else if (boardSquare.getPiece() == Piece.KNIGHT && boardSquare.getPieceColor() == oppositePieceColor) {
                final var oppositeKnightMoves = Knight.knightMoves(boardSquare, gameMaster.getBoard());
                kingLegalMoves.removeAll(oppositeKnightMoves.movementSquares());
                if (oppositeKnightMoves.opponentsKingInCheck()) {
                    gameMaster.setWhiteKingInCheck(pieceColor == PieceColor.WHITE);
                    gameMaster.setBlackKingInCheck(pieceColor == PieceColor.BLACK);
                }
            } else if (boardSquare.getPiece() == Piece.PAWN && boardSquare.getPieceColor() == pieceColor) {
                final var pawnMoves = Pawn.pawnMoves(boardSquare, gameMaster.getBoard());
                phase1LegalMoves.put(boardSquare, pawnMoves.movementSquares());
                kingLegalMoves.removeIf(square ->
                        square.getLetter() == boardSquare.getLetter() && square.getNumber() == boardSquare.getNumber());
            } else if (boardSquare.getPiece() == Piece.PAWN && boardSquare.getPieceColor() == oppositePieceColor) {
                final var oppositePawnMoves = Pawn.pawnMoves(boardSquare, gameMaster.getBoard());
                kingLegalMoves.removeAll(oppositePawnMoves.attackSquares());
                if (oppositePawnMoves.opponentsKingInCheck()) {
                    gameMaster.setWhiteKingInCheck(pieceColor == PieceColor.WHITE);
                    gameMaster.setBlackKingInCheck(pieceColor == PieceColor.BLACK);
                }
            }
        });

        phase1LegalMoves.put(king.get(), kingLegalMoves);

        if (!gameMaster.isWhiteKingInCheck() && !gameMaster.isBlackKingInCheck()) {
            legalMoves = phase1LegalMoves;
        } else {
            phase1LegalMoves.keySet().forEach(piece -> {
                final var pieceLegalMoves = phase1LegalMoves.get(piece);
                final var prunedMoves = pruneMoves(gameMaster, pieceLegalMoves, piece);
                phase2LegalMoves.put(piece, prunedMoves);
            });

            legalMoves = phase2LegalMoves;

            checkmateCheck(gameMaster);
        }
    }

    private List<Square> pruneMoves(GameMaster gameMaster, List<Square> pieceLegalMoves, Square piece) {

        final var prunedMoves = new ArrayList<Square>();

        pieceLegalMoves.forEach(legalMove -> {
            final var newBoard = gameMaster.getBoard().deepCopy();
            final var newLegalMoves = new LegalMoves();
            final var newGameMaster = new GameMaster(newBoard, newLegalMoves);
            final var pieceNewBoard = newBoard.getBoard().stream()
                    .filter(square ->
                            square.getLetter() == piece.getLetter() && square.getNumber() == piece.getNumber())
                    .findFirst();
            // assert
            pieceNewBoard.orElseThrow();
            newGameMaster.setFromSquare(pieceNewBoard);
            final var toSquareNewBoard = newBoard.getBoard().stream()
                    .filter(square ->
                            square.getLetter() == legalMove.getLetter() && square.getNumber() == legalMove.getNumber())
                    .findFirst();
            // assert
            toSquareNewBoard.orElseThrow();
            newGameMaster.setToSquare(toSquareNewBoard);
            newGameMaster.move();
            Pruning.prune(newGameMaster, piece.getPieceColor());

            final var pruneWhite = piece.getPieceColor() == PieceColor.WHITE && !newGameMaster.isWhiteKingInCheck();
            final var pruneBlack = piece.getPieceColor() == PieceColor.BLACK && !newGameMaster.isBlackKingInCheck();

            if (pruneWhite || pruneBlack) {
                prunedMoves.add(toSquareNewBoard.get());
            }
        });

        return prunedMoves;
    }

    private void checkmateCheck(GameMaster gameMaster) {

        var legalMovesCounter = 0;

        for (final var piecesLegalMoves : legalMoves.values()) {
            legalMovesCounter += piecesLegalMoves.size();
        }

        if (legalMovesCounter == 0) {
            if (gameMaster.isWhiteKingInCheck()) {
                gameMaster.setWhiteKingCheckmated(true);
            } else {
                gameMaster.setBlackKingCheckmated(true);
            }
        }
    }
}
