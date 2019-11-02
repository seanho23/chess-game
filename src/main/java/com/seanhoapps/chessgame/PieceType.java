package com.seanhoapps.chessgame;

public enum PieceType {
	KING, QUEEN, BISHOP, KNIGHT, ROOK, PAWN;
	
	public boolean isKing() {
		return this == this.KING;
	}
	
	public boolean isRook() {
		return this == this.ROOK;
	}
	
	public boolean isPawn() {
		return this == this.PAWN;
	}
}
