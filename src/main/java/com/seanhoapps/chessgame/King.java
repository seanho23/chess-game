package com.seanhoapps.chessgame;

public class King extends Piece {
	public King(ChessColor color) {
		super(PieceType.KING, color);
	}
	
	@Override
	public boolean isValidMove(Board board, int newRow, int newCol) {
		return false;
	}
	
}
