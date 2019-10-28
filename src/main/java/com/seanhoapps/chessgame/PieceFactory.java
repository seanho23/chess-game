package com.seanhoapps.chessgame;

public class PieceFactory {
	public static Piece getPiece(PieceType type, PieceColor color) {
		switch (type) {
			case KING:
				return new King(color);
			case QUEEN:
				return new Queen(color);
			case BISHOP:
				return new Bishop(color);
			case KNIGHT:
				return new Knight(color);
			case ROOK:
				return new Rook(color);
			case PAWN:
				return new Pawn(color);
			default:
				return null;
		}
	}
}
