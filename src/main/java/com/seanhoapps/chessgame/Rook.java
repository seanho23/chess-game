package com.seanhoapps.chessgame;

public class Rook extends Piece {
	public Rook(ChessColor color) {
		super(PieceType.ROOK, color);
	}
	
	@Override
	public boolean isValidMove(Board board, int newRow, int newCol) {
		return false;
	}
}
