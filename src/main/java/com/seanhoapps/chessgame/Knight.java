package com.seanhoapps.chessgame;

public class Knight extends Piece {
	public Knight(ChessColor color) {
		super(PieceType.KNIGHT, color);
	}
	
	@Override
	public boolean isValidMove(Board board, int newRow, int newCol) {
		return false;
	}
	
	// King is already K
	@Override
	public char getAbbreviation() {		
		if (color == ChessColor.WHITE) {
			return 'N';
		}
		else {
			return 'n';
		}
	}
	
}
