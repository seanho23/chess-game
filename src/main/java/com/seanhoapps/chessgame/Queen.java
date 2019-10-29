package com.seanhoapps.chessgame;

public class Queen extends Piece {
	public Queen(ChessColor color) {
		super(PieceType.QUEEN, color);
	}
	
	@Override
	public boolean isValidMove(Board board, int newRow, int newCol) {
		return false;
	}

}
