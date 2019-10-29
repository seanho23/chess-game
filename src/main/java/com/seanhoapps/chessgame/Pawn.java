package com.seanhoapps.chessgame;

public class Pawn extends Piece {
	public Pawn(ChessColor color) {
		super(PieceType.PAWN, color);
	}
	
	@Override
	public boolean isValidMove(Board board, int newRow, int newCol) {
		return false;
	}

}
