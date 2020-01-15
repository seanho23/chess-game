package com.seanhoapps.chessgame.pieces;

public enum PieceType {
	
	KING, QUEEN, BISHOP, KNIGHT, ROOK, PAWN;
	
	public boolean isKing() {
		return this == KING;
	}
	
	public boolean isRook() {
		return this == ROOK;
	}
	
	public boolean isPawn() {
		return this == PAWN;
	}
}
