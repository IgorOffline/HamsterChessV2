package igoroffline.practice.hamsterchessv2.main.frontend;

import com.badlogic.gdx.graphics.Texture;
import igoroffline.practice.hamsterchessv2.main.board.Piece;
import igoroffline.practice.hamsterchessv2.main.board.PieceColor;
import igoroffline.practice.hamsterchessv2.main.board.Square;
import lombok.Getter;

@Getter
public class Textures {

    private final Texture textureKingWhite;
    private final Texture textureKingBlack;
    private final Texture textureRookWhite;
    private final Texture textureRookBlack;
    private final Texture textureBishopWhite;
    private final Texture textureBishopBlack;
    private final Texture textureKnightWhite;
    private final Texture textureKnightBlack;
    private final Texture texturePawnWhite;
    private final Texture texturePawnBlack;
    private final Texture textureSelectedSquarePointer;

    public Textures() {
        textureKingWhite = new Texture("assets/Chess_klt45_p.png");
        textureKingBlack = new Texture("assets/Chess_kdt45_p.png");
        textureRookWhite = new Texture("assets/Chess_rlt45_p.png");
        textureRookBlack = new Texture("assets/Chess_rdt45_p.png");
        textureBishopWhite = new Texture("assets/Chess_blt45_p.png");
        textureBishopBlack = new Texture("assets/Chess_bdt45_p.png");
        textureKnightWhite = new Texture("assets/Chess_nlt45_p.png");
        textureKnightBlack = new Texture("assets/Chess_ndt45_p.png");
        texturePawnWhite = new Texture("assets/Chess_plt45_p.png");
        texturePawnBlack = new Texture("assets/Chess_pdt45_p.png");
        textureSelectedSquarePointer = new Texture("assets/openmoji1F449yellow_p.png");
    }

    public Texture getPieceTexture(Square square) {

        Texture t = null;

        if (square.getPiece() == Piece.KING && square.getPieceColor() == PieceColor.WHITE) {
            t = textureKingWhite;
        } else if (square.getPiece() == Piece.KING && square.getPieceColor() == PieceColor.BLACK) {
            t = textureKingBlack;
        } else if (square.getPiece() == Piece.ROOK && square.getPieceColor() == PieceColor.WHITE) {
            t = textureRookWhite;
        } else if (square.getPiece() == Piece.ROOK && square.getPieceColor() == PieceColor.BLACK) {
            t = textureRookBlack;
        } else if (square.getPiece() == Piece.BISHOP && square.getPieceColor() == PieceColor.WHITE) {
            t = textureBishopWhite;
        } else if (square.getPiece() == Piece.BISHOP && square.getPieceColor() == PieceColor.BLACK) {
            t = textureBishopBlack;
        } else if (square.getPiece() == Piece.KNIGHT && square.getPieceColor() == PieceColor.WHITE) {
            t = textureKnightWhite;
        } else if (square.getPiece() == Piece.KNIGHT && square.getPieceColor() == PieceColor.BLACK) {
            t = textureKnightBlack;
        } else if (square.getPiece() == Piece.PAWN && square.getPieceColor() == PieceColor.WHITE) {
            t = texturePawnWhite;
        } else if (square.getPiece() == Piece.PAWN && square.getPieceColor() == PieceColor.BLACK) {
            t = texturePawnBlack;
        }

        return t;
    }

    public void dispose() {
        textureKingWhite.dispose();
        textureKingBlack.dispose();
        textureRookWhite.dispose();
        textureRookBlack.dispose();
        textureBishopWhite.dispose();
        textureBishopBlack.dispose();
        textureKnightWhite.dispose();
        textureKnightBlack.dispose();
        texturePawnWhite.dispose();
        texturePawnBlack.dispose();
        textureSelectedSquarePointer.dispose();
    }
}
