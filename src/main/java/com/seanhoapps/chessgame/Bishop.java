package com.seanhoapps.chessgame;

public class Bishop extends Piece {
	public Bishop(ChessColor color) {
		super(PieceType.BISHOP, color);
	}
	
	@Override
	public boolean isValidMove(Board board, int newRow, int newCol) {
		return false;
	}

}
