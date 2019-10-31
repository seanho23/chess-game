package com.seanhoapps.chessgame;

public class Rook extends Piece {
	public Rook(ChessColor color) {
		super(PieceType.ROOK, color);
	}
	
	// Rook moves within same row and column
	@Override
	public boolean isPossibleMove(Position startPosition, Position endPosition) {
		if (endPosition.equals(startPosition)) {
			return false;
		}
		
		if (startPosition.getRow() == endPosition.getRow() || startPosition.getCol() == endPosition.getCol()) {
			return true;
		}
		
		return false;
	}
}
